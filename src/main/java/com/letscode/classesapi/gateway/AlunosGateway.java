package com.letscode.classesapi.gateway;

import com.letscode.classesapi.exception.AlunoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetrySpec;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class AlunosGateway {

    @Value("${alunos.base.url}")
    private String baseUrl;

    public Mono<String> getAluno(Long alunoId) {
        return WebClient
                .builder()
                .baseUrl(String.format(baseUrl, alunoId))
                .build()
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, erro ->
                        erro.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(erro)
                )
                .retryWhen(RetrySpec.fixedDelay(5, Duration.ofSeconds(5, 0)));
    }
}
