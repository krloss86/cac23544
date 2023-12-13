package ar.com.codoacodo.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import ar.com.codoacodo.entity.Orador;
import ar.com.codoacodo.repository.MySqlOradorRepository;
import ar.com.codoacodo.repository.OradorRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//http://localhost:8080/web-app-23544/api/orador
@WebServlet("/api/orador")
public class NuevoOradorController extends AppBaseServlet{
	
	//ahora por medio del repository guarda en la db
	private OradorRepository repository = new MySqlOradorRepository();
	
	//crear > POST
	protected void doPost(
				HttpServletRequest request, //aca viene lo que manda el usuario 
				HttpServletResponse response /*manda el backend al frontend*/
			) throws ServletException, IOException {
		
		//OradorRequest oradorJson = (OradorRequest )fromJSON(OradorRequest.class, request, response);
		//obtengo el json desde el frontend
		String json = super.toJson(request); 
		
		//convierto de json String a Objecto java usando libreria de jackson2
		OradorRequest oradorRequest = mapper.readValue(json, OradorRequest.class);
		
		//creo mi orador con esos parametros
		Orador nuevo = new Orador(
				oradorRequest.getNombre(), 
				oradorRequest.getApellido(),
				oradorRequest.getEmail(),
				oradorRequest.getTema(),
				LocalDate.now()
		);
		
		repository.save(nuevo);
		
		//ahora respondo al front: json, Convirtiendo el nuevo Orador a json
		String jsonParaEnviarALFrontend = mapper.writeValueAsString(nuevo);
		
		response.getWriter().print(jsonParaEnviarALFrontend);
	}

	protected void doGet(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		//ahora por medio del repository guarda en la db
		List<Orador> listado = this.repository.findAll();
		
		//convierto Objecto java a json string
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
	
	protected void doPut(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		String id  = request.getParameter("id");
		
		//ahora quiero los datos que viene en el body
		String json = super.toJson(request); 
		
		//convierto de json String a Objecto java usando libreria de jackson2
		OradorRequest oradorRequest = mapper.readValue(json, OradorRequest.class);

		//busco el orador en la db
		Orador orador = this.repository.getById(Long.parseLong(id));
		
		//ahora actualizo los datos
		orador.setApellido(oradorRequest.getApellido());
		orador.setNombre(oradorRequest.getNombre());
		orador.setMail(oradorRequest.getEmail());
		orador.setTema(oradorRequest.getTema());
		
		//ahora si, actualizo en la db!!
		this.repository.update(orador);
		
		//le informa al front ok
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
