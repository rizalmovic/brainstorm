package com.mitrais.brainstorm.web.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;

/**
 * Analogous to {@link org.springframework.hateoas.ResourceAssembler} but for resource collections.
 */
public interface ResourcesAssembler<T, D extends ResourceSupport> {
	/**
	 * Converts all given entities into resources and wraps the collection as a resource as well.
	 *
	 * @see org.springframework.hateoas.ResourceAssembler#toResource(Object)
	 * @param entities must not be {@literal null}.
	 * @return {@link Resources} containing {@link org.springframework.hateoas.Resource} of {@code T}.
	 */
	Resources<D> toResources(Iterable<? extends T> entities);
}
