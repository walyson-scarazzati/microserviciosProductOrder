package com.msvc.inventario.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msvc.inventario.dto.InventarioResponse;
import com.msvc.inventario.repository.InventarioRepository;



@Service
public class InventarioService {
	
	@Autowired
	private InventarioRepository inventarioRepository;
	
	@Transactional(readOnly = true)
    public boolean isInStock2(String codigoSku) {
	return inventarioRepository.findByCodigoSku(codigoSku).isPresent();
	}
	
	@Transactional(readOnly = true)
    public List<InventarioResponse> isInStock3(List<String> codigoSku) {
        System.out.println("Received SKU list: " + codigoSku);
        return inventarioRepository.findByCodigoSkuIn(codigoSku).stream()
                .map(inventario -> 
                     InventarioResponse.builder()
                        .codigoSku(inventario.getCodigoSku())
                        .inStock(inventario.getCantidad() > 0)
                        .build()
                ).collect(Collectors.toList());
        
      
    }
	
    @Transactional(readOnly = true)
    public List<InventarioResponse> isInStock(List<String> codigoSku) {
        System.out.println("Received SKU list: " + codigoSku);
        List<InventarioResponse> responses = inventarioRepository.findByCodigoSkuIn(codigoSku).stream()
                .map(inventario -> {
                    System.out.println("Processing SKU: " + inventario.getCodigoSku());
                    return InventarioResponse.builder()
                        .codigoSku(inventario.getCodigoSku())
                        .inStock(inventario.getCantidad() > 0)
                        .build();
                }).collect(Collectors.toList());
        
        System.out.println("Response: " + responses);
        return responses;
    }

}
