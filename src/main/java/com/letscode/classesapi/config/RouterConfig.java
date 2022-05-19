package com.letscode.classesapi.config;

import com.letscode.classesapi.handler.PresencaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RequestPredicates.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> route(PresencaHandler presencaHandler) {
        return RouterFunctions
                .route(POST("/presenca/add").and(contentType(APPLICATION_JSON)), presencaHandler::adicionaPresenca)
                .andRoute(GET("/presenca/{alunoId}").and(contentType(APPLICATION_JSON)), presencaHandler::getPresencasByAluno);
    }
}
