package com.mitrais.brainstorm.persistence;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.mitrais.brainstorm.persistence.domain.Account;
import com.mitrais.brainstorm.persistence.domain.Post;
import com.mitrais.brainstorm.persistence.domain.PostCategory;
import com.mitrais.brainstorm.persistence.domain.PostType;
import com.mitrais.brainstorm.persistence.repository.AccountRepository;
import com.mitrais.brainstorm.persistence.repository.PostCategoryRepository;
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
    private PostCategoryRepository postCategoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ReactiveMongoOperations mongo;

    public static void main(String[] args) {
        SpringApplication.run(Seed.class, args);
    }

    public void populatePosts() {
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

        List<PostCategory> categories = this.postCategoryRepository.findAll().take(2).collectList().block();

        for(int i = 0; i < 10; i++) {
            posts.add(new Post(producer.sentence(), producer.sentence(5), PostType.QUESTION, categories));
        }

        this.postRepository
                .saveAll(Flux.fromIterable(posts))
                .then()
                .block();

        System.out.println("Finish populating data.");
    }

    public void populatePostCategories() {
        System.out.println("Populate Post Categories...");
        TextProducer producer = Fairy.create().textProducer();

        // Drop and Create collection
        this.mongo.collectionExists(PostCategory.class)
                .flatMap(e -> e ? this.mongo.dropCollection(PostCategory.class) : Mono.just(e))
                .flatMap(o -> this.mongo.createCollection(
                            PostCategory.class,
                            CollectionOptions.empty().size(1024 * 1024).maxDocuments(100).capped()
                        )
                )
                .then()
                .block();

        List<PostCategory> categories = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            categories.add(new PostCategory(producer.sentence()));
        }

        this.postCategoryRepository.saveAll(categories);
        System.out.println("Populate Post category has been succeeded");
    }

    public void populateAccounts() {
        System.out.println("Populate Accounts");
        TextProducer producer = Fairy.create().textProducer();
        Person person =  Fairy.create().person();


        // Drop and Create collection
        this.mongo.collectionExists(Account.class)
            .flatMap(e -> e ? this.mongo.dropCollection(Account.class) : Mono.just(e))
            .flatMap(o -> this.mongo.createCollection(
                    Account.class,
                    CollectionOptions.empty().size(1024 * 1024).maxDocuments(100).capped()
                )
            )
            .then()
            .block();

        List<Account> accounts = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            accounts.add(new Account(
                    person.getFullName(),
                    person.getEmail(),
                    person.getPassword()
                )
            );
        }
        this.accountRepository.saveAll(accounts);
        System.out.println("Populate Accounts has been succeeded");
    }

    @Override
    public void run(String... args) {
        this.populateAccounts();
        this.populatePostCategories();
        this.populatePosts();
    }
}
