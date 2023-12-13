package ar.com.codoacodo.entity;

public class User {
	private Long id;
	private String nombre; 
	private String username; 
	private String password;
	
	public User(Long id, String nombre, String username, String password) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	} 
	
}
