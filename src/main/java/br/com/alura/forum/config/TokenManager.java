package br.com.alura.forum.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.alura.forum.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenManager {
	
	@Value("${alura.forum.jwt.secret}")
	private String secret;
	
	@Value("${alura.forum.jwt.expiration}")
	private long expirationIsMillis;
	
	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		
		Date today= new Date();
		
		return Jwts.builder()
				.setIssuer("Alura forum API")
				.setSubject(String.valueOf(user.getId()))
				.setIssuedAt(new Date())
				.setExpiration(new Date(today.getTime() + expirationIsMillis))
				.signWith(SignatureAlgorithm.HS256, this.secret)
				.compact();
	}
}