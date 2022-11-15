-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (arm64)
--
-- Host: 127.0.0.1    Database: website
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `blogs`
--

DROP TABLE IF EXISTS `blogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blogs` (
  `blog_id` int NOT NULL AUTO_INCREMENT COMMENT 'Id of blog',
  `author` int NOT NULL COMMENT 'Author of blog',
  `text` text NOT NULL COMMENT 'Text of blog',
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`blog_id`),
  KEY `blogs_author_fk` (`author`),
  CONSTRAINT `blogs_author_fk` FOREIGN KEY (`author`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blogs`
--

LOCK TABLES `blogs` WRITE;
/*!40000 ALTER TABLE `blogs` DISABLE KEYS */;
INSERT INTO `blogs` VALUES (1,5,'updated text. ','2022-10-27 19:59:53'),(8,2,'asdasdasd','2022-11-07 20:28:06'),(9,6,'gsfdf','2022-11-07 20:28:15'),(10,7,'sgdhfjgk','2022-11-07 20:31:02');
/*!40000 ALTER TABLE `blogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT 'id of category',
  `category_name_id` int DEFAULT NULL COMMENT 'id of category name of category',
  `category_name_blog_id` int DEFAULT NULL COMMENT 'id of blog assigned to category',
  PRIMARY KEY (`category_id`),
  KEY `categories_blogs_null_fk` (`category_name_blog_id`),
  KEY `categories_category_names_null_fk` (`category_name_id`),
  CONSTRAINT `categories_blogs_null_fk` FOREIGN KEY (`category_name_blog_id`) REFERENCES `blogs` (`blog_id`),
  CONSTRAINT `categories_category_names_null_fk` FOREIGN KEY (`category_name_id`) REFERENCES `category_names` (`category_name_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='category of blog';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (8,1,1),(10,4,8),(11,4,9);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_names`
--

DROP TABLE IF EXISTS `category_names`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_names` (
  `category_name_id` int NOT NULL AUTO_INCREMENT COMMENT 'Id of category name',
  `name` varchar(30) NOT NULL COMMENT 'name of category',
  PRIMARY KEY (`category_name_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Names of categories';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_names`
--

LOCK TABLES `category_names` WRITE;
/*!40000 ALTER TABLE `category_names` DISABLE KEYS */;
INSERT INTO `category_names` VALUES (1,'Personal'),(2,'Car'),(4,'Dog'),(5,'Cat');
/*!40000 ALTER TABLE `category_names` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `author` int NOT NULL COMMENT 'author of comment',
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'timestamp of comment',
  `blog_id` int NOT NULL,
  `text` text NOT NULL COMMENT 'text of comment',
  `is_edited` int NOT NULL COMMENT 'boolean if comment is edited',
  PRIMARY KEY (`comment_id`),
  KEY `comments_author_fk` (`author`),
  KEY `comments_blog_fk` (`blog_id`),
  CONSTRAINT `comments_author_fk` FOREIGN KEY (`author`) REFERENCES `users` (`user_id`),
  CONSTRAINT `comments_blog_fk` FOREIGN KEY (`blog_id`) REFERENCES `blogs` (`blog_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='table for user comments on blogs';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (2,6,'2022-11-10 11:14:00',1,'upravene',1),(3,6,'2022-10-27 19:59:53',1,'Toto je skusobny komentar s upravenym textom.',0),(5,6,'2022-11-07 20:36:07',9,'grtdytuiyoukuyjyh',0),(6,6,'2022-11-10 10:56:58',1,'sfgdfhgjh',0),(9,2,'2022-11-13 15:43:43',1,'created',1),(15,2,'2022-11-15 17:14:47',1,'z postmana',0),(16,8,'2022-11-15 17:15:05',1,'pridanyedited',1),(18,8,'2022-11-15 22:27:49',1,'created',0),(19,8,'2022-11-15 22:29:39',1,'created',0),(20,2,'2022-11-15 22:30:55',1,'z postmana',0),(31,8,'2022-11-15 22:39:04',1,'asfdafd',1),(32,8,'2022-11-15 22:49:02',1,'edited',1);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL COMMENT 'sha-256',
  `is_admin` tinyint(1) NOT NULL COMMENT '1 - yes, 0 - no',
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login_unique` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'patrik','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',0,'patrik@gmail.com'),(5,'Janko','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',1,'bcd@gmail.com'),(6,'Peter','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',1,'cde@gmail.com'),(7,'Gabriel','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',1,'fghsdf@gmail.com'),(8,'test','$2y$10$8b/vNr.dZo4LR6LLoDVuQupcUQg1Ft32RS03dznhDmoYXIKQt9gKS',1,'test@gmail.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-15 22:55:12
