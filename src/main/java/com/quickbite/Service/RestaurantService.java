
package com.quickbite.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickbite.DTO.RestaurantDTO;
import com.quickbite.Entity.Restaurant;
import com.quickbite.Repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // CREATE RESTAURANT
    public RestaurantDTO createRestaurant(RestaurantDTO dto) {

        Restaurant restaurant = new Restaurant();

        restaurant.setName(dto.getName());
        restaurant.setLocation(dto.getLocation());
        restaurant.setCuisine(dto.getCuisine());
        restaurant.setPhone(dto.getPhone());

        Restaurant savedRestaurant =
                restaurantRepository.save(restaurant);

        return convertToDTO(savedRestaurant);
    }

    // GET ALL RESTAURANTS
    public List<RestaurantDTO> getAllRestaurants() {

        return restaurantRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET RESTAURANT BY ID
    public RestaurantDTO getRestaurantById(Long id) {

        Restaurant restaurant =
                restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Restaurant not found"
                        )
                );

        return convertToDTO(restaurant);
    }

    // UPDATE RESTAURANT
    public RestaurantDTO updateRestaurant(
            Long id,
            RestaurantDTO dto) {

        Restaurant restaurant =
                restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Restaurant not found"
                        )
                );

        restaurant.setName(dto.getName());
        restaurant.setLocation(dto.getLocation());
        restaurant.setCuisine(dto.getCuisine());
        restaurant.setPhone(dto.getPhone());

        Restaurant updatedRestaurant =
                restaurantRepository.save(restaurant);

        return convertToDTO(updatedRestaurant);
    }

    // DELETE RESTAURANT
    public void deleteRestaurant(Long id) {

        restaurantRepository.deleteById(id);
    }

    // GET RESTAURANTS BY CUISINE
    public List<RestaurantDTO> getRestaurantsByCuisine(
            String cuisine) {

        return restaurantRepository
                .findByCuisine(cuisine)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET RESTAURANTS BY LOCATION
    public List<RestaurantDTO> getRestaurantsByLocation(
            String location) {

        return restaurantRepository
                .findByLocation(location)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // SEARCH RESTAURANTS BY NAME
    public List<RestaurantDTO> searchRestaurantsByName(
            String name) {

        return restaurantRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // SEARCH BY NAME OR LOCATION OR CUISINE
    public List<RestaurantDTO> searchRestaurants(
            String keyword) {

        return restaurantRepository
                .findByNameContainingIgnoreCaseOrLocationContainingIgnoreCaseOrCuisineContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword
                )
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET RESTAURANTS WITH PAGINATION
    public Page<RestaurantDTO> getRestaurantsWithPagination(
            Pageable pageable) {

        return restaurantRepository
                .findAll(pageable)
                .map(this::convertToDTO);
    }

    // ENTITY → DTO
    private RestaurantDTO convertToDTO(
            Restaurant restaurant) {

        return new RestaurantDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getLocation(),
                restaurant.getCuisine(),
                restaurant.getPhone()
        );
    }
}
