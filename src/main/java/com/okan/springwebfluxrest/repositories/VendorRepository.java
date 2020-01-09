package com.okan.springwebfluxrest.repositories;

import com.okan.springwebfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Author:   Okan Hollander
 * Date:     09/01/2020
 * Time:     20:12
 */
public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
