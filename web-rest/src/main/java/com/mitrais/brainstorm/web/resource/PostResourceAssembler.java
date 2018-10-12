package com.mitrais.brainstorm.web.resource;

import com.mitrais.brainstorm.persistence.domain.Post;
import com.mitrais.brainstorm.web.controller.PostController;
import org.springframework.stereotype.Component;

@Component
public class PostResourceAssembler extends SimplePersistableResourceAssembler<Post> {

	/**
	 * Link the {@link Post} domain type to the {@link PostController} using this
	 * {@link SimplePersistableResourceAssembler} in order to generate both {@link org.springframework.hateoas.Resource}
	 * and {@link org.springframework.hateoas.Resources}.
	 */
	public PostResourceAssembler() {
		super(PostController.class);
	}
}
