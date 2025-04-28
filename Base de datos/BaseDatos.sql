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
-- Table structure for table `Categoria`
--

DROP TABLE IF EXISTS `Categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Categoria` (
                             `id_Categoria` int NOT NULL,
                             `Nombre` varchar(50) NOT NULL,
                             `Descripcion` varchar(70) NOT NULL,
                             PRIMARY KEY (`id_Categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Categoria`
--

LOCK TABLES `Categoria` WRITE;
/*!40000 ALTER TABLE `Categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `Categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Compra`
--

DROP TABLE IF EXISTS `Compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Compra` (
                          `id_compra` int NOT NULL,
                          `fecha_compra` date NOT NULL,
                          `total` int NOT NULL,
                          `id_Proveedor` int NOT NULL,
                          `id_Usuario` int NOT NULL,
                          PRIMARY KEY (`id_compra`),
                          KEY `id_Proveedor` (`id_Proveedor`),
                          KEY `id_Usuario` (`id_Usuario`),
                          CONSTRAINT `compra_ibfk_1` FOREIGN KEY (`id_Proveedor`) REFERENCES `Proveedor` (`id_Proveedor`),
                          CONSTRAINT `compra_ibfk_2` FOREIGN KEY (`id_Usuario`) REFERENCES `Usuario` (`id_Usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Compra`
--

LOCK TABLES `Compra` WRITE;
/*!40000 ALTER TABLE `Compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `Compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Detalle_compra`
--

DROP TABLE IF EXISTS `Detalle_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Detalle_compra` (
                                  `id_Detalle` int NOT NULL,
                                  `id_Producto` int NOT NULL,
                                  `id_Compra` int NOT NULL,
                                  `Cantidad` int NOT NULL,
                                  `Monto_final` int NOT NULL,
                                  PRIMARY KEY (`id_Detalle`),
                                  KEY `id_Producto` (`id_Producto`),
                                  KEY `id_Compra` (`id_Compra`),
                                  CONSTRAINT `detalle_compra_ibfk_1` FOREIGN KEY (`id_Producto`) REFERENCES `Productos` (`id_Producto`),
                                  CONSTRAINT `detalle_compra_ibfk_2` FOREIGN KEY (`id_Compra`) REFERENCES `Compra` (`id_compra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Detalle_compra`
--

LOCK TABLES `Detalle_compra` WRITE;
/*!40000 ALTER TABLE `Detalle_compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `Detalle_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Producto_Proveedor`
--

DROP TABLE IF EXISTS `Producto_Proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Producto_Proveedor` (
                                      `id_Proveedor` int NOT NULL,
                                      `id_Producto` int NOT NULL,
                                      KEY `id_Proveedor` (`id_Proveedor`),
                                      KEY `id_Producto` (`id_Producto`),
                                      CONSTRAINT `producto_proveedor_ibfk_1` FOREIGN KEY (`id_Proveedor`) REFERENCES `Proveedor` (`id_Proveedor`),
                                      CONSTRAINT `producto_proveedor_ibfk_2` FOREIGN KEY (`id_Producto`) REFERENCES `Productos` (`id_Producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Producto_Proveedor`
--

LOCK TABLES `Producto_Proveedor` WRITE;
/*!40000 ALTER TABLE `Producto_Proveedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `Producto_Proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Productos`
--

DROP TABLE IF EXISTS `Productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Productos` (
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
                             CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `Categoria` (`id_Categoria`),
                             CONSTRAINT `productos_ibfk_2` FOREIGN KEY (`id_ubicacion`) REFERENCES `Ubicacion` (`id_Ubicacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Productos`
--

LOCK TABLES `Productos` WRITE;
/*!40000 ALTER TABLE `Productos` DISABLE KEYS */;
/*!40000 ALTER TABLE `Productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Proveedor`
--

DROP TABLE IF EXISTS `Proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Proveedor` (
                             `id_Proveedor` int NOT NULL,
                             `Nombre` varchar(100) NOT NULL,
                             `Telefono` varchar(30) NOT NULL,
                             `Correo` varchar(70) DEFAULT NULL,
                             `Direccion` varchar(70) NOT NULL,
                             PRIMARY KEY (`id_Proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Proveedor`
--

LOCK TABLES `Proveedor` WRITE;
/*!40000 ALTER TABLE `Proveedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `Proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ubicacion`
--

DROP TABLE IF EXISTS `Ubicacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Ubicacion` (
                             `id_Ubicacion` int NOT NULL,
                             `Nombre` varchar(50) NOT NULL,
                             `Descripcion` varchar(70) NOT NULL,
                             PRIMARY KEY (`id_Ubicacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ubicacion`
--

LOCK TABLES `Ubicacion` WRITE;
/*!40000 ALTER TABLE `Ubicacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `Ubicacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Usuario`
--

DROP TABLE IF EXISTS `Usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Usuario` (
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
-- Dumping data for table `Usuario`
--

LOCK TABLES `Usuario` WRITE;
/*!40000 ALTER TABLE `Usuario` DISABLE KEYS */;
INSERT INTO `Usuario` VALUES (1,'Miyu','123','Miyu Maldonado','Administrador','2284246692','Xalapa','Los lagos #1'),(2,'Coco','Manzana123','Coco Maldonado','Empleado','2283256672','Xalapa','FEI'),(3,'Prueba','123','Prueba de agregar','Administrador','23214312312','Ninguna','calle');
/*!40000 ALTER TABLE `Usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'PoSmart'
--

--
-- Dumping routines for database 'PoSmart'
--
/*!50003 DROP PROCEDURE IF EXISTS `agregar_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `agregar_usuario`(p_id int, p_Nombre_usuario VARCHAR(20), p_Contraseña VARCHAR(50), p_Nombre_completo VARCHAR(50), p_Rol VARCHAR(20), p_Telefono VARCHAR(15), p_Ciudad VARCHAR(30), p_Direccion VARCHAR(100)
)
BEGIN
INSERT INTO Usuario (id_usuario, Nombre_usuario, Contraseña, Nombre_completo, Rol, Telefono, Ciudad, Direccion)
VALUES (p_id, p_Nombre_usuario, p_Contraseña, p_Nombre_completo, p_Rol, p_Telefono, p_Ciudad, p_Direccion);

SELECT id_usuario
FROM Usuario
WHERE id_usuario = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cambiar_contraseña` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Hiram`@`%` PROCEDURE `cambiar_contraseña`(Nombre_Com varchar(100), Nueva_contraseña varchar(255))
BEGIN
UPDATE Usuario
SET Contraseña = Nueva_contraseña
WHERE Nombre_completo = Nombre_com;

SELECT id_Usuario
FROM Usuario
WHERE Contraseña = Nueva_contraseña;
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-25 13:53:40
