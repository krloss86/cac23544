package ar.com.codoacodo.controllers;

import java.io.IOException;

import ar.com.codoacodo.entity.User;
import ar.com.codoacodo.repository.LoginRepository;
import ar.com.codoacodo.repository.MySQLLoginRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/auth/login")
public class LoginController extends HttpServlet{

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
		
		response.addHeader("token", user.getNombre());		
		response.getWriter().print(user.getNombre());		
	}
}
