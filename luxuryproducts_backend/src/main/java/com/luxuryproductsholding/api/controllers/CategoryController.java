package com.luxuryproductsholding.api.controllers;

import com.luxuryproductsholding.api.dao.CategoryDAO;
import com.luxuryproductsholding.api.dto.CategoryDTO;
import com.luxuryproductsholding.api.models.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/categories"})
public class CategoryController {
    private CategoryDAO categoryDAO;

    public CategoryController(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(this.categoryDAO.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryDTO categoryDTO) {
        this.categoryDAO.createCategory(categoryDTO);
        return ResponseEntity.ok("New category was created");
    }
}
