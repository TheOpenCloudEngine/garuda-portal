create table APP(
	APPID int(11) NOT NULL,
	APPNAME varchar(100),
	SIMPLEOVERVIEW varchar(1000),
	FULLOVERVIEW varchar(5000),
	PRICING varchar(200),
	CREATEDATE datetime DEFAULT NULL,
	UPDATEDATE datetime DEFAULT NULL,
	VERSION varchar(5),
	EXTFILENAME varchar(200),
	FILECONTENT varchar(1000),
	LOGOFILENAME varchar(200),
	LOGOCONTENT varchar(1000),
	STATUS varchar(100),	-- request, approval/reject, published/unpublished
	comcode varchar(20),
	CATEGORYID int(11),
	INSTALLCNT int(11) DEFAULT 0,
	ISDELETED int(11) DEFAULT false,
	url varchar(200),
	projectId varchar(20),
	comname varchar(200),
	runningVersion int,
	subDomain varchar(50),
	appType varchar(20) DEFAULT 'project',
	PRIMARY KEY(APPID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table APPMAPPING(
	APPID int(11) NOT NULL,
	COMCODE  varchar(20) NOT NULL,
	APPNAME varchar(100),
	ISDELETED int(11) DEFAULT false,
	url varchar(200),
	appType varchar(20),
	planId varchar(20) DEFAULT null,
	effectiveDate datetime DEFAULT null,
	expirationDate datetime DEFAULT null,
	isTrial int(11) DEFAULT 0,
	PRIMARY KEY(APPID, COMCODE),
	FOREIGN KEY (APPID) REFERENCES APP (APPID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `category` (
  `categoryId` int(11) DEFAULT NULL,
  `categoryName` varchar(255) DEFAULT NULL,
  `parentCategoryId` int(11) DEFAULT NULL,
  `modDate` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `category` VALUES
	(0,'전체',-1,NULL,0),
	(1,'인사/급여',-1,NULL,0),
	(2,'영업 관리',-1,NULL,0),
	(3,'생산 관리',-1,NULL,0),
	(4,'설비 관리',-1,NULL,0),
	(5,'구매 관리',-1,NULL,0);