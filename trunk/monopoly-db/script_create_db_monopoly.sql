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
  `nombrePropiedad` VARCHAR(45) NULL,
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
INSERT INTO `tarjeta_propiedad` (`tarjetaPropiedadID`, `jugadorID`, `nombre`, `valorPropiedad`, `valorHipotecario`, `nombreImagen`, `isHipotecada`,`nombrePropiedad`) 
VALUES 
(1,NULL,'RONDA DE VALENCIA',60,30,'/images/tarjetas/propiedades/tarjeta02.jpg',0,'tarjeta02'),
(2,NULL,'PLAZA DE LAVAPIÉS',60,30,'/images/tarjetas/propiedades/tarjeta04.jpg',0,'tarjeta04'),
(3,NULL,'GLORIETA CUATRO CAMINOS',100,50,'/images/tarjetas/propiedades/tarjeta07.jpg',0,'tarjeta07'),
(4,NULL,'AVENIDA REINA VICTORIA',100,50,'/images/tarjetas/propiedades/tarjeta09.jpg',0,'tarjeta09'),
(5,NULL,'CALLE BRAVO MURILLO',120,60,'/images/tarjetas/propiedades/tarjeta10.jpg',0,'tarjeta10'),
(6,NULL,'GLORIETA DE BILBAO',140,70,'/images/tarjetas/propiedades/tarjeta12.jpg',0,'tarjeta12'),
(7,NULL,'CALLE ALBERTO AGUILERA',140,70,'/images/tarjetas/propiedades/tarjeta14.jpg',0,'tarjeta14'),
(8,NULL,'CALLE FUENCARRAL',160,80,'/images/tarjetas/propiedades/tarjeta15.jpg',0,'tarjeta15'),
(9,NULL,'AVENIDA FELIPE II',180,90,'/images/tarjetas/propiedades/tarjeta17.jpg',0,'tarjeta17'),
(10,NULL,'CALLE VELÁZQUEZ',180,90,'/images/tarjetas/propiedades/tarjeta19.jpg',0,'tarjeta19'),
(11,NULL,'CALLE SERRANO',200,100,'/images/tarjetas/propiedades/tarjeta20.jpg',0,'tarjeta20'),
(12,NULL,'AVENIDA AMÉRICA',220,110,'/images/tarjetas/propiedades/tarjeta22.jpg',0,'tarjeta22'),
(13,NULL,'CALLE MARÍA DE MOLINA',220,110,'/images/tarjetas/propiedades/tarjeta24.jpg',0,'tarjeta24'),
(14,NULL,'CALLE CEA BERMÚDEZ',240,120,'/images/tarjetas/propiedades/tarjeta25.jpg',0,'tarjeta25'),
(15,NULL,'AVENIDA DE LOS REYES CATÓLICOS',260,130,'/images/tarjetas/propiedades/tarjeta27.jpg',0,'tarjeta27'),
(16,NULL,'CALLE BAILÉN',260,130,'/images/tarjetas/propiedades/tarjeta28.jpg',0,'tarjeta28'),
(17,NULL,'PLAZA ESPAÑA',280,140,'/images/tarjetas/propiedades/tarjeta30.jpg',0,'tarjeta30'),
(18,NULL,'PUERTA DEL SOL',300,150,'/images/tarjetas/propiedades/tarjeta32.jpg',0,'tarjeta32'),
(19,NULL,'CALLE ALCALÁ',300,150,'/images/tarjetas/propiedades/tarjeta33.jpg',0,'tarjeta33'),
(20,NULL,'GRAN VÍA',320,160,'/images/tarjetas/propiedades/tarjeta35.jpg',0,'tarjeta35'),
(21,NULL,'PASEO DE LA CASTELLANA',350,175,'/images/tarjetas/propiedades/tarjeta38.jpg',0,'tarjeta38'),
(22,NULL,'PASEO DEL PRADO',400,200,'/images/tarjetas/propiedades/tarjeta40.jpg',0,'tarjeta40'),
(23,NULL,'ESTACIÓN DE GOYA',200,100,'/images/tarjetas/propiedades/tarjeta06.jpg',0,'tarjeta06'),
(24,NULL,'ESTACIÓN DE LAS DELICIAS',200,100,'/images/tarjetas/propiedades/tarjeta16.jpg',0,'tarjeta16'),
(25,NULL,'ESTACIÓN DEL MEDIODÍA',200,100,'/images/tarjetas/propiedades/tarjeta26.jpg',0,'tarjeta26'),
(26,NULL,'ESTACIÓN DEL NORTE',200,100,'/images/tarjetas/propiedades/tarjeta36.jpg',0,'tarjeta36'),
(27,NULL,'COMPAÑIA DE AGUAS',150,75,'/images/tarjetas/propiedades/tarjeta29.jpg',0,'tarjeta29'),
(28,NULL,'COMPAÑIA DE ELECTRICIDAD',150,75,'/images/tarjetas/propiedades/tarjeta13.jpg',0,'tarjeta13');
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
INSERT INTO monopoly_db.ficha (fichaID,nombre,pathImageSmall,pathImageBig) VALUES ('1','auto','/images/fichas/FichaS01.png','/images/fichas/Ficha01.png');
INSERT INTO monopoly_db.ficha (fichaID,nombre,pathImageSmall,pathImageBig) VALUES ('2','sombrero','/images/fichas/FichaS02.png','/images/fichas/Ficha02.png');
INSERT INTO monopoly_db.ficha (fichaID,nombre,pathImageSmall,pathImageBig) VALUES ('3','bota','/images/fichas/FichaS03.png','/images/fichas/Ficha03.png');
INSERT INTO monopoly_db.ficha (fichaID,nombre,pathImageSmall,pathImageBig) VALUES ('4','plancha','/images/fichas/FichaS04.png','/images/fichas/Ficha04.png');
INSERT INTO monopoly_db.ficha (fichaID,nombre,pathImageSmall,pathImageBig) VALUES ('5','carretilla','/images/fichas/FichaS05.png','/images/fichas/Ficha05.png');
INSERT INTO monopoly_db.ficha (fichaID,nombre,pathImageSmall,pathImageBig) VALUES ('6','dedal','/images/fichas/FichaS06.png','/images/fichas/Ficha06.png');
INSERT INTO monopoly_db.ficha (fichaID,nombre,pathImageSmall,pathImageBig) VALUES ('7','barco','/images/fichas/FichaS07.png','/images/fichas/Ficha07.png');
INSERT INTO monopoly_db.ficha (fichaID,nombre,pathImageSmall,pathImageBig) VALUES ('8','perro','/images/fichas/FichaS08.png','/images/fichas/Ficha08.png');
INSERT INTO monopoly_db.ficha (fichaID,nombre,pathImageSmall,pathImageBig) VALUES ('9','bolsa de dinero','/images/fichas/FichaS09.png','/images/fichas/Ficha09.png');
INSERT INTO monopoly_db.ficha (fichaID,nombre,pathImageSmall,pathImageBig) VALUES ('10','caballo','/images/fichas/FichaS10.png','/images/fichas/Ficha10.png');
INSERT INTO monopoly_db.ficha (fichaID,nombre,pathImageSmall,pathImageBig) VALUES ('11','cañón','/images/fichas/FichaS11.png','/images/fichas/Ficha11.png');
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
