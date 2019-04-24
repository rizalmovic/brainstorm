package com.mitrais.brainstorm.persistence.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@RequiredArgsConstructor
public class Account {

    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    @Indexed(unique = true)
    private String email;

    @NonNull
    private String password;
}
