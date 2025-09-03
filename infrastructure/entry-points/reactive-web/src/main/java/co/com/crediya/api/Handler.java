package co.com.crediya.api;

import co.com.crediya.api.exception.BusinessException;
import co.com.crediya.api.exception.ErrorHandler;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.usecase.solicitud.SolicitudUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final SolicitudUseCase useCase;
    private final ErrorHandler errorHandler;


    public Mono<ServerResponse> listenSaveSolicitud(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Solicitud.class)
                .flatMap(useCase::ejecutar)
                .flatMap(savedSolicitud ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(savedSolicitud))
                .onErrorResume(BusinessException.class, errorHandler::handleBadRequest)
                .onErrorResume(Exception.class, errorHandler::handleInternalServerError);
    }

}
