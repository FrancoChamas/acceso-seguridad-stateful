package com.fac.seguridad.accesoseguridadstateful.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.fac.seguridad.accesoseguridadstateful.exception.BadCredentialsException;

/**
 * Interfaz que se implementa para autenticar a los usuarios.
 * @author Franco Antonio Cham�s.
 *
 */
public interface AccesoAutenticacionService {
	
	/**
	 * M�todo que autentica al usuario.
	 * @param UsernamePasswordAuthenticationToken objeto que contiene las credenciales y el UserDetails.
	 * @return una implementacion de AccesoUsernamePasswordAuthenticationToken si se auntentica correctamente
	 * @throws BadCreateTockenException en caso que exista un error en la autenticaci�n.
	 */
	UsernamePasswordAuthenticationToken autenticar(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws BadCredentialsException;
}
