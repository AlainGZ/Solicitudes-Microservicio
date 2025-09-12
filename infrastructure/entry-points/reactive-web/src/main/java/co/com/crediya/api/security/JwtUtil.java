package co.com.crediya.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
	private static final String SECRET = "miSuperClaveUltraSecreta1234567890";
	private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

	private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

	public static String generateToken(String correo, String rol, Long documentoIdentidad) {
		return Jwts.builder()
				.setSubject(correo)
				.claim("rol",  rol) // ROLE_CLIENTE, ROLE_ADMIN, etc.
				.claim("documentoIdentidad", documentoIdentidad)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public static String getCorreoFromToken(String token) {
		return parseClaims(token).getSubject();
	}

	public static String getRolFromToken(String token) {
		return (String) parseClaims(token).get("rol");
	}

	public static Long getDocumentoIdentidadFromToken(String token) {
		return ((Number) parseClaims(token).get("documentoIdentidad")).longValue();
	}

	public static boolean validateToken(String token) {
		try {
			parseClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	private static Claims parseClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
