package com.rph.ecom_proj.repo;

import com.rph.ecom_proj.model.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<UserData, Integer> {
    UserData findByUsername(String username);
}
