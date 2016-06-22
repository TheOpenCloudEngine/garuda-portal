create table app(
	appid int(11) not null,
	appname varchar(100),
	simpleoverview varchar(1000),
	fulloverview varchar(5000),
	pricing varchar(200),
	createdate datetime default null,
	updatedate datetime default null,
	version varchar(5),
	extfilename varchar(200),
	filecontent varchar(1000),
	logofilename varchar(200),
	logocontent varchar(1000),
	status varchar(100),	-- request, approval/reject, published/unpublished
	comcode varchar(20),
	categoryid int(11),
	installcnt int(11) default 0,
	isdeleted int(11) default false,
	url varchar(200),
	projectid varchar(20),
	comname varchar(200),
	runningversion int,
	subdomain varchar(50),
	apptype varchar(20) default 'project',
	primary key(appid)
) engine=innodb default charset=utf8;


create table appmapping(
	appid int(11) not null,
	comcode  varchar(20) not null,
	appname varchar(100),
	isdeleted int(11) default false,
	url varchar(200),
	apptype varchar(20),
	planid varchar(20) default null,
	effectivedate datetime default null,
	expirationdate datetime default null,
	istrial int(11) default 0,
	primary key(appid, comcode),
	foreign key (appid) references app (appid)
) engine=innodb default charset=utf8;

create table `category` (
  `categoryid` int(11) default null,
  `categoryname` varchar(255) default null,
  `parentcategoryid` int(11) default null,
  `moddate` datetime default null,
  `deleted` int(11) default '0'
) engine=innodb default charset=utf8;

insert into `category` values
	(0,'전체',-1,null,0),
	(1,'인사/급여',-1,null,0),
	(2,'영업 관리',-1,null,0),
	(3,'생산 관리',-1,null,0),
	(4,'설비 관리',-1,null,0),
	(5,'구매 관리',-1,null,0);