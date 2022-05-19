package com.letscode.classesapi.handler;

import com.letscode.classesapi.domain.Presenca;
import com.letscode.classesapi.gateway.AlunosGateway;
import com.letscode.classesapi.gateway.TurmaGateway;
import com.letscode.classesapi.repository.PresencaRepository;
import com.letscode.classesapi.service.PresencaService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.net.URI;
import java.util.AbstractMap;
import java.util.Objects;

@Component
public class PresencaHandler {

    private final PresencaRepository presencaRepository;
    private final PresencaService presencaService;

    public PresencaHandler(PresencaRepository presencaRepository, PresencaService presencaService) {
        this.presencaRepository = presencaRepository;
        this.presencaService = presencaService;
    }

    public Mono<ServerResponse> adicionaPresenca(ServerRequest request) {
        return request.bodyToMono(Presenca.class)
                // Busco o aluno que veio no request
                .flatMap(presencaService::verificaPresenca)
                .flatMap(presencaRepository::save)
                .flatMap(presenca -> ServerResponse
                        .created(URI.create(String.format("/presenca/%s", presenca.getId())))
                        .bodyValue(presenca))
                .switchIfEmpty(ServerResponse.unprocessableEntity().bodyValue("Aluno/Turma inválidos. Por favor verifique."));
    }

    public Mono<ServerResponse> getPresencasByAluno(ServerRequest request) {
        return null;
//        return Mono.just(request.pathVariable("id"))
//                .flatMap(id -> presencaRepository.
//                .flatMap(presencas -> )
    }
}
