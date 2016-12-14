package com.fac.seguridad.accesoseguridadstateful.dominio;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Clase que representa un UsernamePasswordAuthenticationToken.
 * @author Franco Antonio Chamas.
 *
 */
public class AccesoUsernamePasswordAuthenticationToken  extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param principal objeto principal.
	 * @param credentials contrase�a.
	 */
	public AccesoUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);	
	}
	
	/**
	 * Constructor, el objeto resultante se usa con wrapper pero NO esta autenticado.
	 * @param principal objeto principal.
	 * @param credentials contrase�a.
	 */
	public AccesoUsernamePasswordAuthenticationToken(String principal, String credentials) {
		super(new AccesoUserDetails(principal, credentials), credentials);
	}
	
	/**
	 * Constructor, el objeto resultante representa un usuario AUTENTICADO completamente.
	 * @param principal pbjeto principal.
	 * @param credentials contrase�a.
	 */
	public AccesoUsernamePasswordAuthenticationToken(UserDetails principal, Object credentials) {
		super(principal, credentials, principal.getAuthorities());
	}
	
}
