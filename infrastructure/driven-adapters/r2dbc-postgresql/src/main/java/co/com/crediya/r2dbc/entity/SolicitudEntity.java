package co.com.crediya.r2dbc.entity;

import co.com.crediya.model.solicitud.EstadoSolicitud;
import co.com.crediya.model.solicitud.TipoPrestamo;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("solicitudes")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SolicitudEntity {
	@Id
	@Column("solicitud_id")
	private Long id;

	@Column("documento_identidad")
	private Long documentoIdentidad;

	private Long monto;

	private String plazo;

	@Column("tipo_prestamo")
	private TipoPrestamo tipoPrestamo;

	@Column("estado_solicitud")
	private EstadoSolicitud estadoSolicitud;

	@Column("fecha_solicitud")
	private LocalDate fechaSolicitud;

}
