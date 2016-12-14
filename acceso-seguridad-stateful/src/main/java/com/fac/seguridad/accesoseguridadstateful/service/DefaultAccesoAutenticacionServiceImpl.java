package com.fac.seguridad.accesoseguridadstateful.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.fac.seguridad.accesoseguridadstateful.dominio.AccesoUserDetails;
import com.fac.seguridad.accesoseguridadstateful.dominio.AccesoUsernamePasswordAuthenticationToken;
import com.fac.seguridad.accesoseguridadstateful.exception.BadCredentialsException;
import com.fac.seguridad.accesoseguridadstateful.mock.MockAutenticarAutorizarAuditoria;

/**
 * Implementaci�n del autenticador, simula una BD, solo a modo de test.
 * @author Franco Antonio Cham�s.
 */
public class DefaultAccesoAutenticacionServiceImpl implements AccesoAutenticacionService {

	private static Logger logger = LoggerFactory.getLogger(DefaultAccesoAutenticacionServiceImpl.class);

	public UsernamePasswordAuthenticationToken autenticar(UsernamePasswordAuthenticationToken
			usernamePasswordAuthenticationToken) throws BadCredentialsException {
		UsernamePasswordAuthenticationToken retVal = null;
		logger.debug("autenticando usuario.");
		validarCredenciales(usernamePasswordAuthenticationToken);
		
		retVal = MockAutenticarAutorizarAuditoria.autenticar(usernamePasswordAuthenticationToken);
		
		if(usernamePasswordAuthenticationToken.getDetails() == null) {
			logger.error("error al autenticar:" + BadCredentialsException.MSJ_ERROR_AUTENTICACION);
			throw new BadCredentialsException(BadCredentialsException.MSJ_ERROR_AUTENTICACION);
		}
		return retVal;
	}
	
	/**
	 * M�todo que valida que el usuario o la contrase�a tengan contenido.
	 * @param accesoUserDetails objeto que contiene las credenciales.
	 */
	private void validarCredenciales(UsernamePasswordAuthenticationToken 
			usernamePasswordAuthenticationToken) throws BadCredentialsException {
		logger.debug("Validando credenciales");
		
		AccesoUsernamePasswordAuthenticationToken AccesoUsernamePasswordAuthenticationToken = (AccesoUsernamePasswordAuthenticationToken) 
				usernamePasswordAuthenticationToken;
		UserDetails accesoUserDetails = (AccesoUserDetails) AccesoUsernamePasswordAuthenticationToken.getPrincipal();
				
		if(accesoUserDetails.getUsername() == null || accesoUserDetails.getUsername().trim().equals("") ) {
			throw new BadCredentialsException(BadCredentialsException.MSJ_USUARIO_BLANCO);
		}
		
		if(accesoUserDetails.getPassword() == null || accesoUserDetails.getPassword().trim().equals("") ) {
			throw new BadCredentialsException(BadCredentialsException.MSJ_CONTRASENIA_BLANCO);
		}
	}

}
