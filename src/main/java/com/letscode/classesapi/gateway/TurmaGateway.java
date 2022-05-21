package com.letscode.classesapi.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TurmaGateway {

    @Value("${turmas.base.url}")
    private String baseUrl;

    public Mono<String> getTurma(Long turmaId) {
        return WebClient
                .builder()
                .baseUrl(String.format(baseUrl, turmaId))
                .build()
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, erro ->
                        erro.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(erro)
                );
    }
}
