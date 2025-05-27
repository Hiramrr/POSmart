-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: posmart
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra`
--

LOCK TABLES `compra` WRITE;
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
INSERT INTO `compra` VALUES (1,'2025-05-13',18,1,2),(2,'2025-05-13',18,1,2),(3,'2025-05-13',18,1,2),(4,'2025-05-13',18,1,2),(5,'2025-05-13',8,1,2),(6,'2025-05-13',88,1,2),(7,'2025-05-13',8,1,2),(8,'2025-05-14',98,1,2),(9,'2025-05-26',51,1,2),(10,'2025-05-26',9999999,1,2),(11,'2025-05-26',9999999,1,2),(13,'2025-05-26',9999999,1,2),(14,'2025-05-26',19,1,2),(15,'2025-05-26',19999998,1,2),(16,'2025-05-26',9999999,1,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_compra`
--

LOCK TABLES `detalle_compra` WRITE;
/*!40000 ALTER TABLE `detalle_compra` DISABLE KEYS */;
INSERT INTO `detalle_compra` VALUES (1,3,4,1,18),(2,1,5,1,8),(3,1,6,1,8),(4,4,6,1,35),(5,2,6,1,25),(6,5,6,1,20),(7,1,7,1,8),(8,2,8,1,25),(9,3,8,1,18),(10,5,8,1,20),(11,4,8,1,35),(12,2,9,1,25),(13,1,9,1,8),(14,3,9,1,18),(15,10,10,1,9999999),(16,10,11,1,9999999),(17,10,13,1,9999999),(18,7,14,1,19),(19,10,15,2,19999998),(20,10,16,1,9999999);
/*!40000 ALTER TABLE `detalle_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `id_Producto` int NOT NULL,
  `nombre_Producto` varchar(100) NOT NULL,
  `cantidad` int NOT NULL,
  `id_proveedor` int NOT NULL,
  `nombre_Proveedor` varchar(100) NOT NULL,
  `total` int NOT NULL,
  KEY `id_proveedor` (`id_proveedor`),
  KEY `id_Producto` (`id_Producto`),
  CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id_Proveedor`),
  CONSTRAINT `pedido_ibfk_2` FOREIGN KEY (`id_Producto`) REFERENCES `productos` (`id_Producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (7,'Maruchan',5,1,'Proveedor A',70),(7,'Maruchan',50,1,'Proveedor A',700),(3,'Pasta de Dientes',50,2,'Proveedor B',500),(7,'Maruchan',50,1,'Proveedor A',700),(3,'Pasta de Dientes',100,2,'Proveedor B',1000);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
/*!50001 DROP VIEW IF EXISTS `pedidos`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `pedidos` AS SELECT 
 1 AS `id_Producto`,
 1 AS `nombre_Producto`,
 1 AS `cantidad`,
 1 AS `nombre_Proveedor`,
 1 AS `total`*/;
