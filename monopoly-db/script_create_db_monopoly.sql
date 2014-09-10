CREATE DATABASE  IF NOT EXISTS `monopoly_db` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `monopoly_db`;
-- MySQL dump 10.13  Distrib 5.5.28, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: monopoly_db
-- ------------------------------------------------------
-- Server version	5.5.30-log

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
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `usuarioID` bigint(20) NOT NULL AUTO_INCREMENT,
  `userName` varchar(250) DEFAULT NULL,
  `password` blob,
  `nombre` varchar(150) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`usuarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

ALTER TABLE `monopoly_db`.`usuario` 
ADD UNIQUE INDEX `userName_UNIQUE` (`userName` ASC);

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `monopoly_db`.`usuario` (`userName`,`password`,`nombre`,`email`)
VALUES ('pablo','IKIppp','Pablo','pablo_la31@hotmail.com');

-- IKIppp --> Encriptada = 123456
INSERT INTO `monopoly_db`.`usuario` (`userName`,`password`,`nombre`,`email`)
VALUES ('ale','IKIppp','Ale','alebostico@gmail.com');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
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
INSERT INTO `tarjeta_comunidad` (`tarjetaComunidadID`, `objetivo`) VALUES (1,'PAGA POR TU PÓLIZA DE SEGURO 50 €.'),(2,'EN TU  CUMPLEAÑOS RECIBES\\nDE CADA JUGADOR 10 €.'),(3,'COLÓCATE EN LA CASILLA DE SALIDA.'),(4,'PAGA LA FACTURA DEL MÉDICO 50 €.'),(5,'HAS GANADO EL SEGUNDO\\nPREMIO DE BELLEZA.\\nRECIBE 10 €.'),(6,'ERROR EN LA BANCA A TU FAVOR.\\nRECIBE 200 €.'),(7,'VE A LA CÁRCEL.\\nVE DIRECTAMENTE SIN\\nPASAR POR LA\\nCASILLA DE SALIDA\\nY SIN COBRAR LOS 200 €.'),(8,'HACIENDA TE DEVUELVE 20 €.'),(9,'COBRAS UNA HERENCIA DE 100 €.'),(10,'RECIBE 100 € POR LOS INTERESES\\nDE TU PLAZO FIJO.'),(11,'PAGA AL HOSPITAL 100 €.'),(12,'RETROCEDE HASTA RONDA DE VALENCIA.'),(13,'QUEDAS LIBRE DE LA CÁRCEL.\\n\\nEsta carta puede venderse o conservarse\\nhasta que sea utilizada.'),(14,'LA VENTA DE TUS ACCIONES TE PRODUCE 50 €.');
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
INSERT INTO `tarjeta_estacion` (`tarjetaPropiedadID`, `precioAlquiler`, `valorDosEstacion`, `valorTresEstacion`, `valorCuatroEstacion`) VALUES (23,25,50,100,200),(24,25,50,100,200),(25,25,50,100,200),(26,25,50,100,200);
/*!40000 ALTER TABLE `tarjeta_estacion` ENABLE KEYS */;
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
INSERT INTO `tarjeta_compania` (`tarjetaPropiedadID`, `vecesPorUnaCarta`, `vecesPorDosCartas`) VALUES (27,4,10),(28,4,10);
/*!40000 ALTER TABLE `tarjeta_compania` ENABLE KEYS */;
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
  `color` varchar(45) DEFAULT NULL,
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
INSERT INTO `tarjeta_calle` (`tarjetaPropiedadID`, `precioAlquiler`, `valorUnaCasa`, `valorDosCasas`, `valorTresCasas`, `valorCuatroCasas`, `valorHotel`, `precioCadaCasa`, `precioCadaHotel`, `color`) VALUES (1,2,10,30,90,160,250,50,50,'MARRON'),(2,4,20,60,180,320,450,50,50,'MARRON'),(3,6,30,90,270,400,550,50,50,'CELESTE'),(4,6,30,90,270,400,550,50,50,'CELESTE'),(5,8,40,100,300,450,600,50,50,'CELESTE'),(6,10,50,150,450,625,750,70,100,'FUCSIA'),(7,10,50,150,450,625,750,70,100,'FUCSIA'),(8,12,60,180,500,700,900,80,100,'FUCSIA'),(9,14,70,200,550,750,950,90,100,'NARANJA'),(10,14,70,200,550,750,950,90,100,'NARANJA'),(11,16,80,220,600,800,1000,100,100,'NARANJA'),(12,18,90,250,700,875,1050,110,150,'ROJO'),(13,18,90,250,700,875,1050,110,150,'ROJO'),(14,20,100,300,750,925,1100,120,150,'ROJO'),(15,22,110,330,800,975,1150,130,150,'AMARILLO'),(16,22,110,330,800,975,1150,130,150,'AMARILLO'),(17,24,120,360,850,1025,1200,140,150,'AMARILLO'),(18,26,130,390,900,1100,1275,150,200,'VERDE'),(19,26,130,390,900,1100,1275,150,200,'VERDE'),(20,28,150,450,1000,1200,1400,160,200,'VERDE'),(21,35,175,500,1100,1300,1500,175,200,'AZUL'),(22,50,200,600,1400,1700,2000,200,200,'AZUL');
/*!40000 ALTER TABLE `tarjeta_calle` ENABLE KEYS */;
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
  `valorPropiedad` int(11) NOT NULL,
  `valorHipotecario` int(11) NOT NULL,
  `nombreImagen` varchar(45) DEFAULT NULL,
  `isHipotecada` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`tarjetaPropiedadID`),
  KEY `fk_tarjeta_propiedad_jugador` (`jugadorID`),
  CONSTRAINT `fk_tarjeta_propiedad_jugador` FOREIGN KEY (`jugadorID`) REFERENCES `jugador` (`jugadorID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjeta_propiedad`
--

LOCK TABLES `tarjeta_propiedad` WRITE;
/*!40000 ALTER TABLE `tarjeta_propiedad` DISABLE KEYS */;
INSERT INTO `tarjeta_propiedad` (`tarjetaPropiedadID`, `jugadorID`, `nombre`, `valorPropiedad`, `valorHipotecario`, `nombreImagen`, `isHipotecada`) VALUES (1,NULL,'RONDA DE VALENCIA',60,30,'Tarjeta02.jpg',0),(2,NULL,'PLAZA DE LAVAPIÉS',60,30,'Tarjeta04.jpg',0),(3,NULL,'GLORIETA CUATRO CAMINOS',100,50,'Tarjeta07.jpg',0),(4,NULL,'AVENIDA REINA VICTORIA',100,50,'Tarjeta09.jpg',0),(5,NULL,'CALLE BRAVO MURILLO',120,60,'Tarjeta10.jpg',0),(6,NULL,'GLORIETA DE BILBAO',140,70,'Tarjeta12.jpg',0),(7,NULL,'CALLE ALBERTO AGUILERA',140,70,'Tarjeta14.jpg',0),(8,NULL,'CALLE FUENCARRAL',160,80,'Tarjeta15.jpg',0),(9,NULL,'AVENIDA FELIPE II',180,90,'Tarjeta17.jpg',0),(10,NULL,'CALLE VELÁZQUEZ',180,90,'Tarjeta19.jpg',0),(11,NULL,'CALLE SERRANO',200,100,'Tarjeta20.jpg',0),(12,NULL,'AVENIDA AMÉRICA',220,110,'Tarjeta22.jpg',0),(13,NULL,'CALLE MARÍA DE MOLINA',220,110,'Tarjeta24.jpg',0),(14,NULL,'CALLE CEA BERMÚDEZ',240,120,'Tarjeta25.jpg',0),(15,NULL,'AVENIDA DE LOS REYES CATÓLICOS',260,130,'Tarjeta27.jpg',0),(16,NULL,'CALLE BAILÉN',260,130,'Tarjeta28.jpg',0),(17,NULL,'PLAZA ESPAÑA',280,140,'Tarjeta30.jpg',0),(18,NULL,'PUERTA DEL SOL',300,150,'Tarjeta32.jpg',0),(19,NULL,'CALLE ALCALÁ',300,150,'Tarjeta33.jpg',0),(20,NULL,'GRAN VÍA',320,160,'Tarjeta35.jpg',0),(21,NULL,'PASEO DE LA CASTELLANA',350,175,'Tarjeta38.jpg',0),(22,NULL,'PASEO DEL PRADO',400,200,'Tarjeta40.jpg',0),(23,NULL,'ESTACIÓN DE GOYA',200,100,'Tarjeta06.jpg',0),(24,NULL,'ESTACIÓN DE LAS DELICIAS',200,100,'Tarjeta16.jpg',0),(25,NULL,'ESTACIÓN DEL MEDIODÍA',200,100,'Tarjeta26.jpg',0),(26,NULL,'ESTACIÓN DEL NORTE',200,100,'Tarjeta36.jpg',0),(27,NULL,'COMPAÑIA DE AGUAS',150,75,'Tarjeta29.jpg',0),(28,NULL,'COMPAÑIA DE ELECTRICIDAD',150,75,'Tarjeta13.jpg',0);
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
INSERT INTO `tarjeta_suerte` (`tarjetaSuerteID`, `objetivo`) VALUES (1,'VE AL PASEO DEL PRADO.'),(2,'VE A LA GLORIETA DE BILBAO.\\nSI PASAS POR LA CASILLA DE SALIDA\\nCOBRA 200 €.'),(3,'LA BANCA TE PAGA 50 € DE INTERESES.'),(4,'COLÓCATE EN LA CASILLA DE SALIDA.'),(5,'ADELANTATE HASTA LA CALLE CEA BERMÚDEZ.\\nSI PASAS POR LA CASILLA DE SALIDA,\\nCOBRA 200 €.'),(6,'RECIBES EL RESCATE POR EL SEGURO\\nDE TUS EDIFICIOS.\\nCOBRA 150 €.'),(7,'VE A LA CÁRCEL.\\nVE DIRECTAMENTE SIN\\nPASAR POR LA\\nCASILLA DE SALIDA\\nY SIN COBRAR LOS 200 €.'),(8,'MULTA POR EMBRIAGUEZ 20 €.'),(9,'RETROCEDE TRES CASILLAS.'),(10,'HAZ REPARACIONES EN TODOS TUS EDIFICIOS.\\nPAGA POR CADA CASA 25 €.\\nPAGA POR CADA HOTEL 100 €.'),(11,'LA INSPECCIÓN DE LA CALLE TE OBLIGA\\nA REPARACIONES.\\nPAGA 40 € POR CADA CASA.\\nPAGA 115 € POR HOTEL.'),(12,'QUEDAS LIBRE DE LA CÁRCEL.\\n\\nEsta carta puede venderse o conservarse\\nhasta que sea utilizada.'),(13,'PAGA POR GASTOS ESCOLARES 150 €.'),(14,'VE A LA ESTACIÓN DE LAS DELICIAS.\\nSI PASAS POR LA CASILLA DE SALIDA,\\nCOBRA 200 €.');
/*!40000 ALTER TABLE `tarjeta_suerte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jugador`
--

DROP TABLE IF EXISTS `jugador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jugador` (
  `jugadorID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`jugadorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jugador`
