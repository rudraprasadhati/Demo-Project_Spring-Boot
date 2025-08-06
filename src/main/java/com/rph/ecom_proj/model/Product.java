package com.rph.ecom_proj.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "RPH")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

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
