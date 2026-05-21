package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.dao.CategoryRepository;
import com.luxuryproductsholding.api.dto.CategoryDTO;
import com.luxuryproductsholding.api.models.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryDAO {
    private CategoryRepository categoryRepository;

    public CategoryDAO(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    public void createCategory(CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = Optional.ofNullable(this.categoryRepository.findByName(categoryDTO.name));
        if(optionalCategory.isEmpty()){
            Category category = new Category(categoryDTO.name);
            this.categoryRepository.save(category);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categorie bestaat al");
    }

    public Category checkForCategoryByName(String categoryName) {
        Optional<Category> optionalCategory = Optional.ofNullable(this.categoryRepository.findByName(categoryName));
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            return category;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id niet gevonden");
        }
    }

}
