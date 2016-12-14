package com.fac.seguridad.accesoseguridadstateful.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.fac.seguridad.accesoseguridadstateful.exception.BadCredentialsException;

/**
 * Interfaz que se implementa para hacer la auditoria al momento de generar el tocken.
 * @author Franco Antonio Chamas.
 *
 */
public interface AccesoAuditoriaService {
	/**
	 * M�todo que realiza la auditoria al generar el tocken.
	 * @param accesoUserDetails objeto que contiene las credenciales y roles.
	 * @param idAplicativo identificador del aplicativo.
	 * @throws BadCreateTockenException en caso que exista un error en la autorizaci�n.
	 */
	void auditoria(final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken, int idAplicativo) throws BadCredentialsException;
}
