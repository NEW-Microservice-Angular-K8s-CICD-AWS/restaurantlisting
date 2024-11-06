package com.codedecode.restaurantlisting.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.codedecode.restaurantlisting.dto.RestaurantDTO;
import com.codedecode.restaurantlisting.service.RestaurantService;

public class RestaurantControllerTest {

	@InjectMocks
	RestaurantController restaurantController;

	@Mock
	RestaurantService restaurantService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this); // in order for Mock and InjectMocks annotations to take effect, you need to
											// call MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFetchAllRestaurants() {
		// Mock the service behavior
		List<RestaurantDTO> mockRestaurants = Arrays.asList(
				new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"),
				new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Desc 2"));
		when(restaurantService.findAllRestaurants()).thenReturn(mockRestaurants);

		// Call the controller method
		ResponseEntity<List<RestaurantDTO>> response = restaurantController.fetchAllRestaurants();

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockRestaurants, response.getBody());

		// Verify that the service method was called
		verify(restaurantService, times(1)).findAllRestaurants();
	}

	@Test
	public void testSaveRestaurant() {
		// Create a mock restaurant to be saved
		RestaurantDTO mockRestaurant = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");

		// Mock the service behavior
		when(restaurantService.addRestaurantInDB(mockRestaurant)).thenReturn(mockRestaurant);

		// Call the controller method
		ResponseEntity<RestaurantDTO> response = restaurantController.saveRestaurant(mockRestaurant);

		// Verify the response
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(mockRestaurant, response.getBody());

		// Verify that the service method was called
		verify(restaurantService, times(1)).addRestaurantInDB(mockRestaurant);
	}

	@Test
	public void testFetchRestaurantById_Existing() {
		// Create a mock restaurant to be saved
		RestaurantDTO mockRestaurant1 = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");
		RestaurantDTO mockRestaurant2 = new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Desc 2");

		// Mock the service behavior
		when(restaurantService.fetchRestaurantById(1)).thenReturn(mockRestaurant1);
		when(restaurantService.fetchRestaurantById(2)).thenReturn(mockRestaurant2);

		// Call the controller method
		ResponseEntity<RestaurantDTO> response1 = restaurantController.fetchRestaurantById(1);
		ResponseEntity<RestaurantDTO> response2 = restaurantController.fetchRestaurantById(2);

		assertEquals(HttpStatus.OK, response1.getStatusCode());
		assertEquals(mockRestaurant1, response1.getBody());
		assertEquals(HttpStatus.OK, response2.getStatusCode());
		assertEquals(mockRestaurant2, response2.getBody());

		// Verify that the service method was called
		verify(restaurantService, times(1)).fetchRestaurantById(1);
		verify(restaurantService, times(1)).fetchRestaurantById(2);
	}

	@Test
	public void testFetchRestaurantById_NonExisting() {
		// Create a mock restaurant to be saved
		RestaurantDTO mockRestaurant1 = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");
		RestaurantDTO mockRestaurant2 = new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Desc 2");

		// Mock the service behavior
		when(restaurantService.fetchRestaurantById(1)).thenReturn(mockRestaurant1);
		when(restaurantService.fetchRestaurantById(2)).thenReturn(mockRestaurant2);

		// Call the controller method
		ResponseEntity<RestaurantDTO> response = restaurantController.fetchRestaurantById(3);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(null, response.getBody());

		// Verify that the service method was called
		verify(restaurantService, times(1)).fetchRestaurantById(3);
	}
}
