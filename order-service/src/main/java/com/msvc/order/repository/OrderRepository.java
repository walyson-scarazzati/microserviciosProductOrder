package com.msvc.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msvc.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
