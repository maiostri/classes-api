package com.letscode.classesapi.repository;

import com.letscode.classesapi.domain.Presenca;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PresencaRepository extends ReactiveMongoRepository<Presenca, String> {
}
