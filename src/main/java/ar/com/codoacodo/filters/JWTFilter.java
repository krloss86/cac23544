package ar.com.codoacodo.filters;

import java.io.IOException;
import java.util.List;

import com.bastiaanjansen.jwt.JWT;
import com.bastiaanjansen.jwt.algorithms.Algorithm;
import com.bastiaanjansen.jwt.exceptions.JWTCreationException;
import com.bastiaanjansen.jwt.exceptions.JWTDecodeException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = { "/api/orador" })
public class JWTFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		//esto viene desde el front
		String rawJWT = ((HttpServletRequest) request).getHeader("Authorization");

		Algorithm algorithm = Algorithm.HMAC512("cac-23544");

		try {
		  JWT jwt = JWT.fromRawJWT(algorithm, rawJWT);
		  chain.doFilter(request, response);
		  
		} catch (JWTCreationException | JWTDecodeException e) {
			((HttpServletResponse) request).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}

	}

}
