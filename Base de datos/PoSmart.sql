USE PoSmart;

CREATE TABLE productos (
    id_Producto INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Descripcion VARCHAR(200),
    Cantidad_stock INT NOT NULL,
    Precio_compra INT NOT NULL,
    Precio_venta INT NOT NULL,
    id_categoria INT NOT NULL,
    id_ubicacion INT NOT NULL,
    Imagen MEDIUMBLOB,
    disponible TINYINT(1) DEFAULT 1, -- 1 = disponible, 0 = no disponible
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria),
    FOREIGN KEY (id_ubicacion) REFERENCES ubicaciones(id_ubicacion)
);


CREATE TABLE productos (
    id_Producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    id_Proveedor INT,
    imagen LONGBLOB, -- o VARCHAR si solo guardas la ruta
    disponible BOOLEAN DEFAULT TRUE, -- NUEVO CAMPO
    FOREIGN KEY (id_Proveedor) REFERENCES proveedores(id_Proveedor)
);
CREATE TABLE Proveedor(
                          id_Proveedor int PRIMARY KEY,
                          Nombre VARCHAR(100) NOT NULL,
                          Telefono VARCHAR(30) NOT NULL,
                          Correo VARCHAR(70),
                          Direccion VARCHAR(70) NOT NULL
);

CREATE TABLE PEDIDO (
        id_Producto int NOT NULL,
        nombre_Producto VARCHAR(100) NOT NULL,
        cantidad int NOT NULL,
        id_proveedor int NOT NULL,
        nombre_Proveedor VARCHAR(100) NOT NULL,
        total int NOT NULL,
        FOREIGN KEY (id_Proveedor) REFERENCES Proveedor(id_Proveedor),
        FOREIGN KEY (id_Producto) REFERENCES Productos(id_Producto)
);


DROP TABLE IF EXISTS Producto_Proveedor;
CREATE TABLE Producto_Proveedor (
                                    id_Proveedor int NOT NULL,
                                    id_Producto int NOT NULL,
                                    FOREIGN KEY (id_Proveedor) REFERENCES Proveedor(id_Proveedor),
                                    FOREIGN KEY (id_Producto) REFERENCES Productos(id_Producto)
);

select * from Productos;

CREATE TABLE Categoria (
                           id_Categoria int PRIMARY KEY,
                           Nombre VARCHAR(50) NOT NULL,
                           Descripcion VARCHAR(3000) NOT NULL
);

CREATE TABLE Ubicacion(
                          id_Ubicacion int PRIMARY KEY,
                          Nombre VARCHAR(50) NOT NULL,
                          Descripcion VARCHAR (3000) NOT NULL
);

CREATE TABLE Usuario (
                         id_Usuario INT PRIMARY KEY,
                         Nombre_usuario VARCHAR(50) NOT NULL,
                         Contraseña VARCHAR(100) NOT NULL,
                         Nombre_completo VARCHAR(100) NOT NULL,
                         Rol VARCHAR(50) NOT NULL,
                         Telefono VARCHAR(20) NOT NULL,
                         Ciudad VARCHAR(50) NOT NULL,
                         Direccion VARCHAR(150) NOT NULL
);


CREATE TABLE Compra (
    id_compra INT AUTO_INCREMENT PRIMARY KEY,
    fecha_compra DATE NOT NULL,
    total DOUBLE NOT NULL,
    id_Proveedor INT NOT NULL,
    id_Usuario INT NOT NULL,
    FOREIGN KEY (id_Proveedor) REFERENCES Proveedor(id_Proveedor),
    FOREIGN KEY (id_Usuario) REFERENCES Usuario(id_Usuario)
);


CREATE TABLE Detalle_compra (
    id_Detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_Producto INT NOT NULL,
    id_Compra INT NOT NULL,
    Cantidad INT NOT NULL,
    Monto_final DOUBLE NOT NULL,
    FOREIGN KEY (id_Producto) REFERENCES Productos(id_Producto),
    FOREIGN KEY (id_Compra) REFERENCES Compra(id_Compra)
);


CREATE TABLE productos (
                           id_producto INT PRIMARY KEY,
                           nombre VARCHAR(255) NOT NULL,
                           descripcion TEXT,
                           cantidad INT NOT NULL,
                           precio DOUBLE NOT NULL,
                           ubicacion VARCHAR(255),
                           categoria VARCHAR(255)
);

SELECT * from Usuario;