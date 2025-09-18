package co.com.crediya.api;

import co.com.crediya.api.dto.SolicitudRequestDTO;
import co.com.crediya.api.exception.BusinessException;
import co.com.crediya.api.exception.ErrorHandler;
import co.com.crediya.model.solicitud.EstadoSolicitud;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.TipoPrestamo;
import co.com.crediya.usecase.solicitud.SolicitudConstantes;
import co.com.crediya.usecase.solicitud.SolicitudUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Handler {
    private final SolicitudUseCase useCase;
    private final ErrorHandler errorHandler;
    private final Validator validator;


    public Mono<ServerResponse> listenSaveSolicitud(ServerRequest serverRequest) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(context -> {
                    Authentication auth = context.getAuthentication();
                    if (auth == null || auth.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .noneMatch(role -> role.equals("ROLE_CLIENTE"))){
                        return ServerResponse.status(HttpStatus.FORBIDDEN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("{\\\"mensaje\\\":\\\"Solo los clientes pueden crear solicitudes de préstamo\\\"}");
                    }

        return serverRequest
                .bodyToMono(SolicitudRequestDTO.class)
                .flatMap(dto -> {
                    Set<ConstraintViolation<SolicitudRequestDTO>> violations = validator.validate(dto);
                    if (!violations.isEmpty()) {
                        String mensajes = violations.stream()
                                .map(ConstraintViolation::getMessage)
                                .collect(Collectors.joining(", "));
                        return Mono.error(new BusinessException(mensajes));
                    }
                    TipoPrestamo tipo;
                    try {
                        tipo = TipoPrestamo.valueOf(dto.getTipoPrestamo().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        return Mono.error(new BusinessException(SolicitudConstantes.MENSAJE_TIPO_PRESTAMO_INVALIDO));
                    }


                    Solicitud solicitud = Solicitud.builder()
                            .documentoIdentidad(dto.getDocumentoIdentidad())
                            .monto(dto.getMonto())
                            .plazo(dto.getPlazo())
                            .tipoPrestamo(tipo)
                            .estadoSolicitud(EstadoSolicitud.PENDIENTE_REVISION)
                            .fechaSolicitud(LocalDate.now())
                            .build();

                    return useCase.ejecutar(solicitud);
                })
                .flatMap(savedSolicitud ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(savedSolicitud))
                .onErrorResume(BusinessException.class, errorHandler::handleBadRequest)
                .onErrorResume(Exception.class, errorHandler::handleInternalServerError);
                });
    }
	public Mono<ServerResponse> listar(ServerRequest request) {
		String estado = request.queryParam("estado").orElse(null);
		int page = Integer.parseInt(request.queryParam("page").orElse("0"));
		int size = Integer.parseInt(request.queryParam("size").orElse("10"));

		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(useCase.listar(estado, page, size), Solicitud.class);
	}

}
