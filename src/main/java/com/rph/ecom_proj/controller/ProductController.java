package com.rph.ecom_proj.controller;

import com.rph.ecom_proj.model.Product;
import com.rph.ecom_proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//Annotations in Java/Spring-Boot give extra properties and highlights the thing it is implemented on.

//This package named as controller is a part of MVC(Model View Controller) architecture in which different class handles different operations for the web.
//The flow goes like this: controller -> service -> repository
//These classes acts a layer while dealing with data and are called as servlets.
//Their function are as follows:
//Controller: manages the API requests.
//Service: manages the business logic.
//Repository: it acts like a bridge for managing the data from the server to database and vice versa.
//Different servlets have their own specialised annotations which gives them certain properties and resides them in the IOC container.

//As mentioned above this class deals with the API requests implementing the CRUD operations of REST API.

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired //@Autowired annotation is used for creating an object of a class inside another class by only an object reference. The whole process is called as dependency injection.
    private ProductService productService;

    @RequestMapping("/product")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts() , HttpStatus.OK);
    }

    @RequestMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product product = productService.getProductById(id);
        if(product!=null){
            return new ResponseEntity<>(product , HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> postProduct(@RequestBody Product product){
        try{
            Product product1 = productService.postProduct(product);
            return new ResponseEntity<> (product1 , HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(new Product() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id , @RequestBody Product product){
        Product product1 = productService.updateProduct(id , product);
        if(product!=null){
            return new ResponseEntity<>("updated" , HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("product not found" , HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product = productService.getProductById(id);
        if(product!=null && id!=0){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product Deleted" , HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Product not found" , HttpStatus.NOT_FOUND);
        }
    }
}
