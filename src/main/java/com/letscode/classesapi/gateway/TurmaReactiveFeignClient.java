package com.letscode.classesapi.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "turmas-service")
public interface TurmaReactiveFeignClient {

    @GetMapping("/turma/{turmaId}")
    Mono<String> getTurma(@PathVariable("turmaId") Long turmaId);
}
