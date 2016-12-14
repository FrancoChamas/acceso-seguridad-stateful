package com.fac.seguridad.accesoseguridadstateful.entrypoint;

import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * @author Franco Antonio Chamás.
 * Entry point de seguridad.
 *
 */
public class AccesoAuthenticationProcessingEntryPoint extends LoginUrlAuthenticationEntryPoint {

	public AccesoAuthenticationProcessingEntryPoint(String loginFormUrl) {
		super(loginFormUrl);

	}

}
