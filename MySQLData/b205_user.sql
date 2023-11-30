-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: k9b205.p.ssafy.io    Database: b205
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ssafy','$2a$10$zjIIcIVxxA1Rz8yVRZ47E.ZiIOjUtR2N2.cTadXVAYNMQmOaPw12.','8b03525d9481c8dadcad3051923f49db958fd3d9dd09438b8fba8fe41e3cd0ba7373616679',1),(2,'ssafyssssssssssss','$2a$10$2pMneRdM2Kg5ogcmg5NQTekLDo74vFx0XoadENGl9vljlI16YMMAK','0dd7b22aa9ce13bb387089782cb61e7fce7e52693148b405bf2a4a92add413727373616679737373737373737373737373',0),(3,'admin','$2a$10$6cuRlKYiM9pBAD37yiBSh.KPa9lLxft7oeiGQ93KoXupUKJJQZtNG','0983b66bf7c399569c8b266e4c64abc3d1365141b36cc52da6bead21e840691061646d696e',0),(4,'adminsss','$2a$10$6kXtbxsD9Al7EC9pfTjKlOLNNn9UKnf/5Df1l6z64eSPJ/u5cGKVG','956dca3766314e88699557e11a780e100daec661cf6b6bb9debfa83ac509e38961646d696e737373',1),(5,'ssafy','$2a$10$p0fEUmAuZrOUI5v6eUkuJ.UYb9UxgAUJ7zBTtJayoIAboDJ6DmDxu','9b65625a56b0e999cad24ad246bdb48077357d8565a1c39c3b34c7a672074ff97373616679',1),(6,'ssafy','$2a$10$6IRwLRuHAfE.86u.CgW0jenegw8Yo6sUj93qG3Zhfk2vtp4W/PqhO','a9a308411a2c27e2f9cfb1c0c5d8cc239e20d5500e4bb49ed75d066c525c90a97373616679',1),(7,'qwer','$2a$10$0TVObkTG0Va4isydaj8OueqqWPjj0UaMkme.3ORrtHQOfzZWB4gf.','f5fc8843002397adf8c8192f5c29ca0306bebb872a78391f0a61192ac5c172b071776572',0),(8,'admin1','admin1','token',0),(10,'zzino','$2a$10$SQZs3CfKXsyBAeWsRZA2V.F221mZgg.KVE2IEK1t3ecm4UL3MXdRC','6405793bd571f62584418b7bbc8d50ea958ce4e96ce771f6b33c67d48d72a2d17a7a696e6f',0),(11,'qwer@gmail.com','$2a$10$WWvv0Hq7z1e1KbNP8/S46elqIZV4FHCcd3QtosrlZAS3VrSZqGVrS','6c01c9a0c989afe9c19be4b0adcc71f89761d11fed48b8fa14cd3bd053d442817177657240676d61696c2e636f6d',0),(12,'ssafy123@gamil.com','$2a$10$.tbDdDb1qImAI9fr3.ZrAuhL/S611nhJIM2W0Ne94WNcuOv6Rex0q','ba0c94a83cfe876221ede203f03f6ca0a1b413830b1851f06efbcfd127b9869373736166793132334067616d696c2e636f6d',0),(13,'asdf','$2a$10$i4DVfip7oqTtUl3tvRGzn.J2MgZw3/x9MiAUA5mrVw.Lc8qF/2WRm','62e1a8dd29db280c80ff98f9c95c428ca103058c13806c827d7c74518b1c237261736466',0),(14,'test@test.com','$2a$10$e.Xur4OAH1H19.OKy7a9b.Xa7IZNrxcsk82ARMWRLPZg/iXMNjqWG','0f28a49b33c5e84beece83f3b7a8810d975ee323d55dd0895f0e77dc6f10b2307465737440746573742e636f6d',0),(15,'qwer1111','$2a$10$VNvEq.5X2jRt4DB5PlgVhuCPODcxZxAkL6wCL29mMco0N1WvWt2U6','f57dae957ebb979bfb2d75bbafc9a45bbc311da0f628e872c6cdd478795a73b77177657231313131',0),(20,'acrow0330@naver.com','$2a$10$LSIAe6r8mF1LmI/3aBTcJOhxGIM9tC6Vx2fMqY7wiQ9LvW6Sbbkoi','9796f4e51730fd24c5854f14d6b726144e9eb318c2cbb0e6f808a1b61cce47a16163726f7730333330406e617665722e636f6d',0),(23,'dofun8@gmail.com','$2a$10$gQi2B5aNNa9lI/p2sZmjxeHQf3v3vvWavwjkHje0smXouZGrcgFvW','d4183a91457f1ee8f3268aa0be333c36ce4e5675b33b6849c9d6a24b1b0017e5646f66756e3840676d61696c2e636f6d',0),(24,'leezi809@naver.com','$2a$10$py.iDJPW6Oq/jlF0voSNeuxLcWAxmo5mZtQ6ax/p2WAMR8bKjfWca','212852fe423e132f4348e2c5f9b189e4cc71448d68598b6ba3badd0f7edc0cf76c65657a69383039406e617665722e636f6d',0),(25,'asya390@naver.com','$2a$10$x4FX64gbW2LnbaEKUfkBF.7utQazLwpayRxfWPZQNPiyBvGoHn4fK','6eb8b0ee9dfa4af9828802bd7c0d6f49fb6e7ef5c5d3bec51f9501e710f8282a61737961333930406e617665722e636f6d',0),(26,'csg1353@naver.com','$2a$10$EXt4uOU2Cbr6ugoVpLDCTujL70rfqOIVOIGkSmT9R5aUthRpijT8i','12020e55a8137de5c246be85e35215ca8e63310473c5d3ccb7d2dfdef854ac4a63736731333533406e617665722e636f6d',0);
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

-- Dump completed on 2023-11-17 15:05:38
