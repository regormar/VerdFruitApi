CREATE DATABASE  IF NOT EXISTS `verdfruit`;
USE `verdfruit`;

CREATE TABLE `usuarios` (
  `id_usuario` varchar(255) NOT NULL,
  `username` varchar(20) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellidos` varchar(150) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `direccion2` varchar(150),
  `telefono` varchar(9) NOT NULL,
  `tipo` int(1) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
);

CREATE TABLE `empresas` (
  `id_empresa` varchar(255) NOT NULL,
  `cif` varchar(9) NOT NULL,
  `nombre_fiscal` varchar(100) NOT NULL,
  `nombre_comercial` varchar(100) NOT NULL,
  PRIMARY KEY (`id_empresa`),
  CONSTRAINT `fk_empresa` FOREIGN KEY (`id_empresa`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE
);

CREATE TABLE `particulares` (
  `id_particular` varchar(255) NOT NULL,
  `dni` varchar(9) NOT NULL,
  PRIMARY KEY (`id_particular`),
  CONSTRAINT `fk_particular` FOREIGN KEY (`id_particular`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE
);

CREATE TABLE `propietarios` (
  `id_propietario` varchar(255) NOT NULL,
  `numero_cuenta_corriente` varchar(20) NOT NULL,
  PRIMARY KEY (`id_propietario`),
  CONSTRAINT `fk_propietario` FOREIGN KEY (`id_propietario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE
);

CREATE TABLE `productos` (
  `id_producto` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_producto` varchar(100) NOT NULL,
  `descripcion` varchar(500) NOT NULL,
  `origen` varchar(20) NOT NULL,
  `familia` varchar(20) NOT NULL,
  `marca` varchar(20),
  `precio` float(11) NOT NULL,
  `tipo_producto` int(1) NOT NULL,
  `stock` int(11) DEFAULT NULL,
  `img` varchar(100) NOT NULL,
  `mas_vendido` bool NOT NULL,
  `nuevo` bool NOT NULL,
  PRIMARY KEY (`id_producto`)
);

CREATE TABLE `pedidos`(
  `id_usuario` varchar(255) NOT NULL,
  `id_listaproducto` varchar(255) NOT NULL,
  `fecha_creacion` date,
  `estado` int(1),
  `cantidad_productos` int(100) NOT NULL,
  `precio_final` float(11) NOT NULL,
  PRIMARY KEY (`id_listaproducto`),
  KEY `fk_usuario` (`id_usuario`),
  CONSTRAINT `fk_usuario_idusuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE
);

CREATE TABLE `listaproductos` (
  `id_listaproducto` varchar(255) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `cantidad` int(100) NOT NULL,
  `precio` float(11) NOT NULL,
  PRIMARY KEY (`id_listaproducto`,`id_producto`),
  KEY `fk_usuario` (`id_listaproducto`),
  key `fk_producto` (`id_producto`),
  CONSTRAINT `fk_productos_idproducto` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`) ON DELETE CASCADE,
  CONSTRAINT `fk_pedidos_idlistaproducto` FOREIGN KEY (`id_listaproducto`) REFERENCES `pedidos` (`id_listaproducto`) ON DELETE CASCADE
);

CREATE TABLE `pagos` (
  `id_pago` int(11) NOT NULL AUTO_INCREMENT,
  `id_pedido` varchar(255) NOT NULL,
  `importe` float(11) NOT NULL,
  `tipo_pago` int(1) NOT NULL,
  PRIMARY KEY (`id_pago`),
  KEY `fk_pedido` (`id_pedido`),
  CONSTRAINT `fk_pedidos_idpedido` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_listaproducto`)
);

CREATE TABLE `transferencias` (
  `id_transferencia` int(11) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `banco` varchar(20) NOT NULL,
  PRIMARY KEY (`id_transferencia`),
  CONSTRAINT `fk_transferencia` FOREIGN KEY (`id_transferencia`) REFERENCES `pagos` (`id_pago`)
);

CREATE TABLE `creditos` (
  `id_credito` int(11) NOT NULL,
  `numero` int(19) NOT NULL,
  `fecha_caducidad` date NOT NULL,
  PRIMARY KEY (`id_credito`),
  CONSTRAINT `fk_credito` FOREIGN KEY (`id_credito`) REFERENCES `pagos` (`id_pago`)
);

