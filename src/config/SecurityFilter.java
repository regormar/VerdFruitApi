package config;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import exceptions.FruitException;
import service.ServiceManager;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

	private ServiceManager service;
	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String SECURED_URL_PREFIX = "secured";
	
	public SecurityFilter() {
		service = new ServiceManager();
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		//Aplico el filtro solo a las llamdas que contengan "secured" en el path.
		if(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
			//Guardo todos los headers con la clave "Athorization".
			List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			//Compruebo que hay un header de autorización.
			System.out.println(authHeader);
			if(authHeader != null && authHeader.size() > 0) {
				String authToken = authHeader.get(0);
				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				byte[] decodedArr = Base64.getDecoder().decode(authToken);
				String decodedString = new String(decodedArr, "UTF-8");
				String[] decodedArray = decodedString.split(":");
				String username = decodedArray[0];
				String pass =  decodedArray[1];
				try {
					service.checkLogin(username, pass);
					return;
				} catch (FruitException e) {	
					System.out.println(e.getMessage());
				}
			}
			Response unauthorizedStatus = Response
					.status(Response.Status.UNAUTHORIZED)
					.entity("< El usuario no puede acceder a este recurso >").build();
			requestContext.abortWith(unauthorizedStatus);
		}	
	}

}
