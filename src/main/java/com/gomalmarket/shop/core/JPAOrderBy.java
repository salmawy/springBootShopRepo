package com.gomalmarket.shop.core;

import com.gomalmarket.shop.core.Enum.JPAOrderByEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class JPAOrderBy {

	private String columnName;
	private JPAOrderByEnum type;
	
	
}
