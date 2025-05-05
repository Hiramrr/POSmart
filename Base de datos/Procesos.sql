/*Valida el usuario para el login*/
DELIMITER //
CREATE PROCEDURE validacion_usuario(Nombre_User varchar(20), Contraseña_user varchar(255))
BEGIN
SELECT *
FROM Usuario
WHERE Nombre_usuario = Nombre_User
  AND Contraseña = Contraseña_user;
END //
DELIMITER ;

INSERT INTO Usuario (id_Usuario, Nombre_usuario, Contraseña, Nombre_completo, Rol, Telefono, Ciudad, Direccion)
VALUES (1, "Miyu", "coco123", "Miyu Maldonado","Administrador", "2284246692", "Xalapa", "Los lagos #1");


DELETE FROM Usuario WHERE id_Usuario = 2;

SELECT * FROM Usuario;

/*Cambia la contraseña del usuario*/
DELIMITER //
CREATE PROCEDURE cambiar_contraseña(Nombre_Com varchar(100), Nueva_contraseña varchar(255))
BEGIN
UPDATE Usuario
SET Contraseña = Nueva_contraseña
WHERE Nombre_completo = Nombre_com;

SELECT id_Usuario
FROM Usuario
WHERE Contraseña = Nueva_contraseña;
END //
DELIMITER ;

DROP PROCEDURE cambiar_contraseña;


/*Valida si el usuario existe por medio de su nombre completo*/
DELIMITER //
CREATE PROCEDURE existe_usuario(Nombre_Com varchar(20))
BEGIN
SELECT Nombre_completo
FROM Usuario
WHERE Nombre_completo = Nombre_Com;
END //
DELIMITER ;


/*Valida si el usuario existe por medio de su nombre completo*/
DELIMITER //
CREATE PROCEDURE agregar_usuario(p_id int, p_Nombre_usuario VARCHAR(20), p_Contraseña VARCHAR(50), p_Nombre_completo VARCHAR(50), p_Rol VARCHAR(20), p_Telefono VARCHAR(15), p_Ciudad VARCHAR(30), p_Direccion VARCHAR(100)
)
BEGIN
    INSERT INTO Usuario (id_usuario, Nombre_usuario, Contraseña, Nombre_completo, Rol, Telefono, Ciudad, Direccion)
    VALUES (p_id, p_Nombre_usuario, p_Contraseña, p_Nombre_completo, p_Rol, p_Telefono, p_Ciudad, p_Direccion);
    
    SELECT id_usuario
    FROM Usuario
    WHERE id_usuario = p_id;
END //
DELIMITER ;

DROP PROCEDURE agregar_usuario;

/*Agregar Proveedor*/
DELIMITER //

CREATE PROCEDURE agregar_proveedor(
    IN p_id INT,
    IN p_nombre VARCHAR(100),
    IN p_telefono VARCHAR(30),
    IN p_correo VARCHAR(70),
    IN p_direccion VARCHAR(255)
)
BEGIN
    -- Insert a new provider into the Proveedor table
    INSERT INTO Proveedor (id_Proveedor, Nombre, Telefono, Correo, Direccion)
    VALUES (p_id, p_nombre, p_telefono, p_correo, p_direccion);

    -- Return the ID of the newly added provider
    SELECT id_Proveedor AS Proveedor_Agregado
    FROM Proveedor
    WHERE id_Proveedor = p_id;
END //

DELIMITER ;

/*Modificar Proveedor*/

DELIMITER $$

CREATE PROCEDURE modificar_proveedor(
    IN p_id INT,
    IN p_nombre VARCHAR(255),
    IN p_telefono VARCHAR(50),
    IN p_correo VARCHAR(255),
    IN p_direccion VARCHAR(255)
)
BEGIN
    UPDATE Proveedor
    SET Nombre = p_nombre,
        Telefono = p_telefono,
        Correo = p_correo,
        Direccion = p_direccion
    WHERE id_Proveedor = p_id;
END$$

DELIMITER ;

/*Eliminar Proveedor*/
DELIMITER $$

CREATE PROCEDURE eliminar_proveedor(IN id INT)
BEGIN
    DELETE FROM Proveedor WHERE id_Proveedor = id;
END$$

DELIMITER ;


/*DELIMITER //

CREATE PROCEDURE agregar_producto(
    IN p_id INT,
    IN p_nombre VARCHAR(255),
    IN p_descripcion TEXT,
    IN p_cantidad INT,
    IN p_precio DOUBLE,
    IN p_ubicacion VARCHAR(255),
    IN p_categoria VARCHAR(255)
)
BEGIN
    INSERT INTO productos (id_producto, nombre, descripcion, cantidad, precio, ubicacion, categoria)
    VALUES (p_id, p_nombre, p_descripcion, p_cantidad, p_precio, p_categoria, p_ubicacion);
END //

DELIMITER ;*/


