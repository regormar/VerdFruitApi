package service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import config.ConstantsApi;
import dao.DatabaseDao;
import exceptions.FruitException;
import model.Empresa;
import model.JsonString;
import model.ListaProductos;
import model.Particular;
import model.Pedido;
import model.Producto;
import model.Token;
import model.Usuario;

public class ServiceManager {
	private DatabaseDao dao;

	public ServiceManager () {
		this.dao = new DatabaseDao();
	}

//USUARIO---------------------------------------------------------------------------------------
	
	//Función que comprueba si el id generado existe en la bbdd.
	public String generateIdUsuario() throws SQLException {
		String id = "";
		boolean existe = true;
		while(existe) {
		    id = generateRandomId();
		    if(dao.getUserBy(id, ConstantsApi.GET_USER_BY_ID) == null) {
		    	existe = false;
		    }
		}    
	    return id;
	}
	
	public Usuario getUsuarioById(String id) throws FruitException {
		Usuario u = null;
		try {
			u = dao.getUserBy(id, ConstantsApi.GET_USER_BY_ID);
			if(u == null) {
				throw new FruitException(FruitException.USER_NOT_FOUND);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return u;
	}
	
	public Particular getParticular(String id, Usuario u) throws FruitException {
		Particular p = null;
		try {
			p = dao.getParticularBy(id, u, ConstantsApi.GET_PARTICULAR_BY_ID);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return p;
	}
	
	public Empresa getEmpresa(String id, Usuario u) throws FruitException {
		Empresa em = null;
		try {
			em = dao.getEmpresaBy(id, u, ConstantsApi.GET_EMPRESA_BY_ID);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return em;
	}
	
	public void checkUserFields(Usuario u) throws FruitException {
		if(u.getUsername() == null || u.getPass() == null || u.getEmail() == null || u.getNombre() == null 
				|| u.getApellidos() == null || u.getDireccion() == null || u.getTelefono() == null) {
			throw new FruitException(FruitException.FIELDS_LEFT);
		}
		if(u.getUsername().trim().equals("") || u.getPass().trim().equals("") || u.getEmail().trim().equals("")
				|| u.getNombre().trim().equals("") || u.getApellidos().trim().equals("") || u.getDireccion().trim().equals("")
				|| u.getTelefono().trim().equals("")) {
			throw new FruitException(FruitException.EMPTY_FIELDS);
		}
		//Validar patrones de campos de usuario.
		patternMatcher(u.getUsername(), "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$", FruitException.INCORRECT_USERNAME_PATTERN);
		patternMatcher(u.getPass(), "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", FruitException.INCORRECT_PASSWORD_PATTERN);
		patternMatcher(u.getEmail(), "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", FruitException.INCORRECT_EMAIL_PATTERN);
		patternMatcher(u.getTelefono(), "[0-9]{9}", FruitException.INCORRECT_TELEFONO_PATTERN);
	}
	
	public void patternMatcher(String data, String pattern, int exception) throws FruitException {
		Pattern dniPattern = Pattern.compile(pattern);
		Matcher m = dniPattern.matcher(data);
		if(!m.matches()) {
			throw new FruitException(exception);
		}
	}
	
	public void checkParticularFields(Particular p) throws FruitException {
		if(p.getDni() == null) {
			throw new FruitException(FruitException.FIELDS_LEFT);
		}
		patternMatcher(p.getDni(), "[0-9]{8}[A-Za-z]{1}", FruitException.INCORRECT_DNI_PATTERN);
	}
	
	public void checkEmpresaFields(Empresa em) throws FruitException {
		if(em.getCif() == null || em.getNombreFiscal() == null || em.getNombreComercial() == null) {
			throw new FruitException(FruitException.FIELDS_LEFT);
		}
		patternMatcher(em.getCif(), "[A-Za-z]{1}[0-9]{8}", FruitException.INCORRECT_CIF_PATTERN);
	}
	
	public Particular postParticular(Particular p) throws FruitException {
		try {	
			checkUserFields((Usuario)p);
			checkParticularFields(p);
			if(p.getTipo() != 1) {
				throw new FruitException(FruitException.INCORRECT_USER_TYPE);
			}
			if(dao.getUserBy(p.getUsername(), ConstantsApi.GET_USER_BY_USERNAME) != null) {
				throw new FruitException(FruitException.USERNAME_EXISTS);
			}
			if(dao.getUserBy(p.getEmail(), ConstantsApi.GET_USER_BY_EMAIL) != null) {
				throw new FruitException(FruitException.EMAIL_EXISTS);
			}
			if(dao.getParticularByDni(p.getDni())) {
				throw new FruitException(FruitException.DNI_EXISTS);
			}
			p.setId(generateIdUsuario());
			dao.postParticular(p);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return p;
	}
	
	public Empresa postEmpresa(Empresa em) throws FruitException {
		try {
			checkUserFields((Usuario) em);
			checkEmpresaFields(em);
			if(em.getCif() == null || em.getNombreFiscal() == null || em.getNombreComercial() == null) {
				throw new FruitException(FruitException.FIELDS_LEFT);
			}
			if(dao.getUserBy(em.getUsername(), ConstantsApi.GET_USER_BY_USERNAME) != null) {
				throw new FruitException(FruitException.USERNAME_EXISTS);
			}
			if(dao.getUserBy(em.getEmail(), ConstantsApi.GET_USER_BY_EMAIL) != null) {
				throw new FruitException(FruitException.EMAIL_EXISTS);
			}
			em.setId(generateIdUsuario());
			dao.postEmpresa(em);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return em;
	}
	
	public void putUsuario(Usuario u) throws FruitException  {
		try {
			if(dao.getUserBy(u.getId(), ConstantsApi.GET_USER_BY_ID) == null) {
				throw new FruitException(FruitException.NOT_EXIST_USER);
			}
			Usuario uUsername = dao.getUserBy(u.getUsername(), ConstantsApi.GET_USER_BY_USERNAME);
			if(uUsername != null && !uUsername.getId().equals(u.getId())) {
				throw new FruitException(FruitException.USERNAME_EXISTS);
			}
			Usuario uEmail = dao.getUserBy(u.getEmail(), ConstantsApi.GET_USER_BY_EMAIL);
			if(uEmail != null && !uEmail.getId().equals(u.getId())) {
				throw new FruitException(FruitException.EMAIL_EXISTS);
			}
			dao.putUsuario(u);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void putParticular(Particular p) throws FruitException {
		try {
			checkUserFields((Usuario)p);
			checkParticularFields(p);
			Usuario u = dao.getUserBy(p.getId(), ConstantsApi.GET_USER_BY_ID);
			Particular uDni = dao.getParticularBy(p.getDni(), u, ConstantsApi.GET_USER_BY_DNI);
			if(uDni != null && !uDni.getId().equals(p.getId())) {
				throw new FruitException(FruitException.DNI_EXISTS);
			}		
			putUsuario((Usuario) p);
			dao.putParticular(p);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void putEmpresa(Empresa em) throws FruitException {
		try {
			checkUserFields((Usuario) em);
			checkEmpresaFields(em);
			Usuario u = dao.getUserBy(em.getId(), ConstantsApi.GET_USER_BY_ID);
			Empresa uCif = dao.getEmpresaBy(em.getCif(), u, ConstantsApi.GET_EMPRESA_BY_CIF);
			if(uCif != null && !uCif.getId().equals(em.getId())) {
				throw new FruitException(FruitException.CIF_EXISTS);
			}
			Empresa uNFiscal = dao.getEmpresaBy(em.getCif(), u, ConstantsApi.GET_EMPRESA_BY_NOMBRE_FISCAL);
			if(uNFiscal != null && !uNFiscal.getId().equals(em.getId())) {
				throw new FruitException(FruitException.NFISC_EXISTS);
			}
			Empresa uNComercial = dao.getEmpresaBy(em.getCif(), u, ConstantsApi.GET_EMPRESA_BY_NOMBRE_COMERCIAL);
			if(uNComercial != null && !uNComercial.getId().equals(em.getId())) {
				throw new FruitException(FruitException.NCOMER_EXISTS);
			}
			putUsuario((Usuario) em);
			dao.putEmpresa(em);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Usuario checkLogin(String username, String pass) throws FruitException {
		Usuario u = null;
		try {
			u = dao.checkLogin(username, pass, ConstantsApi.GET_USUARIO_LOGIN);
			if(u == null) {
				throw new FruitException(FruitException.NOT_EXIST_USER);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return u;
	}
	
	public Usuario checkLoginEmail(String email, String pass) throws FruitException {
		Usuario u = null;
		try {
			u = dao.checkLogin(email, pass, ConstantsApi.GET_USUARIO_LOGIN_EMAIL);
			if(u == null) {
				throw new FruitException(FruitException.NOT_EXIST_USER);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return u;
	}
	
	public JsonString getEmail(String email) throws FruitException {
		JsonString e = null;
		try {
			e = dao.checkEmail(email);
			if(e == null) {
				throw new FruitException(FruitException.EMAIL_NOT_EXIST);
			}
			Usuario u = dao.getUserBy(email, ConstantsApi.GET_USER_BY_EMAIL);
			String msg = "<html> <head></head> <body> <div> <div style='margin:0;padding:0;width:600px;display:flex;justify-content:center;align-items:center;height:100px;background: rgb(0,71,31);background: linear-gradient(90deg, rgba(0,71,31,1) 0%, rgba(68,122,91,1) 0%, rgba(221,255,216,1) 22%, rgba(212,247,209,1) 72%, rgba(68,122,91,1) 100%, rgba(0,71,31,1) 100%);'> <img src='http://verdfruit.s3-website.eu-west-3.amazonaws.com/assets/img/logoVF.png' style='height:90px;margin-left: 198px;'> </div> <table border='0' cellpadding='2' cellspacing='2' width='600' style='margin-top:30px; width:500px;font-size: 16px;color: #666666;'> <tr><td>Hola "+ u.getNombre() +", has pedido un cambio de contraseña.</td></tr> <tr><td><br></td></tr> <tr><td>Gracias por tu tiempo e interés en VerdFruit.com, tu plataforma de venta de fruta y verdura de confianza.</td></tr> <tr><td><br></td></tr> <tr><td>Si quieres restablecer la contraseña pulsa en \"continuar\".</td></tr> <tr><td><br></td></tr> <tr><td>También puedes llamarnos al <a href='tel:666840209'>+34 66 684 02 09</a>, estaremos encantados de contarte más.</td></tr> <tr><td><br></td></tr> <tr><td>Saludos,<br> Equipo VerdFruit.com</td></tr> <tr><td><br></td></tr> <tr><td align='center'> <form method='get' action='http://http://verdfruit.s3-website.eu-west-3.amazonaws.com/changePassword/"+u.getId()+"'> <button style='color:rgb(0,71,31);background-color: #FFB400;padding-left:20px;padding-right:20px;border-radius: 15px;font-size:20px;'><p>Continuar</p></button> </form> </td></tr> </table> </div> </body> </html>";
			String subject = "VerdFruit restablecer contraseña";
			if(EmailService.sendEmail(email, msg, subject)) {
				return e;
			}
		}catch(SQLException | MessagingException x) {
			System.out.println(x.getMessage());
		}
		return e;
	}
	
	public Token generateToken(Usuario u) {
		String encodedText = createToken(u);
		return new Token("Basic " + encodedText, u.getId());	   
	}
	
	//Función que retorna un string con el token generado a partir del usuario y la contraseña
	public String createToken(Usuario u) {
		String username = u.getUsername();
		String pass = u.getPass();
		String userAndPass = username + ":" + pass;	
		String encodedText = null;
		try {
			encodedText = Base64.getEncoder().encodeToString(userAndPass.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}
		return encodedText;
	}
	
//PRODUCTO----------------------------------------------------------------------------------------------
	
	public ArrayList<Producto> getProductos(String f) throws FruitException{
		ArrayList<Producto> productos = new ArrayList<>();
		try {
			productos = dao.getProductos(f);
			if(productos.isEmpty()) {
				throw new FruitException(FruitException.EMPTY_PRODUCTS);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return productos;
	}
	
	public Producto getProductoById(int id) throws FruitException{
		Producto p = null;
		try {
			p = dao.getProductoById(id);
			if(p == null) {
				throw new FruitException(FruitException.PRODUCT_NOT_FOUND);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return p;
	}
	
	public ArrayList<Producto> getProductosBy(String w, String key) throws FruitException{
		ArrayList<Producto> productos = new ArrayList<>();
		try {
			String select;
			switch(key) {
			case "origen":
				select = ConstantsApi.GET_PRODUCTO_BY_ORIGEN;
				break;
			case "familia":
				select = ConstantsApi.GET_PRODUCTO_BY_FAMILIA;
				break;
			case "marca":
				select = ConstantsApi.GET_PRODUCTO_BY_MARCA;
				break;
			default:
				throw new FruitException(FruitException.INCORRECT_PRODUCT_FIELD);
			}
			productos = dao.getProductosBy(w, select);
			if(productos.isEmpty()) {
				throw new FruitException(FruitException.EMPTY_PRODUCTS);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return productos;
	}
	
	public ArrayList<Producto> getProductosByBoolean(String tipo) throws FruitException{
		ArrayList<Producto> productos = new ArrayList<>();
		try {
			String select;
			switch(tipo) {
			case "nuevo":
				select = ConstantsApi.GET_PRODUCTO_NUEVO;
				break;
			case "mas_vendido":
				select = ConstantsApi.GET_PRODUCTO_MAS_VENDIDOS;
				break;
			default:
				throw new FruitException(FruitException.INCORRECT_PRODUCT_FIELD);
			}
			productos = dao.getProductosByBoolean(select);
			if(productos.isEmpty()) {
				throw new FruitException(FruitException.EMPTY_PRODUCTS);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return productos;
	}
	
	public ArrayList<Producto> getProductosByTipo(int tipo) throws FruitException{
		ArrayList<Producto> productos = new ArrayList<>();
		try {
			if(tipo < 1 || tipo > 2)
				throw new FruitException(FruitException.INCORRECT_PRODUCT_TYPE);
			
			productos = dao.getProductosByTipo(tipo);;
			if(productos.isEmpty()) {
				throw new FruitException(FruitException.EMPTY_PRODUCTS);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return productos;
	}
	
//PEDIDO-----------------------------------------------------------------------------------------
	
	public String generateIdListaProducto() throws SQLException {
		String id = "";
		boolean existe = true;
		while(existe) {
		    id = generateRandomId();
		    if(dao.getPedidoBy(id, ConstantsApi.GET_PEDIDO_BY_ID_LISTA).isEmpty()) {
		    	existe = false;
		    }	    
		}    
	    return id;
	}
	
	public ArrayList<Pedido> getPedidoByUsuario(String id) throws FruitException {
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		try {
			listaPedidos = dao.getPedidoBy(id, ConstantsApi.GET_PEDIDO_BY_USER);
			if(listaPedidos.isEmpty()) {
				throw new FruitException(FruitException.PEDIDO_NOT_FOUND);
			}
			for(Pedido p :listaPedidos) {
				ArrayList<ListaProductos> listaProductos = dao.getListaProductoByIdLista(p.getId_listaproducto());
				if(!listaProductos.isEmpty()) {
					p.setListaProductos(listaProductos);
				}
			}	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return listaPedidos;
	}
	
	public ArrayList<Pedido> getPedidosByUsuario(String id) throws FruitException {
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		try {
			listaPedidos = dao.getPedidoBy(id, ConstantsApi.GET_PEDIDO_BY_USER);
			for(Pedido p :listaPedidos) {
				ArrayList<ListaProductos> listaProductos = dao.getListaProductoByIdLista(p.getId_listaproducto());
				if(!listaProductos.isEmpty()) {
					p.setListaProductos(listaProductos);
				}
			}	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return listaPedidos;
	}
	
	public ArrayList<Pedido> getPedidosByStatus(String id_usuario, int status) throws FruitException {
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		try {
			Usuario u = dao.getUserBy(id_usuario, ConstantsApi.GET_USER_BY_ID);
			if(u == null) {
				throw new FruitException(FruitException.USER_NOT_FOUND);
			}
			listaPedidos = dao.getPedidoByStatus(id_usuario, status);
			/*if(listaPedidos.isEmpty()) {
				throw new FruitException(FruitException.ORDER_BY_STATUS_NOT_FOUND);
			}*/
			for(Pedido p : listaPedidos) {
				p.setListaProductos(dao.getListaProductoByIdLista(p.getId_listaproducto()));				
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return listaPedidos;
	}
	
	public void checkPedidoFields(Pedido p) throws FruitException {
		if(p.getId_usuario() == null || p.getCantidad_productos() == 0 || p.getPrecio_final() == 0) {
			throw new FruitException(FruitException.FIELDS_LEFT);
		}
	}
	
	public void checkListaProductoFields(ArrayList<ListaProductos> lista) throws FruitException {
		for(ListaProductos lp : lista) {
			if(lp.getId_producto() == 0 || lp.getCantidad() == 0 || lp.getPrecio() == 0) {
				throw new FruitException(FruitException.FIELDS_LEFT);
			}
		}
	}
	
	//Función que se encarga de hacer comprobaciones comunes para POST y PUT producto.
	public void checkPedido(Pedido pedido) throws FruitException {	
		try {
			checkPedidoFields(pedido);
	
			if(dao.getUserBy(pedido.getId_usuario(), ConstantsApi.GET_USER_BY_ID) == null) {
				throw new FruitException(FruitException.USER_NOT_FOUND);
			}
			ArrayList<ListaProductos> lp = pedido.getListaProductos();
			checkProductDuplicated(lp);
			checkListaProductoFields(lp);
			if(lp.isEmpty()) {
				throw new FruitException(FruitException.EMPTY_PRODUCT_LIST);
			}	
			if(pedido.getCantidad_productos() != lp.size()) {
				throw new FruitException(FruitException.INCORRECT_PRODUCT_UNITS);
			}
			checkTotalLista(lp, pedido.getPrecio_final());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		
	}

	public Pedido postPedido(Pedido pedido) throws FruitException {
		Pedido p = null;
		try {
			//EN LA CESTA = -1, PROCESANDO = 0, CONFIRMADO = 1, PREPARANDO = 2, PREPARADO = 3, ENTRAGADO = 4, CANCELADO = 5, DEVUELTO = 6 
			pedido.setEstado(-1);
			checkPedido(pedido);

			ArrayList<Pedido> productList = getPedidosByUsuario(pedido.getId_usuario());
			for(Pedido producto : productList) {
				if(producto.getEstado() == -1) {
					throw new FruitException(FruitException.ONLY_ONE_ORDER_IN_CART_ALLOWED);
				}
			}
			ArrayList<ListaProductos> lp = pedido.getListaProductos();
			checkTotalLista(lp, pedido.getPrecio_final());
			ArrayList<ListaProductos> listaProductos = new ArrayList<>();
			if(!dao.getPedidoBy(pedido.getId_listaproducto(), ConstantsApi.GET_PEDIDO_BY_ID_LISTA).isEmpty()) {
				throw new FruitException(FruitException.INCORRECT_ID_LISTAPEDIDO);
			}
			pedido.setId_listaproducto(generateIdListaProducto());
			p = dao.postPedido(pedido);
			for(ListaProductos lista : lp) {
				listaProductos.add(dao.insertListaPedido(lista, p.getId_listaproducto()));
			}
			p.setListaProductos(listaProductos);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		
		return p;
	}
	
	public void checkProductDuplicated(ArrayList<ListaProductos> lp) throws FruitException {
		for (int i = 0; i < lp.size(); i++) { 
			for (int j = i + 1 ; j < lp.size(); j++) { 
				if (lp.get(i).getId_producto() == lp.get(j).getId_producto()) { 
					throw new FruitException(FruitException.DUPLICATED_PRODUCT);
				}
			}
		}
	}
	
	public Pedido putPedido(Pedido pedido) throws FruitException {
		Pedido p = null;
		try {
			checkPedido(pedido);
			if(pedido.getId_listaproducto() == null) {
				throw new FruitException(FruitException.FIELDS_LEFT);
			}
			ArrayList<ListaProductos> listaProductos = new ArrayList<>();
			//Comprueba si el id de lista corresponde al usuario dado.
			ArrayList<Pedido> pedidoByIdLista = dao.getPedidoBy(pedido.getId_listaproducto(), ConstantsApi.GET_PEDIDO_BY_ID_LISTA);
			if(pedidoByIdLista.isEmpty()) {
				throw new FruitException(FruitException.ID_LISTA_NOT_FOUND);
			}
			p = pedidoByIdLista.get(0);
			//Asigno al pedido el mismo estado que tenia anteriormente
			pedido.setEstado(p.getEstado());
			if(p.getEstado() > 0) {
				throw new FruitException(FruitException.CANT_UPDATE_ORDER);
			}
			if(!p.getId_usuario().equals(pedido.getId_usuario())) {
				throw new FruitException(FruitException.INCORRECT_LIST_ID);
			}
			dao.updatePedido(pedido);
			ArrayList<ListaProductos> lp = pedido.getListaProductos();
			for(ListaProductos lista : lp) {
				//Compruebo si existe este producto en la cesta para hacerle un update o un insert.
				int id = lista.getId_producto();
				ListaProductos listaUptade = checkProductInProductList(id, pedido.getId_listaproducto());
				if(listaUptade != null) {
					listaProductos.add(dao.updateListaPedido(lista, p.getId_listaproducto()));
				}else {
					listaProductos.add(dao.insertListaPedido(lista, p.getId_listaproducto()));
				}
			}
			pedido.setListaProductos(listaProductos);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		
		return pedido;
	}
	

	public void deleteProductFromOrder(int id_producto, String id_listaproducto) throws FruitException {
		try{	
			ListaProductos lista = checkProductInProductList(id_producto, id_listaproducto);
			if(lista == null) {
				throw new FruitException(FruitException.PRODUCT_NOT_FOUND_IN_LIST);
			}
			ArrayList<Pedido> pedidoByIdLista = dao.getPedidoBy(id_listaproducto, ConstantsApi.GET_PEDIDO_BY_ID_LISTA);
			if(!pedidoByIdLista.isEmpty()) {
				if(pedidoByIdLista.get(0).getEstado() > 0) {
					throw new FruitException(FruitException.CANT_UPDATE_ORDER);
				}
			}
			
			//Reducir el precio total del pedido al borrar el producto de la cesta.
			double totalProducto = 0;
			Pedido p = pedidoByIdLista.get(0);
			if(p.getCantidad_productos() == 1) {
				//Eliminar pedido
				dao.deletePedidoByIdLista(id_listaproducto);
			}else {
				for(ListaProductos lp : dao.getListaProductoByIdLista(p.getId_listaproducto())) {
					if(lp.getId_producto() == id_producto) {
						totalProducto = lp.getPrecio();
					}
				}
				double precioTotal = p.getPrecio_final() - totalProducto;
				p.setPrecio_final(precioTotal);
				dao.deleteListaPedidoByUsuario(id_producto, id_listaproducto);
				//Update de pedido con el nuevo total.
				p.setCantidad_productos(p.getCantidad_productos()-1);
				dao.updatePedido(p);
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	//Función que comprueba si existe un producto dentro de una lista de productos concreta.
	public ListaProductos checkProductInProductList(int id_producto, String id_listaproducto) throws FruitException {
		ListaProductos lista = null;
		try{
			for(ListaProductos l : dao.getListaProductoByIdLista(id_listaproducto)){
				if(l.getId_producto() == id_producto) {					
					lista  = l;
				}
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}
	
	//Función que permite calcular si el precio total recibido como paremetro y los precios por tipo concuerdan con la lista de productos recibida.
	public void checkTotalLista(ArrayList<ListaProductos> listaProductos, double totalExterno) throws FruitException {	
		try {
			double totalCalculado = 0;
			for(ListaProductos lp : listaProductos) {
				Producto p = dao.getProductoById(lp.getId_producto());	
				if(p == null) {
					throw new FruitException(FruitException.PRODUCT_NOT_FOUND);
				}
				totalCalculado += p.getPrecio() * lp.getCantidad();	
				double totalProducto = p.getPrecio() * lp.getCantidad();
				totalProducto = Math.round(totalProducto*100.0)/100.0;
				double lpPrecio = Math.round(lp.getPrecio()*100.0)/100.0;
				if(totalProducto != lpPrecio) {
					throw new FruitException(FruitException.PRECIO_PRODUCTO_INCORRECTO);
				}
			}
			totalExterno = Math.round(totalExterno*100.0)/100.0;
			totalCalculado = Math.round(totalCalculado*100.0)/100.0; 
			if(totalCalculado != totalExterno) {
				throw new FruitException(FruitException.PRECIO_TOTAL_INCORRECTO);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Pedido realizarPedido(String id_usuario, String id_listaproducto) throws FruitException {
		Pedido p = null;
		try {
			Usuario u;
			u = dao.getUserBy(id_usuario, ConstantsApi.GET_USER_BY_ID);

			if(u == null) {
				throw new FruitException(FruitException.USER_NOT_FOUND);
			}
	
			ArrayList<Pedido> pedidosList = getPedidosByUsuario(id_usuario);
			if(pedidosList.isEmpty()) {
				throw new FruitException(FruitException.EMPTY_USER_PRODUCT_LIST);
			}
			for(Pedido pl : pedidosList) {
				if(pl.getId_listaproducto().equals(id_listaproducto)) {
					p = pl;
				}
			}
			if(p == null) {
				throw new FruitException(FruitException.PEDIDO_NOT_FOUND);
			}
			//Comprobar si el estado del pedido es -1 (EN LA CESTA)
			if(p.getEstado() != -1) {
				throw new FruitException(FruitException.ORDER_ALREADY_BEING_PROCESSED);
			}
			if(p.getPrecio_final() < 10) {
				throw new FruitException(FruitException.PRECIO_TOTAL_MENOR);
			}
			
			//Enviar correo con información de pago.
			String msg = "<html> <head></head> <body> <div> <div style='margin:0;padding:0;width:600px;display:flex;justify-content:center;align-items:center;height:100px;background: rgb(0,71,31);background: linear-gradient(90deg, rgba(0,71,31,1) 0%, rgba(68,122,91,1) 0%, rgba(221,255,216,1) 22%, rgba(212,247,209,1) 72%, rgba(68,122,91,1) 100%, rgba(0,71,31,1) 100%);'> <img src='http://verdfruit.s3-website.eu-west-3.amazonaws.com/assets/img/logoVF.png' style='height:90px;margin-left: 198px;'> </div> <table border='0' cellpadding='2' cellspacing='2' width='600' style='margin-top:30px; width:500px;font-size: 16px;color: #666666;'> <tr> <td style=\"color:#FFB400;font-size:20px\">Hola "+ u.getNombre() +":<br> <hr style=\"border-top: 1px solid rgb(0,71,31);\"> </td> </tr> <tr> <td><br></td> </tr> <tr> <td>Gracias por tu pedido en VerdFruit.com, tu plataforma de venta de fruta y verdura de confianza.</td> </tr> <tr> <td><br></td> </tr> <tr> <td style=\"color:#FFB400;font-size:20px\">Detalles del pedido: <hr style=\"border-top: 1px solid rgb(0,71,31);\"> </td> </tr> <tr> <td>";
			
			for(ListaProductos lp : p.getListaProductos()) {
				Producto prod = getProductoById(lp.getId_producto());
				String imagenProducto = prod.getImg();
				String nombreProducto = prod.getNombre_producto();
				double precioProducto = prod.getPrecio();
				String origenProducto = prod.getOrigen();
				String marcaProducto = prod.getMarca();
				msg += "<div style=\"width:100%;\"> <div style=\"width:100%;border:solid 1px rgb(0,71,31);margin-bottom:1px;display:flex;flex-direction:row;\"> <div style=\"width:65%;display:flex;justify-content:center;\"> <img src='http://verdfruit.s3-website.eu-west-3.amazonaws.com/assets/img/productos/"+ imagenProducto +"' style='width:90%;'> </div> <div style=\"width:70%;padding:10px; padding-top:20px;padding-bottom:20px;\"> <div style=\"width:100%;display:flex;flex-direction:row;justify-content:space-between;\"> <div style=\"font-size:15px;width: 50%;\"><b>"+ nombreProducto +"</b></div> <div style=\"width:50%;font-size:15px;\"><b style=\"float:right;margin-right:30px\">EUR "+ precioProducto +"</b></div> </div><br> <div style=\"font-size:14px;\">"+ origenProducto +"</div> <div style=\"font-size:14px;\">"+ marcaProducto +"</div> </div> </div> ";						 
			}
			
			double ivaDouble = p.getPrecio_final() * 0.04;
			String iva = String.format("%.2f", ivaDouble);
			String importeAntesDeImpuestos = String.format("%.2f", p.getPrecio_final());		
			String total = String.format("%.2f", p.getPrecio_final() + ivaDouble);
			msg += "</td></tr><tr><td><div style=\"display:flex;justify-content:flex-end;\"> <div> <p>Importe total antes de impuestos:	EUR "+ importeAntesDeImpuestos +"</p> <p>IVA:	EUR "+ iva +"</p> <p><b>Total de este pedido:	EUR "+ total +"</b></p> </div> </div> </div> <br> </td> </tr> <tr> <td style=\"font-size: 16px;\">Para más información puedes llamarnos al <a href='tel:696573066'>+34 696 573 066</a>, estaremos encantados de contarte más.</td> </tr> <tr> <td><br></td> </tr> <tr> <td style=\"font-size: 16px;\">Saludos,<br> Equipo VerdFruit.com</td> </tr> <tr> <td><br></td> </tr></table> </div> </body> </html>";
			
			String subject = "Pedido de VerdFruit";
			
			EmailService.sendEmail(u.getEmail(), msg, subject);
			
			Date date = new Date(System.currentTimeMillis());
			changePedidoStatus(id_usuario, 0, id_listaproducto, date);
			p.setEstado(0);
			p.setFecha_creacion(date);
		} catch (SQLException | MessagingException e) {
			System.out.println(e.getMessage());
		}
		return p;
	}
	
	public void changePedidoStatus(String id_usuario, int status, String id_listaproducto, Date date) throws FruitException {
		try {
			if(status < -1 || status > 6) {
				throw new FruitException(FruitException.INCORRECT_STATUS);
			}
			dao.updatePedidoStatus(id_usuario, status, id_listaproducto, date);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//TODO: Optimizar código.

//GLOBAL----------------------------------------------------------------------------------------------------------

	//Función que retorna un string con la id random generada.
	public String  generateRandomId() {
		final SecureRandom secureRandom = new SecureRandom();
		final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
	    byte[] randomBytes = new byte[24];
	    secureRandom.nextBytes(randomBytes);
	    return base64Encoder.encodeToString(randomBytes);
	}
		
}
