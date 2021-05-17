package api;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import exceptions.FruitException;
import model.Producto;
import service.ServiceManager;

@Path("/producto")
@Produces("application/json;charset=UTF-8")
@Consumes("application/json;charset=UTF-8")
public class ProductoApi {

	private ServiceManager service;
	
	public ProductoApi() {
		service=new ServiceManager();
	}
	
	//Devuelve todos los productos y todos los productos con filtro.
	@GET
	@Path("/")
	public Response getProductos(@QueryParam("filtro") String f) {
		try {
			ArrayList<Producto> productos = service.getProductos(f);
			return Response.ok(productos).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("{id}")	
	public Response getProductoById(@PathParam("id") int id) {
		Response response = null;
		try {
			Producto p = service.getProductoById(id);
			response = Response.ok(p).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return response;
	}
	
	//getProductosByOrigen, getProductosByFamilia, getProductosByMarca.
	@GET
	@Path("/{tipo}/{nombre}")
	public Response getProductosByString(@PathParam("tipo") String tipo, @PathParam("nombre") String nombre) {
		try {
			ArrayList<Producto> productos = service.getProductosBy(nombre, tipo);
			return Response.ok(productos).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	//getProductosByMas_vendidos, getProductosByNuevo
	@GET
	@Path("/inicio/{tipo}")
	public Response getProductosByBool(@PathParam("tipo") String tipo) {
		try {
			ArrayList<Producto> productos = service.getProductosByBoolean(tipo);
			return Response.ok(productos).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	//Devuelve frutas si el tipo es 1 y verduras si el tipo es 2.
	@GET
	@Path("/tipo/{tipo}")
	public Response getProductosByBool(@PathParam("tipo") int tipo) {
		try {
			ArrayList<Producto> productos = service.getProductosByTipo(tipo);
			return Response.ok(productos).build();
		} catch (FruitException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
}
