package co.com.crediya.usecase.solicitud;

public class SolicitudConstantes {

	private SolicitudConstantes(){}

	public static final String MENSAJE_DOCUMENTO_IDENTIDAD_OBLIGATORIO = "El documento de identidad es obligatorio";
	public static final String MENSAJE_PLAZO_OBLIGATORIO = "El plazo de pago es obligatorio";
	public static final String MENSAJE_TIPO_PRESTAMO_OBLIGATORIO = "El tipo de prestamo es obligatorio";
	public static final String MENSAJE_MONTO_OBLIGATORIO = "El monto es obligatorio";
	public static final String MENSAJE_MONTO_INVALIDO = "El monto debe ser mayor a cero";
	public static final String MENSAJE_TIPO_PRESTAMO_INVALIDO = "Tipo de prestamo invalido. (PERSONAL, HIPOTECARIO, CONSUMO)";
	public static final String MENSAJE_DOCUMENTO_NO_REGISTRADO = "El documento no se encuentra registrado";

	public static final String VALIDAR_DATOS = "ERROR Datos Invalidos";
	public static final String ERROR_INTERNO = "ERROR INTERNO";
	public static final String OK = "Solicitud registrada con exito";

	public static final String ERROR_200 = "200";
	public static final String ERROR_400 = "400";
	public static final String ERROR_500 = "500";

	public static final String PATH = "/api/v1/solicitud";
	public static final String METHOD = "listenSaveSolicitud";
	public static final String OPERATIONID = "saveSolicitud";
	public static final String SUMMARY = "Permite crear una nueva solicitud";

	public static final String TITLE = "CrediYa - Solicitud de Prestamo ";
	public static final String VERSION = "1.0";
	public static final String DESCRIPTION = "Documentacion de la API soliitud de prestamos";

}
