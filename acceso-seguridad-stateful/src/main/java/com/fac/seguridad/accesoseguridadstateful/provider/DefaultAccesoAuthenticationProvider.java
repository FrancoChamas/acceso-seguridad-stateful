package com.fac.seguridad.accesoseguridadstateful.provider;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fac.seguridad.accesoseguridadstateful.dominio.AccesoUsernamePasswordAuthenticationToken;
import com.fac.seguridad.accesoseguridadstateful.service.AccesoAuditoriaService;
import com.fac.seguridad.accesoseguridadstateful.service.AccesoAutenticacionService;
import com.fac.seguridad.accesoseguridadstateful.service.AccesoAutorizadorService;
import com.fac.seguridad.accesoseguridadstateful.service.DefaultAccesoAuditoriaServiceImpl;
import com.fac.seguridad.accesoseguridadstateful.service.DefaultAccesoAutenticacionServiceImpl;
import com.fac.seguridad.accesoseguridadstateful.service.DefaultAccesoAutorizadorServiceImpl;

/**
 * @author Franco Antonio Chamás.
 * Clase proveedor de autenticación.
 */
public class DefaultAccesoAuthenticationProvider implements AuthenticationProvider, InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(DefaultAccesoAuthenticationProvider.class);
	@Autowired(required=false)
	AccesoAutenticacionService accesoAutenticacionService;
	@Autowired(required=false)
	AccesoAutorizadorService accesoAutorizadorService;
	@Autowired(required=false)
	AccesoAuditoriaService accesoAuditoriaService;
	private int idAplicacion;
	
	public void afterPropertiesSet() throws ServletException {
		logger.debug("invocando afterPropertiesSet");
		validateInstance();
	}
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.debug("Iniciando proceso de autenticaci�n.");
		AccesoUsernamePasswordAuthenticationToken accesoUsernamePasswordAuthenticationToken = (AccesoUsernamePasswordAuthenticationToken)authentication;
		
		validateInstance();
		accesoUsernamePasswordAuthenticationToken = (AccesoUsernamePasswordAuthenticationToken) accesoAutenticacionService.autenticar(accesoUsernamePasswordAuthenticationToken);
		accesoUsernamePasswordAuthenticationToken = (AccesoUsernamePasswordAuthenticationToken) accesoAutorizadorService.autorizar(accesoUsernamePasswordAuthenticationToken, idAplicacion);
		accesoAuditoriaService.auditoria(accesoUsernamePasswordAuthenticationToken, idAplicacion);
		
		logger.debug("Registrando el token de seguridad del usuario ...");
		SecurityContextHolder.getContext().setAuthentication(accesoUsernamePasswordAuthenticationToken);
		return accesoUsernamePasswordAuthenticationToken;
	}
	
	/**
	 * Obtiene una instancia de DefaultAccesoAutenticacionServiceImpl.
	 * @return  DefaultAccesoAutenticacionServiceImpl
	 */
	private DefaultAccesoAutenticacionServiceImpl getDefaultAccesoAutenticacionServiceImpl() {
		return new DefaultAccesoAutenticacionServiceImpl();
	}
	
	/**
	 * Obtiene una instancia de DefaultAccesoAutorizadorServiceImpl.
	 * @return  DefaultAccesoAutorizadorServiceImpl
	 */
	private DefaultAccesoAutorizadorServiceImpl getDefaultAccesoAutorizadorServiceImpl() {
		return new DefaultAccesoAutorizadorServiceImpl();
	}
	
	/**
	 * Obtiene una instancia de DefaultAccesoAutorizadorServiceImpl.
	 * @return  DefaultAccesoAutorizadorServiceImpl
	 */
	private DefaultAccesoAuditoriaServiceImpl getDefaultAccesoAuditoriaServiceImpl() {
		return new DefaultAccesoAuditoriaServiceImpl();
	}
	
	/**
	 * Valida que exista las instancias necesarias para ejecutar los procesos.
	 **/
	private void validateInstance() {
		logger.debug("Validando instancias");
		accesoAutenticacionService = accesoAutenticacionService == null ? getDefaultAccesoAutenticacionServiceImpl() : accesoAutenticacionService;
		accesoAutorizadorService = accesoAutorizadorService == null ? getDefaultAccesoAutorizadorServiceImpl() : accesoAutorizadorService;
		accesoAuditoriaService = accesoAuditoriaService == null ? getDefaultAccesoAuditoriaServiceImpl() : accesoAuditoriaService;
	}

	public boolean supports(Class<?> authentication) {
		return authentication.equals(AccesoUsernamePasswordAuthenticationToken.class);
	}

}
