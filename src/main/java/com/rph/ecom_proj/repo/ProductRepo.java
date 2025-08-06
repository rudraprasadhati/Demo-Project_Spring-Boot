package com.rph.ecom_proj.repo;

import com.rph.ecom_proj.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends MongoRepository<Product , Integer> {
}
