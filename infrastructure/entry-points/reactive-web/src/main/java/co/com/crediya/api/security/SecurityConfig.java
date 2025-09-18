package co.com.crediya.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
		return http
				.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.authorizeExchange(exchange -> exchange
						.pathMatchers("/swagger-ui/**", "/v3/api-docs/**", "/webjars/**").permitAll()
						.pathMatchers(HttpMethod.POST, "/api/v1/solicitud").hasRole("CLIENTE")
						.pathMatchers(HttpMethod.GET,"/api/v1/solicitud").hasRole("ASESOR")
						.anyExchange().authenticated()
				)
				.addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.build();
	}
}
