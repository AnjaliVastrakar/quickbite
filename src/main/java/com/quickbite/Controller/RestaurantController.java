
package com.quickbite.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickbite.DTO.RestaurantDTO;
import com.quickbite.Service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(
            RestaurantService restaurantService) {

        this.restaurantService = restaurantService;
    }

    // CREATE RESTAURANT
    @PostMapping
    public RestaurantDTO createRestaurant(
            @RequestBody RestaurantDTO restaurantDTO) {

        return restaurantService.createRestaurant(
                restaurantDTO
        );
    }

    // SEARCH RESTAURANTS
    // Searches by name, location, or cuisine
    @GetMapping("/search")
    public List<RestaurantDTO> searchRestaurants(
            @RequestParam String keyword) {

        return restaurantService.searchRestaurants(keyword);
    }

    // GET ALL RESTAURANTS
    @GetMapping
    public List<RestaurantDTO> getAllRestaurants() {

        return restaurantService.getAllRestaurants();
    }

    // GET RESTAURANTS BY CUISINE
    @GetMapping("/cuisine/{cuisine}")
    public List<RestaurantDTO> getRestaurantsByCuisine(
            @PathVariable String cuisine) {

        return restaurantService.getRestaurantsByCuisine(
                cuisine
        );
    }

    // GET RESTAURANTS BY LOCATION
    @GetMapping("/location/{location}")
    public List<RestaurantDTO> getRestaurantsByLocation(
            @PathVariable String location) {

        return restaurantService.getRestaurantsByLocation(
                location
        );
    }

    // GET RESTAURANT BY ID
    @GetMapping("/{id}")
    public RestaurantDTO getRestaurantById(
            @PathVariable Long id) {

        return restaurantService.getRestaurantById(id);
    }

    // GET RESTAURANTS WITH PAGINATION AND SORTING
    @GetMapping("/page")
    public Page<RestaurantDTO> getRestaurantsWithPagination(
            Pageable pageable) {

        return restaurantService.getRestaurantsWithPagination(
                pageable
        );
    }

    // UPDATE RESTAURANT
    @PutMapping("/{id}")
    public RestaurantDTO updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantDTO restaurantDTO) {

        return restaurantService.updateRestaurant(
                id,
                restaurantDTO
        );
    }

    // DELETE RESTAURANT
    @DeleteMapping("/{id}")
    public String deleteRestaurant(
            @PathVariable Long id) {

        restaurantService.deleteRestaurant(id);

        return "Restaurant deleted successfully";
    }
}

