package com.fac.seguridad.accesoseguridadstateful.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * interfaz UserDetailsService con loadUserByUsername.
 * @author Franco Antonio Cham�s.
 *
 */
public interface AccesoUserDetailsService extends UserDetailsService {
	/**
	 * Obtiene el UserDetails, con los roles par el usuario en esa aplicaci�n.
	 * @param usernamePasswordAuthenticationToken objeto UsernamePasswordAuthenticationToken.
	 * @param idAplicacion identificador del aplicativo.
	 * @return UserDetails.
	 */
	UserDetails loadUserByUsername(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken, int idAplicacion);
}
