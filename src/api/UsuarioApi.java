package api;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import exceptions.FruitException;
import model.Empresa;
import model.JsonString;
import model.Particular;
import model.Token;
import model.Usuario;
import service.ServiceManager;

@Path("/usuario")
@Produces("application/json;charset=UTF-8")
@Consumes("application/json;charset=UTF-8")
public class UsuarioApi {
	
	private ServiceManager service;
	
	public UsuarioApi() {
		service=new ServiceManager();
	}
	
	@GET
	@Path("/{id}")	
	public Response getUsuarioById(@PathParam("id") String id) {
		Response response = null;
		try {
			Usuario u = service.getUsuarioById(id);
			//Usuario tipo 1 particular, tipo 2 empresa, (TODO: tipo 3 = admins ), tipo 0 = null.
			int tipo = u.getTipo();
			if(tipo == 1){
				response = Response.ok(service.getParticular(id, u)).build();
			}
			if(tipo == 2) {
				response = Response.ok(service.getEmpresa(id, u)).build();
			}
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return response;
	}
	
	@GET
	@Path("/sendEmail/{email}")	
	public Response sendEmail(@PathParam("email") String email) {
		try {
			return Response.ok(service.getEmail(email)).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/login")
	public Response checkLogin(@QueryParam("username") String username, 
			@QueryParam("pass") String pass, @QueryParam("email") String email) {
		try {
			Usuario u = null;
			if(username == null) {
				u = service.checkLoginEmail(email, pass);
			}else {
				u = service.checkLogin(username, pass);
			}
			Token token = service.generateToken(u);
			return Response.ok(token).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Path("/particular")
	public Response postParticular(Particular p) {
		try {
			return Response.ok(service.postParticular(p)).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Path("/empresa")
	public Response postEmpresa(Empresa em) {
		try {
			return Response.ok(service.postEmpresa(em)).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@PUT
	@Path("secured/particular")
	public Response putParticular(Particular p) {
		try {
			service.putParticular(p);
			return Response.ok(new JsonString("Usuario modificado correctamente.")).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@PUT
	@Path("secured/empresa")
	public Response putEmpresa(Empresa em) {
		try {
			service.putEmpresa(em);
			return Response.ok(new JsonString("Usuario modificado correctamente.")).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
}
