package com.fac.seguridad.accesoseguridadstateful.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.fac.seguridad.accesoseguridadstateful.exception.BadCredentialsException;

/**
 * Interfaz que se implementa para autorizar a los usuarios.
 * @author Franco Antonio Chamas.
 *
 */
public interface AccesoAutorizadorService {
	/**
	 * M�todo que autoriza al usuario.
	 * @param idUsuario identificador del usuario .
	 * @param idAplicativo identificador del aplicativo.
	 * @return una implementacion de UserDetails con la lista de roles.
	 * @throws BadCredentialsException en caso que exista un error en la autorizaci�n.
	 */
	UsernamePasswordAuthenticationToken autorizar(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken, int idAplicacion) throws BadCredentialsException;
}
