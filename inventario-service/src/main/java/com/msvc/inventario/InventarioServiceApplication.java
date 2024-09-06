package com.msvc.inventario;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.msvc.inventario.model.Inventario;
import com.msvc.inventario.repository.InventarioRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class InventarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioServiceApplication.class, args);
	}
	
//	Run only first time
//	@Bean
//	public CommandLineRunner loadData(InventarioRepository inventarioRepository) {
//		return args ->{
//			Inventario inventario = new Inventario();
//			inventario.setCodigoSku("Iphone_12");
//			inventario.setCantidad(100);
//			
//			Inventario inventario2 = new Inventario();
//			inventario2.setCodigoSku("Iphone_12_blue");
//			inventario2.setCantidad(0);
//			
//			inventarioRepository.save(inventario);
//			inventarioRepository.save(inventario2);
//		};
//	}

}
