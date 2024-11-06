package com.codedecode.restaurantlisting.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.codedecode.restaurantlisting.dto.RestaurantDTO;
import com.codedecode.restaurantlisting.entity.Restaurant;
import com.codedecode.restaurantlisting.repo.RestaurantRepo;

public class RestaurantServiceTest {

	@InjectMocks
	RestaurantService restaurantService;

	@Mock
	RestaurantRepo restaurantRepo;

	@Mock
	ModelMapper modelMapper;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this); // in order for Mock and InjectMocks annotations to take effect, you need to
											// call MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindAllRestaurants() {
		// Create mock restaurants
		List<Restaurant> mockRestaurants = Arrays.asList(
				new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"),
				new Restaurant(2, "Restaurant 2", "Address 2", "city 2", "Desc 2"));
		when(restaurantRepo.findAll()).thenReturn(mockRestaurants);
		when(modelMapper.map(new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"), RestaurantDTO.class))
				.thenReturn(new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"));
		when(modelMapper.map(new Restaurant(2, "Restaurant 2", "Address 2", "city 2", "Desc 2"), RestaurantDTO.class))
				.thenReturn(new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Desc 2"));

		// Call the service method
		List<RestaurantDTO> restaurantDTOList = restaurantService.findAllRestaurants();

		// Verify the result
		assertEquals(mockRestaurants.size(), restaurantDTOList.size());

		for (int i = 0; i < mockRestaurants.size(); i++) {
			RestaurantDTO expectedDTO = modelMapper.map(mockRestaurants.get(i), RestaurantDTO.class);
			assertEquals(expectedDTO, restaurantDTOList.get(i));
		}

		// Verify that the repository method was called
		verify(restaurantRepo, times(1)).findAll();
	}

	@Test
	public void testAddRestaurantInDB() {
		// Create a mock restaurant to be saved
		Restaurant mockRestaurant = new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");
		RestaurantDTO mockRestaurantDto = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");

		// Mock the repository behavior
		when(restaurantRepo.save(mockRestaurant)).thenReturn(mockRestaurant);

		when(modelMapper.map(mockRestaurantDto, Restaurant.class)).thenReturn(mockRestaurant);
		when(modelMapper.map(mockRestaurant, RestaurantDTO.class)).thenReturn(mockRestaurantDto);

		RestaurantDTO savedRestaurantDTO = restaurantService.addRestaurantInDB(mockRestaurantDto);

		assertEquals(mockRestaurantDto, savedRestaurantDTO);

		// Verify that the repository method was called
		verify(restaurantRepo, times(1)).save(mockRestaurant);
	}

	@Test
	public void testFetchRestaurantById_Existing() {
		Restaurant restaurant1 = new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");
		Restaurant restaurant2 = new Restaurant(2, "Restaurant 2", "Address 2", "city 2", "Desc 2");

		when(restaurantRepo.findById(1)).thenReturn(Optional.of(restaurant1));
		when(restaurantRepo.findById(2)).thenReturn(Optional.of(restaurant2));

		when(modelMapper.map(new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"), RestaurantDTO.class))
				.thenReturn(new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"));
		when(modelMapper.map(new Restaurant(2, "Restaurant 2", "Address 2", "city 2", "Desc 2"), RestaurantDTO.class))
				.thenReturn(new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Desc 2"));

		RestaurantDTO restaurantDTO1 = restaurantService.fetchRestaurantById(1);
		assertEquals(restaurantDTO1, new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"));

		RestaurantDTO restaurantDTO2 = restaurantService.fetchRestaurantById(2);
		assertEquals(restaurantDTO2, new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Desc 2"));

		verify(restaurantRepo, times(1)).findById(1);
		verify(restaurantRepo, times(1)).findById(2);
	}

	@Test
	public void testFetchRestaurantById_NonExisting() {
		Restaurant restaurant1 = new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");
		Restaurant restaurant2 = new Restaurant(2, "Restaurant 2", "Address 2", "city 2", "Desc 2");

		when(restaurantRepo.findById(1)).thenReturn(Optional.of(restaurant1));
		when(restaurantRepo.findById(2)).thenReturn(Optional.of(restaurant2));

		when(modelMapper.map(new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"), RestaurantDTO.class))
				.thenReturn(new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"));
		when(modelMapper.map(new Restaurant(2, "Restaurant 2", "Address 2", "city 2", "Desc 2"), RestaurantDTO.class))
				.thenReturn(new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Desc 2"));

		RestaurantDTO restaurantDTO = restaurantService.fetchRestaurantById(3);
		assertNull(restaurantDTO);
		verify(restaurantRepo, times(1)).findById(3);
	}

}
