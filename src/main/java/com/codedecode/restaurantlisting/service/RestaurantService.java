package com.codedecode.restaurantlisting.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codedecode.restaurantlisting.dto.RestaurantDTO;
import com.codedecode.restaurantlisting.entity.Restaurant;
import com.codedecode.restaurantlisting.repo.RestaurantRepo;

@Service
public class RestaurantService {

	@Autowired
	RestaurantRepo restaurantRepo;

	@Autowired
	ModelMapper modelMapper;

	public List<RestaurantDTO> findAllRestaurants() {
		List<Restaurant> restaurants = restaurantRepo.findAll();
		if (restaurants != null && !restaurants.isEmpty()) {
			List<RestaurantDTO> restaurantDTOList = restaurants.stream()
					.map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class)).collect(Collectors.toList());
			return restaurantDTOList;
		}
		return null;
	}

	public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {
		Restaurant savedRestaurant = restaurantRepo.save(modelMapper.map(restaurantDTO, Restaurant.class));
		return modelMapper.map(savedRestaurant, RestaurantDTO.class);
	}

	public RestaurantDTO fetchRestaurantById(Integer id) {
		Optional<Restaurant> restaurant = restaurantRepo.findById(id);
		if (restaurant.isPresent()) {
			return modelMapper.map(restaurant.get(), RestaurantDTO.class);
		}
		return null;
	}
}
