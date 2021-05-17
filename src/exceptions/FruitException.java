package exceptions;

import java.util.Arrays;
import java.util.List;

public class FruitException extends Exception{
	
	private int code;
	
    public static final int USER_NOT_FOUND = 0;
    public static final int USERNAME_EXISTS = 1;
    public static final int EMAIL_EXISTS = 2;
    public static final int NOT_EXIST_USER = 3;
    public static final int EMAIL_NOT_EXIST = 4;
    public static final int DNI_EXISTS = 5;
    public static final int INVALID_TOKEN = 6;
    public static final int CIF_EXISTS = 7;
    public static final int NFISC_EXISTS = 8;
    public static final int NCOMER_EXISTS = 9;
    public static final int PRODUCT_NOT_FOUND = 10;
    public static final int EMPTY_PRODUCTS = 11;
    public static final int INCORRECT_PRODUCT_FIELD = 12;
    public static final int PEDIDO_NOT_FOUND = 13;
    public static final int EMPTY_PRODUCT_LIST = 14;
    public static final int PRECIO_TOTAL_INCORRECTO = 15;
    public static final int PRECIO_PRODUCTO_INCORRECTO = 16;
    public static final int INCORRECT_PRODUCT_UNITS = 17;
    public static final int INCORRECT_STATUS = 18;
    public static final int INCORRECT_ID_LISTAPEDIDO = 19;
    public static final int WRONG_ORDER_STATUS = 20;
    public static final int INCORRECT_LIST_ID  = 21;
    public static final int NOT_MATCH_ID_LISTA = 22;
    public static final int PRODUCT_NOT_FOUND_IN_LIST = 23;
    public static final int PRECIO_TOTAL_MENOR = 24;
    public static final int ID_LISTA_NOT_FOUND = 25;
    public static final int CANT_UPDATE_ORDER = 26;
    public static final int EMPTY_USER_PRODUCT_LIST = 27;
    public static final int ORDER_ALREADY_BEING_PROCESSED = 28;
    public static final int ONLY_ONE_ORDER_IN_CART_ALLOWED = 29;
    public static final int FIELDS_LEFT = 30;
    public static final int INCORRECT_USER_TYPE = 31;
    public static final int INCORRECT_DNI_PATTERN = 32;
    public static final int INCORRECT_CIF_PATTERN = 33;
    public static final int INCORRECT_USERNAME_PATTERN = 34;
    public static final int INCORRECT_PASSWORD_PATTERN = 35;
    public static final int INCORRECT_EMAIL_PATTERN = 36;
    public static final int INCORRECT_TELEFONO_PATTERN = 37;
    public static final int EMPTY_FIELDS = 38;
    public static final int DUPLICATED_PRODUCT = 39;
    public static final int ORDER_BY_STATUS_NOT_FOUND = 40;
    public static final int INCORRECT_PRODUCT_TYPE = 41;
    
    private final List<String> messages = Arrays.asList(
    		"< Usuario no encontrado >",
    		"< Este nombre de usuario ya existe >",
    		"< Ya existe un usuario con este correo >",
    		"< Este usuario no existe >",
    		"< Este correo no pertenece a ningún usuario >",
    		"< Ya existe un usuario con este DNI >",
    		"< Token de acceso no válido >",
    		"< Ya existe una empresa con este CIF >",
    		"< Ya existe una empresa con este nombre fiscal >",
    		"< Ya existe una empresa con este nombre comercial >",
    		"< Este producto no existe >",
    		"< Productos no encontrados >",
    		"< Filtro incorrecto >",
    		"< Pedido no encontrado >",
    		"< Lista de productos vacía >",
    		"< Precio total incorrecto >",
    		"< Precio de producto incorrecto >",
    		"< Cantidad de productos incorrecta >",
    		"< Estado de pedido incorrecto >",
    		"< Este id de lista ya esta en uso >",
    		"< Este estado de pedido no es válido >",
    		"< Esta id de lista no corresponde al usuario dado >",
    		"< El id_listaproducto no coincide con uno o varios de la lista >",
    		"< El producto no existe en este pedido >",
    		"< El precio total debe ser superior a 10€ >",
    		"< Esta id de lista no existe >",
    		"< No se puede actualizar un pedido que ya se esta procesando >",
    		"< Este usuario no tiene pedidos >",
    		"< El pedido ya esta en proceso >",
    		"< Ya existe un pedido en el carro >",
    		"< Faltan campos por añadir >",
    		"< Tipo de usuario incorrecto >",
    		"< Patrón de DNI incorrecto >",
    		"< Patrón de CIF incorrecto >",
    		"< Patrón de nombre de usuario incorrecto>",
    		"< Patrón de contraseña incorrecto >",
    		"< Patrón de correo incorrecto >",
    		"< Patrón de teléfono incorrecto >",
    		"< No se permiten campos vacíos >",
    		"< No se permiten productos repetidos >",
    		"< No se han encontrado pedidos con este estado >",
    		"< Tipo de producto no válido >"
    		);
    
    public FruitException(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return messages.get(this.code);
    }
}
