package co.com.crediya.usecase.solicitud;

import reactor.core.publisher.Mono;

public interface UsuarioGateway {
	Mono<Boolean> existeUsuario(Long documentoIdentidad);
}
