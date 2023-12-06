package ar.com.codoacodo.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ar.com.codoacodo.entity.Orador;
import ar.com.codoacodo.repository.MySqlOradorRepository;
import ar.com.codoacodo.repository.OradorRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//http://localhost:8080/web-app-23544/api/orador
@WebServlet("/api/orador")
public class NuevoOradorController extends HttpServlet{

	//crear > POST
	protected void doPost(
				HttpServletRequest request, //aca viene lo que manda el usuario 
				HttpServletResponse response /*manda el backend al frontend*/
			) throws ServletException, IOException {
		
		//OradorRequest oradorJson = (OradorRequest )fromJSON(OradorRequest.class, request, response);
		//obtengo el json desde el frontend
		String json = request.getReader()
				.lines()
				.collect(Collectors.joining(System.lineSeparator()));//spring
		
		//convierto de json String a Objecto java usando libreria de jackson2
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		OradorRequest oradorRequest = mapper.readValue(json, OradorRequest.class);
		
		//creo mi orador con esos parametros
		Orador nuevo = new Orador(
				oradorRequest.getNombre(), 
				oradorRequest.getApellido(),
				oradorRequest.getEmail(),
				oradorRequest.getTema(),
				LocalDate.now()
		);
		
		//ahora por medio del repository guarda en la db
		OradorRepository repository = new MySqlOradorRepository();
		
		repository.save(nuevo);
		
		//ahora respondo al front: json, Convirtiendo el nuevo Orador a json
		String jsonParaEnviarALFrontend = mapper.writeValueAsString(nuevo);
		
		response.getWriter().print(jsonParaEnviarALFrontend);
	}

	protected void doGet(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		//ahora por medio del repository guarda en la db
		OradorRepository repository = new MySqlOradorRepository();
		List<Orador> listado = repository.findAll();
		
		//convierto Objecto java a json string
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);		
		
		//ahora respondo al front: json, Convirtiendo el nuevo Orador a json
		String jsonParaEnviarALFrontend = mapper.writeValueAsString(listado);
			
		response.getWriter().print(jsonParaEnviarALFrontend);
	}
	
	protected void doDelete(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		OradorRepository repository = new MySqlOradorRepository();
		repository.delete(Long.parseLong(id));
		
		response.setStatus(HttpServletResponse.SC_OK);//200
	}
	
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(req, resp);
	}
}
