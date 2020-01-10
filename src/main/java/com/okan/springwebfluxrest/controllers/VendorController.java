package com.okan.springwebfluxrest.controllers;

import com.okan.springwebfluxrest.domain.Vendor;
import com.okan.springwebfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Author:   Okan Hollander
 * Date:     10/01/2020
 * Time:     08:45
 */
@RestController
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors/";
    public static final String ID = "{id}";
    private final VendorRepository vendorRepository;

    @Autowired
    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping(VendorController.BASE_URL)
    Flux<Vendor> showAllVendors() {
        return vendorRepository.findAll();
    }

    @GetMapping(VendorController.BASE_URL + VendorController.ID)
    Mono<Vendor> findVendorById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @PostMapping(BASE_URL)
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorStream) {
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping(BASE_URL + ID)
    Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);

        return vendorRepository.save(vendor);
    }
}
