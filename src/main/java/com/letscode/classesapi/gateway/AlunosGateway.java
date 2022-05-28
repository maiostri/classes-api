package com.letscode.classesapi.gateway;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetrySpec;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class AlunosGateway {

    private final AlunoReactiveFeignClient alunoReactiveFeignClient;

    public Mono<String> getAluno(Long alunoId) {
        return alunoReactiveFeignClient.getAluno(alunoId)
                .onErrorResume(FeignException.NotFound.class, erro ->
                        Mono.empty()
                );
    }
}
