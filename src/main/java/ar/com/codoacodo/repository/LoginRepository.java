package ar.com.codoacodo.repository;

import ar.com.codoacodo.entity.User;

public interface LoginRepository {

	public User login(String username, String password);
}
