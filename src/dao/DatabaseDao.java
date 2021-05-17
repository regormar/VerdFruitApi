package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import config.ConstantsApi;
import model.Empresa;
import model.JsonString;
import model.ListaProductos;
import model.Particular;
import model.Pedido;
import model.Producto;
import model.Usuario;

public class DatabaseDao {

	private Connection conexion;	
	
	public void conectar() throws SQLException{
		String url = ConstantsApi.CONNECTION;
        String user = ConstantsApi.USER_CONNECTION;
        String pass = ConstantsApi.PASS_CONNECTION;
        conexion = DriverManager.getConnection(url, user, pass);
	}
	
    public void desconectar() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }

//USUARIO-----------------------------------------------------------------------------------------
    
    //Función que se encarga de generar el objeto de tipo Usuario a partir del ResultSet.
    public Usuario generateUser(ResultSet rs) throws SQLException {
    	return new Usuario(
				rs.getString("id_usuario"),
				rs.getString("username"),
				rs.getString("pass"),
				rs.getString("email"),
				rs.getString("nombre"),
				rs.getString("apellidos"),
				rs.getString("direccion"),
				rs.getString("direccion2"),
				rs.getString("telefono"),
				rs.getInt("tipo"));  
    }

	public void postParticular(Particular p) throws SQLException {
    	Usuario u = postUsuario((Usuario)p);
    	conectar();
    	String insert2 = ConstantsApi.INSERT_PROPIETARIO;
    	try (PreparedStatement ps2 = conexion.prepareStatement(insert2)){
    		ps2.setString(1, u.getId());
    		ps2.setString(2, p.getDni());
    		ps2.executeUpdate();
    	}
    	desconectar();
	}
	
	public Empresa postEmpresa(Empresa em) throws SQLException {
    	Usuario u = postUsuario((Usuario)em); 	
    	conectar();
    	String insert2 = ConstantsApi.INSERT_EMPRESA;
    	try (PreparedStatement ps2 = conexion.prepareStatement(insert2)){
    		ps2.setString(1, u.getId());
    		ps2.setString(2, em.getCif());
    		ps2.setString(3, em.getNombreFiscal());
    		ps2.setString(4, em.getNombreComercial());
    		ps2.executeUpdate();
    	}
    	desconectar();
    	return em;
	}
	
	public Usuario postUsuario(Usuario u) throws SQLException {
		u.setDireccion2(null);
		conectar();
    	String insert = ConstantsApi.INSERT_USUARIO;
    	try(PreparedStatement ps = conexion.prepareStatement(insert,Statement.RETURN_GENERATED_KEYS)){
    		ps.setString(1, u.getId());
    		ps.setString(2, u.getUsername());
    		ps.setString(3, u.getPass());
    		ps.setString(4, u.getEmail());
    		ps.setString(5, u.getNombre());
    		ps.setString(6, u.getApellidos());
    		ps.setString(7, u.getDireccion());
    		ps.setString(8, u.getDireccion2());
    		ps.setString(9, u.getTelefono());
    		ps.setInt(10, u.getTipo());
    		ps.executeUpdate();
    	}
    	desconectar();
		return u;
	}

	public void putUsuario(Usuario u) throws SQLException {
    	conectar();
    	String update = ConstantsApi.UPDATE_USUARIO;
    	try(PreparedStatement ps = conexion.prepareStatement(update)){
    		ps.setString(1, u.getUsername());
    		ps.setString(2, u.getPass());
    		ps.setString(3, u.getEmail());
    		ps.setString(4, u.getNombre());
    		ps.setString(5, u.getApellidos());
    		ps.setString(6, u.getDireccion());
    		ps.setString(7, u.getDireccion2());
    		ps.setString(8, u.getTelefono());
    		ps.setInt(9, u.getTipo());
    		ps.setString(10, u.getId());
    		ps.executeUpdate();
    	}
    	desconectar();
	}
	
	public void putParticular(Particular p) throws SQLException{
    	conectar();
    	String update = ConstantsApi.UPDATE_PARTICULAR;
    	try(PreparedStatement ps = conexion.prepareStatement(update)){
    		ps.setString(1, p.getDni());
    		ps.setString(2, p.getId());
    		ps.executeUpdate();
    	}
    	desconectar();
	}
	
	public void putEmpresa(Empresa em) throws SQLException{
    	conectar();
    	String update = ConstantsApi.UPDATE_EMPRESA;
    	try(PreparedStatement ps = conexion.prepareStatement(update)){
    		ps.setString(1, em.getCif());
    		ps.setString(2, em.getNombreFiscal());
    		ps.setString(3, em.getNombreComercial());
    		ps.setString(4, em.getId());
    		ps.executeUpdate();
    	}
    	desconectar();
	}
	
	public Usuario getUserBy(String data, String select) throws SQLException {
		conectar();
    	Usuario u = null;
    	try (PreparedStatement ps = conexion.prepareStatement(select)) { 
    	  	ps.setString(1,data); 
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            		u = generateUser(rs);        		
            	}
            }
        }    
    	desconectar();
    	return u;	
	}
	
	public Particular getParticularByDNI(String dni, Particular p) throws SQLException {
		conectar();
    	try (PreparedStatement ps = conexion.prepareStatement(ConstantsApi.GET_USER_BY_DNI)) { 
    	  	ps.setString(1,dni); 
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {           		
    				p.setId(rs.getString("id_particular"));
    				p.setDni(rs.getString("dni"));      		
            	}
            }
        }    
    	desconectar();
    	return p;	
	}
	
	public Particular getParticularBy(String data, Usuario u, String select) throws SQLException {
		conectar();
		Particular particular = null;
    	try (PreparedStatement ps = conexion.prepareStatement(select)) { 
    	  	ps.setString(1,data); 
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) { 
            		particular = new Particular(
            						rs.getString("id_particular"),
		            				u.getUsername(),
		            				u.getPass(),
		            				u.getEmail(),
		            				u.getNombre(),
		            				u.getApellidos(),
		            				u.getDireccion(),
		            				u.getDireccion2(),
		            				u.getTelefono(),
		            				u.getTipo(),
		            				rs.getString("dni")
	            				);     		
            	}
            }
        }    
    	desconectar();
    	return particular;
	}
	
	public Empresa getEmpresaBy(String data, Usuario u, String select) throws SQLException {
		conectar();
		Empresa empresa = null;
    	try (PreparedStatement ps = conexion.prepareStatement(select)) { 
    	  	ps.setString(1,data); 
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) { 
            		empresa = new Empresa(
            						rs.getString("id_empresa"),
		            				u.getUsername(),
		            				u.getPass(),
		            				u.getEmail(),
		            				u.getNombre(),
		            				u.getApellidos(),
		            				u.getDireccion(),
		            				u.getDireccion2(),
		            				u.getTelefono(),
		            				u.getTipo(),
		            				rs.getString("cif"),
		            				rs.getString("nombre_fiscal"),
		            				rs.getString("nombre_comercial")
        					);     		
            	}
            }
        }    
    	desconectar();
    	return empresa;
	}
	
	public Usuario checkLogin(String login, String pass, String select) throws SQLException {
		conectar();
    	Usuario u = null;
    	try (PreparedStatement ps = conexion.prepareStatement(select)) { 
    	  	ps.setString(1,login); 
    	  	ps.setString(2,pass); 
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            		u = generateUser(rs);       		
            	}
            }
        }   
    	desconectar();
    	return u;	
	}
	
	public boolean getParticularByDni(String dni) throws SQLException{
		conectar();
    	try (PreparedStatement ps = conexion.prepareStatement(ConstantsApi.GET_USER_BY_DNI)) { 
    	  	ps.setString(1,dni);  
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            		return true;
            	}
            }
        }   
    	desconectar();
		return false;
	}
	
	public JsonString checkEmail(String email) throws SQLException {
		conectar();
    	JsonString e = null;
    	try (PreparedStatement ps = conexion.prepareStatement(ConstantsApi.GET_EMAIL)) { 
    	  	ps.setString(1,email); 
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            		e = new JsonString(rs.getString("email"));      		          	
            	}
            }
        }   
    	desconectar();
    	return e;	
	}
	
