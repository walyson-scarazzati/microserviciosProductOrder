package com.msvc.order.service;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.msvc.order.config.rabbitmq.Producer;
import com.msvc.order.dto.InventarioResponse;
import com.msvc.order.dto.OrderLineItemsDto;
import com.msvc.order.dto.OrderRequest;
import com.msvc.order.event.OrderPlacedEvent;
import com.msvc.order.model.Order;
import com.msvc.order.model.OrderLineItems;
import com.msvc.order.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class OrderService {
	
	@Autowired
	private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
    @Autowired
    private Tracer tracer;

    @Autowired
    private Producer producer;
	 
    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setNumeroPedido(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);


        List<String> codigoSku = order.getOrderLineItems().stream()
                        .map(OrderLineItems::getCodigoSku)
                                .collect(Collectors.toList());

        System.out.println("Codigos sku : " + codigoSku);

        Span inventarioServiceLookup = tracer.nextSpan().name("InventarioServiceLookup");

        try(Tracer.SpanInScope isLookup = tracer.withSpan(inventarioServiceLookup.start())){
            inventarioServiceLookup.tag("call","inventario-service");

            InventarioResponse[] inventarioResponseArray = webClientBuilder.build().get()
                    .uri("http://inventario-service/api/inventario",uriBuilder -> uriBuilder.queryParam("codigoSku",codigoSku).build())
                    .retrieve()
                    .bodyToMono(InventarioResponse[].class)
                    .block();

            boolean allProductosInStock = Arrays.stream(inventarioResponseArray)
                    .allMatch(InventarioResponse::isInStock);

            if(allProductosInStock){
                orderRepository.save(order);
                enviarMensajeConRabbitMQ("Notificacion con RabbitMQ, Pedido ordenado con exito");
                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getNumeroPedido()));
                return "Pedido ordenado con exito";
            }
            else{
                throw new IllegalArgumentException("El producto no esta en stock");
            }
        }finally {
            inventarioServiceLookup.end();
        }
    }

    private void enviarMensajeConRabbitMQ(String message){
        log.info("El mensaje '{}' ha sido enviado con exito",message);
        producer.send(message);
    }

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrecio(orderLineItemsDto.getPrecio());
		orderLineItems.setCantidad(orderLineItemsDto.getCantidad());
		orderLineItems.setCodigoSku(orderLineItemsDto.getCodigoSku());
		return orderLineItems;
	}

}
