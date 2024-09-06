package com.msvc.producto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msvc.producto.dto.ProductoRequest;
import com.msvc.producto.dto.ProductoResponse;
import com.msvc.producto.model.Producto;
import com.msvc.producto.repository.ProductoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductoService {

	@Autowired
	private ProductoRepository productoRepository;

	public void createProducto(ProductoRequest productoRequest) {
		Producto producto = Producto.builder().nombre(productoRequest.getNombre())
				.descripcion(productoRequest.getDescripcion()).precio(productoRequest.getPrecio()).build();

		productoRepository.save(producto);
		log.info("Producto{} ha sido guardado con exito", producto.getId());
	}

	public List<ProductoResponse> getAllProductos() {
		List<Producto> productos = productoRepository.findAll();
		return productos.stream().map(this::mapToProductoResponse).collect(Collectors.toList());
	}

	private ProductoResponse mapToProductoResponse(Producto producto) {
		return ProductoResponse.builder().id(producto.getId()).nombre(producto.getNombre())
				.descripcion(producto.getDescripcion()).precio(producto.getPrecio()).build();
	}
}
