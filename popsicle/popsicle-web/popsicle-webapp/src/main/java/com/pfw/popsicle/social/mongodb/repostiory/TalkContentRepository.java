package com.pfw.popsicle.social.mongodb.repostiory;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pfw.popsicle.social.mongodb.entity.TalkContent;


public interface TalkContentRepository extends MongoRepository<TalkContent, String>{//, QueryDslPredicateExecutor<TalkContent>
}
