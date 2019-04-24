package com.mitrais.brainstorm.persistence.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@RequiredArgsConstructor
@Data
public class PostCategory {

    @Id
    private String id;

    @NonNull
    private String name;
}
