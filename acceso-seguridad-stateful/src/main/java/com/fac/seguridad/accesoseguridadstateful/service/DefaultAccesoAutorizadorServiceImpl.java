package com.fac.seguridad.accesoseguridadstateful.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.fac.seguridad.accesoseguridadstateful.dominio.AccesoUserDetails;
import com.fac.seguridad.accesoseguridadstateful.dominio.AccesoUsernamePasswordAuthenticationToken;
import com.fac.seguridad.accesoseguridadstateful.exception.BadCredentialsException;

/**
 * @author Franco Antonio cham�s.
 *clase implementaci�n de un autorizador, simula una BD, solo a modo de test.
 */
public class DefaultAccesoAutorizadorServiceImpl implements AccesoAutorizadorService, InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(DefaultAccesoAutorizadorServiceImpl.class);
	@Autowired(required=false)
	private AccesoUserDetailsService accesoUserDetailsService;
	
	public void afterPropertiesSet() throws Exception {
		logger.debug("invocando afterPropertiesSet");
		validateInstance();
	}
	
	public UsernamePasswordAuthenticationToken autorizar(
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken, int idAplicacion)
			throws BadCredentialsException {
		UsernamePasswordAuthenticationToken retVal;
		
		validarCredenciales(usernamePasswordAuthenticationToken);
		
		UserDetails accesoUserDetails = accesoUserDetailsService.loadUserByUsername(usernamePasswordAuthenticationToken, idAplicacion);
		
		if(accesoUserDetails == null) {
			logger.error("error al autenticar:" + BadCredentialsException.MSJ_ERROR_AUTENTICACION);
			throw new BadCredentialsException(BadCredentialsException.MSJ_ERROR_AUTENTICACION);
		}
		
		retVal = new AccesoUsernamePasswordAuthenticationToken(accesoUserDetails, usernamePasswordAuthenticationToken.getCredentials());
		
		retVal.setDetails(accesoUserDetails);
		

		return retVal;
	}
	
	
	/**
	 * M�todo que valida que el usuario o la contrase�a tengan contenido.
	 * @param accesoUserDetails objeto que contiene las credenciales.
	 */
	private void validarCredenciales(UsernamePasswordAuthenticationToken 
			usernamePasswordAuthenticationToken) throws BadCredentialsException {
		logger.debug("Validando credenciales");
		validateInstance();
		
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
	
	/**
	 * obtiene un DefaultAccesoUserDetailsServiceImp
	 */
	private DefaultAccesoUserDetailsServiceImp getDefaultAccesoUserDetailsServiceImp() {
		return new DefaultAccesoUserDetailsServiceImp();
	}
	
	
	/**
	 * Valida que exista las instancias necesarias para ejecutar los procesos.
	 **/
	private void validateInstance() {
		logger.debug("Validando instancias");
		accesoUserDetailsService = accesoUserDetailsService == null ? getDefaultAccesoUserDetailsServiceImp() : accesoUserDetailsService;
	}

}
