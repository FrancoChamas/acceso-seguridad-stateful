package com.fac.seguridad.accesoseguridadstateful.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Franco Antonio Cham�s.
 * Exception para manejar los errores de autenticaci�n.
 *
 */
public class BadCredentialsException extends AuthenticationException {
	private static final long serialVersionUID = 1L;
	
	public static final String MSJ_ERROR = "Error de autenticaci�n.";
	public static final String MSJ_ERROR_AUTENTICACION = "Error, usuario o contrase�a incorrecta.";
	public static final String MSJ_ERROR_AUTORIZACION = "Error, no posee roles para esta aplicaci�n.";
	public static final String MSJ_USUARIO_BLANCO = "Error, usuario no puede estan en blanco.";
	public static final String MSJ_CONTRASENIA_BLANCO = "Error, la contrase�a no puede estan en blanco.";
	
	/**
	 * Constructor con par�metro
	 * 
	 * @param message
	 *            mensaje a mostrar.
	 */
	public BadCredentialsException(String message) {
		super(message);
	}

	/**
	 * Constructor con par�metro
	 * 
	 * @param message
	 *            mensaje a mostrar
	 * @param throwable
	 *            excepci�n.
	 */
	public BadCredentialsException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
