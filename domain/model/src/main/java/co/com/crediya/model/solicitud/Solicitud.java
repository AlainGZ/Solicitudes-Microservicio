package co.com.crediya.model.solicitud;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Solicitud {

	private Long documentoIdentidad;
	private Long monto;
	private String plazo;
	private TipoPrestamo tipoPrestamo;
	private EstadoSolicitud estadoSolicitud;
	private LocalDate fechaSolicitud;

}
