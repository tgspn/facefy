package com.facefy.facefy_api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.facefy.facefy_api.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
