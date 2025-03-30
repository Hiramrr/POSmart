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

INSERT INTO Usuario (id_Usuario, Nombre_usuario, Contraseña, Nombre_completo, Rol)
VALUES (1, "Miyu", "coco123", "Miyu Maldonado","Administrador");


DELETE FROM Usuario WHERE id_Usuario = 1;

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
