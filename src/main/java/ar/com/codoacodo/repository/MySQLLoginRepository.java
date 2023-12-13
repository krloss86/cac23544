package ar.com.codoacodo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ar.com.codoacodo.entity.User;

public class MySQLLoginRepository implements LoginRepository {

	@Override
	public User login(String username, String password) {
		String sql = "select * from users where username = ? and password = ?";

		User entity = null;
		
		try(Connection con = AdministradorDeConexiones.getConnection()) {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet res = statement.executeQuery();// SELECT

			if (res.next()) {
				Long dbId = res.getLong(1);  
				String nombre = res.getString(2);  
				String dbUsername= res.getString(3);  
				String dbPassword = res.getString(4);  
				
				entity = new User(dbId, nombre, dbUsername,dbPassword);
			}
			
		} catch (Exception e) {
			throw new IllegalArgumentException("No se pudo obtener usuario:", e);
		}
		return entity;	
	}

}
