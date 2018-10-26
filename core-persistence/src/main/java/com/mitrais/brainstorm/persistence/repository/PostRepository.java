package com.mitrais.brainstorm.persistence.repository;

import com.mitrais.brainstorm.persistence.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 */
public interface PostRepository extends ReactiveCrudRepository<Post, String> {
	Flux<Post> findByTitleLike(Mono<String> title);
	@Query("{ id: { $exists: true }}")
	Flux<Post> findAllPaged(Pageable pageable);
}
