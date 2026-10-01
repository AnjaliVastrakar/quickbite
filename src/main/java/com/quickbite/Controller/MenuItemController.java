
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

import com.quickbite.DTO.MenuItemDTO;
import com.quickbite.Service.MenuItemService;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    // CREATE MENU ITEM
    @PostMapping
    public MenuItemDTO createMenuItem(@RequestBody MenuItemDTO dto) {
        return menuItemService.createMenuItem(dto);
    }

    // GET ALL MENU ITEMS
    @GetMapping
    public List<MenuItemDTO> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    // GET MENU ITEMS WITH PAGINATION
    @GetMapping("/page")
    public Page<MenuItemDTO> getMenuItemsWithPagination(Pageable pageable) {
        return menuItemService.getMenuItemsWithPagination(pageable);
    }

    // SEARCH MENU ITEMS
    @GetMapping("/search")
    public List<MenuItemDTO> searchMenuItems(
            @RequestParam String keyword) {

        return menuItemService.searchMenuItems(keyword);
    }

    // GET MENU ITEM BY ID
    @GetMapping("/{id}")
    public MenuItemDTO getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id);
    }

    // UPDATE MENU ITEM
    @PutMapping("/{id}")
    public MenuItemDTO updateMenuItem(
            @PathVariable Long id,
            @RequestBody MenuItemDTO dto) {

        return menuItemService.updateMenuItem(id, dto);
    }

    // DELETE MENU ITEM
    @DeleteMapping("/{id}")
    public String deleteMenuItem(@PathVariable Long id) {

        menuItemService.deleteMenuItem(id);

        return "Menu item deleted successfully";
    }

    // GET MENU ITEMS BY RESTAURANT
    @GetMapping("/restaurant/{restaurantId}")
    public List<MenuItemDTO> getMenuItemsByRestaurantId(
            @PathVariable Long restaurantId) {

        return menuItemService.getMenuItemsByRestaurantId(restaurantId);
    }

    // GET MENU ITEMS BY CATEGORY
    @GetMapping("/category/{category}")
    public List<MenuItemDTO> getMenuItemsByCategory(
            @PathVariable String category) {

        return menuItemService.getMenuItemsByCategory(category);
    }

    // GET AVAILABLE MENU ITEMS
    @GetMapping("/available")
    public List<MenuItemDTO> getAvailableMenuItems() {

        return menuItemService.getAvailableMenuItems();
    }
}
