package co.com.crediya.usecase.solicitud;

import co.com.crediya.model.solicitud.EstadoSolicitud;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.TipoPrestamo;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SolicitudUseCaseTest {
	private SolicitudRepository repository;
	private SolicitudUseCase useCase;

	@BeforeEach
	void setUp() {
		repository = Mockito.mock(SolicitudRepository.class);
		useCase = new SolicitudUseCase(repository);
	}

	@Test
	void ejecutar_debeSetearEstadoYFecha_siNoExisten() {
		// Given
		Solicitud solicitud = Solicitud.builder()
				.documentoIdentidad(123456L)
				.monto(1000L)
				.plazo("12 meses")
				.tipoPrestamo(TipoPrestamo.PERSONAL)
				.estadoSolicitud(null)
				.fechaSolicitud(null)
				.build();

		// Mock repository
		when(repository.save(any(Solicitud.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

		// When & Then
		StepVerifier.create(useCase.ejecutar(solicitud))
				.expectNextMatches(s -> s.getEstadoSolicitud() == EstadoSolicitud.PENDIENTE_REVISION
						&& s.getFechaSolicitud() != null)
				.verifyComplete();

		verify(repository, times(1)).save(any(Solicitud.class));
	}

	@Test
	void ejecutar_debeMantenerEstadoYFecha_siYaExisten() {
		// Given
		LocalDate fecha = LocalDate.of(2025, 9, 2);
		Solicitud solicitud = Solicitud.builder()
				.documentoIdentidad(123456L)
				.monto(1000L)
				.plazo("12 meses")
				.tipoPrestamo(TipoPrestamo.PERSONAL)
				.estadoSolicitud(EstadoSolicitud.APROVADO)
				.fechaSolicitud(fecha)
				.build();

		when(repository.save(any(Solicitud.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

		// When & Then
		StepVerifier.create(useCase.ejecutar(solicitud))
				.expectNextMatches(s -> s.getEstadoSolicitud() == EstadoSolicitud.APROVADO
						&& s.getFechaSolicitud().equals(fecha))
				.verifyComplete();

		verify(repository, times(1)).save(any(Solicitud.class));
	}
}
