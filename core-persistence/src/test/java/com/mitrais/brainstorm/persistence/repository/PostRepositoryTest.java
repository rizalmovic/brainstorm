package com.mitrais.brainstorm.persistence.repository;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.mitrais.brainstorm.persistence.domain.Post;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class PostRepositoryTest {
	@Autowired
	PostRepository postRepository;
	@Autowired
	ReactiveMongoOperations operations;

	@BeforeEach
	void init(){
		TextProducer tp = Fairy.create().textProducer();
		Mono<MongoCollection<Document>> recreateCollection = operations.collectionExists(Post.class) //
				.flatMap(exists -> exists ? operations.dropCollection(Post.class) : Mono.just(exists)) //
				.then(operations.createCollection(Post.class, CollectionOptions.empty() //
						.size(1024 * 1024) //
						.maxDocuments(100) //
						.capped()));

		StepVerifier.create(recreateCollection).expectNextCount(1).verifyComplete();

		Flux<Post> insertAll = operations.insertAll(Flux.just(
				new Post("1235 " + tp.sentence(), tp.paragraph(5)), //
				new Post(tp.sentence(), tp.paragraph(5)), //
				new Post(tp.sentence(), tp.paragraph(5)), //
				new Post(tp.sentence(), tp.paragraph(5))).collectList());

		StepVerifier.create(insertAll).expectNextCount(4).verifyComplete();
	}

	@Test
	void findByTitleLike() {
		StepVerifier.create(postRepository.findByTitleLike(Mono.just("1235")).doOnNext(System.out::println)) //
				.expectNextCount(1) //
				.verifyComplete();
	}
}