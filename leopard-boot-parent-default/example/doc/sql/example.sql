
create table `user` {
	`uid` bigint(11) not null  COMMENT '用户ID',
	`nickname` varchar(30) not null default '' COMMENT '昵称',
	`gender` varchar(10) not null COMMENT '性别',
	`posttime` datetime not null default '1970-01-01 08:00:00' COMMENT '注册时间',
	`lmodify` datetime not null default '1970-01-01 08:00:00' COMMENT '最后修改时间',
	primary key(`uid`)
} COMMENT='用户';



create table `article` {
	`articleId` varchar(40) not null default '' COMMENT '文章ID',
	`title` varchar(100) not null default '' COMMENT '标题',
	`deleted` tinyint(1) not null default 0 COMMENT '是否已删除',
	`content` text not null COMMENT '内容',
	`posttime` datetime not null default '1970-01-01 08:00:00' COMMENT '发表时间',
	`lmodify` datetime not null default '1970-01-01 08:00:00' COMMENT '最后修改时间',
	primary key(`articleId`)
} COMMENT='文章';