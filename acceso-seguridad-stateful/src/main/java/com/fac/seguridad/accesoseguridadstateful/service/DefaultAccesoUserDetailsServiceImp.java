package com.fac.seguridad.accesoseguridadstateful.service;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fac.seguridad.accesoseguridadstateful.mock.MockAutenticarAutorizarAuditoria;

/**
 * Clase servicio que provee un IserDetails.
 * 
 * @author Franco Antonio Cham�s.
 *
 */
public class DefaultAccesoUserDetailsServiceImp implements AccesoUserDetailsService, InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(DefaultAccesoUserDetailsServiceImp.class);

	public void afterPropertiesSet() throws ServletException {
		logger.debug("Invocando afterPropertiesSet.");
		validateInstance();
	}

	/**
	 * M�todo que retorna un UserDetails .
	 * 
	 * @author Franco Antonio Cham�s.
	 *
	 */
	public final UserDetails loadUserByUsername(String token)
			throws UsernameNotFoundException, BadCredentialsException {
		logger.debug("Cargando usuario por nombre de usuario.");
		throw new BadCredentialsException("El m�todo no debe utilizarse en esta implementaci�n.");
	}

	/**
	 * M�todo que retorna un UserDetails .
	 * 
	 * @author Franco Antonio Cham�s.
	 *
	 */
	public final UserDetails loadUserByUsername(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken, int idAplicacion)
			throws UsernameNotFoundException, BadCredentialsException {
		UserDetails retVal = null;
		logger.debug("Cargando usuario por nombre de usuario.");
		validateInstance();
		
		retVal = MockAutenticarAutorizarAuditoria.autorizar(usernamePasswordAuthenticationToken, idAplicacion);

		if (retVal == null) {
			logger.error("Error obteniendo detalle de usuario, Usuario no v�lido.");
			throw new UsernameNotFoundException("Usuario no v�lido.");
		}
		return retVal;
	}

	/**
	 * Valida que exista una instancia de userDetailsService, si no existe
	 * asigna una implementaci�n por defecto.
	 */
	private void validateInstance() {
		logger.debug("Validando instancias.");
//		tokenService = tokenService == null ? getTokenService() : tokenService;
	}

}
