package co.com.crediya.api.dto;

import co.com.crediya.model.solicitud.TipoPrestamo;
import co.com.crediya.usecase.solicitud.SolicitudConstantes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SolicitudRequestDTO {

	@NotNull(message = SolicitudConstantes.MENSAJE_DOCUMENTO_IDENTIDAD_OBLIGATORIO)
	private Long documentoIdentidad;

	@NotNull(message = SolicitudConstantes.MENSAJE_MONTO_OBLIGATORIO)
	@Positive(message = SolicitudConstantes.MENSAJE_MONTO_INVALIDO)
	private Long monto;

	@NotBlank(message = SolicitudConstantes.MENSAJE_PLAZO_OBLIGATORIO)
	private String plazo;

	@NotBlank(message = SolicitudConstantes.MENSAJE_TIPO_PRESTAMO_OBLIGATORIO)
	private TipoPrestamo tipoPrestamo;

}
