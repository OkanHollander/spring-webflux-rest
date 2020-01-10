package com.okan.springwebfluxrest.controllers;

import com.okan.springwebfluxrest.domain.Category;
import com.okan.springwebfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Author:   Okan Hollander
 * Date:     10/01/2020
 * Time:     08:06
 */
@RestController
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories/";
    public static final String ID = "{id}";
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(CategoryController.BASE_URL)
        // Flux for 0 or many results
    Flux<Category> showCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping(CategoryController.BASE_URL + CategoryController.ID)
        // Mono for 0 or 1 result
    Mono<Category> findCategoryById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(BASE_URL)
    Mono<Void> createCategory(@RequestBody Publisher<Category> categoryStream) {
        return categoryRepository.saveAll(categoryStream).then();
    }

    @PutMapping(BASE_URL + ID)
    Mono<Category> updateCategory(@PathVariable String id, @RequestBody Category category) {
        category.setId(id);

        return categoryRepository.save(category);
    }

    @PatchMapping("/api/v1/categories/{id}")
    Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {

        Category foundCategory = categoryRepository.findById(id).block();

        if(foundCategory.getDescription() != category.getDescription()){
            foundCategory.setDescription(category.getDescription());
            return categoryRepository.save(foundCategory);
        }

        return Mono.just(foundCategory);
    }
}
