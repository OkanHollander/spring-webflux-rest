package com.okan.springwebfluxrest.controllers;


import com.okan.springwebfluxrest.domain.Category;
import com.okan.springwebfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

/**
 * Author:   Okan Hollander
 * Date:     10/01/2020
 * Time:     08:20
 */
public class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;


    @Before
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void showCategories() {
        // Behavior Driven
        BDDMockito.given(categoryRepository.findAll())
                // Flux for 0 or more
                .willReturn(Flux.just(Category.builder().description("Cat1").build(),
                        Category.builder().description("Cat2").build()));

        webTestClient.get().uri(CategoryController.BASE_URL)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void findCategoryById() {
        BDDMockito.given(categoryRepository.findById("someID"))
                // Mono for 0 or 1
                .willReturn(Mono.just(Category.builder().description("Cat1").build()));

        webTestClient.get().uri(CategoryController.BASE_URL + "someID")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void createCategory() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().description("Desc").build()));

        Mono<Category> savedCategory = Mono.just(Category.builder().description("Cat1").build());

        webTestClient.post()
                .uri(CategoryController.BASE_URL)
                .body(savedCategory, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void updateCategory() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdate = Mono.just(Category.builder().description("Cat1").build());

        webTestClient.put().uri(CategoryController.BASE_URL + "blablabla")
                .body(catToUpdate, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
