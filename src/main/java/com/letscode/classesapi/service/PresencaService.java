package com.letscode.classesapi.service;

import com.letscode.classesapi.domain.Presenca;
import com.letscode.classesapi.gateway.AlunosGateway;
import com.letscode.classesapi.gateway.TurmaGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Objects;

@Service
public class PresencaService {

    private final AlunosGateway alunosGateway;
    private final TurmaGateway turmaGateway;

    public PresencaService(AlunosGateway alunosGateway, TurmaGateway turmaGateway) {
        this.alunosGateway = alunosGateway;
        this.turmaGateway = turmaGateway;
    }

    public Mono<Presenca> verificaPresenca(Presenca p) {
        return Mono.zip(
                Mono.just(p).flatMap(presenca -> alunosGateway.getAluno(presenca.getAlunoId())),
                Mono.just(p).flatMap(presenca -> turmaGateway.getTurma(presenca.getTurmaId()))
        ).thenReturn(p);
    }
}
