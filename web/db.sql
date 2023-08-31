/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.5.20-log : Database - jeevahini
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jeevahini` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `jeevahini`;

/*Table structure for table `ambulance_location` */

DROP TABLE IF EXISTS `ambulance_location`;

CREATE TABLE `ambulance_location` (
  `Loc_id` int(11) NOT NULL AUTO_INCREMENT,
  `D_id` int(11) DEFAULT NULL,
  `latitude` varchar(25) DEFAULT NULL,
  `Longitude` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`Loc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `ambulance_location` */

insert  into `ambulance_location`(`Loc_id`,`D_id`,`latitude`,`Longitude`) values 
(1,2,'11.257784','75.7845392'),
(3,6,'11.2577975','75.7845374'),
(4,3,'11.2577763','75.7845398'),
(5,7,'11.2577753','75.7845335');

/*Table structure for table `driver_rating` */

DROP TABLE IF EXISTS `driver_rating`;

CREATE TABLE `driver_rating` (
  `R_id` int(11) NOT NULL AUTO_INCREMENT,
  `L_id` int(11) DEFAULT NULL,
  `D_id` int(11) DEFAULT NULL,
  `Rating` varchar(20) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  PRIMARY KEY (`R_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `driver_rating` */

insert  into `driver_rating`(`R_id`,`L_id`,`D_id`,`Rating`,`Date`) values 
(1,6,1,'5','2012-05-22'),
(2,6,14,'2.0','2022-10-15'),
(3,6,2,'4.5','2022-12-27');

/*Table structure for table `driver_reg` */

DROP TABLE IF EXISTS `driver_reg`;

CREATE TABLE `driver_reg` (
  `D_id` int(11) NOT NULL AUTO_INCREMENT,
  `L_id` int(11) DEFAULT NULL,
  `F_name` varchar(20) DEFAULT NULL,
  `L_name` varchar(20) DEFAULT NULL,
  `Address` varchar(40) DEFAULT NULL,
  `Vehicle_no` varchar(20) DEFAULT NULL,
  `License_no` varchar(20) DEFAULT NULL,
  `Phone_no` bigint(20) DEFAULT NULL,
  `Email` varchar(20) DEFAULT NULL,
  `State` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`D_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

/*Data for the table `driver_reg` */

insert  into `driver_reg`(`D_id`,`L_id`,`F_name`,`L_name`,`Address`,`Vehicle_no`,`License_no`,`Phone_no`,`Email`,`State`) values 
(1,6,'Sayand','A K','kottaram veetil','KL58A4563','KL580001423',7894561230,'sayand@gmail.com','Available'),
(2,7,'Abirag','RTR','cv house','KL67M9085','KL580004785',8520147963,'abirag@gmail.com','Available'),
(3,8,'Shijin','P','SS nivas','KL45M0987','KL580005236',9784561230,'shijinp@gmail.com',NULL),
(4,9,'Amal','P P','PP nivas','KL09N4523','KL580014236',9784561230,'amalpp@gmail.com',NULL),
(6,11,'Sarang','M','Girinagar colony ','KL10AN1203','KL580000236',9784561230,'sarang@gmail.com',NULL),
(7,12,'Sayooj',' ','Theerath House','KL05A7856','KL580005436',9784561230,'sayooj@gmail.com',NULL),
(8,13,'Nandu','K K','KK house','KL04N4523','KL582022453',9784561230,'kknandu@gmail.com',NULL),
(12,31,'lpf','udh','ditkridhg','fhdf6557','76655',6555787,'dagag@gmail.com',NULL),
(13,32,'','','','','',0,'',NULL),
(14,33,'','','','','',0,'',NULL),
(15,34,'','','','','',0,'',NULL),
(16,72,'huu','hhj','ghhh','vvh','vvgh',9966,'cvghhh','pending');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `F_id` int(11) NOT NULL AUTO_INCREMENT,
  `D_id` int(11) DEFAULT NULL,
  `U_id` int(11) DEFAULT NULL,
  `Feedback` varchar(100) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  PRIMARY KEY (`F_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`F_id`,`D_id`,`U_id`,`Feedback`,`Date`) values 
(1,1,1,'Good','2022-09-09'),
(2,2,2,'Bad','2022-09-30'),
(3,6,14,'good','2022-10-15'),
(4,6,14,'baddd','2022-10-15'),
(5,6,14,'dgsgsg','2022-10-15'),
(6,6,14,'dgsgsg','2022-10-15'),
(7,6,14,'yeyey ','2022-10-15'),
(8,6,14,'ook ','2022-10-15'),
(9,6,14,'ook ','2022-10-15'),
(10,6,14,'ook ','2022-10-15'),
(11,6,2,'','2022-12-27');

/*Table structure for table `hospital_noti` */

DROP TABLE IF EXISTS `hospital_noti`;

CREATE TABLE `hospital_noti` (
  `noti_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `hospital_noti` */

insert  into `hospital_noti`(`noti_id`) values 
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8);

/*Table structure for table `hospital_reg` */

DROP TABLE IF EXISTS `hospital_reg`;

CREATE TABLE `hospital_reg` (
  `H_id` int(11) NOT NULL AUTO_INCREMENT,
  `L_id` int(11) DEFAULT NULL,
  `Hospital_name` varchar(40) DEFAULT NULL,
  `District` varchar(25) DEFAULT NULL,
  `Place` varchar(20) DEFAULT NULL,
  `Pin` int(6) DEFAULT NULL,
  `Post` varchar(20) DEFAULT NULL,
  `Phone_NO` bigint(10) DEFAULT NULL,
  `Email` varchar(35) DEFAULT NULL,
  `Latitude` varchar(20) DEFAULT NULL,
  `Longitude` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`H_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `hospital_reg` */

insert  into `hospital_reg`(`H_id`,`L_id`,`Hospital_name`,`District`,`Place`,`Pin`,`Post`,`Phone_NO`,`Email`,`Latitude`,`Longitude`) values 
(1,4,'CO-OP Hospital','kannur','Tly',670642,'Tly',8547961230,'coop@gmail.com','11.2577825','75.7845336'),
(3,5,'Mission Hospital','Kannur','Tly',670841,'Tly',9012345678,'missionhospital@gmail.com',NULL,NULL),
(5,18,'Aster Mims','KANNUR','KANNUR',654987,'kanuur',7894561230,'astermims@gmail.com',NULL,NULL),
(6,38,'edrftghyn','Ko','rftgyhuj',852,'drftgyh',748526,'dcfvgbhn','84512','4852'),
(7,75,'dsgfg','Kn','gtytgu',0,'gfug',34567890,'sdxfcgvhb','4567','34567'),
(8,76,'fvdgb','Ko','dssa',640410,'hrf',7856785678,'gvhv@gmail.com','741852','4411');

/*Table structure for table `hospital_req` */

DROP TABLE IF EXISTS `hospital_req`;

CREATE TABLE `hospital_req` (
  `R_id` int(11) NOT NULL AUTO_INCREMENT,
  `L_id` int(11) DEFAULT NULL,
  `H_id` int(11) DEFAULT NULL,
  `Level` varchar(30) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  PRIMARY KEY (`R_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `hospital_req` */

insert  into `hospital_req`(`R_id`,`L_id`,`H_id`,`Level`,`Date`) values 
(1,6,4,'Level 1(Emegency)','2023-02-11'),
(2,6,4,'Level3 (Minor injury)','2023-02-11'),
(3,6,4,'Level3 (Minor injury)','2023-02-23'),
(4,6,4,'Level 2(Major injury)','2023-03-06'),
(5,6,4,'hgghv',NULL),
(6,6,4,NULL,NULL),
(7,6,4,NULL,NULL),
(8,6,4,'Level 1(Emegency)','2023-03-06'),
(9,6,4,'Level 1(Emegency)','2023-04-19');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `L_id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(25) DEFAULT NULL,
  `Password` varchar(18) DEFAULT NULL,
  `Type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`L_id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`L_id`,`Username`,`Password`,`Type`) values 
(1,'admin','Admin@12','admin'),
(2,'user1','user12','user'),
(3,'user2','user22','user'),
(4,'hospital1','Hospital@1','hospital'),
(5,'hospital2','hospital','hospital'),
(6,'driver1','driver1','driver'),
(7,'driver2','driver2','driver'),
(8,'driver3','driver3','driver'),
(9,'driver4','driver4','driver');

/*Table structure for table `notifications` */

DROP TABLE IF EXISTS `notifications`;

CREATE TABLE `notifications` (
  `Not_id` int(11) NOT NULL AUTO_INCREMENT,
  `D_id` int(11) DEFAULT NULL,
  `H_id` int(11) DEFAULT NULL,
  `Notification` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Not_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `notifications` */

insert  into `notifications`(`Not_id`,`D_id`,`H_id`,`Notification`) values 
(1,1,1,'emergency'),
(2,2,2,'Accident');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `req_id` int(11) DEFAULT NULL,
  `Amount` int(11) DEFAULT NULL,
  `Date` varchar(50) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`id`,`req_id`,`Amount`,`Date`) values 
(1,2,509,'2023-03-06'),
(2,2,356,'2023-03-06');

/*Table structure for table `report` */

DROP TABLE IF EXISTS `report`;

CREATE TABLE `report` (
  `Rep_id` int(11) NOT NULL AUTO_INCREMENT,
  `D_id` int(11) DEFAULT NULL,
  `U_id` int(11) DEFAULT NULL,
  `Report` varchar(30) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  PRIMARY KEY (`Rep_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `report` */

insert  into `report`(`Rep_id`,`D_id`,`U_id`,`Report`,`Date`) values 
(1,1,1,'Misbehave',NULL),
(2,2,2,'Good behaviour',NULL),
(3,6,2,'Misbehave',NULL),
(4,6,2,'Misbehave',NULL),
(5,6,2,'gghzh',NULL),
(6,6,2,'gghzh',NULL),
(7,6,2,'Fake User','2023-02-11'),
(8,6,2,'Fake User','2023-02-11');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `Req_id` int(11) NOT NULL AUTO_INCREMENT,
  `D_id` int(11) DEFAULT NULL,
  `L_id` int(11) DEFAULT NULL,
  `Request_status` varchar(20) DEFAULT NULL,
  `latitude` varchar(20) DEFAULT NULL,
  `longitude` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Req_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`Req_id`,`D_id`,`L_id`,`Request_status`,`latitude`,`longitude`) values 
(1,6,2,'Rejected','11.2577715','75.7845235'),
(2,6,2,'Completed','11.25774751','75.78452744'),
(3,6,2,'Rejected','11.2577633','75.7845172'),
(4,6,2,'Rejected','11.2577794','75.7845225'),
(5,6,2,'Rejected','11.2577681','75.7845521'),
(6,6,3,'Accepted','11.2577959','75.7845466'),
(7,7,2,'pending','11.2577886','75.7845431');

/*Table structure for table `user_notification` */

DROP TABLE IF EXISTS `user_notification`;

CREATE TABLE `user_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `reqid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user_notification` */

/*Table structure for table `user_reg` */

DROP TABLE IF EXISTS `user_reg`;

CREATE TABLE `user_reg` (
  `U_id` int(11) NOT NULL AUTO_INCREMENT,
  `L_id` int(11) DEFAULT NULL,
  `F_name` varchar(20) DEFAULT NULL,
  `L_name` varchar(20) DEFAULT NULL,
  `Address` varchar(50) DEFAULT NULL,
  `Phone_NO` bigint(20) DEFAULT NULL,
  `Email` varchar(35) DEFAULT NULL,
  PRIMARY KEY (`U_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `user_reg` */

insert  into `user_reg`(`U_id`,`L_id`,`F_name`,`L_name`,`Address`,`Phone_NO`,`Email`) values 
(1,2,'Swathi','N','KTKvilla',753214698,'swathi@gmail.com'),
(2,3,'Akshay','M','NMV house',963258741,'akshay@gmail.com'),
(3,25,'Say','G','Kakak',79797949,'shshs@gmail.com'),
(4,26,'Say','G','Kakak',79797949,'shshs@gmail.com'),
(5,27,'Say','G','Kakak',79797949,'shshs@gmail.com'),
(6,74,'SAYAND','rtyjjkk','AK',23336665,'rfyuiii');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
