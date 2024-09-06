package com.msvc.order.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {

	private Long id;
	private String codigoSku;
	private BigDecimal precio;
	private Integer cantidad;

}
