-- MySQL dump 10.13  Distrib 5.7.20, for Win64 (x86_64)
--
-- Host: localhost    Database: testdb
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `ADMID` varchar(45) NOT NULL,
  `ADMPSWD` varchar(45) DEFAULT NULL,
  `ADMSSN` int(11) NOT NULL,
  PRIMARY KEY (`ADMSSN`),
  UNIQUE KEY `ADMID_UNIQUE` (`ADMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `level`
--

DROP TABLE IF EXISTS `level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `level` (
  `LNAME` varchar(45) NOT NULL,
  `LID` int(11) NOT NULL,
  `LMANAGE` int(11) DEFAULT NULL,
  PRIMARY KEY (`LID`),
  KEY `LMANAGE_idx` (`LMANAGE`),
  CONSTRAINT `LMANAGE` FOREIGN KEY (`LMANAGE`) REFERENCES `admin` (`ADMSSN`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `level`
--

LOCK TABLES `level` WRITE;
/*!40000 ALTER TABLE `level` DISABLE KEYS */;
/*!40000 ALTER TABLE `level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mgenre`
--

DROP TABLE IF EXISTS `mgenre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mgenre` (
  `MMID` int(11) NOT NULL,
  `MGENRE` varchar(45) NOT NULL,
  PRIMARY KEY (`MMID`,`MGENRE`),
  CONSTRAINT `MMID` FOREIGN KEY (`MMID`) REFERENCES `music` (`MID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mgenre`
--

LOCK TABLES `mgenre` WRITE;
/*!40000 ALTER TABLE `mgenre` DISABLE KEYS */;
/*!40000 ALTER TABLE `mgenre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music`
--

DROP TABLE IF EXISTS `music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `music` (
  `MID` int(11) NOT NULL,
  `MNAME` varchar(45) DEFAULT NULL,
  `MSINGER` varchar(45) DEFAULT NULL,
  `MLIKE` int(11) DEFAULT '0',
  `MMANAGE` int(11) DEFAULT NULL,
  `MLYRIC` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`MID`),
  KEY `MMANAGE_idx` (`MMANAGE`),
  CONSTRAINT `MMANAGE` FOREIGN KEY (`MMANAGE`) REFERENCES `admin` (`ADMSSN`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music`
--

LOCK TABLES `music` WRITE;
/*!40000 ALTER TABLE `music` DISABLE KEYS */;
/*!40000 ALTER TABLE `music` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `musiccontain`
--

DROP TABLE IF EXISTS `musiccontain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `musiccontain` (
  `CPID` int(11) NOT NULL,
  `CMID` int(11) NOT NULL,
  PRIMARY KEY (`CPID`,`CMID`),
  KEY `CMID_idx` (`CMID`),
  CONSTRAINT `CMID` FOREIGN KEY (`CMID`) REFERENCES `music` (`MID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `playlistid` FOREIGN KEY (`CPID`) REFERENCES `playlist` (`PID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `musiccontain`
--

LOCK TABLES `musiccontain` WRITE;
/*!40000 ALTER TABLE `musiccontain` DISABLE KEYS */;
/*!40000 ALTER TABLE `musiccontain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playlist` (
  `PID` int(11) NOT NULL AUTO_INCREMENT,
  `PNUM` int(11) DEFAULT NULL,
  `POWNER` int(11) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  KEY `POWNER_idx` (`POWNER`),
  CONSTRAINT `POWNER` FOREIGN KEY (`POWNER`) REFERENCES `user` (`SSN`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist`
--

LOCK TABLES `playlist` WRITE;
/*!40000 ALTER TABLE `playlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `NAME` varchar(45) NOT NULL,
  `SEX` varchar(45) NOT NULL,
  `SSN` int(13) NOT NULL,
  `ID` varchar(45) DEFAULT NULL,
  `PSWD` varchar(45) DEFAULT NULL,
  `UMANAGE` int(11) DEFAULT NULL,
  PRIMARY KEY (`SSN`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `UMANAGE_idx` (`UMANAGE`),
  CONSTRAINT `adminssn` FOREIGN KEY (`UMANAGE`) REFERENCES `admin` (`ADMSSN`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='usertable';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-06 19:06:09
