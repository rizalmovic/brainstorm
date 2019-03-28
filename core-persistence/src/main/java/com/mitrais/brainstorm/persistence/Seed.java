package com.mitrais.brainstorm.persistence;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.mitrais.brainstorm.persistence.domain.Post;
import com.mitrais.brainstorm.persistence.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Profile("development")
@Component
public class Seed implements CommandLineRunner {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReactiveMongoOperations mongo;

    public static void main(String[] args) {
        SpringApplication.run(Seed.class, args);
    }

    public void populateData() {
        System.out.println("Populating Data...");

        TextProducer producer = Fairy.create().textProducer();
        this.mongo.collectionExists(Post.class)
                .flatMap(exists -> exists ? this.mongo.dropCollection(Post.class) : Mono.just(exists))
                .flatMap(o -> this.mongo.createCollection(
                        Post.class, CollectionOptions.empty() //
                            .size(1024 * 1024)
                            .maxDocuments(100)
                            .capped()
                        )
                ).then()
                .block();

        List<Post> posts = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            posts.add(new Post(producer.sentence(), producer.paragraph(5)));
        }

        this.postRepository
                .saveAll(Flux.fromIterable(posts))
                .then()
                .block();

        System.out.println("Finish populating data.");
    }

    @Override
    public void run(String... args) {
        this.populateData();
    }
}
