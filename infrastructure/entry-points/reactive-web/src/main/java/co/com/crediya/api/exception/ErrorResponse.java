package co.com.crediya.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	private String mensaje;
	private String codigo;
}