//PRODUCTO------------------------------------------------------------------------------------------------
	   
    //Función que se encarga de generar el objeto de tipo Producto a partir del ResultSet.
    public Producto generateProducto(ResultSet rs) throws SQLException {
    	return new Producto(
				 rs.getInt("id_producto"),
				 rs.getString("nombre_producto"),
				 rs.getString("descripcion"),
				 rs.getString("origen"),
				 rs.getString("familia"),
				 rs.getString("marca"),
				 rs.getDouble("precio"),
				 rs.getInt("tipo_producto"),
				 rs.getInt("stock"),
				 rs.getString("img"),
				 rs.getBoolean("mas_vendido"),
				 rs.getBoolean("nuevo")
				 );  
    }
	
    public ArrayList<Producto> getProductos(String f) throws SQLException{
    	conectar();
    	ArrayList<Producto> productos = new ArrayList<>();
    	String select;
    	if(f==null) {
    		select = ConstantsApi.GET_PRODUCTOS;
    	}else {
    		select = ConstantsApi.GET_PRODUCTOS_FILTRO + "'%"+ f +"%'";
    	}
    	try (Statement st = conexion.createStatement()) { 
            try (ResultSet rs = st.executeQuery(select)) {
                while(rs.next()){
            		 Producto producto = generateProducto(rs);
                     productos.add(producto);     		          	
            	}
            }
        }   
    	desconectar();
    	return productos;
    }
	
    public Producto getProductoById(int id) throws SQLException {
    	conectar();
    	Producto p = null;
    	try (PreparedStatement ps = conexion.prepareStatement(ConstantsApi.GET_PRODUCTO_BY_ID)) { 
    	  	ps.setInt(1,id); 
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            		p = generateProducto(rs);      		
            	}
            }
        }    
    	desconectar();
    	return p;	
	}
    
    public ArrayList<Producto> getProductosBy(String w, String select) throws SQLException{
    	conectar();
    	ArrayList<Producto> productos = new ArrayList<>();
    	try (PreparedStatement ps = conexion.prepareStatement(select)) { 
    	  	ps.setString(1, w); 
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
            		 Producto producto = generateProducto(rs);
                     productos.add(producto);     		          	
            	}
            }
        }   
    	desconectar();
    	return productos;
    }
    
    public ArrayList<Producto> getProductosByBoolean(String select) throws SQLException{
    	conectar();
    	ArrayList<Producto> productos = new ArrayList<>();
    	try (Statement st = conexion.createStatement()) { 
            try (ResultSet rs = st.executeQuery(select)) {
                while(rs.next()){
            		 Producto producto = generateProducto(rs);
                     productos.add(producto);     		          	
            	}
            }
        }   
    	desconectar();
    	return productos;
    }
    
    public ArrayList<Producto> getProductosByTipo(int tipo) throws SQLException{
    	conectar();
    	ArrayList<Producto> productos = new ArrayList<>();
    	try (PreparedStatement ps = conexion.prepareStatement(ConstantsApi.GET_PRODUCTO_BY_TIPO)) { 
    	  	ps.setInt(1, tipo); 
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
            		 Producto producto = generateProducto(rs);
                     productos.add(producto);     		          	
            	}
            }
        }   
    	desconectar();
    	return productos;
    }
    
 //PEDIDO---------------------------------------------------------------------------------------------
    
  //Función que se encarga de generar el objeto de tipo Pedido a partir del ResultSet.
    public Pedido generatePedido(ResultSet rs) throws SQLException {
    	return new Pedido(
				rs.getString("id_usuario"),
				rs.getString("id_listaproducto"),
				rs.getDate("fecha_creacion"),
				rs.getInt("estado"),
				rs.getInt("cantidad_productos"),
				rs.getDouble("precio_final"),
				new ArrayList<ListaProductos>()
				);   
    }
    
    public ArrayList<Pedido> getPedidoBy(String data, String select) throws SQLException {
    	conectar();
    	ArrayList<Pedido> listaPedidos = new ArrayList<>();
    	try (PreparedStatement ps = conexion.prepareStatement(select)) { 
    	  	ps.setString(1,data); 
            try (ResultSet rs = ps.executeQuery()) {
            	while (rs.next()) {
            		Pedido p = generatePedido(rs);
            		listaPedidos.add(p);  
            	}
            }
        }    
    	desconectar();
    	return listaPedidos;	
    }
    	
	public ArrayList<Pedido> getPedidoByStatus(String id_usuario, int status) throws SQLException {
		conectar();
		ArrayList<Pedido> listaPedidos = new ArrayList<>();
    	String select = ConstantsApi.GET_PEDIDO_BY_STATUS;
    	try (PreparedStatement ps = conexion.prepareStatement(select)) { 
    	  	ps.setInt(1, status); 
    	  	ps.setString(2, id_usuario);
            try (ResultSet rs = ps.executeQuery()) {
            	while (rs.next()) {
            		Pedido p = generatePedido(rs);  
            		listaPedidos.add(p);
            	}
            }
        }    
    	desconectar();
    	return listaPedidos;	
	}
    
    public void deleteListaPedidoByUsuario(int id_producto, String id_listaproducto) throws SQLException {
    	conectar();
    	try (PreparedStatement ps = conexion.prepareStatement(ConstantsApi.DELETE_LISTA_PEDIDO)) { 
    	  	ps.setInt(1, id_producto); 
    	  	ps.setString(2, id_listaproducto); 
            ps.executeUpdate();
        }   
    	desconectar();
    }
    
    public void deletePedidoByIdLista(String id_listaproducto) throws SQLException {
    	conectar();
    	try (PreparedStatement ps = conexion.prepareStatement(ConstantsApi.DELETE_PEDIDO)) { 
    	  	ps.setString(1, id_listaproducto); 
            ps.executeUpdate();
        }   
    	desconectar();
    }
    
    public ArrayList<ListaProductos> getListaProductoByIdLista(String id) throws SQLException{
    	conectar();
    	ArrayList<ListaProductos> listaProductos = new ArrayList<>();
    	try (PreparedStatement ps = conexion.prepareStatement(ConstantsApi.GET_LISTA_PEDIDO_BY_ID)) { 
    	  	ps.setString(1,id); 
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
            		 ListaProductos productos = new ListaProductos(            				 
            				 rs.getInt("id_producto"),
            				 rs.getInt("cantidad"),
            				 rs.getDouble("precio")
            				 );
            		 listaProductos.add(productos);     		          	
            	}
            }
        }   
    	desconectar();
    	return listaProductos;
    }

	public Pedido postPedido(Pedido p) throws SQLException {
		conectar();
		try (PreparedStatement ps = conexion.prepareStatement(ConstantsApi.INSERT_PEDIDO)){
			ps.setString(1, p.getId_usuario());
			ps.setString(2, p.getId_listaproducto());
			ps.setDate(3, null);
			ps.setInt(4, p.getEstado());
			ps.setInt(5, p.getCantidad_productos());
			ps.setDouble(6, p.getPrecio_final());
			ps.executeUpdate();
		}
		desconectar();
		return p;
	}
	
	public Pedido updatePedido(Pedido p) throws SQLException {
		conectar();
		try (PreparedStatement ps = conexion.prepareStatement(ConstantsApi.UPDATE_PEDIDO)){
			ps.setString(1, p.getId_usuario());
			ps.setString(2, p.getId_listaproducto());
			ps.setDate(3, p.getFecha_creacion());
			ps.setInt(4, p.getEstado());
			ps.setInt(5, p.getCantidad_productos());
			ps.setDouble(6, p.getPrecio_final());
			ps.setString(7, p.getId_listaproducto());
			ps.executeUpdate();
		}
		desconectar();
		return p;
	}
	
	public ListaProductos insertListaPedido(ListaProductos listaProductos, String id_listaproducto) throws SQLException {
		conectar();
		try (PreparedStatement ps = conexion.prepareStatement(ConstantsApi.INSERT_LISTA_PEDIDO)){
			ps.setString(1, id_listaproducto);
			ps.setInt(2, listaProductos.getId_producto());
			ps.setInt(3, listaProductos.getCantidad());
			ps.setDouble(4, listaProductos.getPrecio());
			ps.executeUpdate();
		}
		desconectar();
		return listaProductos;
	}
	
	public ListaProductos updateListaPedido(ListaProductos listaProductos, String id_listaproducto) throws SQLException {
		conectar();
		try (PreparedStatement ps = conexion.prepareStatement(ConstantsApi.UPDATE_LISTA_PEDIDO)){
			ps.setInt(1, listaProductos.getId_producto());
			ps.setInt(2, listaProductos.getCantidad());
			ps.setDouble(3, listaProductos.getPrecio());
			ps.setInt(4, listaProductos.getId_producto());
			ps.setString(5, id_listaproducto);
			ps.executeUpdate();
		}
		desconectar();
		return listaProductos;
	}
	
	public void updatePedidoStatus(String id_usuario, int status, String id_listaproducto, Date date) throws SQLException {
		conectar();
		try (PreparedStatement ps = conexion.prepareStatement(ConstantsApi.UPDATE_PEDIDO_STATUS)){
			ps.setDate(1, date);
			ps.setInt(2, status);
			ps.setString(3, id_usuario);
			ps.setString(4, id_listaproducto);
			ps.executeUpdate();
		}
		desconectar();
	}
	
}
