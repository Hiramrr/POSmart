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
INSERT INTO `categoria` VALUES (1,'Gatos','Aqui se agregaran todos los productos de gatos'),(2,'Bebidas','Productos l√≠quidos como jugos, refrescos, aguas y otras bebidas.'),(3,'Comida R√°pida','Productos listos para consumir como hamburguesas, papas fritas, y snacks.'),(4,'Postres','Dulces y productos de reposter√≠a como pasteles, helados y galletas.');
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
INSERT INTO `productos` VALUES (1,'Manzana Roja','Fruta fresca y crujiente',104,5,8,1,3,NULL),(2,'Jab√≥n L√≠quido','Jab√≥n antibacterial para manos',153,15,25,2,2,NULL),(3,'Pasta de Dientes','Con fl√∫or y protecci√≥n anticaries',194,10,18,2,2,NULL),(4,'Queso Fresco','Producto l√°cteo refrigerado',68,20,35,1,3,NULL),(5,'Cuaderno Universitario','100 hojas rayadas, tapa dura',53,12,20,3,1,NULL),(6,'Miyu','Miyu es un gato sordo',500,432123,3216321,1,3,NULL),(7,'Maruchan','maruchan',98,14,19,3,1,NULL),(10,'Mapache','Un mapache se metio al almacen!!!!!!!!!!!!',1,1000000,9999999,1,4,_binary 'ˇ\ÿˇ\‡\0JFIF\0\0\0\0\0\0ˇ\€\0Ñ\0	( \Z%!1!%)+...383-7(-.+\n\n\n\r\Z-%%--------------------------------------------------ˇ¿\0\0∑\"\0ˇ\ƒ\0\0\0\0\0\0\0\0\0\0\0\0\0\0ˇ\ƒ\0B\0\0\0\0\0!1AQa\"qÅë2°±¡\·\—#BRbÇÒrí¢\¬\‚$3Cc≤\“ˇ\ƒ\0\0\0\0\0\0\0\0\0\0\0\0\0\0ˇ\ƒ\0#\0\0\0\0\0\0\0\0\0!1AQ\"2aqˇ\⁄\0\0\0?\0\Â£@Ç£®¿x≠ü§Ó∂ßÖùó\"ïäéZ\Á&¯\\ò.∑\¬\‘4¬É6\ŒL@CyM\“πKà⁄Ω@-*\'4ì—®\\nQ\⁄\·E\ƒ:\–-J0Jì\Ì\—tÄpP=∫Mì\Ï9∂	ﬂød\Ì*\”\⁄.Œäå\◊LAé\nöj¡û*\Ìóg\Óu&¥7Ωa\'`π≥,ÆQp\'%+N%\0a^\ZLÇõ\·Ú∫≈≤\ZU≥Ñ\‘\\cQ\‘/\√ir`¸KiÇ÷éÒ1\‡ˇ\03\ÊWmZ\Èj≤Åà\Ï˝yò∑>\n/\ÿ*0]•_üä\‘A∞Åw E≠&\Îz\‘iê	-øüÿ£ÒFh\Êï\‹GÖ•r∫\\≤õ…ãıã˙EÇOå»¢\‚>æàU®Hñ\ÿ:\œ	é/<Q\rÀà28.i\ÂQm⁄∏\Z‹±\≈\Õa°ÛÃö2\‰\◊	ô8A-ªR^\–guk8o ∏±ºÆTù}àîÄÅ\Êµ\r•œ¨V≠\ƒW|q¥>£\Ê\0D)∞ôix6Ñó\rç!¿WLõUÄ¥¬ûI\œì∏ï\⁄x\“|Q\ÊYâ6VJ¯B´x\‹ôP%≤íSï≥FVKá®\Í‘ãH$çäÜ^6∫\Ë\Ÿ8\√˚1¶Ñ6s\Ï¢\¬˝óÚ™N)9>(É±≠\r§\‡˚*∑ù\’\’Q\⁄E•1f/Hé\\U\·,C´+ó\ÿ>lF\ 2TÉwıiΩßeÎ´ñéÖv6˛\nY-\né&	^b\ÍöbAB\–&e]Ö◊Ö6îZ15<p ä\nz\0∏2ºMq j\ÿxæ\ÍJ8Ú¥k\‰#pLk\ÏwO*Ø\ÿf˛¡™bK¨°©áî\ÀÇ”≤T\‡ö-/\Í\ræÅôGJûïb“∂\"V∑~ö\…\rBvΩ8S≠∞çx\–äÆÖ&%\ƒ\ÿxÆy\ÀVí∑egÅ/v\€+NYÑ@ôê \…Q\‡ú\÷\0 ÇD8¯û$|¢ª[SK\"\›\‚„∑∫@ng\≈uÆ*.êp\√Ä56\◊\‚f\Á‘Å\ÍP\‘\Èµ⁄ãdÿÄ\„\∆¸¸¶\›î(\Z≤O-\"L†r\Î\‚Ω˝å\0ôáSs…†l±®	\Ÿs¡i∏ù¿€†\È\Õ\‹%F\Ó¿H\‚n\œµ\n\Óê\„s;\Ï>∫5:´¶L\Ã\œ€ß$l\‘\Í\0\r$4:\Ê0,∑£EÑç-õ¶\Â\œ\’¡$±∞`∫OÊÇ´Öqn¶ì\‚\rè=è\—k>ï\'∫˙X\Ëé<<†™\Ê1\’\ZHÙ#bùµÜ\œ\‹x\ÓãvTm\√Y&\∆\—6)gé2ÙYE2,á	™ô\÷$ê´ç  Æo	V\Z\ÿ\◊\·Å\"\ƒq¸ïòπ\’èº¸\∆»≤Iø\Î+\ËN3\n\›\–g\0N…û∞xÇ¶¶\Õ>\nÒ\…(ÒõfÑß•î\„ùM\÷\Ÿkå\‚â\ r\ÌCY\ŸVM8ΩÇ⁄Æáb3óL-5π\∆\\\·	~.êa.+”Ü/`x>Jz\≈.$ÜæŸÉg-]ôA\“\„*∏˙Ñ(´<π2¬ô¥-é§$l∑¡R:HÑ´!\≈u\∆\…˚q4\ÈIq\›rN.3‘úì\\5£@∫\Œ\\\€(\Ó˜TÆ\œ)ÉΩî¥s1P\√n78Ùe“¥\⁄z7\Ê≠xl+\r0yÖ_\œ\Î6cäû=≠\”¿#,s\À$Uß$â¶\–\‚:¨K_à.2x¨TX_\ÿhT\\F\ l(µ¡iUê`Ø8∫\Î§\∆Ù∏Sx{A@V\ √çå 2¨gõ:§Æi\'D©≈õ\÷\ÏÛ¥ç\Á\rì\‡KéßVë\“˙oa˛¶ñ\Ô1∏\Ë}N¡\„ød\¬Rs{uΩˇ\0≈π\rh<\0èö\€\⁄j8∂:é 6≥\·ˇ\0\√1≥à\ÓzÆàbz˛\Ãx¶rf\…N≤\Ã#\»\„~äÕûv)¥}á\’Rì∂kΩ\Ê8n\◊s\Ëz%Xlø\Ó9≈π&Jä\∆$Ù≠hó;S¨>˛J|=6j0vW\‹˘]\Ã√°ﬂ´]6£Üh7Å\ ˜∫tV)\·(@˜Ñ\…\ }\—9˘\"®ÇÊπåÄ\ƒo\‘\ÿo\’.4\‡\» 7q~$\Óö\Â:\Œ\Zò\0aÛå\08\«_U5—öHYNê{\Àmç\…&O\¬í˜HHh,\0o!\÷\‰ã¶ı≤2N™OßPçò\◊\0\È\ÊÄ\‘zâ¯Ç	0\·:öZ\“gàÊûö\”óò\Z∞∆∑\⁄Fƒç utVUæöêgp/\ÍD˙5*\Ãp\rh=Ah\÷\Ê¥h¶Lq\–\¬ËÄì6¬á∂≈≠Ò\‡í\·!Ω\ŸlÒ\‘>£ube\rSπ<¯\"~â5•Æ\“˚oüå0ç™\–”§\r∑?Øä°vü uê`ç¡\‡Bº\‡YD˚\„Kº#\ÂuÆ}ñ±‘ùÆ\"˚ù∏¢éeÑêS™N.Ç´CEó¥j8L.y´ ’∞\∆aX\„r\‹#©6ûô\nüNun¥\≈ÉπSñ\'.X;˘	\œ#]çëô¡iaIZç\—T51\ÌÖI\√Ù\‘.<†\Ï\Î.k\Zåo\r\Ê≠y\Õ\"XD%)¿Qm\Î®\·\Ãˇ\0øAŒÅc[\Ï\›\0\Ï†\≈\„\⁄\‡ÆU0∏jÕëgK\«\–\–Ú\‘¯rG\'´®1í`é\Ïø\ÍRB=Q\r].ü\ƒ¨A{â+l3ou>≥Ü,\Ÿk_\’◊Ñû—´≤\‚±o\∆\rGy•á!CBÆ(Ω\“Q\÷GVçTiTÅ≤ÚûbF\Í\Z\ \—?F£†\Â,n/\n´øKPwi:öv1}Bß\‘0ı\⁄xázÛV?\√Z5ùZX\“\Ê\0Cˇ\0óKÑ˛WO{IŸ†k∂.\ÿ˜dXÒ<”Æ*4y\√\\∑7Æ_•µ\0¶\‡\–Zwôô°:!õ	\Zm<\Ì˛<™ù(∑xÉô˛]∂)æ\Á2@\‹\0x\Õ\œV˙©4U2µX±ïö\Ÿ\'T\ƒsGU\–\”g\\GIÇº\ƒ\‡ub#NÕê\0\€ı©\«\Ï\“\'ÑIÅ#¨ù\ƒ¡ÚB\∆*òjîi\÷∏$»ÜI\“NÚ\Ë\·”äﬂµΩ≥}\"\∆0n\–@∏\0l ∑\‰öc2f∏KIqâN\‹eƒ™ñgê\÷v-≤\«T$\0\‚Ië\ \0\ÿt	°Ëí†æ\«\Ê´V\0\»2\Ï\0\ﬁm¿+j\∆\nÆ™_\Ô9\«H0Dôﬂö!òá`¶\–œæDÄ∂N¸\ ﬁ¶Yﬁ¶@\ﬂPÇ||úæ\r¸â©∫ûπ`#\0\»ı\ﬂ\…<\√\ËÇZ\„[â)[†Cu8¥‹ãOç¨ö\‡Ú»®G©<∂?D¬®5Œü\r\€\◊T.Éo®\»\ÊxzÑ“õ§Cl<?+®á¶f[X\ÍA˙†¿Ö,£¸ßP\ﬁ\‰É\È\≈áf°¶\«\Èc\0[Öäê0í4à?\ÕK˝\n¡9\Áh0aµàù˜˚•vÅ]˚Ká%\ƒ\Èì\ƒÃÉ\ÕU^\'™ñ^xB|ΩêgÄR‘†\Á¡u=g1Õâ∫;*h-é!FS•blê\‰L®{Û=\ﬂg}âˆçq D\„&\Ã\Õ\"e≤ÖŒ≥ß∂r\‰¸ôw\‡äR≥\\ƒö≠§G \nî\Â-\0K«Ç\\1Bõ\0m\œ\…˚k¶\\JM&¸\“C|\Íï\Z8rCˇ\0y\¬7TjïKåù\—\ÿ\⁄ZâqqKóß¸x(\«˝)H∆ÖΩBºX\Âv8^§#1§•¶lã≠NQÌÇÖéj\ƒQ`X≠∞\÷\≈&¢µp#qQ ù\ÏQSlò@\«H¸+\ƒ\ZL´\¬H˛a√ñ\≈Y0!ı˜$ã«§˙$=ä\¬E#∏$Ûˆ],√±≠\‘\Á\Ó˛	}WÅ\ $˚√Ä[n¸vV\\ª.1ﬁê [îI˘™éo¯ìá\√Um6—´RK@\0o\ZA=\Ôlöˆc∑tÒxä¥\Zbì\\Æ\’$<ùàñ€™4É\–j4çL\Œ\÷_ñ\‰G§z´^?+a?OX\Ï\Ã˛\⁄jw\0;n+Ç*Ø\‚nùz\Ï®\‚=ùOf\–\–\\\Á;\ƒ5≤cTâ\Ëí)1\‰\ÿ^#*!¶	É$\€\‚\'e-iµ\‚&ƒÑ\Ô#\Ì&Ÿ£R\\7cÅk«ãJ\Ã\œ¢\Ì∑Q éñπ	®[+}Ø`\’M\¬ã	É\Ê9$¥Hqá\ﬁ:ùºÉµñ”õü?™ı ECa^^¢å˝)\0∞\ƒ5\Ó\r2\'àÉ	êß©\√vBo\Œ%à¶C\…i?\ÿ\Ô˙c\‡òjé™2;pÒ∫(\Ã` ÅªÒ6ì\Á˘Ø∏…ê<˝z\Êée˚«úü\–R	q˛\Ó\‚&»±@]w{\∆< £0\·†ií\Óáq\ÂG∞\Zg§[\ƒ\"÷∞çMπ˛.#\Ã-F±&uDÎ∏â\√ı\‚®\Ÿ\Œ¥\Ôe\—3A7q\Î\◊\‘$O¿öçs\‚\„iˇ\0+q™`j\ \Ê%h3(—ìñ]¶ä\‘\ÎP®E\€\‚•˝≤´˜y\\91\Œ˝\·\À(¥«éß¶¯†qØ!†/iLI++\”3\"\≈rÎ¨≠ôR0`\Ìp°ØÉhΩ\√\„ on\»qñêö;Eˇ\0Ç≤ª\\\À\‡à⁄òQ\n\”Iåwu\Ì(ZπP3†\œE\ŸÒ™m\—O® ≠E\”Lvá\\AA{+Æ≠\Ìª#iÖ\Î_(á·¨¥¶\"\ƒ\"∫åè=ö\ƒKb¡bå(aS∞-[Üx›•o∞V(mPà∫\◊wà\0	\ﬂc\Íd°\‡∏\¬gïRax˜ù†\Êw=Vf:\0˚\ZlsD37Û&\…wj˚ZÛL\—\r-ë\ﬁtô#†d˚fò\‘\€G?¥z%9éGN†Å`xCHÒ¥Gà ¯•HT\ V*®Æ\–gΩ\0q¯ïd¸2\ \Í7\Ìà=\—{\ﬂ;ä∑dk±\‡\“\Z¡ [\ﬁ\∆é±˘ÆÕêe\r\√a\⁄\Õ\ÃOEuA¥Aña\\*{B/$ÙÇ#\Ó∏\÷wóV•ä®Xj1\≈\Ô$∂f\Ê}\‡A‚æá¡\0@çäÆ˛!vwUVìuú\–.w∏?ëN=\Z\”9\œgÛ∑\‡ö\\5·πº\Ê<I2∫\ﬁI\⁄z8¶\◊\\ã¥Ç<A\\Füf±#∏G˙Å\ —®¯Ä∫dr˜\–iÜ∏π=\Ÿ˛\ﬁ)õ£Q`\Œ]\0<˛™øNûóì±\Á\À\‡≠>¢\ÕW≥<9ê\·¿ÛRí1uZ ;Q∂Úxí+áí\\-˛\Ô	≠¥5n	\"¸`˙lèßákf:é]DnH\rõ2\€Ò\„`|∫X\‘LÙËà§F\‰\ﬁ/#öë\–m\"x[\“»äh 8\«\"∫Ç¶Z`˙\œ\Î®R:õßQ˙\Á∫±ù\‰¡µ\Ô¿¨Jt\ZL˘âøQ)U:lß\\\È36-<˘\œ`§lz Ø\Ê \»;ÛËïåÄ;IÄ¶Ú]±\Â*ò¡ñ\Ì\≈\\+c!áª}∂ï6/•RúÅ•\‹x(\Õlç<w\‘T≤\◊\Áp[cû˙H7JŸÉ^3\≈%\Í9$öª&\Î\›%Ü“è´N@*<“†k\Õ<\'\⁄ïˆ\ÁJÙfAÑJã\n\ÈX\„Ù2H\'é/72Pu_uB\¬\È)îipz\√\◊u#\ÿ“ñ±§ò	æ\“V#§	\Ï◊®\„bü\‰`ÿìèi\0=°/Œ©Sô¶å•Öß §ÿê@\›W≠îã∞\'ÑÀ≥¥™\ÁîYbä\ \·T ì\√\Î\≈]éŒïÑ•›É©«ÑÄ\0^V;Lt\ÓÉ\Ë-ÒﬂóVX¶\€rlO=¯(±4Ùôâ3s\¬vÅ\Ã\ƒ\ﬂ\Ïâ4\¬rå\≈\ÕpíKgñ\›wÅ∫k\⁄\Œ\“˚8h=˙é\“\”h.\„\–[\‘$5E\‚gï¢\0\È˜U\Ã¯:≠-\ﬁ\·\‘\”»õGü.âîæ≠ª-8_\ƒ\n¥\Õ!†9ç#\⁄.·≥àû;ü\…t\ÏVh\∆S\÷LÇ%†n\·#\’|ŸÄ\√>{\’$\0ñπ\“o∂\–GS¥Æ≤\ÏK´Rµ˚ÉH\Â\r0%õH41\≈f°\Ì}˜Ll\ƒ\r≈º<æ\‹\’k”≠Ç˝\ﬁ\·û]\◊\Á\«\’>Ç\”\“«áØ\Õ Bò\'\Ìœâ\Ëë\Á\ÿrL4m<∏\„\'\Ÿ{\⁄L\“¸>\›R\Ã⁄ômYhü1\Ê\…ô\ÊÄ`\»\ﬂiÛG\“lëk?0V\ÿwò\·»Ö5;|!-\—\–\”\ÎÚ^:èH\È√°Æ+JgQ=G¯Û˚-^e∫\\n	\√{m1\ÈÍââò=P∏ÜÚ\'c1ˆS\·Ò\¬\“föôÙ\…\ÿ[ë˘Öå\Ínã™Fp˜π≥#v¯+^}ò\ZMù∂º«Ü\ÍùN®®Ú\„òJ∆ã£|≤ìûon_Æ*\∆j±≠Ç\01\’\rÖkK\Zy±	_hs/\‡‹ãuéBÑΩ-e{@T™`\ÓV\ÿ\Ó\Œ;F∂ã¯ß6Z rüb\‹)∑†êô\‰Æ,iú\ÍÜ \ƒ8ABcXIì\ËòfUEJáEÆö\Â˘°ﬂº\Ï@$|¸Iu3ù\·iß\›Eà©u\Ã˚L7^ßz[ÛU\\F^\–v*êW\„„êãQD*ÖenBj\ÎÙ¯§ÿúµ\Ã0B™˙\rR$¡πG\‘\∆\0,óäv^—¢A7d\⁄Lé¶$\…^≠_\0ØS\‘F\rd∆ë\≈/≈¥ã\'Y]=Ry-Ü\0\\\Ìó4s(…¶*ï)`úo≤?ÛL\√w\„˛´K\‹n4\„o\‡ß\»2\›oá6lƒû¶	Ö\’)uçgA\Ï¯%Ç@ÒéC\«\·\“\ËúE9\“¡πígx\ÊG\r\Á\…Öß°Å†ã˝x[\≈%\ÕÒbõ`©\Óõ¿˛3\Àef,Cq%∫x\0~Ò¯S\Ã\Ó\⁄/\„\ƒ| ∂\‚i\≈2cq\·ø¿ïW\∆aÀ§ÚàGÑ¢\\\0\‹\ﬂ\√«Ä]7&H`>M\’*=\È\Â˙€äª\·•∑π \œ3∞€Ä\‰∞63¡¥>£\€iS\€}Sä¨LZ\“> ¸˛\nµóUsj\”\⁄`\…àüAÛV:?Ñß\–l+|ñN∞¶\‹\nIûc!\“ıı∂¸ïíêÜ;á–Æq⁄ºd8\ƒ^\'àü\‚ë∑\Õf;˝Ω∫¡ããà#¿˙Ö\Ê8\◊JG=1\ÎıPŸâvì~±˛õ\À$≠x\‡H?SÛBå_jº±≠#ê˘~pµ\√VL¡.2zˇ\0>Qu•j†Ç—æˇ\0˛EÉd9ÙÕ¶\„\√S†˝|\–0cf\€M\‰m\—E\¬fmøá0R˙t\›!\√\ﬁ\‚8/)G\·ö\0\‘\0\0ãéGîñ1N\Ì˚I\'ß\":*vi∂N\„\‰öˆÉ0˝\„©6d\›7\‰Ug7kò@<W#õ\‹G.ñ,v\ÿsI± épG\Ê§\¬\—ˆ\Œ\‘\‡d\’8>!Z{=õµÑj6ê< ØÖ£ê∑a∞˙\"b\›y™ˇ\0hsC\Z6v\‹\«\Ÿ:Õ≥6±≤\€œª\–ÚT|n\'\⁄\’.6òSP∑\“\ FôN\Îí\…§Æáî\“c[b@>?Tè#†[q[hÅ¶G\Á\…\€7Ä9ê¶\‰™j\‡#~#Çª\Ê¡Äm¬°c*∑^\‡âKè˚¯X{?òóà,ë\«¸-˚Ií≥[\Z;˝îyªNù≠¥Ñ\„¥m-¢\\NùKÇMZ\È\ Òâ(-L1l$ Å¥\Ê ¨\‰±{ú±\ZppkA˘eb\√\—»ú[\ﬁdi≤éÆ)†Am¬ìö[à\Ê∏Óêè\À?\Ì]\–eZ˚( çpozˇ\0\‘I<|\0Q\„1\r≥Äéièg*á\‘lXOxÒßV\Í\≈rlºª4KÆ\0ÒıU\Ô\ÿMGÇGtÉ§n@Lû$\Ó>\È\ﬁ:°q\rmá\ƒ\‹!ÇçpàkèX˘.¶Q*!≈≤X\ﬂA∑ı1\„˜U\\}of;\ﬂÚ#\’]÷á0\"Aù¨πom±dWãã^xê\\\”>@2ÉfZ\Ê\Í\‘~\\\'™ªS`\ƒ\ﬁd\Ô¿˘\\Û≤7>†q\rkF\Ôu\‹b\—\«¿yê∫V2=üt,\—Ãùâ\ÿy\¬TÖ^êÂïö˜¥\Ï{≥± ∆ã\Ïv#ö≥`Æ@p\Ôì\Œf˛duIp¯a\ﬁ`\⁄IıÇ\◊≠Å˙*’Ö•\ƒ\ÔG6∆ªM29¥Å>ï«≥:Ö\Œ$óù˜c\’tå\Á5oyÄâiÒ%£\ c\’PÛ™≥Ä\»Ò\Î\Â7ÛD\»P\ÍGo4~Z\·MÕû3\nzXMá7i\Ï\√\\9«á$¨Ã∑äÑπØãß\¬\‰|ëx⁄•ï\Zˇ\0\Âû6∏ãu\0™\ÊSòÉFOÛ1£\Ã8}>)x®\«˛7«ãI1Ú>e!é,πÖé≥Ãµ€Å™ûìo4\√\Îõ;√ØÁ∫Ø\·´gfóù†\»˙¶}m]l\·>b\ŒØÊµåP3Z!∏\«\»Ú¥òh¿ˆ\Ó>\Ÿ“ä\Ìx3#ı˙C`±çwvg¢ÚÛ\Ìlé\\ñ•e7O59˙`Ög\ÕÚyó≥nJüöì0Ø¸y©æ\roÉ1ô∫£4ìpA\nZtMúl\ZyÉu£Ñ.â\œRéM2\‹–Ä\‡¨\’sM4ı:ÖÕ®bK_\“S^!\Ì¥\ÀHRs¥o\ \Ïwú\Á\ÏÜ\ÔSö\\{yV~\…\‚Y≈Ñ\\ì∂8gQ\ƒ@\ÿ\‹-ã\'\Ì≠t\À3r¶7¿\„CY ¡˘ÙYä\Õ\ÍTnÇd*˛6riHµ\ﬁ\ÍØ∫\ZYÖYï@\—\’*ßâM≥\∆0ê∂ô	\„RV*¶1¬±-Ha\ÌJ!\¬R|KM7áV\"π¶\Ë[CjX©A5ˇ\0	´A‘±lsD´c\√€Ç\rÄ&˚O˘\\˚\“\«Y[;3ò;A-˜yA;˘&«ãˆT\rkßS\ƒa\…p\"\0èô\0ì\Î1˝+lS@õÇ\Ÿ3\Ã;W\ƒé©/c3≤ÒQµ.aì¸ßhû™©€Æ\÷S§cMPAC@∑¨µ]®Wk{H)\‚}ìvõb\‹Ù\Í\‘¬¶f5Mg˚B\"\ﬂ\„\Íñ’Æ\ÍïâíN˛ä\…Jàˆb\ y\'\\Nàrº\’\ri\r\ﬁyπ\r\—t\ÏK¨¿›ÜõqÄ\Ê\Õ˘Óπæ]£D¿\‘	\Î∫fU@\‘\ÔÉ Ç7ã	\€\Œˇ\0\Âm\–a—æOHh\‹\Í7\È<<\«¸\ﬁå\√2m\…\"\«\Ë\È˘DØ:\Ã€Ü§\„®L#õáy≠Òë\ÿ*\ƒˆÜ≠k8\€[Oó{Pˇ\0òß\ÿfZÒx¢\ÏQ\√ì\–D¸AÛPbû}∂†6\„\ }Rä8≤A \‹\‘cI˛ë\«\Êõ`ûj:F\‰jı\"°Y;\n\·\Èày˛©öG\€\n\„@ç\«u\√ˇ\0©ı¸µ\ƒ4OP|t\«˝#\’QªeàäÅ\Ì\Ÿ\¬\ÎoÅ±ˇ\0IÅêQ\Õt≤õf!∆°\Â>\Îm\‡\ŸW|£Nì\Ê¬∞˝Úr\‰u\…q\0\»Bπvs0çV\Ã\Ëm\0:õm\Ê’™åã\ﬁX\‚\Ô\⁄0\‰\\5˙:\Ó\“\ﬂFÉ\Í§\»\Í\ZgA˜K†ÙsßK¸∫zÄx®0¯êkÉ¸\Ì\Ÿ\Ì\”o4\œ˙¸Tòå15\\\„ÓπÄÙ1c<†Ä|\œ$¨¢)˝¨\Ã™\·\»¸˜Uå>4¥ìƒ¢ª@\Í9—§ÇCÑÒ\Ê:\“\Ê\”H¢ü§dïÜ?¥5∂õ%x˙∫\ÃÒ*LU+ \Z¯MQã¥çæh∂9\œ!∂\‚Ä\¬F\Èùw5°,\◊Ed\rp‚öóáRé!\"J ct¿\‡íXæÄ\—?d±~\À$¿&É∑\ÿ∏∂®\0ULm\r$<q˘´VO\⁄vö~Œ∏õnßö2RéXi¶§ä\÷êr6ï2\·.Ø\\\nÆslŸ≤-π®6W\ÌYödx™\Œ;ÑÆªû“Æ\¬d®s:≠uÄZ¥4`ï\Í(PXØEh\€;tæB]yê¨\Ï\Âw\Ï˙Gª3©¿y7X{+Yç\’QÙ\⁄\Àw\‰ê$ƒõm(®4ÑM%BBÚwW>\≈\‡s≠¿GOº¢N2ZN˜q\r≥\\\‚Ka†4Òí#Ü“¨ï1NfSßﬁ¶\Z)¥à\ﬂﬁóH\"Lß∆©ÇIûT}61\Óat\ÎkG\"”™d˘˙ïH\ÃDù^™\»\Í\›KŸÜÀâk»ë∞\"|BS[\"ƒìô3\¬[?4%\ÿx+¡^⁄∏≠\“\⁄=ùƒá\Èõrs5a•\ŸQâü\Â5®FÚ&\—\’Fxõ`í@Up˙ÄπΩ\–A<-7∫Íùö™ÛHLAÄ\›6\0O/™†\ÂYEV\‘{M`Cà\⁄&b\”ccÛW\nŸì)a^)ê⁄åà›∑\ryç\ƒI\›Z0QçzUªaö˚\\C©É,ap=^wéÇ\„\’WÇ\"\‡-\ÿyy!⁄Åºò7\‚|—è{YHûpûø%Õ§∂±ömÅ\‡Íπ†\⁄}\„\ÊaZÚå;Äl\ŸˆûÄ^:>I~_ã£Iæ—¥ÖBLNπÉ\·\√\∆l&bk;hq\·\«\ÃÒ‰™†\Èp\‘Klf˜#ıÒ\\ß9≈∏\‘x\·®\Ô\¬\ÊÀ¶\’\ÕNõ§ôk}\›Ãù≠Êπñj\ÁπŒÜÒ\'c\≈g\Za±}&íöa*{6∏sâÚ2Ö\√–®6câÂ§¶¥2 §1•Ñ9\›\„® \Ïo¡	_¿-5?mIÆ$á\r˘\»èc\»+}™QíH<g\–\«/≤ØeÇÖ*m`™Ä$ò Lòí\‰ü`Û6∂õ\⁄l4ºàã	;\ ?fRtræ\–\–}:\œa2Zo\‘näR\ÃA(\ÏÛ5±pp#T\Œ-){Ärï”†7\”zòêBÅ\‘\ÕB\„	Üh{î»∞\”jû®ÜÖ(\‚å∆Ä\Ê∂˚%-™T∆§¨—ò]\'\”ïö\ÈOÑ,¥≠.µsO⁄ç\–\€,ˆcc∏J©8±\“8&ïá¥\“\Ê˘©J4+V\rXép€¢qt4(0\’gtWW\rA,0∑qSS£\"\ ¬âKSZ4\÷®9X©∏\÷}¸ÜÑäM\ÊLü(æ€≠1›ò\√\‘ii§\” Xπ„≠†,XΩsÇé\ƒ`\‡∞a\ÿªáΩ7\Z∏Û\·\—GS±ò3-\–\Ê\ÿYØ{Zr_Ü˚ã§√≥&¡ˆZã¶µ\Ó\Ô9¸\«?U)\Ïæ\Z\‰áH¥\Ôù\„uã§k=ë§\”\›söfFƒà\‰}Üˆ´^1Rgç3â\Ÿ\Î≈âZ\nd˘wfqTkj\ÿd¡\È\“\\;ay\nb*∞bZåˇ\0\Ã5¬°h:•\‚n\‚G?\ÈX±+äÃà˛S-c\rw˜`É\rízò^\„ö\·j≈£i\“ÚÄ>´!H;1cø\rq,ù\Z°\Ïk\≈\»h#U\›\›;Å#ç˘\"\Î˛bAëäh\‰C#Ωeã\’fOóv\Z≠\"ÍÜ∏{\›\›.-á7¿\ﬁÛ«¢r\ﬁ\œV<ZH\‹LN˝,FíÿèŸº\≈ÿçms[Hw{ÆhxiçBH\Ê\ﬂR∂\¬vo\⁄ˆ\0÷Çi\‘kò\\\\ÈªÑÉ≥¢, ,X∂®;\0\‡2úYc}•\r4á8ö%œû=\«C[ßHÅ\»ÛL2‹¶§\√¿\” Ä`\Ì¿˜çÆ±b?çv3°ï\√,•:åK~[))d°\◊4\Ë¥h#p$ã†ø≥xw{\‘(\Í<È∑èÇ\«ˆ_\Ê\È4(6ñX±\”\Ï>$\·®uΩx-.\0ô˝ôÄmª∑Ú+,cGˆ\„Éx\ÿ9\„n@C¯{Ç¥Q={\Ó:]b\ƒiè˛\‡Æ=õ\ÃÒˆÜGÆ\Î\ á4\ÓT®\Ÿ;r≈â\\\"\’4`\\\√›µ\"+ë˝†\€\’ˇ\0\nMÙb}Yˇ\0rıb\—\«∏Élñü\·≠v˚˙g˚\\cø1´Hü\Ó ¡¸\0L\Ô¬¨y>ı˜ªˇ\0\ ≈ã~(áfˇ\Ÿ');
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
INSERT INTO `ubicacion` VALUES (1,'Estanteria #5','Es la estanteria que esta a la derecha de la entrada'),(2,'Almac√©n Principal','Ubicaci√≥n principal de almacenamiento de productos antes de ser exhibidos.'),(3,'Mostrador','Zona frontal donde se exhiben los productos listos para la venta.'),(4,'C√°mara Fr√≠a','√Årea refrigerada destinada a conservar productos perecederos.');
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
  `Contrase√±a` varchar(100) NOT NULL,
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
    IN p_imagen LONGBLOB  -- nuevo par√°metro para la imagen
)
BEGIN
    DECLARE v_id_categoria INT;
    DECLARE v_id_ubicacion INT;

    -- Buscar la categor√≠a
    SELECT id_Categoria INTO v_id_categoria
    FROM Categoria
    WHERE Nombre = p_nombre_categoria
    LIMIT 1;

    -- Si no existe, insertarla
    IF v_id_categoria IS NULL THEN
        INSERT INTO Categoria(Nombre, Descripcion) VALUES (p_nombre_categoria, 'Categor√≠a agregada autom√°ticamente');
        SET v_id_categoria = LAST_INSERT_ID();
    END IF;

    -- Buscar la ubicaci√≥n
    SELECT id_Ubicacion INTO v_id_ubicacion
    FROM Ubicacion
    WHERE Nombre = p_nombre_ubicacion
    LIMIT 1;

    -- Si no existe, insertarla
    IF v_id_ubicacion IS NULL THEN
        INSERT INTO Ubicacion(Nombre, Descripcion) VALUES (p_nombre_ubicacion, 'Ubicaci√≥n agregada autom√°ticamente');
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
    IN contrase√±a_user VARCHAR(100)
)
BEGIN
    SELECT Rol
    FROM Usuario
    WHERE Nombre_usuario = nombre_user
      AND Contrase√±a = contrase√±a_user;
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
CREATE DEFINER=`Hiram`@`%` PROCEDURE `validacion_usuario`(Nombre_User varchar(20), Contrase√±a_user varchar(255))
BEGIN
SELECT *
FROM Usuario
WHERE Nombre_usuario = Nombre_User
  AND Contrase√±a = Contrase√±a_user;
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
