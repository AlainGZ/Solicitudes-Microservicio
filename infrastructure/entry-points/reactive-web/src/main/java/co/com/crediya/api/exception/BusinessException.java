package co.com.crediya.api.exception;

public class BusinessException extends RuntimeException{
	public BusinessException(String mensaje){
		super(mensaje);
	}
}
