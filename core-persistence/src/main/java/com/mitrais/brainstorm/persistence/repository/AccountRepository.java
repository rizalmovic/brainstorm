package com.mitrais.brainstorm.persistence.repository;

import com.mitrais.brainstorm.persistence.domain.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveCrudRepository<Account, String> {
    Mono<Account> findByEmail(Mono<String> email);
}
