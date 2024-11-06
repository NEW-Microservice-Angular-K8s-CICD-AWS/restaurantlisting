package com.codedecode.restaurantlisting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RestaurantDTO {
	private int id;
	private String name;
	private String address;
	private String city;
	private String restaurantDescription;
}
