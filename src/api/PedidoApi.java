package api;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import exceptions.FruitException;
import model.Pedido;
import service.ServiceManager;

@Path("secured/pedido")
@Produces("application/json;charset=UTF-8")
@Consumes("application/json;charset=UTF-8")
public class PedidoApi {

	private ServiceManager service;
	
	public PedidoApi() {
		service=new ServiceManager();
	}

	@GET
	@Path("/{id}")
	public Response getPedidoByUsuario(@PathParam("id") String id) {
		Response response = null;
		try {
			ArrayList<Pedido> p = service.getPedidoByUsuario(id);
			response = Response.ok(p).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return response;
	}
	
	@GET
	@Path("/{id_usuario}/{status}")
	public Response getPedidosByStatus(@PathParam("id_usuario") String id_usuario, @PathParam("status") int status) {
		Response response = null;
		try {
			ArrayList<Pedido> p = service.getPedidosByStatus(id_usuario, status);
			response = Response.ok(p).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return response;
	}
	
	@POST
	@Path("/")
	public Response insertPedido(Pedido pedido) {
		Response response = null;
		try {
			Pedido p = service.postPedido(pedido);
			response = Response.ok(p).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return response;
	}
	
	@PUT
	@Path("/")
	public Response updatePedido(Pedido pedido) {
		Response response = null;
		try {
			Pedido p = service.putPedido(pedido);
			response = Response.ok(p).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return response;
	}
	
	@DELETE
	@Path("lista/{id_listaproducto}/{id_producto}")
	public Response deleteProductFromOrder(@PathParam("id_producto") int id_producto, @PathParam("id_listaproducto") String id_listaproducto){
		Response response = null;
		try {
			service.deleteProductFromOrder(id_producto, id_listaproducto);
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return response;
	}
	
	@GET
	@Path("/realizarPedido/{id_usuario}/{id_listaproducto}")
	public Response realizarPedido(@PathParam("id_usuario") String id_usuario, @PathParam("id_listaproducto") String id_listaproducto){
		Response response = null;
		try {
			Pedido p = service.realizarPedido(id_usuario, id_listaproducto);
			response = Response.ok(p).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return response;
	}
	
		
}
