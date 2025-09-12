package co.com.crediya.api.security;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements WebFilter {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();


		if (path.startsWith("/swagger") ||
				path.startsWith("/v3/api-docs") ||
				path.startsWith("/webjars")) {
			return chain.filter(exchange);
		}

		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return unauthorized(exchange, "Token no encontrado");
		}

		String token = authHeader.substring(7);

		if (!JwtUtil.validateToken(token)) {
			return unauthorized(exchange, "Token inválido");
		}

		String correo = JwtUtil.getCorreoFromToken(token);
		String rol = JwtUtil.getRolFromToken(token); // ej: "CLIENTE"

		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(
						correo,
						null,
						List.of(new SimpleGrantedAuthority("ROLE_" + rol))
				);

		return chain.filter(exchange)
				.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
	}

	private Mono<Void> unauthorized(ServerWebExchange exchange, String mensaje) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		DataBuffer buffer = exchange.getResponse()
				.bufferFactory()
				.wrap(("{\"mensaje\":\"" + mensaje + "\",\"detalle\":\"UNAUTHORIZED\"}")
						.getBytes(StandardCharsets.UTF_8));
		return exchange.getResponse().writeWith(Mono.just(buffer));
	}
}
