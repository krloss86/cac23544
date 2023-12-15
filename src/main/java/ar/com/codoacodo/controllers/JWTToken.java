package ar.com.codoacodo.controllers;

public class JWTToken {

	private String jwt;
	
	public JWTToken(String jwt) {
		this.jwt = jwt;			
	}

	public String getJwt() {
		return jwt;
	}
	
}
