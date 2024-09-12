package com.msvc.order.model;

import java.util.List;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String numeroPedido;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderLineItems> orderLineItems;

}
