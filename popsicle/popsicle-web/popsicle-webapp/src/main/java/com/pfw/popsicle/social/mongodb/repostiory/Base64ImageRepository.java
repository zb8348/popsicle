package com.pfw.popsicle.social.mongodb.repostiory;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pfw.popsicle.social.mongodb.entity.Base64Image;

public interface Base64ImageRepository extends MongoRepository<Base64Image, String>{

}
