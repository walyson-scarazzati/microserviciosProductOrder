package com.message.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class MessageServiceApplication {

	@RabbitListener(queues = {"${msvc.queue.name}"})
	public void recibirMensajeConRabbitMQ(String mensaje){
		log.info("Mensaje recibido {}",mensaje);
	}

	public static void main(String[] args) {
		SpringApplication.run(MessageServiceApplication.class, args);
	}

}