INSERT INTO productos (id_producto,nombre_producto,descripcion,origen,familia,marca,precio,tipo_producto,stock,img,mas_vendido,nuevo) 
VALUES (1,"Manzana Golden (1Kg)",
"La manzana golden es la reina de las manzanas, su nombre oficial es ???Golden Delicious???, y tambi??n se la conoce como Yellow Delicious, Delicious Aurin y Arany Delicious. Es una manzana barata y la m??s cultivada en el mundo, est?? presente en el mercado todo el a??o y posee una pulpa de consistencia firme, crujiente y de blanca, que resulta irresistible.",
"Espa??a(Catalu??a)","Semilla","Tornafruit",1.75,1,500,"manzana_golden.png",true,true);

INSERT INTO productos (id_producto,nombre_producto,descripcion,origen,familia,marca,precio,tipo_producto,stock,img,mas_vendido,nuevo) 
VALUES (2,"Lim??n (1Kg)",
"El lim??n es un aut??ntico tesoro nutricional y muy beneficioso para nuestra salud. Es una fruta que nos aporta vitaminas, elimina toxinas y es un poderoso bactericida, por lo que la OMS recomienda su consumo regular.",
"Espa??a(Murcia)","C??tricos","Mellado",1.65,1,100,"limon.png",true,false);

INSERT INTO productos (id_producto,nombre_producto,descripcion,origen,familia,marca,precio,tipo_producto,stock,img,mas_vendido,nuevo) 
VALUES (3,"Aguacate (1Kg)",
"El Aguacate es una fruta con un sabor exquisito y suave a avellana y una consistencia tierna y cremosa permiten su combinaci??n con cualquier alimento. El aguacate es rico en fibra, ayuda al organismo a saciar el apetito, evitar el estre??imiento y regular los niveles de glucosa en la sangre. Y por si fuera poco, el aguacate es rico en ??cido oleico, un tipo de ??cido graso que ayuda a controlar los niveles de colesterol.",
"Espa??a(Andalucia)","Fruta tropical",null,5.95,1,100,"aguacate.png",false,true);

INSERT INTO productos (id_producto,nombre_producto,descripcion,origen,familia,marca,precio,tipo_producto,stock,img,mas_vendido,nuevo) 
VALUES (4,"Naranja (1Kg)",
"La naranja, junto con el pl??tano y la manzana, es uno de los frutos m??s consumidos en el mundo. Las naranjas son bajas en calor??as y fuente de fibra, potasio, vitamina C y folato. Ayudan a prevenir el c??ncer y las enfermedades cardiovasculares.",
"Espa??a(Valencia)","C??tricos","Ponche",1.75,1,100,"naranja_ponche.png",true,false);

INSERT INTO productos (id_producto,nombre_producto,descripcion,origen,familia,marca,precio,tipo_producto,stock,img,mas_vendido,nuevo) 
VALUES (5,"Fresa (1Kg)",
"La fresa es un fruto de color rojo brillante, suculento y fragante que se obtiene de la planta que recibe su mismo nombre. En Occidente es considerada la reina de las frutas. Adem??s de poderse comer cruda se puede consumir como compota o mermelada. Es empleada con fines medicinales ya que posee excelentes propiedades que ayudan a preservar la salud.",
"Espa??a(Andaluc??a)","Fruta(Rosacea)","Espinosa",2.95,1,100,"fresa.png",false,false);

INSERT INTO productos (id_producto,nombre_producto,descripcion,origen,familia,marca,precio,tipo_producto,stock,img,mas_vendido,nuevo) 
VALUES (6,"Esp??rrago (1 Mjo)",
"El esp??rrago es el brote de la planta esparraguera, que se cosecha inmaduro, antes de ramificarse y endurecerse. Seg??n el manejo durante el cultivo se obtienen dos tipos. blancos y verdes. Se adapta a numerosos platos y preparaciones, y se conserva muy bien enlatado o en tarros de cristal.",
"Espa??a(Navarra)","Verdura(Esparraguera)","Roca",2.95,2,100,"esparrago.png",false,false);

INSERT INTO productos (id_producto,nombre_producto,descripcion,origen,familia,marca,precio,tipo_producto,stock,img,mas_vendido,nuevo) 
VALUES (7,"Calabac??n (1Kg)",
"El calabac??n, es una gran baya carnosa y alargada, de piel verde y tama??o variable seg??n la subespecie, que puede llegar a los 50 cm de largo y 12 cm de di??metro. El calabac??n forma parte de guisos, ensaladas y todo tipo de recetas. Adem??s, es un poderoso antioxidante.",
"Espa??a(Andaluc??a)","Verdura(Cucurbita)",null,0.95,2,100,"calabacin.png",true,false);

