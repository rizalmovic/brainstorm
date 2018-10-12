package com.mitrais.brainstorm;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.mitrais.brainstorm.persistence.domain.Post;
import com.mitrais.brainstorm.persistence.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class Main implements CommandLineRunner {
	@Autowired
	PostRepository postRepository;
	@Autowired
	ReactiveMongoOperations operations;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) {
		TextProducer tp = Fairy.create().textProducer();
		operations.collectionExists(Post.class)
				.flatMap(exists -> exists ? operations.dropCollection(Post.class) : Mono.just(exists))
				.flatMap(o -> operations.createCollection(Post.class, CollectionOptions.empty() //
						.size(1024 * 1024) //
						.maxDocuments(100) //
						.capped()))
				.then()
				.block();

		postRepository
				.saveAll(Flux.just(new Post(tp.sentence(), tp.paragraph(5)), new Post(tp.sentence(), tp.paragraph(5))))
				.then()
				.block();
	}
}