--

LOCK TABLES `jugador` WRITE;
/*!40000 ALTER TABLE `jugador` DISABLE KEYS */;
/*!40000 ALTER TABLE `jugador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ficha`
--

DROP TABLE IF EXISTS `ficha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ficha` (
  `fichaID` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `pathImageSmall` varchar(250) DEFAULT NULL,
  `pathImageBig` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`fichaID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ficha`
--

LOCK TABLES `ficha` WRITE;
/*!40000 ALTER TABLE `ficha` DISABLE KEYS */;
INSERT INTO `ficha` (`fichaID`, `nombre`, `pathImageSmall`, `pathImageBig`) VALUES (1,'auto','FichaS01.png','Ficha01.png'),(2,'sombrero','FichaS02.png','Ficha02.png'),(3,'bota','FichaS03.png','Ficha03.png'),(4,'plancha','FichaS04.png','Ficha04.png'),(5,'carretilla','FichaS05.png','Ficha05.png'),(6,'dedal','FichaS06.png','Ficha06.png'),(7,'barco','FichaS07.png','Ficha07.png'),(8,'perro','FichaS08.png','Ficha08.png'),(9,'bolsa de dinero','FichaS09.png','Ficha09.png'),(10,'caballo','FichaS10.png','Ficha10.png'),(11,'cañón','FichaS11.png','Ficha11.png');
/*!40000 ALTER TABLE `ficha` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'monopoly_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-02-07 18:29:17
