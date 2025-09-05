package co.com.crediya.api.config;

import co.com.crediya.usecase.solicitud.SolicitudConstantes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI customOpenApi(){
		return new OpenAPI()
				.info(new Info()
						.title(SolicitudConstantes.TITLE)
						.version(SolicitudConstantes.VERSION)
						.description(SolicitudConstantes.DESCRIPTION));
	}
}
