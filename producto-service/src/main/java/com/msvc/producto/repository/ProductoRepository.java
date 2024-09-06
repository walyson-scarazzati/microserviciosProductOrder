package com.msvc.producto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.msvc.producto.model.Producto;

public interface ProductoRepository extends MongoRepository<Producto, String> {

}
