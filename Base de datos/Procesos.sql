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

/*Permite editar datos de un usuario*/
DELIMITER //
CREATE PROCEDURE editar_usuario(
    IN p_id INT,
    IN p_nombre_usuario VARCHAR(20),
    IN p_contraseña VARCHAR(50),
    IN p_nombre_completo VARCHAR(50),
    IN p_rol VARCHAR(20),
    IN p_telefono VARCHAR(15),
    IN p_ciudad VARCHAR(30),
    IN p_direccion VARCHAR(100)
)
BEGIN
    UPDATE Usuario
    SET Nombre_usuario = p_nombre_usuario,
        Contraseña = p_contraseña,
        Nombre_completo = p_nombre_completo,
        Rol = p_rol,
        Telefono = p_telefono,
        Ciudad = p_ciudad,
        Direccion = p_direccion
    WHERE id_Usuario = p_id;

    SELECT id_Usuario
    FROM Usuario
    WHERE id_Usuario = p_id;
END$$

DELIMITER ;


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




