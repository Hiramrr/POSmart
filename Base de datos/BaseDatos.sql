-- MySQL dump 10.13  Distrib 8.0.41, for macos15 (arm64)
--
-- Host: localhost    Database: PoSmart
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `PoSmart`
--

/*!40000 DROP DATABASE IF EXISTS `PoSmart`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `PoSmart` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `PoSmart`;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id_Categoria` int NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Descripcion` varchar(3000) DEFAULT NULL,
  PRIMARY KEY (`id_Categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Gatos','Aqui se agregaran todos los productos de gatos'),(2,'Bebidas','Productos líquidos como jugos, refrescos, aguas y otras bebidas.'),(3,'Comida Rápida','Productos listos para consumir como hamburguesas, papas fritas, y snacks.'),(4,'Postres','Dulces y productos de repostería como pasteles, helados y galletas.');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compra`
--

DROP TABLE IF EXISTS `compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compra` (
  `id_compra` int NOT NULL AUTO_INCREMENT,
  `fecha_compra` date NOT NULL,
  `total` double NOT NULL,
  `id_Proveedor` int NOT NULL,
  `id_Usuario` int NOT NULL,
  PRIMARY KEY (`id_compra`),
  KEY `id_Proveedor` (`id_Proveedor`),
  KEY `id_Usuario` (`id_Usuario`),
  CONSTRAINT `compra_ibfk_1` FOREIGN KEY (`id_Proveedor`) REFERENCES `proveedor` (`id_Proveedor`),
  CONSTRAINT `compra_ibfk_2` FOREIGN KEY (`id_Usuario`) REFERENCES `usuario` (`id_Usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra`
--

LOCK TABLES `compra` WRITE;
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
INSERT INTO `compra` VALUES (1,'2025-05-13',18,1,2),(2,'2025-05-13',18,1,2),(3,'2025-05-13',18,1,2),(4,'2025-05-13',18,1,2),(5,'2025-05-13',8,1,2),(6,'2025-05-13',88,1,2),(7,'2025-05-13',8,1,2),(8,'2025-05-14',98,1,2);
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_compra`
--

DROP TABLE IF EXISTS `detalle_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_compra` (
  `id_Detalle` int NOT NULL AUTO_INCREMENT,
  `id_Producto` int NOT NULL,
  `id_Compra` int NOT NULL,
  `Cantidad` int NOT NULL,
  `Monto_final` double NOT NULL,
  PRIMARY KEY (`id_Detalle`),
  KEY `id_Producto` (`id_Producto`),
  KEY `id_Compra` (`id_Compra`),
  CONSTRAINT `detalle_compra_ibfk_1` FOREIGN KEY (`id_Producto`) REFERENCES `productos` (`id_Producto`),
  CONSTRAINT `detalle_compra_ibfk_2` FOREIGN KEY (`id_Compra`) REFERENCES `compra` (`id_compra`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_compra`
--

LOCK TABLES `detalle_compra` WRITE;
/*!40000 ALTER TABLE `detalle_compra` DISABLE KEYS */;
INSERT INTO `detalle_compra` VALUES (1,3,4,1,18),(2,1,5,1,8),(3,1,6,1,8),(4,4,6,1,35),(5,2,6,1,25),(6,5,6,1,20),(7,1,7,1,8),(8,2,8,1,25),(9,3,8,1,18),(10,5,8,1,20),(11,4,8,1,35);
/*!40000 ALTER TABLE `detalle_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto_proveedor`
--

DROP TABLE IF EXISTS `producto_proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto_proveedor` (
  `id_Proveedor` int NOT NULL,
  `id_Producto` int NOT NULL,
  KEY `id_Proveedor` (`id_Proveedor`),
  KEY `id_Producto` (`id_Producto`),
  CONSTRAINT `producto_proveedor_ibfk_1` FOREIGN KEY (`id_Proveedor`) REFERENCES `proveedor` (`id_Proveedor`),
  CONSTRAINT `producto_proveedor_ibfk_2` FOREIGN KEY (`id_Producto`) REFERENCES `productos` (`id_Producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto_proveedor`
--

LOCK TABLES `producto_proveedor` WRITE;
/*!40000 ALTER TABLE `producto_proveedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `producto_proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id_Producto` int NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Descripcion` varchar(200) DEFAULT NULL,
  `Cantidad_stock` int NOT NULL,
  `Precio_compra` int NOT NULL,
  `Precio_venta` int NOT NULL,
  `id_categoria` int NOT NULL,
  `id_ubicacion` int NOT NULL,
  PRIMARY KEY (`id_Producto`),
  KEY `id_categoria` (`id_categoria`),
  KEY `id_ubicacion` (`id_ubicacion`),
  CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_Categoria`),
  CONSTRAINT `productos_ibfk_2` FOREIGN KEY (`id_ubicacion`) REFERENCES `ubicacion` (`id_Ubicacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'Manzana Roja','Fruta fresca y crujiente',103,5,8,1,3),(2,'Jabón Líquido','Jabón antibacterial para manos',154,15,25,2,2),(3,'Pasta de Dientes','Con flúor y protección anticaries',95,10,18,2,2),(4,'Queso Fresco','Producto lácteo refrigerado',68,20,35,1,3),(5,'Cuaderno Universitario','100 hojas rayadas, tapa dura',53,12,20,3,1);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `id_Proveedor` int NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Telefono` varchar(30) NOT NULL,
  `Correo` varchar(70) DEFAULT NULL,
  `Direccion` varchar(70) NOT NULL,
  PRIMARY KEY (`id_Proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (1,'Proveedor A','555-1234','contacto@proveedora.com','Calle 123, Ciudad X'),(2,'Proveedor B','555-5678','contacto@proveedorb.com','Calle 456, Ciudad Y'),(3,'Proveedor C','555-8765','contacto@proveedorc.com','Calle 789, Ciudad Z');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ubicacion`
--

DROP TABLE IF EXISTS `ubicacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ubicacion` (
  `id_Ubicacion` int NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Descripcion` varchar(3000) DEFAULT NULL,
  PRIMARY KEY (`id_Ubicacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ubicacion`
--

LOCK TABLES `ubicacion` WRITE;
/*!40000 ALTER TABLE `ubicacion` DISABLE KEYS */;
INSERT INTO `ubicacion` VALUES (1,'Estanteria #5','Es la estanteria que esta a la derecha de la entrada'),(2,'Almacén Principal','Ubicación principal de almacenamiento de productos antes de ser exhibidos.'),(3,'Mostrador','Zona frontal donde se exhiben los productos listos para la venta.'),(4,'Cámara Fría','Área refrigerada destinada a conservar productos perecederos.');
/*!40000 ALTER TABLE `ubicacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_Usuario` int NOT NULL,
  `Nombre_usuario` varchar(50) NOT NULL,
  `Contraseña` varchar(100) NOT NULL,
  `Nombre_completo` varchar(100) NOT NULL,
  `Rol` varchar(50) NOT NULL,
  `Telefono` varchar(20) NOT NULL,
  `Ciudad` varchar(50) NOT NULL,
  `Direccion` varchar(150) NOT NULL,
  PRIMARY KEY (`id_Usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Miyu','123','Miyu Maldonado','Administrador','2284246692','Xalapa','Los lagos #1'),(2,'Coco','Manzana123','Coco Maldonado','Empleado','2283256672','Xalapa','FEI'),(3,'Prueba','123','Prueba de agregar','Administrador','23214312312','Ninguna','calle');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vista_productos`
--

DROP TABLE IF EXISTS `vista_productos`;
/*!50001 DROP VIEW IF EXISTS `vista_productos`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_productos` AS SELECT 
 1 AS `id_Producto`,
 1 AS `Nombre`,
 1 AS `Descripcion`,
 1 AS `Cantidad_stock`,
 1 AS `Precio_compra`,
 1 AS `Precio_venta`,
 1 AS `categoria`,
 1 AS `ubicacion`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'PoSmart'
--

--
-- Dumping routines for database 'PoSmart'
--
/*!50003 DROP PROCEDURE IF EXISTS `agregar_Categoria` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `agregar_Categoria`(
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
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregar_Compra` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `agregar_Compra`(
    IN pfecha DATE,
    IN ptotal DOUBLE,
    IN pid_proveedor INT,
    IN pid_usuario INT
)
BEGIN
    INSERT INTO Compra (fecha_compra, total, id_Proveedor, id_Usuario)
    VALUES (pfecha, ptotal, pid_proveedor, pid_usuario);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregar_DetalleCompra` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `agregar_DetalleCompra`(
    IN pid_producto INT,
    IN pid_compra INT,
    IN pcantidad INT,
    IN pmonto_final DOUBLE
)
BEGIN
    INSERT INTO Detalle_compra (id_Producto, id_Compra, Cantidad, Monto_final)
    VALUES (pid_producto, pid_compra, pcantidad, pmonto_final);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregar_producto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `agregar_producto`(
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
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregar_proveedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `agregar_proveedor`(
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
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `agregar_Ubicacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `agregar_Ubicacion`(
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
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminar_Categoria` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `eliminar_Categoria`(IN id INT)
BEGIN
    DELETE FROM Categoria WHERE id_Categoria = id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminar_proveedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `eliminar_proveedor`(IN id INT)
BEGIN
    DELETE FROM Proveedor WHERE id_Proveedor = id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminar_Ubicacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `eliminar_Ubicacion`(IN id INT)
BEGIN
    DELETE FROM Ubicacion WHERE id_Ubicacion = id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `existe_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `existe_usuario`(Nombre_Com varchar(20))
BEGIN
SELECT Nombre_completo
FROM Usuario
WHERE Nombre_completo = Nombre_Com;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modificar_Categoria` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `modificar_Categoria`(
    IN p_id INT,
    IN p_nombre VARCHAR(255),
    IN p_descripcion VARCHAR(3000)
)
BEGIN
    UPDATE Categoria
    SET Nombre = p_nombre,
        Descripcion = p_descripcion
    WHERE id_Categoria = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modificar_proveedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `modificar_proveedor`(
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
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modificar_Ubicacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `modificar_Ubicacion`(
    IN p_id INT,
    IN p_nombre VARCHAR(255),
    IN p_descripcion VARCHAR(3000)
)
BEGIN
    UPDATE Ubicacion
    SET Nombre = p_nombre,
        Descripcion = p_descripcion
    WHERE id_Ubicacion = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `obtener_rol` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`localhost` PROCEDURE `obtener_rol`(
    IN nombre_user VARCHAR(50),
    IN contraseña_user VARCHAR(100)
)
BEGIN
    SELECT Rol
    FROM Usuario
    WHERE Nombre_usuario = nombre_user
      AND Contraseña = contraseña_user;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `validacion_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `validacion_usuario`(Nombre_User varchar(20), Contraseña_user varchar(255))
BEGIN
SELECT *
FROM Usuario
WHERE Nombre_usuario = Nombre_User
  AND Contraseña = Contraseña_user;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Current Database: `PoSmart`
--

USE `PoSmart`;

--
-- Final view structure for view `vista_productos`
--

/*!50001 DROP VIEW IF EXISTS `vista_productos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`Hiram`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_productos` AS select `p`.`id_Producto` AS `id_Producto`,`p`.`Nombre` AS `Nombre`,`p`.`Descripcion` AS `Descripcion`,`p`.`Cantidad_stock` AS `Cantidad_stock`,`p`.`Precio_compra` AS `Precio_compra`,`p`.`Precio_venta` AS `Precio_venta`,`c`.`Nombre` AS `categoria`,`u`.`Nombre` AS `ubicacion` from ((`productos` `p` join `categoria` `c` on((`p`.`id_categoria` = `c`.`id_Categoria`))) join `ubicacion` `u` on((`p`.`id_ubicacion` = `u`.`id_Ubicacion`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-25 17:08:50
