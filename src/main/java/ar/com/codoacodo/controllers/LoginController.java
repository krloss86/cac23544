package ar.com.codoacodo.controllers;

import java.io.IOException;
import java.util.Date;

import com.bastiaanjansen.jwt.JWT;
import com.bastiaanjansen.jwt.algorithms.Algorithm;
import com.bastiaanjansen.jwt.exceptions.JWTCreationException;

import ar.com.codoacodo.entity.User;
import ar.com.codoacodo.repository.LoginRepository;
import ar.com.codoacodo.repository.MySQLLoginRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/auth/login")
public class LoginController extends AppBaseServlet {

	protected void doGet(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//validaciones
		
		LoginRepository repository = new MySQLLoginRepository();
		
		User user =  repository.login(username, password);
		
		//crar el token para enviar al front
		response.setStatus(HttpServletResponse.SC_CREATED);//201
		
		try {
			String jwt = new JWT.Builder(Algorithm.HMAC512("cac-23544"))
			    .withIssuer(user.getNombre())
			    .withAudience("aud1", "aud2")
			    .withIssuedAt(new Date())
			    .withID("id")
			    .withClaim("username", user.getUsername()) // add custom claims
			    .sign();
			  
			  response.getWriter().print(mapper.writeValueAsString(new JWTToken(jwt)));		
			} catch (JWTCreationException e) {
			  e.printStackTrace(); // Handle error
			}
	}
}
