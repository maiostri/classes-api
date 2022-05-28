package com.letscode.classesapi;

import com.letscode.classesapi.domain.Presenca;
import com.letscode.classesapi.gateway.AlunosGateway;
import com.letscode.classesapi.gateway.TurmaGateway;
import com.letscode.classesapi.service.PresencaService;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.io.IOException;


@WebFluxTest
@ExtendWith(SpringExtension.class)
@Import({PresencaService.class, AlunosGateway.class, TurmaGateway.class})
@Disabled
public class PresencaServiceTests {

    public static MockWebServer mockWebServer;

    @Autowired
    private PresencaService presencaService;

    static Dispatcher dispatcher = new Dispatcher() {
        @NotNull
        @Override
        public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) throws InterruptedException {

            // turma/1
            return switch (recordedRequest.getPath()) {
                case "/aluno/1" -> new MockResponse()
                        .setResponseCode(200)
                        .setBody("{ 'nome': 'ricardo', 'email': 'teste@gmail.com'}");
                case "/aluno/2" -> new MockResponse()
                        .setResponseCode(500)
                        .setBody("Caiu tudo...");
                case "/aluno/3" -> new MockResponse()
                        .setResponseCode(404)
                        .setBody("Aluno nao existe");
                case "/turma/1" -> new MockResponse()
                        .setResponseCode(200)
                        .setBody("{ 'id': 1, 'nome': '810 os feras EM webflux");
                case "/turma/2" -> new MockResponse()
                        .setResponseCode(500)
                        .setBody("Caiu de novooo");
                default -> new MockResponse()
                        .setResponseCode(404)
                        .setBody("Aluno não existe!!");
            };
        }
    };


    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.setDispatcher(dispatcher);
        mockWebServer.start(9000);
    }

    @Test
    void testNonExistingAluno() {
        Presenca presenca = new Presenca();
        presenca.setAlunoId(3L);
        presenca.setTurmaId(1L);

        StepVerifier.create(presencaService.verificaPresenca(presenca))
                .verifyComplete();
    }

    @Test
    void testExistingAlunoWithError() {
        Presenca presenca = new Presenca();
        presenca.setAlunoId(2L);
        presenca.setTurmaId(1L);

        StepVerifier.create(presencaService.verificaPresenca(presenca))
                .verifyError();
    }

    @Test
    void testInsertionAlunoSuccess() {
        Presenca presenca = new Presenca();
        presenca.setAlunoId(1L);
        presenca.setTurmaId(1L);

        StepVerifier.create(presencaService.verificaPresenca(presenca))
                .consumeNextWith(p ->
                        Assertions.assertNotNull(p.getData())
                )
                .verifyComplete();
    }

}
