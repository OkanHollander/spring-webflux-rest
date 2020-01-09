package com.okan.springwebfluxrest.repositories;

import com.okan.springwebfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Author:   Okan Hollander
 * Date:     09/01/2020
 * Time:     20:02
 */
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {


}
