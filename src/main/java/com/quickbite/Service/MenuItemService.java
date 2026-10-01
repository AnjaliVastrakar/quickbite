
package com.quickbite.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickbite.DTO.MenuItemDTO;
import com.quickbite.Entity.MenuItem;
import com.quickbite.Repository.MenuItemRepository;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    // CREATE MENU ITEM
    public MenuItemDTO createMenuItem(MenuItemDTO dto) {

        MenuItem menuItem = new MenuItem();

        menuItem.setRestaurantId(dto.getRestaurantId());
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setCategory(dto.getCategory());
        menuItem.setAvailable(dto.isAvailable());

        MenuItem saved = menuItemRepository.save(menuItem);

        return convertToDTO(saved);
    }

    // GET ALL MENU ITEMS
    public List<MenuItemDTO> getAllMenuItems() {

        return menuItemRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET MENU ITEM BY ID
    public MenuItemDTO getMenuItemById(Long id) {

        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Menu item not found"));

        return convertToDTO(menuItem);
    }

    // UPDATE MENU ITEM
    public MenuItemDTO updateMenuItem(Long id, MenuItemDTO dto) {

        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Menu item not found"));

        menuItem.setRestaurantId(dto.getRestaurantId());
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setCategory(dto.getCategory());
        menuItem.setAvailable(dto.isAvailable());

        MenuItem updated = menuItemRepository.save(menuItem);

        return convertToDTO(updated);
    }

    // DELETE MENU ITEM
    public void deleteMenuItem(Long id) {

        menuItemRepository.deleteById(id);
    }

    // GET MENU ITEMS BY RESTAURANT
    public List<MenuItemDTO> getMenuItemsByRestaurantId(Long restaurantId) {

        return menuItemRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET MENU ITEMS BY CATEGORY
    public List<MenuItemDTO> getMenuItemsByCategory(String category) {

        return menuItemRepository.findByCategory(category)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET AVAILABLE MENU ITEMS
    public List<MenuItemDTO> getAvailableMenuItems() {

        return menuItemRepository.findByAvailable(true)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // SEARCH MENU ITEMS BY NAME
    public List<MenuItemDTO> searchMenuItemsByName(String name) {

        return menuItemRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // SEARCH MENU ITEMS BY NAME OR CATEGORY
    public List<MenuItemDTO> searchMenuItems(String keyword) {

        return menuItemRepository
                .findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                        keyword,
                        keyword
                )
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // CONVERT ENTITY TO DTO
    private MenuItemDTO convertToDTO(MenuItem menuItem) {

        return new MenuItemDTO(
                menuItem.getId(),
                menuItem.getRestaurantId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getCategory(),
                menuItem.isAvailable()
        );
    }

    // PAGINATION
    public Page<MenuItemDTO> getMenuItemsWithPagination(Pageable pageable) {

        return menuItemRepository.findAll(pageable)
                .map(this::convertToDTO);
    }
}