INSERT INTO productos (id_producto,nombre_producto,descripcion,origen,familia,marca,precio,tipo_producto,stock,img,mas_vendido,nuevo) 
VALUES (8,"Frambuesa (0.125Kg)",
"Esta rica fruta se caracteriza por tener vitamina C, potasio, antioxidantes y fibra, con un bajo contenido de calor??as y az??cares. Sus propiedades las transforman en un alimento muy beneficioso para nuestro organismo.",
"Espa??a(Andaluc??a)","Fruta(Rosacea)",null,2.75,1,100,"frambuesa.png",false,true);

INSERT INTO productos (id_producto,nombre_producto,descripcion,origen,familia,marca,precio,tipo_producto,stock,img,mas_vendido,nuevo) 
VALUES (9,"R??cula (1Ud)",
"La r??cula o r??gula es un tipo de hortaliza de la familia de las brasic??ceas, considerada para fines culinarios un tipo de verdura de hoja. La r??cula es especialmente usada en ensaladas, pero tambi??n cocinada como verdura con pasta o cecina. Tambi??n es com??n en Italia su uso en pizzas, a??adi??ndosela s??lo tras el horneado. Es rica en vitamina C y hierro.",
"Espa??a","Planta(Ortaliza)",null,2.25,2,100,"rucula.png",false,true);

INSERT INTO productos (id_producto,nombre_producto,descripcion,origen,familia,marca,precio,tipo_producto,stock,img,mas_vendido,nuevo) 
VALUES (10,"Pimiento rojo (1Kg)",
"El Pimiento rojo se puede comer crudo o cocido pero jam??s hervido porque as?? pierde todas las vitaminas hidrosolubles. En caso de comerlo crudo, hay que lavar cuidadosamente el exterior para eliminar los pesticidas. Los pimientos son una buena fuente de selenio y de vitaminas C, E, provitamina A y de otros carotenoides como la capsantina, todos ellos de acci??n antioxidante y beneficiosa para el organismo.",
"Espa??a(Andaluc??a)","Planta(Ortaliza)",null,3.75,2,100,"pimiento_rojo.png",true,false);

INSERT INTO productos (id_producto,nombre_producto,descripcion,origen,familia,marca,precio,tipo_producto,stock,img,mas_vendido,nuevo) 
VALUES (11,"Berenjena (1Kg)",
"La berenjena es muy saludable. Es un alimento bajo en calor??as y en hidratos de carbono. Es de muy f??cil digesti??n, incluso para est??magos delicados; contiene una buena cantidad de zinc y potasio. Adem??s contiene magnesio, hierro, f??sforo y calcio. Respecto a su contenido en vitaminas presenta en su mayor parte vitaminas A, C y ??cido f??lico.",
"Espa??a(Andaluc??a)","Planta(Ortaliza)",null,0.95,2,100,"berenjena.png",true,false);

INSERT INTO usuarios (id_usuario, username, pass, email, nombre, apellidos, direccion, direccion2, telefono, tipo) VALUES ("u1h2o1ll12blln111nk","regormar","Passw0rd","regormar99@gmail.com","Roger","Marquez Ramos","Avinguda del Puig 6-10","","666840209", 1);
INSERT INTO particulares (id_particular,dni) VALUES ("u1h2o1ll12blln111nk","54246750P");

INSERT INTO usuarios (id_usuario, username, pass, email, nombre, apellidos, direccion, direccion2, telefono, tipo) VALUES ("1","josep","123","josepprofitos99@gmail.com","Roger","Marquez Ramos","Avinguda del Puig 6-10","","666840209", 1);
INSERT INTO particulares (id_particular,dni) VALUES ("1","54246750P");

INSERT INTO pedidos VALUES ("u1h2o1ll12blln111nk","fewew3232f2asdsadaasd",null,-1,4,11.1);
INSERT INTO listaproductos VALUES ("fewew3232f2asdsadaasd",1,1,1.75); 
INSERT INTO listaproductos VALUES ("fewew3232f2asdsadaasd",2,1,1.65); 
INSERT INTO listaproductos VALUES ("fewew3232f2asdsadaasd",3,1,5.95); 
INSERT INTO listaproductos VALUES ("fewew3232f2asdsadaasd",4,1,1.75); 

select * from pagos;
select * from particulares;
select * from pedidos;
select * from propietarios;
select * from transferencias;
select * from usuarios;
select * from creditos;
select * from empresas;
select * from listaproductos;
select * from productos;
