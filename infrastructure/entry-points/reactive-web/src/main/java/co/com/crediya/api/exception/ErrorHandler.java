package co.com.crediya.api.exception;

import co.com.crediya.usecase.solicitud.SolicitudConstantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ErrorHandler {

	public <T extends Throwable> Mono<ServerResponse> handleBadRequest(T ex) {
		return ServerResponse.badRequest()
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ErrorResponse(ex.getMessage(), SolicitudConstantes.VALIDAR_DATOS));
	}

	public <T extends Throwable> Mono<ServerResponse> handleInternalServerError(T ex) {
		return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new ErrorResponse(SolicitudConstantes.ERROR_INTERNO, ex.getMessage()));
	}

}
