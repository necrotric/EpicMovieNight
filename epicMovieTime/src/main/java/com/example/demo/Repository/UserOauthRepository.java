package com.example.demo.Repository;

import com.example.demo.Classes.UserOauth;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserOauthRepository extends MongoRepository<UserOauth, String> {
    public List<UserOauth> findUserOauthByEmail(String email);
    public UserOauth findOneUserOauthByEmail(String email);
    public UserOauth deleteUserOauthByEmail(String email);
}
