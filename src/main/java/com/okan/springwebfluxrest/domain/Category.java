package com.okan.springwebfluxrest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Author:   Okan Hollander
 * Date:     09/01/2020
 * Time:     20:00
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class Category {

    @Id
    private String id;
    private String description;
}
