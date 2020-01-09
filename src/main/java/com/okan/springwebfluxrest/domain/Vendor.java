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
 * Time:     20:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Vendor {

    @Id
    private String id;
    private String firstName;
    private String lastName;
}
