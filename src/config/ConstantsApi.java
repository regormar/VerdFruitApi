package config;

public class ConstantsApi {
	
	//DB AWS CONNECTION
	public static final String CONNECTION = "jdbc:mysql://verdfruit.ctcwp0dnaxxi.us-east-2.rds.amazonaws.com/verdfruit?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static final String USER_CONNECTION = "admin";
	public static final String PASS_CONNECTION = "Passw0rd";
	
	//SMTP
	public static final String USER_EMAIL="regormar99@gmail.com";
	public static final String USER_PASS="regormar1999";
	
	public static final String GET_USER_BY_ID = "SELECT * FROM usuarios WHERE id_usuario=?";
	public static final String GET_USER_BY_USERNAME = "SELECT * FROM usuarios WHERE username=?";
	public static final String GET_USER_BY_EMAIL = "SELECT * FROM usuarios WHERE email=?";
	public static final String GET_USER_BY_TL = "SELECT * FROM usuarios WHERE telefono=?";
	public static final String GET_USER_BY_DNI = "SELECT * FROM particulares WHERE dni=?";
	public static final String GET_PARTICULAR_BY_ID = "SELECT * FROM particulares WHERE id_particular=?";
	public static final String GET_EMPRESA_BY_ID = "SELECT * FROM empresas WHERE id_empresa=?";
	public static final String GET_EMPRESA_BY_CIF = "SELECT * FROM empresas WHERE cif=?";
	public static final String GET_EMPRESA_BY_NOMBRE_FISCAL = "SELECT * FROM empresas WHERE nombre_fiscal=?";
	public static final String GET_EMPRESA_BY_NOMBRE_COMERCIAL = "SELECT * FROM empresas WHERE nombre_comercial=?";
	public static final String INSERT_USUARIO = "INSERT INTO usuarios (id_usuario, username, pass, email, nombre, apellidos, direccion, direccion2, telefono, tipo) VALUES (?,?,?,?,?,?,?,?,?,?)";
	public static final String INSERT_PROPIETARIO = "INSERT INTO particulares (id_particular, dni) VALUES (?,?)";
	public static final String INSERT_EMPRESA = "INSERT INTO empresas (id_empresa, cif, nombre_fiscal, nombre_comercial) VALUES (?,?,?,?)";
	public static final String UPDATE_USUARIO = "UPDATE usuarios SET username = ?, pass = ?, email = ?, nombre = ?, apellidos = ?, direccion = ?, direccion2 = ?, telefono = ?, tipo = ? WHERE id_usuario = ?";
	public static final String UPDATE_PARTICULAR = "UPDATE particulares SET dni = ? WHERE id_particular = ?";
	public static final String UPDATE_EMPRESA = "UPDATE empresas SET cif = ?, nombre_fiscal = ?, nombre_comercial = ? WHERE id_empresa = ?";
	public static final String GET_USUARIO_LOGIN = "SELECT * FROM usuarios WHERE username=? AND pass=?";
	public static final String GET_USUARIO_LOGIN_EMAIL = "SELECT * FROM usuarios WHERE email=? AND pass=?";
	public static final String GET_EMAIL = "SELECT email FROM usuarios WHERE email=?";
	
	public static final String GET_PRODUCTOS = "SELECT * FROM productos";
	public static final String GET_PRODUCTOS_FILTRO = "SELECT * FROM productos WHERE nombre_producto LIKE";
	public static final String GET_PRODUCTO_BY_ID = "SELECT * FROM productos WHERE id_producto = ?";
	public static final String GET_PRODUCTO_BY_ORIGEN = "SELECT * FROM productos WHERE origen = ?";
	public static final String GET_PRODUCTO_BY_FAMILIA = "SELECT * FROM productos WHERE familia = ?";
	public static final String GET_PRODUCTO_MAS_VENDIDOS = "SELECT * FROM productos WHERE mas_vendido = true";
	public static final String GET_PRODUCTO_NUEVO = "SELECT * FROM productos WHERE nuevo = true";
	public static final String GET_PRODUCTO_BY_MARCA = "SELECT * FROM productos WHERE marca = ?";
	public static final String GET_PRODUCTO_BY_TIPO = "SELECT * FROM productos WHERE tipo_producto = ?";
	
	public static final String GET_PEDIDO_BY_USER = "SELECT * FROM pedidos WHERE id_usuario = ?";
	public static final String GET_PEDIDO_BY_ID_LISTA = "SELECT * FROM pedidos WHERE id_listaproducto = ?";
	public static final String GET_PEDIDO_BY_STATUS = "SELECT * FROM pedidos WHERE estado = ? AND id_usuario = ?";
	public static final String INSERT_PEDIDO = "INSERT INTO pedidos (id_usuario,id_listaproducto,fecha_creacion,estado,cantidad_productos,precio_final) VALUES (?,?,?,?,?,?)";
	public static final String UPDATE_PEDIDO = "UPDATE pedidos SET id_usuario = ?, id_listaproducto = ?, fecha_creacion = ?, estado = ?, cantidad_productos = ?, precio_final = ? WHERE id_listaproducto = ?";
	public static final String UPDATE_PEDIDO_STATUS = "UPDATE pedidos SET fecha_creacion = ?, estado = ? WHERE id_usuario = ? AND id_listaproducto = ?";
	public static final String DELETE_PEDIDO = "DELETE FROM pedidos WHERE id_listaproducto = ?";
	
	public static final String GET_LISTA_PEDIDO_BY_ID = "SELECT * FROM listaproductos WHERE	id_listaproducto = ?";
	public static final String INSERT_LISTA_PEDIDO = "INSERT INTO listaproductos (id_listaproducto,id_producto,cantidad,precio) VALUES (?,?,?,?)";
	public static final String UPDATE_LISTA_PEDIDO = "UPDATE listaproductos SET id_producto = ? ,cantidad = ? ,precio = ? WHERE id_producto = ? AND id_listaproducto = ?";
	public static final String DELETE_LISTA_PEDIDO = "DELETE FROM listaproductos WHERE id_producto = ? AND id_listaproducto = ?";
	
	
}