SET character_set_client = @saved_cs_client;

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
INSERT INTO `producto_proveedor` VALUES (1,7),(1,1),(2,2),(2,3),(3,4),(3,5),(3,6),(1,10);
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
  `Imagen` mediumblob,
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
INSERT INTO `productos` VALUES (1,'Manzana Roja','Fruta fresca y crujiente',104,5,8,1,3,NULL),(2,'Jabón Líquido','Jabón antibacterial para manos',153,15,25,2,2,NULL),(3,'Pasta de Dientes','Con flúor y protección anticaries',194,10,18,2,2,NULL),(4,'Queso Fresco','Producto lácteo refrigerado',68,20,35,1,3,NULL),(5,'Cuaderno Universitario','100 hojas rayadas, tapa dura',53,12,20,3,1,NULL),(6,'Miyu','Miyu es un gato sordo',500,432123,3216321,1,3,NULL),(7,'Maruchan','maruchan',98,14,19,3,1,NULL),(10,'Mapache','Un mapache se metio al almacen!!!!!!!!!!!!',1,1000000,9999999,1,4,_binary '�\��\�\0JFIF\0\0\0\0\0\0�\�\0�\0	( \Z%!1!%)+...383-7(-.+\n\n\n\r\Z-%%--------------------------------------------------��\0\0�\"\0�\�\0\0\0\0\0\0\0\0\0\0\0\0\0\0�\�\0B\0\0\0\0\0!1AQa\"q��2���\�\��#BRb��r��\�\�$3Cc�\��\�\0\0\0\0\0\0\0\0\0\0\0\0\0\0�\�\0#\0\0\0\0\0\0\0\0\0!1AQ\"2aq�\�\0\0\0?\0\�@����x������\"����Z\�&�\\�.�\�\�46\�L@CyM\��K�ڽ@-*\'4�Ѩ\\nQ\�\�E\�:\�-J0J�\�\�t�pP=�M�\�9�	߿d\�*\�\�.Ί�\�LA�\n�j��*\�g\�u&�7�a\'`��,�Qp\'%+N%\0a^\ZL��\��Ų\ZU��\�\\cQ\�/\�ir`�Ki�֎�1\��\03\�WmZ\�j���\��y��>\n/\�*0]�_��\�A��w E�&\�z\�i�	-��أ�Fh\�\�G��r�\\��ɋ���E�O�Ȣ\�>��U�H�\�:\�	�/<Q\rˈ28.i\�Qmڸ\Zܱ\�\�a��̚2\�\�	�8A-�R^\�guk8�o ����T�}����\�\r�ϬV�\�W|q�>�\�\0D)��ix6��\r�!��WL�U��I\����\�x\�|�Q\�Y�6VJ�B�x\��P%��S��FVK��\�ԋH$���^�6�\�\�8\��1��6s\�\����N)9>(���\r�\��*��\�\�Q\�E�1f/H�\\U\�,C�+�\�>lF\�2T�w�i��e뫖��v6�\nY-\n�&	^b\�bAB\�&e]�ׅ6�Z15<p �\nz\0�2�Mq j\�x�\�J8��k\�#pLk\�wO*�\�f���bK�����\��ӲT\��-/\�\r���GJ��bҶ\"V�~�\�\rBv�8S���x\����&%\�\�x�y\�V��eg�/v\�+NY�@�� \�Q\��\�\0 �D8��$|��[SK\"\�\�㷺@ng\�u�*.�p\��56\�\�f\�ԁ\�P\�\�ڋd؀\�\����\��(\Z�O-\"L�r\�\���\0��Ssɠl��	\�s�i���۠\�\�\�%F\��H\�n�\��\n\�\�s;\�>�5:��L\�\�ۧ$l\�\�\0\r$4:\�0,��E��-��\�\�\��$��`�O悫�qn��\�\r�=�\�k>�\'��X\�<<��\�1\�\ZH�#b���\�\�x\�vTm\�Y&\�\��6)g�2�YE2,�	��\�$��� ʮo	V\Z\�\�\�\"\�q����\����\�ȲI�\�+\�N3\n\�\�g\0Nɞ�x���\�>\n�\�(�f����\�M\�\�k�\�\�r\�CY\�VM8��ڮ�b3�L-5�\�\\\�	~.�a.+ӆ/`x>Jz\�.$��كg-]�A\�\�*���(�<�2�-��$l��R:H��!\�u\�\��q4\�Iq\�rN.3Ԝ�\\5�@�\�\\\�(\��T�\�)����s1P\�n78�eҴ\�z7\�xl+\r0y�_\�\�6c��=�\��#,s\�$U�$��\�\�:�K_�.2x�TX_\�hT\\F\�l(��iU�`�8�\�\���Sx{A@V\�Í� 2�g�:��i\'D�ś\�\��\�\r�\�K��V�\��oa���\�1�\�}N�\�d\�Rs{u��\0Ź\rh<\0��\�\�j8�:� 6�\��\0\�1��\�z��bz�\�x�rf\�N�\�#\�\�~�͞v)�}�\�R��k�\�8n\�s\�z%Xl�\�9Ź&J�\�$��h�;S�>�J|=6j0vW\��]\�á߫]6��h7�\���tV)\�(@��\�\�}\�9�\"��湌�\�o\�\�o\�.4\�\� 7q~$\�\�:\�\Z�\0a�\08\�_U5њHYN�{\�m�\�&O\���HHh,\0o!\�\�����2N�O�P��\�\0\�\��\�z���	0\�:�Z\�g�枚\���\Z�Ʒ\�Fč utVU���gp/\�D�5*\�p\rh=Ah\�\��h�Lq\�\�耓6�ŭ�\��\�!�\�l�\�>�ube\rS�<�\"~�5��\��o��0��\�Ӥ\r�?���v� u�`��\�B�\�YD�\�K�#\�u�}��ԝ�\"�����e��S�N.��CE��j8L.y� հ\�aX\�r\�#�6��\n�Nun�\���S�\'.X;�	\�#]����iaIZ�\�T51\�I\��\�.<�\�\�.k\Z�o\r\�y\�\"XD%)�Qm\�\�\��\0�A΁c[\�\�\0\�\�\�\�\�U0�j͑gK\�\�\��\��rG\'��1�`�\�\�RB=Q\r].�\��A{�+l3ou>��,\�k_\�ׄ�ѫ�\�o\�\rGy��!CB�(�\�Q\�GV�TiT���bF\�\Z\�\�?F��\�,n/\n��KPwi:�v1}B�\�0�\�x�z�V?\�Z5�ZX\�\�\0C�\0�K��WO{I٠k�.\��dX�<Ӯ*4y\�\\�7�_��\0�\�\�Zw���:!�	\Zm<\��<��(�x���]�)�\�2@\�\0x\�\�V��4U2�X���\�\'T\�sGU\�\�g\\GI��\�\�ub#N͐\0\���\�\�\�\'�I�#��\���B\�*�j�i\��$ȆI\�N�\�\�ӊߵ��}\"\�0n\�@�\0l �\�c2f�KIq�N\�eĪ�g�\�v-�\�T$\0\�I�\�\0\�t	�蒠�\�\��V\0\�2\�\0\�m�+j\�\n��_\�9\�H0D�ߚ!��`�\�ϾD��N�\�ަYަ@\�P�||��\r������`#\0\��\�\�<\�\�Z\�[�)[�Cu8�܋O���\��ȨG�<�?D¨5Ο\r\�\�T.�o�\�\�xz�қ�Cl<?+���f[X\�A����,���P\�\�\�\��f��\�\�c\0[���0�4�?\�K�\n�9\�h0a������v�]�K�%\�\�\�̃\�U^\'��^xB|��g�RԠ\��u=g1͉�;*h-�!FS�bl�\�L�{�=\�g}���q D\�&\�\�\"e��γ��r\���w\��R�\\Ě��G \n�\�-\0Kǂ\\1B�\0m\�\��k�\\JM&��\�C|\�\Z8rC�\0y\�7Tj�K��\�\�\�Z�qqK���x(\��)Hƅ�B�X\�v8^�#1���l��NQ킅�j\�Q`X��\�\�&��p#qQʝ\�QSl�@\�H�+\�\ZL�\�H�aÖ\�Y0!��$��Ǥ�$=�\�E#�$��],ñ�\�\�\��	}W�\�$�À[n�vV\\�.1ސ [�I���o���\�Um6ѫR�K@\0o\ZA=\�l��c�t�x��\Zb�\\�\�$<���۪4�\�j4�L\�\�_�\�G�z�^?+a?OX\�\��\�jw\0;n+�*�\�n�z\�\�=�Of\�\�\\\�;\�5�cT�\�)1\�\�^#*!�	�$\�\�\'e-i�\�&Ą\�#\�&٣R\\7c�kǋJ\�\��\�Q ���	�[+}�`\�M\��	�\�9$�Hq�\�:�����ӛ�?�� ECa^^���)\0�\�5\�\r2\'��	���\�vBo\�%��C\�i?\�\��c\��j��2;p�(\�`ʁ��6�\����ɐ<�z\��e�ǜ�\�R	q�\�\�&ȱ@]w{\�< �0\�i�\�q\�G�\Zg�[\�\"ְ�M��.#\�-F�&uD븉\��\�\�\��\�e\�3A7q\�\�\�$O���s\�\�i�\0+q�`j\�\�%h3(ѓ�]��\�\�P�E\�\�����y\\91\��\�\�(�ǎ����q�!�/iLI++\�3\"\�r묭�R0`\�p���h�\�\� on\�q���;E�\0���\\\�\��ژQ\n\�I�wu\�(Z�P3�\�E\���m\�O� �E\�Lv�\\AA{+��\��#i�\�_(�᬴�\"\�\"���=�\�Kb�b�(aS�-[�xݥo�V(mP��\�w�\0	\�c\�d�\�\�g�Rax���\�w=Vf:\0�\ZlsD37�&\�wj�Z�L\�\r-�\�t�#�d�f�\�\�G?�z%9�GN��`xCH�G� ��HT\�V*��\�g�\0q��d�2\�\�7\�=\�{\�;��dk�\�\�\Z� [\�\�����͐e\r\�a\�\�\�OEuA�A�a\\*{B/$�#\�\�w�V���Xj1\�\�$�f\�}\�A⾇�\0@����!vwUV�u�\�.w�?�N=\Z\�9\�g�\��\\5Ṽ\�<I2�\�I\�z8�\�\\���<A\\F�f�#�G��\�Ѩ���dr�\�i���=\��\�)��Q`\�]\0<���N����\�\�\�>�\�W�<9�\�����R�1uZ ;Q��x�+��\\-�\�	��5n	\"�`�l���kf:�]DnH\r�2\��\�`|�X\�L�舤F\��\�/#��\�m\"x[\�Ȋh 8\�\"���Z`�\�\�R:��Q�\���\���\���Jt\ZL���Q)U:l�\\\�36-<�\�`�lzʯ\�� \�;�蕌�;I���]�\�*���\�\�\\+c!��}��6/�R���\�x(\�l�<w\�T�\�\�p[c��H7Jك^3\�%\�9$��&\�\�%�ҏ�N@*<Ҡk\�<\'\����\�J�fA�J�\n\�X\��2H\'�/72Pu_uB\�\�)�ipz\�\�u#\�Җ���	�\�V#�	\�ר\�b�\�`ؓ�i\0=�/ΩS������ �ؐ@\�W����\'�˳��\�Yb�\�\�T �\�\�\�]�Ε��݃�Ǆ�\0^V;Lt\�\�-�ߗVX�\�rlO=�(�4���3s\�v�\�\�\�\�4\�r�\�\�p�Kg�\�w��k\�\�\��8h=��\�\�h.\�\�[\�$5E\�g��\0\��U\��:�-\�\�\�\�țG�.�����-8_\�\n�\�!�9�#\�.᳈�;�\�t\�Vh\�S\�L�%�n\�#\�|ـ\�>{\�$\0��\�o�\�GS���\�K�R���H\�\r0%�H41\�f�\�}�Ll\�\rż<�\�\�kӭ��\�\�]\�\�\�\�>�\�\�Ǉ�\� B�\'\�ω\�\�\�rL4m<�\�\'\�{\�L\��>\�R\�ڙmYh�1\�\��\�`\�\�i�G\�l�k?0V\�w�\�ȅ5;|!-\�\�\�\��^:�H\�á�+JgQ=G���-^e�\\n	\�{m1\�ꉉ�=P���\'c1�S\��\�\�f���\�\�[����\�n��Fp���#v�+^}�\ZM���ǆ\�N���\��JƋ�|���on_�*\�j���\01\�\r�kK\Zy�	_hs/\�܋u�B��-e{@T�`\�V\�\�\�;F����6Z r�b\�)����\�,i�\� \�8ABcXI�\�fUEJ�E��\���߼\�@$|�Iu3�\�i�\�E��u\��L7^�z[�U\\F^\�v*�W\�㐋QD*�enBj\����؜�\�0B��\rR$��G\�\�\0,��v^ѢA7d\�L��$\�^�_\0�S\�F\rdƑ\�/Ŵ�\'Y]=Ry-�\0\\\�4s(ɦ*�)`�o�?�L\�w\���K\�n4\�o\�\�2\�o�6lĞ�	�\�)u�gA\��%�@�C\�\�\�\�E9\����gx\�G\r\�\��������x[\�%\��b�`�\����3\�ef,Cq%�x\0~��S\�\�\�/\�\�|ʶ\�i\�2cq\���W\�aˤ��G��\\�\0\�\�\�ǀ]7&H`>M\�*=\�\��ۊ�\���� \�3�ۀ\��63��>�\�iS\�}S��LZ\�> ��\n��Usj\�\�`\����A�V:?��\�l+|�N��\�\nI�c!\���������;�Юqڼd8\�^\'��\���\�f;�������#���\�8\�JG=1\��Pىv�~���\�$�x\�H?S�B�_j���#��~p�\�VL�.2z�\0>Qu�j��Ѿ�\0�E�d9�ͦ\�\�S��|\�0cf\�M\�m\�E\�fm��0R�t\�!\�\�\�8/)G\�\0\�\0\0��G��1N\��I\'�\":*vi�N\�\���0�\�6d\�7\�Ug7k�@<W#�\�G.�,v\�sI� �pG\�\�\��\�\�\�d\�8>!Z{=���j6�<ʯ����a��\"�b\�y��\0hsC\Z6v\�\�\�:ͳ6��\�ϻ\��T|n\'\�\�.6�SP�\�\�F�N\�\�����\�c[b@>?T�#�[q[h��G\�\�\�7�9��\�j\�#~#��\���m¡c*�^\��K���X{?���,�\��-�I��[\Z;��y�N����\�m-�\\N�K�MZ\�\���(-L1l$ʁ�\�ʬ\�{��\ZppkA�eb\�\�Ȝ[\�di���)�Am�[�\�\�?\�]\�eZ�(ʍpoz�\0\�I<|\0Q\�1\r���i�g*�\�lXOx���V\�\�rl��4K�\0��U\�\�MG�Gt��n@L�$\�>\�\�:�q\rm�\�\�!���p�k�X�.�Q*!ŲX\�A��1\��U\\}of;\��#\�]և0\"A���om�dW��^x�\\\�>@2�fZ\�\�\�~\\\'��S`\�\�d\����\\�7>�q\rkF\�u\�b�\�\��y��V2=�t,\�̝��\�y\�T�^�啚��\�{�� Ƌ\�v#��`�@p\��\�f�duIp�a\�`\�I��\����*Յ�\�\�G6ƻM29��>�ǳ:�\�$���c\�t�\�5oy��i�%�\�c\�P����\��\�\�7�D\�P\�Go4~Z\�M͞3\nzXM�7i\�\�\\9Ǉ$�̷������\�\�|�xڥ�\Z�\0\��6��u\0�\�S��FO�1�\�8}>)�x�\��7ǋI1�>e!�,����̵ہ���o4\��\�;ï纯\��gf���\���}m]�l\�>b\��浌P3Z!�\�\����h��\�>\�Ҋ\�x3#���C`��wvg���\�l�\\��e7O59�`�g\��y��nJ���0��y��\ro�1���4�pA\nZtM�l\Zy�u��.�\�R�M2\�Ѐ\�\�sM4�:�ͨbK_\�S^!\�\�HRs�o\�\�w�\�\�\�S�\\{yV~\�\�Yń\\��8gQ\�@\�\�-�\'\�t\�3r�7�\�CY ���Y�\�\�Tn�d*�6riH�\�\��\ZY�Y�@\�\�*��M�\�0���	\�RV*�1±-Ha\�J!\�R|KM7�V\"��\�[CjX�A5�\0	�AԱlsD�c�\�ۂ\r�&�O�\\�\�\�Y[;3�;A-�yA;�&ǋ�T\rk�S\�a\�p\"\0��\0�\�1�+lS@��\�3\�;W\���/c3��Q�.a���h���ۮ\�S�cMPAC@���]�Wk{H)\�}�v�b\��\�\�¦f5Mg�B\"\�\�\�ծ\���N��\�J��b\�y\'\\N�r�\�\ri\r\�y�\r\�t\�K��݆�q�\�\��]�D�\�	\��fU@\�\� �7�	\�\��\0\�m\�aѾOHh\�\�7\�<<\��\��\�2m\�\"\�\�\��D�:\�ۆ�\�L#��y��\�*\����k8\�[O�{P�\0��\�fZ�x�\�Q\��\�D�A�Pb�}��6\�\�}R�8�A \�\�cI��\�\�`�j:F\�j�\"�Y;\n\�\�y���G\�\n\�@�\�u\��\0����\�4OP|t\��#\�Q�e���\�\�\�\�o���\0I��Q\�t��f!ơ\�>\�m\�\�W|�N�\�°��r\�u\�q\0\�B�vs0�V\�\�m\0:�m\�ժ��\�X\�\�\�0\�\\5�:\�\�\�F�\�\�\�\ZgA�K��s�K��z�x�0��k��\�\�\�\�o4\���T��15\\\��1c<��|\�$��)��\��\�\���U�>4��Ģ�@\�9Ѥ�C��\�:\�\�\�H���d��?�5��%x��\��*LU+ \Z�MQ����h�9\�!�\�\�F\�w5�,\�Ed\rp⚗�R�!\"J ct�\��X��\�?d�~\�$�&��\����\0ULm\r$<q��VO\�v�~θ�n��2R�Xi���\��r6�2\�.�\\\n�slٲ-��6W\�Y�dx�\�;����Ү\�d�s:�u�Z�4`�\�(PX�Eh\�;t�B]y��\�\�w\��G�3��y7X{+Y�\�Q�\�\�w\�$ěm(�4�M%BB�wW>\�\�s��GO��N2ZN�q\r�\\\�Ka�4�#�Ҭ�1NfS�ަ\Z)��\�ޗH\"L�Ʃ�I�T}61\�at\�kG\"Ӫd���H\�D�^�\�\�\�Kنˉkȑ�\"|BS[\"ē�3\�[?4%\�x+�^ڸ�\�\�=�ć\�rs5a�\�Q��\�5�F�&\�\�Fx�`�@Up����\�A<-7�Ꝛ��HLA�\�6\0O/��\�YEV\�{M`C�\�&b\�cc�W\nٓ)a^)�ڌ�ݷ\ry�\�I\�Z0Q�zU�a��\\C��,ap=^w��\�\�W�\"\�-�\�yy!ځ��7\�|я{YH�p��%ͤ���m�\�깠\�}\�\�aZ�;�l\����^:>I~_��I�Ѵ�BLN��\�\�\�l&bk;hq\�\�\��䪠\�p\�Klf�#��\\�9Ÿ\�x\�\�\�\�˦\�\�N���k}\�̝�湖j\�Ά�\'c\�g\Za�}&��a*{6�s��2�\�Ш6c�夦�2ʤ1��9\�\� \�o�	_�-5?mI�$�\r�\��c\�+}�Q�H<g\�\�/��e��*m`��$� L��\�`�6��\�l4���	;\�?fRtr�\�\�}:\�a2Zo\�n�R\�A(\��5�pp#T\�-){�r�Ӡ7\�z��B�\�\�B\�	�h{�Ȱ\�j����(\�ƀ\��%-�TƤ�ј]\'\���\�O�,��.�sOڍ\�\�,�cc�J�8�\�8&���\�\���J4+V\rX�pۢqt4(0\�gtWW\rA,0�qSS�\"\�KSZ4\��9X��\�}����M\�L�(�ۭ1ݘ\�\�ii�\� X�㭠�,X�s��\�`\�a\����7\Z��\�\�GS��3-\�\�\�Y�{Zr_����ó&��Z���\�\�9�\�?U)\�\Z\�H�\��\�u��k=��\�\�s�fFĈ\�}���^1Rg�3�\�\�ŉZ\nd�wfqTkj\��d�\�\�\\;ay\nb*�bZ��\0\�5¡h:�\�n\�G?\�X�+�̈�S-c\rw�`�\r�z�^\��\�jţi\��>�!H;1c�\rq,�\Z�\�k\�\�h#U\�\�;�#��\"\��bA��h\�C#�e�\�fO�v\Z�\"ꆸ{\�\�.-�7�\��Ǣr\�\�V<ZH\�LN�,F�؏ټ\�؍ms[Hw{�hxi�BH\�\�R�\�vo\��\0ւi\�k�\\\\黄���, ,X��;\0\�2�Yc}�\r4�8�%Ϟ=\�C[�H�\��L2ܦ�\��\� �`\������b?�v3��\�,�:�K~[))d�\�4\��h#p$����xw{\�(\�<鷏�\��_\�\�4(6�X�\�\�>$\�u�x-�.\0����m���+,cG�\��x\�9\�n@C�{��Q={\�:]b\�i��\�=�\����G�\�\��4\�T�\�;rŉ\\\"\�4`\\\��ݵ\"+���\�\��\0\nM�b}Y�\0r�b\�\���l��\�v��g�\\c�1�H�\� ���\0L\�¬y>����\0\�ŋ~(�f�\�');
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
-- Dumping events for database 'posmart'
--

--
-- Dumping routines for database 'posmart'
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
    IN p_nombre_ubicacion VARCHAR(50),
    IN p_imagen LONGBLOB  -- nuevo parámetro para la imagen
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

    -- Insertar el producto con la imagen
    INSERT INTO Productos (
        id_Producto, Nombre, Descripcion, Cantidad_stock,
        Precio_compra, Precio_venta, id_Categoria, id_Ubicacion, Imagen
    ) VALUES (
        p_id_producto, p_nombre, p_descripcion, p_cantidad_stock,
        p_precio_compra, p_precio_venta, v_id_categoria, v_id_ubicacion, p_imagen
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
-- Final view structure for view `pedidos`
--

/*!50001 DROP VIEW IF EXISTS `pedidos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`Hiram`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `pedidos` AS select `pedido`.`id_Producto` AS `id_Producto`,`pedido`.`nombre_Producto` AS `nombre_Producto`,`pedido`.`cantidad` AS `cantidad`,`pedido`.`nombre_Proveedor` AS `nombre_Proveedor`,`pedido`.`total` AS `total` from `pedido` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

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

-- Dump completed on 2025-05-26 23:10:30
