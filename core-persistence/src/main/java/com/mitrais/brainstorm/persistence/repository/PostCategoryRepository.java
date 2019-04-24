package com.mitrais.brainstorm.persistence.repository;

import com.mitrais.brainstorm.persistence.domain.PostCategory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostCategoryRepository extends ReactiveCrudRepository<PostCategory, String> {
}
