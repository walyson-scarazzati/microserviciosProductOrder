package com.msvc.inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msvc.inventario.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long>{
	
	List<Inventario> findByCodigoSkuIn(List<String> codigoSku);
}
