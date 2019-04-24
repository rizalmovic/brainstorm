package com.mitrais.brainstorm.persistence.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
	private PostType type;

	@DBRef
	private List<PostCategory> categories = new ArrayList<>();

	public Post(String title, String content, PostType type) {
		this.title = title;
		this.content = content;
		this.type = type;
	}

	public Post(String title, String content, PostType type, List<PostCategory> categories) {
		this.title = title;
		this.content = content;
		this.type = type;
		this.categories = categories;
	}
}
