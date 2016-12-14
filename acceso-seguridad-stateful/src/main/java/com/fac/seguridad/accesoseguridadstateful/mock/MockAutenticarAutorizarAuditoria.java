package com.fac.seguridad.accesoseguridadstateful.mock;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.fac.seguridad.accesoseguridadstateful.dominio.AccesoUserDetails;
import com.fac.seguridad.accesoseguridadstateful.dominio.Rol;


/**
 * Mock server para autenticar, autorizar y hacer la auditoria al momento de generar el tocken.
 * @author Franco Antonio Chamás.
 *
 */
public class MockAutenticarAutorizarAuditoria {
	 private static List<AccesoUserDetails> listAccesoUserDetails = new ArrayList<AccesoUserDetails>();
	
	 private static Logger logger = LoggerFactory.getLogger(MockAutenticarAutorizarAuditoria.class);
	 
	/**
	 * Carga los usuario en memoria.
	 */
	public static void loadUser() {
		logger.debug("cargando lista de usuarios en el mock.");
		listAccesoUserDetails.clear();
		
		Set<Rol> roles;
		Rol rol;
		
		AccesoUserDetails accesoUserDetails;
		accesoUserDetails = new AccesoUserDetails();
		accesoUserDetails.setUsername("Admin");
		accesoUserDetails.setPassword("Admin");

		roles = new LinkedHashSet<Rol>();
		rol = new Rol();
		rol.setCodigo("1");
		rol.setNombre("administrador");
		rol.setDescripcion("rol administrador");
		rol.setAuthority("ROLE_ADMIN");
		
		
		roles.add(rol);
		
		accesoUserDetails.setAuthorities(roles);
		
		listAccesoUserDetails.add(accesoUserDetails);
		
		accesoUserDetails = new AccesoUserDetails();
		accesoUserDetails.setUsername("Asistente");
		accesoUserDetails.setPassword("Asistente");
		roles = new LinkedHashSet<Rol>();
		rol = new Rol();
		rol.setCodigo("2");
		rol.setNombre("asistente");
		rol.setDescripcion("rol asistente");
		rol.setAuthority("ROLE_ASISTENTE");
		
		roles.add(rol);
		
		accesoUserDetails.setAuthorities(roles);
		
		listAccesoUserDetails.add(accesoUserDetails);		
		
		accesoUserDetails = new AccesoUserDetails();
		accesoUserDetails.setUsername("Conductor");
		accesoUserDetails.setPassword("Conductor");
	
		roles = new LinkedHashSet<Rol>();
		rol = new Rol();
		rol.setCodigo("3");
		rol.setNombre("conductor");
		rol.setDescripcion("rol conductor");
		rol.setAuthority("ROLE_CONDUCTOR");
		
		roles.add(rol);
		
		accesoUserDetails.setAuthorities(roles);
		
		listAccesoUserDetails.add(accesoUserDetails);
		
		//usuario que tiene dos roles
		accesoUserDetails = new AccesoUserDetails();
		accesoUserDetails.setUsername("SuperAdmin");
		accesoUserDetails.setPassword("SuperAdmin");
	
		roles = new LinkedHashSet<Rol>();
		rol = new Rol();
		rol.setCodigo("1");
		rol.setNombre("Admin");
		rol.setDescripcion("rol Administrador");
		rol.setAuthority("ROLE_ADMIN");
		
		roles.add(rol);
		
		rol = new Rol();
		rol.setCodigo("2");
		rol.setNombre("asistente");
		rol.setDescripcion("rol asistente");
		rol.setAuthority("ROLE_ASISTENTE");
		
		roles.add(rol);
		
		accesoUserDetails.setAuthorities(roles);
		
		listAccesoUserDetails.add(accesoUserDetails);
	}
	
	
	/**
	 * busca el usuario en una list hardcodeada.
	 * @param accesoUserDetails
	 * @return UserDetails implementaci�n del detalle de usuario.
	 * @throws BadCreateTockenException
	 */
	public static UsernamePasswordAuthenticationToken autenticar(UsernamePasswordAuthenticationToken 
			usernamePasswordAuthenticationToken) {
		logger.debug("autenticando en el mock.");
		UserDetails userDetails = null;
		UserDetails accesoUserDetails = (AccesoUserDetails) usernamePasswordAuthenticationToken.getPrincipal();
		String password = (String) usernamePasswordAuthenticationToken.getCredentials();
		
		loadUser();
		
		for(AccesoUserDetails aud : listAccesoUserDetails) {
			if(aud.getUsername().equals(accesoUserDetails.getUsername()) 
					&& aud.getPassword().equals(password)) {
				userDetails = accesoUserDetails;
			}
		}
		
		//asignamos el UserDetails
		usernamePasswordAuthenticationToken.setDetails(userDetails);
		return usernamePasswordAuthenticationToken;
	}
	
	
	
	/**
	 * busca el usuario en una list hardcodeada y retorna el usuario con los roles en caso que sea correcta
	 * las credenciales.
	 * @param accesoUserDetails
	 * @return UserDetails detalles del usuario.
	 * @throws BadCreateTockenException representa un error al autorizar.
	 */
	public static UserDetails autorizar(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken, int idAplicativo) {
		logger.debug("autorizando en el mock");
		UserDetails retVal = null;
		
		UserDetails accesoUserDetails = (AccesoUserDetails) usernamePasswordAuthenticationToken.getPrincipal();
		String password = (String) usernamePasswordAuthenticationToken.getCredentials();
		
		loadUser();
		for(AccesoUserDetails aud : listAccesoUserDetails) {
			if(aud.getUsername().equals(accesoUserDetails.getUsername()) 
					&& aud.getPassword().equals(password)) {
				retVal = aud;;
			}
		}
		
		return retVal;
	}
	
	
	/**
	 * busca el usuario en una list hardcodeada y retorna el usuario con los roles
	 * @param accesoUserDetails
	 * @return
	 * @throws BadCreateTockenException
	 */
	public static void auditar(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
		UserDetails accesoUserDetails = (AccesoUserDetails) usernamePasswordAuthenticationToken.getPrincipal();
		logger.debug("auditando en el mock, usuario: " + accesoUserDetails.getUsername());
	}
}
