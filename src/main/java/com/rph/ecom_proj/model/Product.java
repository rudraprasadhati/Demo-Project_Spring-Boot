package com.rph.ecom_proj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private int id;
    private String name;
    private String desc;
    private String brand;
    private BigDecimal price;
    private String category;
    private Date releaseDate;
    private Boolean available;
    private int quantity;

//    {
//        "id":1,
//        "name":"Maruti",
//        "desc":"Very fast",
//        "brand":"Hero",
//        "price":50000.65,
//        "category":"Vehicle",
//        "releaseDate":"2025-08-03T23:02:00",
//        "available":true,
//        "quantity":1
//    }

}
