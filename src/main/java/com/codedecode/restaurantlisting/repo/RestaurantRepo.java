package com.codedecode.restaurantlisting.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codedecode.restaurantlisting.entity.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant, Integer>{

}
