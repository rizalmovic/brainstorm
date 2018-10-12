package com.mitrais.brainstorm.persistence.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document
@RequiredArgsConstructor
@Data
public class Post implements Persistable<String> {
	@Id
	private String id;
	@NonNull
	private String title;
	@NonNull
	private String content;
}
