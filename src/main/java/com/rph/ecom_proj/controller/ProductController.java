package com.rph.ecom_proj.controller;

import com.rph.ecom_proj.model.Product;
import com.rph.ecom_proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
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
        if(product!=null){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product Deleted" , HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Product not found" , HttpStatus.NOT_FOUND);
        }
    }
}
