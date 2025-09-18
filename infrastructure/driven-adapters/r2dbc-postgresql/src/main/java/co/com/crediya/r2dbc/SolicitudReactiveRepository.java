package co.com.crediya.r2dbc;

import co.com.crediya.r2dbc.entity.SolicitudEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

// TODO: This file is just an example, you should delete or modify it
public interface SolicitudReactiveRepository extends ReactiveCrudRepository<SolicitudEntity, Long>, ReactiveQueryByExampleExecutor<SolicitudEntity> {
  @Query("SELECT * FROM solicitudes " +
  "WHERE estado_solicitud = 'PENDIENTE_REVISION' " +
  "LIMIT :size OFFSET :offset")
	Flux<SolicitudEntity> listarSolicitudes(int size, int offset);
}
