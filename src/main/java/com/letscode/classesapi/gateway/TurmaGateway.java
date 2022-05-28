package com.letscode.classesapi.gateway;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TurmaGateway {

    private final TurmaReactiveFeignClient turmaReactiveFeignClient;

    public Mono<String> getTurma(Long turmaId) {
        return turmaReactiveFeignClient.getTurma(turmaId)
                .onErrorResume(FeignException.NotFound.class, erro ->
                        Mono.empty()
                );
    }
}
