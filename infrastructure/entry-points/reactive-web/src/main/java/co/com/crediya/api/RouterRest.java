package co.com.crediya.api;

import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.usecase.solicitud.SolicitudConstantes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = SolicitudConstantes.PATH,
                    beanClass = Handler.class,
                    beanMethod = SolicitudConstantes.METHOD,
                    operation = @Operation(
                            operationId = SolicitudConstantes.OPERATIONID,
                            summary = SolicitudConstantes.SUMMARY,
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = Solicitud.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = SolicitudConstantes.ERROR_200, description = SolicitudConstantes.OK,
                                            content = @Content(schema = @Schema(implementation = Solicitud.class))),
                                    @ApiResponse(responseCode = SolicitudConstantes.ERROR_400, description = SolicitudConstantes.VALIDAR_DATOS),
                                    @ApiResponse(responseCode = SolicitudConstantes.ERROR_500, description = SolicitudConstantes.ERROR_INTERNO)
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/v1/solicitud"), handler::listenSaveSolicitud);
    }
}
