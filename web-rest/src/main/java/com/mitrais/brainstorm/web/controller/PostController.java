package com.mitrais.brainstorm.web.controller;

import com.mitrais.brainstorm.persistence.domain.Post;
import com.mitrais.brainstorm.persistence.repository.PostRepository;
import com.mitrais.brainstorm.web.resource.PostResourceAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequiredArgsConstructor
public class PostController {
	private final PostRepository postRepository;
	private final PostResourceAssembler postResourceAssembler;

	@GetMapping(value = "/posts", produces = MediaTypes.HAL_JSON_VALUE)
	public ResponseEntity<Resources<Resource<Post>>> findAll(Pageable pageable) {
		Iterable<Post> posts = postRepository.findAllPaged(pageable).toIterable();
		Resources<Resource<Post>> resources = postResourceAssembler.toResources(posts);
		return ResponseEntity.ok(resources);
	}
	@GetMapping(value = "/posts/{id}", produces = MediaTypes.HAL_JSON_VALUE)
	public ResponseEntity<Resource<Post>> findOne(@PathVariable String id) {
		Post post = postRepository.findById(id).block();
		Resource<Post> resource = postResourceAssembler.toResource(post);
		return ResponseEntity.ok(resource);
	}
}
