package com.okan.springwebfluxrest.controllers;

import com.okan.springwebfluxrest.domain.Category;
import com.okan.springwebfluxrest.domain.Vendor;
import com.okan.springwebfluxrest.repositories.VendorRepository;
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
 * Time:     08:49
 */
public class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void showAllVendors() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Okan").build(),
                        Vendor.builder().lastName("Hollander").build()));

        webTestClient.get().uri(VendorController.BASE_URL)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void findVendorById() {
        BDDMockito.given(vendorRepository.findById("id"))
                .willReturn(Mono.just(Vendor.builder().firstName("Okan").build()));

        webTestClient.get().uri(VendorController.BASE_URL + "id")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void createCategory() {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().firstName("firstName").build()));

        Mono<Vendor> savedVendor = Mono.just(Vendor.builder().firstName("Okan").build());

        webTestClient.post()
                .uri(VendorController.BASE_URL)
                .body(savedVendor, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }
}
