-- MySQL dump 10.13  Distrib 5.5.31, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: monopoly_db
-- ------------------------------------------------------
-- Server version	5.5.31-0ubuntu0.12.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Jugador`
--

DROP TABLE IF EXISTS `Jugador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Jugador` (
  `jugadorID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`jugadorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Jugador`
--

LOCK TABLES `Jugador` WRITE;
/*!40000 ALTER TABLE `Jugador` DISABLE KEYS */;
/*!40000 ALTER TABLE `Jugador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjeta_calle`
--

DROP TABLE IF EXISTS `tarjeta_calle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarjeta_calle` (
  `tarjetaPropiedadID` int(11) NOT NULL,
  `precioAlquiler` int(11) NOT NULL,
  `valorUnaCasa` int(11) NOT NULL,
  `valorDosCasas` int(11) NOT NULL,
  `valorTresCasas` int(11) NOT NULL,
  `valorCuatroCasas` int(11) NOT NULL,
  `valorHotel` int(11) NOT NULL,
  `precioCadaCasa` int(11) NOT NULL,
  `precioCadaHotel` int(11) NOT NULL,
  PRIMARY KEY (`tarjetaPropiedadID`),
  KEY `fk_tarjeta_calle_1` (`tarjetaPropiedadID`),
  CONSTRAINT `fk_tarjeta_calle_propiedad` FOREIGN KEY (`tarjetaPropiedadID`) REFERENCES `tarjeta_propiedad` (`tarjetaPropiedadID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjeta_calle`
--

LOCK TABLES `tarjeta_calle` WRITE;
/*!40000 ALTER TABLE `tarjeta_calle` DISABLE KEYS */;
INSERT INTO `tarjeta_calle` VALUES (1,2,10,30,90,160,250,50,50),(2,4,20,60,180,320,450,50,50),(3,6,30,90,270,400,550,50,50),(4,6,30,90,270,400,550,50,50),(5,8,40,100,300,450,600,50,50),(6,10,50,150,450,625,750,70,100),(7,10,50,150,450,625,750,70,100),(8,12,60,180,500,700,900,80,100),(9,14,70,200,550,750,950,90,100),(10,14,70,200,550,750,950,90,100),(11,16,80,220,600,800,1000,100,100),(12,18,90,250,700,875,1050,110,150),(13,18,90,250,700,875,1050,110,150),(14,20,100,300,750,925,1100,120,150),(15,22,110,330,800,975,1150,130,150),(16,22,110,330,800,975,1150,130,150),(17,24,120,360,850,1025,1200,140,150),(18,26,130,390,900,1100,1275,150,200),(19,26,130,390,900,1100,1275,150,200),(20,28,150,450,1000,1200,1400,160,200),(21,35,175,500,1100,1300,1500,175,200),(22,50,200,600,1400,1700,2000,200,200);
/*!40000 ALTER TABLE `tarjeta_calle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjeta_compania`
--

DROP TABLE IF EXISTS `tarjeta_compania`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarjeta_compania` (
  `tarjetaPropiedadID` int(11) NOT NULL,
  `vecesPorUnaCarta` int(11) NOT NULL,
  `vecesPorDosCartas` int(11) NOT NULL,
  PRIMARY KEY (`tarjetaPropiedadID`),
  KEY `fk_tarjeta_compania_propiedad` (`tarjetaPropiedadID`),
  CONSTRAINT `fk_tarjeta_compania_propiedad` FOREIGN KEY (`tarjetaPropiedadID`) REFERENCES `tarjeta_propiedad` (`tarjetaPropiedadID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjeta_compania`
--

LOCK TABLES `tarjeta_compania` WRITE;
/*!40000 ALTER TABLE `tarjeta_compania` DISABLE KEYS */;
INSERT INTO `tarjeta_compania` VALUES (27,4,10),(28,4,10);
/*!40000 ALTER TABLE `tarjeta_compania` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjeta_comunidad`
--

DROP TABLE IF EXISTS `tarjeta_comunidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarjeta_comunidad` (
  `tarjetaComunidadID` int(11) NOT NULL AUTO_INCREMENT,
  `objetivo` varchar(250) NOT NULL,
  PRIMARY KEY (`tarjetaComunidadID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjeta_comunidad`
--

LOCK TABLES `tarjeta_comunidad` WRITE;
/*!40000 ALTER TABLE `tarjeta_comunidad` DISABLE KEYS */;
INSERT INTO `tarjeta_comunidad` VALUES (1,'PAGA POR TU PÓLIZA DE SEGURO 50 €.'),(2,'EN TU  CUMPLEAÑOS RECIBES\\nDE CADA JUGADOR 10 €.'),(3,'COLÓCATE EN LA CASILLA DE SALIDA.'),(4,'PAGA LA FACTURA DEL MÉDICO 50 €.'),(5,'HAS GANADO EL SEGUNDO\\nPREMIO DE BELLEZA.\\nRECIBE 10 €.'),(6,'ERROR EN LA BANCA A TU FAVOR.\\nRECIBE 200 €.'),(7,'VE A LA CÁRCEL.\\nVE DIRECTAMENTE SIN\\nPASAR POR LA\\nCASILLA DE SALIDA\\nY SIN COBRAR LOS 200 €.'),(8,'HACIENDA TE DEVUELVE 20 €.'),(9,'COBRAS UNA HERENCIA DE 100 €.'),(10,'RECIBE 100 € POR LOS INTERESES\\nDE TU PLAZO FIJO.'),(11,'PAGA AL HOSPITAL 100 €.'),(12,'RETROCEDE HASTA RONDA DE VALENCIA.'),(13,'QUEDAS LIBRE DE LA CÁRCEL.\\n\\nEsta carta puede venderse o conservarse\\nhasta que sea utilizada.'),(14,'LA VENTA DE TUS ACCIONES TE PRODUCE 50 €.');
/*!40000 ALTER TABLE `tarjeta_comunidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjeta_estacion`
--

DROP TABLE IF EXISTS `tarjeta_estacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarjeta_estacion` (
  `tarjetaPropiedadID` int(11) NOT NULL,
  `precioAlquiler` int(11) NOT NULL,
  `valorDosEstacion` int(11) NOT NULL,
  `valorTresEstacion` int(11) NOT NULL,
  `valorCuatroEstacion` int(11) NOT NULL,
  PRIMARY KEY (`tarjetaPropiedadID`),
  KEY `fk_tarjeta_estacion_propiedad` (`tarjetaPropiedadID`),
  CONSTRAINT `fk_tarjeta_estacion_propiedad` FOREIGN KEY (`tarjetaPropiedadID`) REFERENCES `tarjeta_propiedad` (`tarjetaPropiedadID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjeta_estacion`
--

LOCK TABLES `tarjeta_estacion` WRITE;
/*!40000 ALTER TABLE `tarjeta_estacion` DISABLE KEYS */;
INSERT INTO `tarjeta_estacion` VALUES (23,25,50,100,200),(24,25,50,100,200),(25,25,50,100,200),(26,25,50,100,200);
/*!40000 ALTER TABLE `tarjeta_estacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjeta_propiedad`
--

DROP TABLE IF EXISTS `tarjeta_propiedad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarjeta_propiedad` (
  `tarjetaPropiedadID` int(11) NOT NULL AUTO_INCREMENT,
  `jugadorID` int(11) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  `valorHipotecario` int(11) NOT NULL,
  `nombreImagen` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tarjetaPropiedadID`),
  KEY `fk_tarjeta_propiedad_jugador` (`jugadorID`),
  CONSTRAINT `fk_tarjeta_propiedad_jugador` FOREIGN KEY (`jugadorID`) REFERENCES `Jugador` (`jugadorID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjeta_propiedad`
--

LOCK TABLES `tarjeta_propiedad` WRITE;
/*!40000 ALTER TABLE `tarjeta_propiedad` DISABLE KEYS */;
INSERT INTO `tarjeta_propiedad` VALUES (1,NULL,'RONDA DE VALENCIA',30,'image_1554.png'),(2,NULL,'PLAZA DE LAVAPIÉS',30,'image_1561.png'),(3,NULL,'GLORIETA CUATRO CAMINOS',50,'image_1565.png'),(4,NULL,'AVENIDA REINA VICTORIA',50,'image_1569.png'),(5,NULL,'CALLE BRAVO MURILLO',50,'image_1573.png'),(6,NULL,'GLORIETA DE BILBAO',70,'image_1577.png'),(7,NULL,'CALLE ALBERTO AGUILERA',70,'image_1581.png'),(8,NULL,'CALLE FUENCARRAL',80,'image_1585.png'),(9,NULL,'AVENIDA FELIPE II',90,'image_1589.png'),(10,NULL,'CALLE VELÁZQUEZ',100,'image_1593.png'),(11,NULL,'CALLE SERRANO',100,'image_1597.png'),(12,NULL,'AVENIDA AMÉRICA',110,'image_1601.png'),(13,NULL,'CALLE MARÍA DE MOLINA',110,'image_1605.png'),(14,NULL,'CALLE CEA BERMÚDEZ',120,'image_1609.png'),(15,NULL,'AVENIDA DE LOS REYES CATÓLICOS',130,'image_1613.png'),(16,NULL,'CALLE BAILÉN',130,'image_1617.png'),(17,NULL,'PLAZA ESPAÑA',140,'image_1621.png'),(18,NULL,'PUERTA DEL SOL',150,'image_1625.png'),(19,NULL,'CALLE ALCALÁ',150,'image_1629.png'),(20,NULL,'GRAN VÍA',160,'image_1633.png'),(21,NULL,'PASEO DE LA CASTELLANA',175,'image_1637.png'),(22,NULL,'PASEO DEL PRADO',200,'image_1641.png'),(23,NULL,'ESTACIÓN DE GOYA',100,'image_1534.png'),(24,NULL,'ESTACIÓN DE LAS DELICIAS',100,'image_1529.png'),(25,NULL,'ESTACIÓN DEL MEDIODÍA',100,'image_1542.png'),(26,NULL,'ESTACIÓN DEL NORTE',100,'image_1538.png'),(27,NULL,'COMPAÑIA DE AGUAS',75,'image_1546.png'),(28,NULL,'COMPAÑIA DE ELECTRICIDAD',75,'image_1550.png');
/*!40000 ALTER TABLE `tarjeta_propiedad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjeta_suerte`
--

DROP TABLE IF EXISTS `tarjeta_suerte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarjeta_suerte` (
  `tarjetaSuerteID` int(11) NOT NULL AUTO_INCREMENT,
  `objetivo` varchar(250) NOT NULL,
  PRIMARY KEY (`tarjetaSuerteID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjeta_suerte`
--

LOCK TABLES `tarjeta_suerte` WRITE;
/*!40000 ALTER TABLE `tarjeta_suerte` DISABLE KEYS */;
INSERT INTO `tarjeta_suerte` VALUES (1,'VE AL PASEO DEL PRADO.'),(2,'VE A LA GLORIETA DE BILBAO.\\nSI PASAS POR LA CASILLA DE SALIDA\\nCOBRA 200 €.'),(3,'LA BANCA TE PAGA 50 € DE INTERESES.'),(4,'COLÓCATE EN LA CASILLA DE SALIDA.'),(5,'ADELANTATE HASTA LA CALLE CEA BERMÚDEZ.\\nSI PASAS POR LA CASILLA DE SALIDA,\\nCOBRA 200 €.'),(6,'RECIBES EL RESCATE POR EL SEGURO\\nDE TUS EDIFICIOS.\\nCOBRA 150 €.'),(7,'VE A LA CÁRCEL.\\nVE DIRECTAMENTE SIN\\nPASAR POR LA\\nCASILLA DE SALIDA\\nY SIN COBRAR LOS 200 €.'),(8,'MULTA POR EMBRIAGUEZ 20 €.'),(9,'RETROCEDE TRES CASILLAS.'),(10,'HAZ REPARACIONES EN TODOS TUS EDIFICIOS.\\nPAGA POR CADA CASA 25 €.\\nPAGA POR CADA HOTEL 100 €.'),(11,'LA INSPECCIÓN DE LA CALLE TE OBLIGA\\nA REPARACIONES.\\nPAGA 40 € POR CADA CASA.\\nPAGA 115 € POR HOTEL.'),(12,'QUEDAS LIBRE DE LA CÁRCEL.\\n\\nEsta carta puede venderse o conservarse\\nhasta que sea utilizada.'),(13,'PAGA POR GASTOS ESCOLARES 150 €.'),(14,'VE A LA ESTACIÓN DE LAS DELICIAS.\\nSI PASAS POR LA CASILLA DE SALIDA,\\nCOBRA 200 €.');
/*!40000 ALTER TABLE `tarjeta_suerte` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-06-06 21:55:31
