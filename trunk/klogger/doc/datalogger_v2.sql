-- MySQL dump 10.13  Distrib 5.1.36, for Win32 (ia32)
--
-- Host: localhost    Database: datalogger
-- ------------------------------------------------------
-- Server version	5.1.36-community-log

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

CREATE DATABASE datalogger;
USE datalogger;

--
-- Table structure for table `alarmas`
--

DROP TABLE IF EXISTS `alarmas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alarmas` (
  `ID_EST` int(11) NOT NULL,
  `ID_CANAL` varchar(45) NOT NULL,
  `LIMITE_ALAR` double NOT NULL,
  `ESTADO` int(11) NOT NULL,
  PRIMARY KEY (`ID_EST`,`ID_CANAL`),
  KEY `EST_ALARMA` (`ID_EST`),
  CONSTRAINT `EST_ALARMA` FOREIGN KEY (`ID_EST`) REFERENCES `estaciones` (`ID_EST`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comandos`
--

DROP TABLE IF EXISTS `comandos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comandos` (
  `ID_CMD_ENV` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ID_EST` int(11) NOT NULL,
  `COMANDO` varchar(255) DEFAULT NULL,
  `RESPUESTA` varchar(255) DEFAULT NULL,
  `FH_ENCOLADO` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ESTADO` int(1) unsigned DEFAULT NULL,
  `FH_ENVIADO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `IDEQUIPO_EST` varchar(6) NOT NULL,
  PRIMARY KEY (`ID_CMD_ENV`) USING BTREE,
  KEY `EST_COMAND` (`ID_EST`),
  KEY `FK_comandos_EQP` (`IDEQUIPO_EST`) USING BTREE,
  CONSTRAINT `FK_comandos_EQP` FOREIGN KEY (`IDEQUIPO_EST`) REFERENCES `estaciones` (`IDEQUIPO_EST`),
  CONSTRAINT `FK_comandos_est` FOREIGN KEY (`ID_EST`) REFERENCES `estaciones` (`ID_EST`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `configuraciones`
--

DROP TABLE IF EXISTS `configuraciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuraciones` (
  `ID_EST` int(11) NOT NULL,
  `LLAVE` varchar(255) NOT NULL,
  `VALOR` varchar(255) NOT NULL,
  `DESCRIPCION` varchar(255) DEFAULT NULL,
  `FECHA_HORA` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_EST`,`LLAVE`,`VALOR`) USING BTREE,
  CONSTRAINT `FK_ESTACION` FOREIGN KEY (`ID_EST`) REFERENCES `estaciones` (`ID_EST`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_estaciones`
--

DROP TABLE IF EXISTS `data_estaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_estaciones` (
  `ID_PARTICION_DE` int(11) NOT NULL,
  `ID_GRUPO` varchar(45) NOT NULL,
  `ID_EST` int(11) DEFAULT NULL,
  `FECHA_HORA_DE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `VAR1_DE` double DEFAULT NULL,
  `VAR2_DE` double DEFAULT NULL,
  `VAR3_DE` double DEFAULT NULL,
  `VAR4_DE` double DEFAULT NULL,
  `VAR5_DE` double DEFAULT NULL,
  `VAR6_DE` double DEFAULT NULL,
  `DIG_IN1_DE` double DEFAULT NULL,
  `DIG_IN2_DE` double DEFAULT NULL,
  `DIG_IN3_DE` double DEFAULT NULL,
  `DIG_IN4_DE` double DEFAULT NULL,
  `DIG_IN5_DE` double DEFAULT NULL,
  `DIG_IN6_DE` double DEFAULT NULL,
  `DIG_IN7_DE` double DEFAULT NULL,
  `DIG_IN8_DE` double DEFAULT NULL,
  `DIG_OUT1_DE` double DEFAULT NULL,
  `DIG_OUT2_DE` double DEFAULT NULL,
  `DIG_OUT3_DE` double DEFAULT NULL,
  `DIG_OUT4_DE` double DEFAULT NULL,
  `DIG_OUT5_DE` double DEFAULT NULL,
  `DIG_OUT6_DE` double DEFAULT NULL,
  `DIG_OUT7_DE` double DEFAULT NULL,
  `DIG_OUT8_DE` double DEFAULT NULL,
  `CARGA` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `datalogger`.`TRG_ULTIMA_DATA_ESTACION` BEFORE INSERT
    ON datalogger.data_estaciones FOR EACH ROW
BEGIN

    DECLARE ID_ESTACION INT(10);

    SET ID_ESTACION=0;

    SELECT ID_EST 

    INTO ID_ESTACION

    FROM ultimo_data_estaciones WHERE ID_EST=NEW.ID_EST;

    IF (ID_ESTACION=NEW.ID_EST) THEN

      UPDATE ULTIMO_DATA_ESTACIONES 

      SET ID_PARTICION_DE = NEW.ID_PARTICION_DE,

        ID_GRUPO = NEW.ID_GRUPO,

        ID_EST = NEW.ID_EST,

        FECHA_HORA_DE = NEW.FECHA_HORA_DE,

        VAR1_DE = NEW.VAR1_DE,

        VAR2_DE = NEW.VAR2_DE,

        VAR3_DE = NEW.VAR3_DE,

        VAR4_DE = NEW.VAR4_DE,

        VAR5_DE = NEW.VAR5_DE,

        VAR6_DE = NEW.VAR6_DE,

        DIG_IN1_DE = NEW.DIG_IN1_DE,

        DIG_IN2_DE = NEW.DIG_IN2_DE,

        DIG_IN3_DE = NEW.DIG_IN3_DE,

        DIG_IN4_DE = NEW.DIG_IN4_DE,

        DIG_IN5_DE = NEW.DIG_IN5_DE,

        DIG_IN6_DE = NEW.DIG_IN6_DE,

        DIG_IN7_DE = NEW.DIG_IN7_DE,

        DIG_IN8_DE = NEW.DIG_IN8_DE,

        DIG_OUT1_DE = NEW.DIG_OUT1_DE,

        DIG_OUT2_DE = NEW.DIG_OUT2_DE,

        DIG_OUT3_DE = NEW.DIG_OUT3_DE,

        DIG_OUT4_DE = NEW.DIG_OUT4_DE,

        DIG_OUT5_DE = NEW.DIG_OUT5_DE,

        DIG_OUT6_DE = NEW.DIG_OUT6_DE,

        DIG_OUT7_DE = NEW.DIG_OUT7_DE,

        DIG_OUT8_DE = NEW.DIG_OUT8_DE,

        CARGA = NEW.CARGA 

      WHERE ID_EST = ID_ESTACION;

    ELSE

      INSERT INTO ULTIMO_DATA_ESTACIONES

      VALUES(

        NEW.ID_PARTICION_DE,

        NEW.ID_GRUPO,

        NEW.ID_EST,

        NEW.FECHA_HORA_DE,

        NEW.VAR1_DE,

        NEW.VAR2_DE,

        NEW.VAR3_DE,

        NEW.VAR4_DE,

        NEW.VAR5_DE,

        NEW.VAR6_DE,

        NEW.DIG_IN1_DE,

        NEW.DIG_IN2_DE,

        NEW.DIG_IN3_DE,

        NEW.DIG_IN4_DE,

        NEW.DIG_IN5_DE,

        NEW.DIG_IN6_DE,

        NEW.DIG_IN7_DE,

        NEW.DIG_IN8_DE,

        NEW.DIG_OUT1_DE,

        NEW.DIG_OUT2_DE,

        NEW.DIG_OUT3_DE,

        NEW.DIG_OUT4_DE,

        NEW.DIG_OUT5_DE,

        NEW.DIG_OUT6_DE,

        NEW.DIG_OUT7_DE,

        NEW.DIG_OUT8_DE,

        NEW.CARGA);

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `estaciones`
--

DROP TABLE IF EXISTS `estaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estaciones` (
  `ID_EST` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_EST` varchar(255) DEFAULT NULL,
  `DIRECCION_EST` varchar(255) DEFAULT NULL,
  `FECHA_INSTALACION_EST` timestamp NULL DEFAULT NULL,
  `RESPONSABLE_EST` varchar(255) DEFAULT NULL,
  `RESPONSABLE_CONT_EST` varchar(45) DEFAULT NULL,
  `IDEQUIPO_EST` varchar(6) NOT NULL,
  `LON_EST` double DEFAULT NULL,
  `LAT_EST` double DEFAULT NULL,
  `ALTITUD` double NOT NULL,
  `ALARMA` tinyint(1) DEFAULT '0' COMMENT 'Actualiza a 1 si se despliega una alarma para cambiar el icono',
  PRIMARY KEY (`ID_EST`),
  UNIQUE KEY `IDEQUIPO_EST_UNIQUE` (`IDEQUIPO_EST`),
  UNIQUE KEY `ID_EST_UNIQUE` (`ID_EST`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `factores_conversion`
--

DROP TABLE IF EXISTS `factores_conversion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factores_conversion` (
  `ID_EST` int(11) NOT NULL,
  `GRUPO_STCN` varchar(45) NOT NULL COMMENT 'idEquipo',
  `ID_CANAL` int(11) unsigned NOT NULL,
  `VAL_SENSOR` double NOT NULL,
  `VAL_EQV` double NOT NULL,
  `SIMB` varchar(10) NOT NULL,
  `ESTADO` varchar(1) NOT NULL,
  PRIMARY KEY (`ID_EST`,`ID_CANAL`,`GRUPO_STCN`) USING BTREE,
  CONSTRAINT `FK_factores_conversion_1` FOREIGN KEY (`ID_EST`) REFERENCES `estaciones` (`ID_EST`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mails`
--

DROP TABLE IF EXISTS `mails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mails` (
  `ID_EST` int(11) NOT NULL,
  `DIR_MAIL` varchar(125) NOT NULL,
  PRIMARY KEY (`ID_EST`,`DIR_MAIL`),
  KEY `ESTACION_ID_MAIL` (`ID_EST`),
  CONSTRAINT `ESTACION_ID_MAIL` FOREIGN KEY (`ID_EST`) REFERENCES `estaciones` (`ID_EST`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recorrido_estaciones`
--

DROP TABLE IF EXISTS `recorrido_estaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recorrido_estaciones` (
  `ID` int(12) NOT NULL,
  `ID_EST` int(11) unsigned NOT NULL,
  `LATITUD` double NOT NULL,
  `LONGITUD` double NOT NULL,
  `FECHA_HORA` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`LATITUD`,`LONGITUD`,`ID_EST`,`ID`,`FECHA_HORA`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC
/*!50100 PARTITION BY HASH (ID)
PARTITIONS 400 */;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `datalogger`.`TGR_ACTUALIZAR_COORDENADAS_ESTACION` BEFORE INSERT
    ON datalogger.recorrido_estaciones FOR EACH ROW
BEGIN

    DECLARE ID_ESTACION INT(10);

    SET ID_ESTACION=0;

    SELECT ID_EST 

    INTO ID_ESTACION

    FROM ESTACIONES WHERE ID_EST=NEW.ID_EST;

    IF (ID_ESTACION=NEW.ID_EST) THEN

      UPDATE ESTACIONES 

      SET LAT_EST = NEW.LATITUD,

        LON_EST = NEW.LONGITUD

      WHERE ID_EST = ID_ESTACION;     

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `ultimo_data_estaciones`
--

DROP TABLE IF EXISTS `ultimo_data_estaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ultimo_data_estaciones` (
  `ID_PARTICION_DE` int(11) NOT NULL,
  `ID_GRUPO` varchar(45) NOT NULL,
  `ID_EST` int(11) DEFAULT NULL,
  `FECHA_HORA_DE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `VAR1_DE` double DEFAULT NULL,
  `VAR2_DE` double DEFAULT NULL,
  `VAR3_DE` double DEFAULT NULL,
  `VAR4_DE` double DEFAULT NULL,
  `VAR5_DE` double DEFAULT NULL,
  `VAR6_DE` double DEFAULT NULL,
  `DIG_IN1_DE` double DEFAULT NULL,
  `DIG_IN2_DE` double DEFAULT NULL,
  `DIG_IN3_DE` double DEFAULT NULL,
  `DIG_IN4_DE` double DEFAULT NULL,
  `DIG_IN5_DE` double DEFAULT NULL,
  `DIG_IN6_DE` double DEFAULT NULL,
  `DIG_IN7_DE` double DEFAULT NULL,
  `DIG_IN8_DE` double DEFAULT NULL,
  `DIG_OUT1_DE` double DEFAULT NULL,
  `DIG_OUT2_DE` double DEFAULT NULL,
  `DIG_OUT3_DE` double DEFAULT NULL,
  `DIG_OUT4_DE` double DEFAULT NULL,
  `DIG_OUT5_DE` double DEFAULT NULL,
  `DIG_OUT6_DE` double DEFAULT NULL,
  `DIG_OUT7_DE` double DEFAULT NULL,
  `DIG_OUT8_DE` double DEFAULT NULL,
  `CARGA` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario_estacion`
--

DROP TABLE IF EXISTS `usuario_estacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario_estacion` (
  `ID_US` int(11) NOT NULL,
  `ID_EST` int(11) NOT NULL,
  PRIMARY KEY (`ID_US`,`ID_EST`),
  KEY `USUARIO_REL_EST` (`ID_US`),
  KEY `EST_REL_USU` (`ID_EST`),
  CONSTRAINT `EST_REL_USU` FOREIGN KEY (`ID_EST`) REFERENCES `estaciones` (`ID_EST`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `USUARIO_REL_EST` FOREIGN KEY (`ID_US`) REFERENCES `usuarios` (`ID_US`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `ID_US` int(11) NOT NULL AUTO_INCREMENT,
  `USER_US` varchar(45) NOT NULL,
  `PASS_US` varchar(45) NOT NULL,
  `NOMBRE_US` varchar(45) DEFAULT NULL,
  `DIRECCION_US` varchar(45) DEFAULT NULL,
  `ESTADO_US` varchar(45) NOT NULL,
  PRIMARY KEY (`ID_US`),
  UNIQUE KEY `USER_US_UNIQUE` (`USER_US`),
  UNIQUE KEY `ID_US_UNIQUE` (`ID_US`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'datalogger'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-03-12 12:20:41
