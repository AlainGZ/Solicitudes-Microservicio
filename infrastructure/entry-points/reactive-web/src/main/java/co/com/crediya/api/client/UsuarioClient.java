package co.com.crediya.api.client;

import co.com.crediya.usecase.solicitud.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UsuarioClient implements UsuarioGateway {

	private final WebClient.Builder builder;

	@Override
	public Mono<Boolean> existeUsuario(Long documentoIdentidad) {
		return builder.baseUrl("http://localhost:8080/api/v1/usuarios")
				.build()
				.get()
				.uri("/{documentoIdentidad}", documentoIdentidad)
				.retrieve()
				.bodyToMono(Boolean.class)
				.onErrorReturn(false);
	}
}
