package com.rph.ecom_proj.repo;

import com.rph.ecom_proj.model.UserData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<UserData, ObjectId> {
    UserData findByUsername(String username);
}
