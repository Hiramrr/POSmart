USE PoSmart;

CREATE TABLE Productos(
	id_Producto int PRIMARY KEY,
	Nombre VARCHAR(100) NOT NULL,
	Descripcion VARCHAR(200),
	Cantidad_stock int NOT NULL,
	Precio_compra int NOT NULL,
	Precio_venta int NOT NULL,
	id_categoria int NOT NULL,
	id_ubicacion int NOT NULL,
    FOREIGN KEY (id_Categoria) REFERENCES Categoria(id_Categoria),
	FOREIGN KEY (id_Ubicacion) REFERENCES Ubicacion(id_Ubicacion)
);
 
 CREATE TABLE Proveedor(
	id_Proveedor int PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Telefono VARCHAR(30) NOT NULL,
    Correo VARCHAR(70),
    Direccion VARCHAR(70) NOT NULL
 );
 
 CREATE TABLE Producto_Proveedor (
	id_Proveedor int NOT NULL,
    id_Producto int NOT NULL,
    FOREIGN KEY (id_Proveedor) REFERENCES Proveedor(id_Proveedor),
	FOREIGN KEY (id_Producto) REFERENCES Productos(id_Producto)
 );
 
 CREATE TABLE Categoria (
	id_Categoria int PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL,
    Descripcion VARCHAR(70) NOT NULL
 );
 
 CREATE TABLE Ubicacion(
	id_Ubicacion int PRIMARY KEY,
	Nombre VARCHAR(50) NOT NULL,
    Descripcion VARCHAR (70) NOT NULL
);

CREATE TABLE Usuario (
	id_Usuario int PRIMARY KEY,
    Nombre_usuario VARCHAR(50),
    Contraseña VARCHAR(100),
    Nombre_completo VARCHAR(100),
    Rol VARCHAR(50)
);

CREATE TABLE Compra (
	id_compra int PRIMARY KEY,
    fecha_compra date NOT NULL,
    total int NOT NULL,
    id_Proveedor int NOT NULL,
    id_Usuario int NOT NULL,
	FOREIGN KEY (id_Proveedor) REFERENCES Proveedor(id_Proveedor),
	FOREIGN KEY (id_Usuario) REFERENCES Usuario(id_Usuario)
);

CREATE TABLE Detalle_compra (
	id_Detalle int PRIMARY KEY,
    id_Producto int NOT NULL,
    id_Compra int NOT NULL,
    Cantidad int NOT NULL,
    Monto_final int NOT NULL,
    FOREIGN KEY (id_Producto) REFERENCES Productos(id_Producto),
	FOREIGN KEY (id_Compra) REFERENCES Compra(id_Compra)
);


 