CREATE DATABASE billing CHARACTER SET utf8 COLLATE utf8_general_ci;

USE billing;

drop table IF EXISTS `usageStatus`;

CREATE TABLE `usageStatus` (
  `monthId` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  APPID varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `accountId` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `planId` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `serviceId` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `usage` int NOT NULL DEFAULT 0,
  `isExceed` int DEFAULT 0,
  `isBlock` int DEFAULT 0,
  PRIMARY KEY (`monthId`, `accountId`, `planId`, `serviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


drop table IF EXISTS APPMAPPING;

create table APPMAPPING(
	APPID varchar(20) NOT NULL,
	COMCODE  varchar(20) NOT NULL,
	APPNAME varchar(100),
	ISDELETED int(11) DEFAULT false,
	url varchar(200),
	appType varchar(20),
	planId varchar(20),
	effectiveDate datetime,
	expirationDate datetime,
	isTrial int(11),
	PRIMARY KEY(APPNAME, COMCODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table IF EXISTS comtable;

CREATE TABLE `comtable` (
  `COMCODE` varchar(20) NOT NULL DEFAULT '',
  `COMNAME` varchar(30) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `ISDELETED` varchar(1) DEFAULT '0',
	`ALIAS` varchar(30),

  PRIMARY KEY (`COMCODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

