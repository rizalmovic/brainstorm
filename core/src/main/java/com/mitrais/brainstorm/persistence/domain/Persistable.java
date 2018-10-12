package com.mitrais.brainstorm.persistence.domain;

import java.io.Serializable;

/**
 *
 */
public interface Persistable<ID extends Serializable> {

	ID getId();
}
