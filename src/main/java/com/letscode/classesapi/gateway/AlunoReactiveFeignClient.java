package com.letscode.classesapi.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "alunos-service")
public interface AlunoReactiveFeignClient {

    @GetMapping("/aluno/{alunoId}")
    Mono<String> getAluno(@PathVariable("alunoId") Long alunoId);
}
