package com.fac.seguridad.accesoseguridadstateful.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fac.seguridad.accesoseguridadstateful.dominio.AccesoUsernamePasswordAuthenticationToken;

/**
 * Filtro que procesa una llamada de autenticació desde un formularios.
 * @author Franco Antonio Chamás.
 */
public class AccesoUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	  public static final String DOMAIN_CHAR_DELIMITER = "\\";
	  public static final String DEFAUT_FILTER_PROCESSES_URL = "/j_spring_security_check";
	  public static final String SPRING_SECURITY_FORM_LOGED_USER = "j_loged_user";
	  public static final String SPRING_SECURITY_FORM_LOGED_FROM = "j_loged_from";

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

	private static Logger logger = LoggerFactory.getLogger(AccesoUsernamePasswordAuthenticationFilter.class);
	/**
	 * Constructor
	 */
	public AccesoUsernamePasswordAuthenticationFilter() {
		setFilterProcessesUrl("/j_spring_security_check");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		logger.debug("procesando solicitud de autenticaci�n");
		
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		
		UsernamePasswordAuthenticationToken authRequest = getUsernamePasswordAuthenticationToken(request);

		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	/** 
	 * Obtiene un AccesoUsernamePasswordAuthenticationToken con las credenciales del request.
	 * @param request request enviados desde el filtro.
	 * @return objeto resultante.
	 */
	private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(HttpServletRequest request) {
		logger.debug("obteniendo UsernamePasswordAuthenticationToken.");
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		username = (username == null)? "" : username.trim();
		password = (password == null)? "" : password;

		UsernamePasswordAuthenticationToken authRequest = new AccesoUsernamePasswordAuthenticationToken(username, password);
		return authRequest;
	}
	
	protected String obtainUsername(HttpServletRequest request) {
		logger.debug("obteniendo username");
		return request.getParameter(usernameParameter);
	}

	protected String obtainPassword(HttpServletRequest request) {
		logger.debug("obteniendo password");
		return request.getParameter(passwordParameter);
	}
}
