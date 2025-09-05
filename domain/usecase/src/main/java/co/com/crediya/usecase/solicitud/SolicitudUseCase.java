package co.com.crediya.usecase.solicitud;

import co.com.crediya.model.solicitud.EstadoSolicitud;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RequiredArgsConstructor
public class SolicitudUseCase {

	private final SolicitudRepository solicitudRepository;
	private final UsuarioGateway usuarioGateway;

	public Mono<Solicitud> ejecutar(Solicitud solicitud) {

		return usuarioGateway.existeUsuario(solicitud.getDocumentoIdentidad())
				.flatMap(existe -> {
					if (!existe){
						return Mono.error(new IllegalArgumentException(SolicitudConstantes.MENSAJE_DOCUMENTO_NO_REGISTRADO));
					}
					solicitud.setEstadoSolicitud(
							solicitud.getEstadoSolicitud() == null
									? EstadoSolicitud.PENDIENTE_REVISION
									: solicitud.getEstadoSolicitud()
					);
					solicitud.setFechaSolicitud(
							solicitud.getFechaSolicitud() == null
									? LocalDate.now()
									: solicitud.getFechaSolicitud()
					);
					return solicitudRepository.save(solicitud);
				});

	}



}