DELIMITER //

CREATE PROCEDURE agregar_producto(
    IN p_id_producto INT,
    IN p_nombre VARCHAR(100),
    IN p_descripcion VARCHAR(200),
    IN p_cantidad_stock INT,
    IN p_precio_compra INT,
    IN p_precio_venta INT,
    IN p_nombre_categoria VARCHAR(50),
    IN p_nombre_ubicacion VARCHAR(50)
)
BEGIN
    DECLARE v_id_categoria INT;
    DECLARE v_id_ubicacion INT;

    -- Buscar la categoría
    SELECT id_Categoria INTO v_id_categoria
    FROM Categoria
    WHERE Nombre = p_nombre_categoria
    LIMIT 1;

    -- Si no existe, insertarla
    IF v_id_categoria IS NULL THEN
        INSERT INTO Categoria(Nombre, Descripcion) VALUES (p_nombre_categoria, 'Categoría agregada automáticamente');
        SET v_id_categoria = LAST_INSERT_ID();
    END IF;

    -- Buscar la ubicación
    SELECT id_Ubicacion INTO v_id_ubicacion
    FROM Ubicacion
    WHERE Nombre = p_nombre_ubicacion
    LIMIT 1;

    -- Si no existe, insertarla
    IF v_id_ubicacion IS NULL THEN
        INSERT INTO Ubicacion(Nombre, Descripcion) VALUES (p_nombre_ubicacion, 'Ubicación agregada automáticamente');
        SET v_id_ubicacion = LAST_INSERT_ID();
    END IF;

    -- Insertar el producto
    INSERT INTO Productos (
        id_Producto, Nombre, Descripcion, Cantidad_stock,
        Precio_compra, Precio_venta, id_Categoria, id_Ubicacion
    ) VALUES (
                 p_id_producto, p_nombre, p_descripcion, p_cantidad_stock,
                 p_precio_compra, p_precio_venta, v_id_categoria, v_id_ubicacion
             );
END//

DELIMITER ;



DELIMITER ;

/*Agregar Ubicacion*/
DELIMITER //

CREATE PROCEDURE agregar_Ubicacion(
    IN p_id INT,
    IN p_nombre VARCHAR(100),
    IN p_descripcion VARCHAR(3000)
)
BEGIN
    INSERT INTO Ubicacion (id_Ubicacion, Nombre, Descripcion)
    VALUES (p_id, p_nombre, p_descripcion);

    SELECT id_Ubicacion AS Ubicacion_agregada
    FROM Ubicacion
    WHERE id_Ubicacion = p_id;
END //

DELIMITER ;

/*Modificar Ubicacion*/

DELIMITER $$

CREATE PROCEDURE modificar_Ubicacion(
    IN p_id INT,
    IN p_nombre VARCHAR(255),
    IN p_descripcion VARCHAR(3000)
)
BEGIN
    UPDATE Ubicacion
    SET Nombre = p_nombre,
        Descripcion = p_descripcion
    WHERE id_Ubicacion = p_id;
END$$

DELIMITER ;

/*Eliminar Ubicacion*/
DELIMITER $$

CREATE PROCEDURE eliminar_Ubicacion(IN id INT)
BEGIN
    DELETE FROM Ubicacion WHERE id_Ubicacion = id;
END$$

DELIMITER ;

/*Agregar Categoria*/
DELIMITER //

CREATE PROCEDURE agregar_Categoria(
    IN p_id INT,
    IN p_nombre VARCHAR(100),
    IN p_descripcion VARCHAR(3000)
)
BEGIN
    INSERT INTO Categoria (id_Categoria, Nombre, Descripcion)
    VALUES (p_id, p_nombre, p_descripcion);

    SELECT id_Categoria AS Categoria_agregada
    FROM Categoria
    WHERE id_Categoria = p_id;
END //

DELIMITER ;

/*Modificar Categoria*/

DELIMITER $$

CREATE PROCEDURE modificar_Categoria(
    IN p_id INT,
    IN p_nombre VARCHAR(255),
    IN p_descripcion VARCHAR(3000)
)
BEGIN
    UPDATE Categoria
    SET Nombre = p_nombre,
        Descripcion = p_descripcion
    WHERE id_Categoria = p_id;
END$$

DELIMITER ;

/*Eliminar Categoria*/
DELIMITER $$

CREATE PROCEDURE eliminar_Categoria(IN id INT)
BEGIN
    DELETE FROM Categoria WHERE id_Categoria = id;
END$$

DELIMITER ;

