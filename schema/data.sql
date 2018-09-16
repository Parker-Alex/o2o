create table tb_area(
  area_id int(4) not null auto_increment ,
  area_name varchar(200) not null ,
  area_priority int(2) not null default 0 ,
  creat_time datetime default null ,
  last_edit_time datetime default null ,
  primary key (area_id),
  unique key UK_NAME (area_name)
)engine = InnoDB auto_increment = 1 default charset = UTF8;

create table tb_user(
  user_id int(11) not null auto_increment,
  user_name varchar(200) not null ,
  sex varchar(4) default null ,
  profile_img varchar(1024) default null ,
  email varchar(1024) default null ,
  status int(2) default 0 comment '用户状态 0:禁用 1:可登录',
  identity int(2) default 0 comment '用户身份 0:普通用户 1:商家 2:管理员',
  create_time datetime default null ,
  last_edit_time datetime default null ,
  primary key (user_id),
  unique key UK_NAME(user_name)
)engine = InnoDB auto_increment =1 default charset = utf8;

create table tb_wechat_user(
  id int(11) not null auto_increment,
  wechat_user_id int(11) not null comment '用户id',
  open_id varchar(1024) not null comment '唯一开放id',
  create_time datetime default null ,
  primary key (id),
  unique index (open_id),#建立索引，方便查询，索引需维护，不易过多
  constraint FK_USER foreign key (wechat_user_id) references tb_user (user_id)
)engine = InnoDB auto_increment = 1 default charset = utf8;

create table tb_local_user(
  id int(11) not null auto_increment,
  local_user_id int(11) not null comment '用户id',
  name varchar(200) not null ,
  password varchar(200) not null ,
  create_time datetime default null ,
  last_edit_time datetime default null ,
  primary key (id),
  unique key UK_NAME (name),
  constraint FK_LOCAL_USER foreign key (local_user_id) references tb_user (user_id)
)engine = InnoDB auto_increment = 1 default charset = utf8;

create table tb_headline(
  line_id int(11) not null auto_increment,
  line_name varchar(200) not null ,
  line_priority int(2) not null default 0,
  status int(2) default 0 ,
  link varchar(1024) default null ,
  img varchar(1024) default null ,
  create_time datetime default null ,
  last_edit_time datetime default null ,
  primary key (line_id)
)engine = InnoDB auto_increment = 1 default charset = utf8;

create table tb_shopcategory(
  shopcategory_id int(11) not null auto_increment,
  shopcategory_name varchar(200) not null ,
  priority int(2) not null default 0,
  shopcategory_desc varchar(1024) default null ,
  shopcategory_img varchar(1024) default null ,
  parent_id int(11) default null comment '上一级类别id',#如id为空，表示此为根商品类别
  create_time datetime default null ,
  last_edit_time datetime default null ,
  primary key (shopcategory_id) ,
  constraint FK_SHOPCATEGORY foreign key (parent_id) references tb_shopcategory(shopcategory_id)
)engine = InnoDB auto_increment = 1 default charset = utf8;

create table tb_shop(
  shop_id int(11) not null auto_increment ,
  shop_name varchar(200) not null ,
  priority int(2) not null default 0,
  shop_img varchar(1024) default null ,
  shop_addr varchar(1024) default null ,
  shop_desc varchar(1024) default null ,
  shop_tel varchar(100) default null ,
  shop_advice varchar(1024) default null ,
  status int(2) not null default 0,#0:审核中 -1:下线 1:操作成功 2:过审
  create_time datetime default null ,
  last_edit_time datetime default null ,
  shop_area_id int(11) default null ,
  shop_user_id int(11) default null ,
  shop_shopcategory_id int(11) default null,
  primary key (shop_id),
  constraint FK_SHOP_USER foreign key (shop_user_id) references tb_user(user_id),
  constraint FK_SHOP_AREA foreign key (shop_area_id) references tb_area(area_id),
  constraint FK_SHOP_SHOPCATEGORY foreign key (shop_shopcategory_id) references tb_shopcategory(shopcategory_id)
)engine = InnoDB auto_increment = 1 default charset = utf8;

create table tb_productcategory(
  id int(11) not null auto_increment,
  priority int(2) default 0,
  name varchar(200) not null ,
  create_time datetime default null ,
  pc_shop_id int(11) default null ,
  primary key (id),
  constraint FK_PC_SHOP foreign key (id) references tb_shop (shop_id)
)engine = InnoDB auto_increment = 1 default charset = utf8;

create table tb_product(
  product_id int(11) not null auto_increment,
  product_name varchar(200) not null ,
  priority int(2) not null default 0,
  product_desc varchar(1024) default null ,
  img_addr varchar(1024) default null ,
  normal_price varchar(100) default null ,
  promotion_price varchar(100) default null ,
  create_time datetime default null ,
  last_edit_time datetime default null ,
  status int(2) not null default 0 comment '0:下架 1:上架',
  product_shop_id int(11) default null ,
  product_category_id int(11) default null ,
  primary key (product_id),
  constraint FK_PRO_SHOP_ID foreign key (product_shop_id) references tb_shop (shop_id),
  constraint FK_PRO_CATEGORY_ID foreign key (product_category_id) references tb_productcategory (id)
)engine = InnoDB auto_increment = 1 default charset = utf8;

create table tb_img(
  img_id int(11) not null auto_increment,
  img_addr varchar(1024) default null ,
  img_desc varchar(1024) default null ,
  priority int(2) not null default 0,
  create_time datetime default null ,
  img_product_id int(11) default null ,
  primary key (img_id),
  constraint FK_IMG_PRO_ID foreign key (img_product_id) references tb_product (product_id)
)engine = InnoDB auto_increment = 1 default charset = utf8;

ALTER TABLE tb_productcategory
  ADD CONSTRAINT FK_PC_SHOP
FOREIGN KEY (sid) REFERENCES tb_shop (shop_id);

alter table tb_shop add unique key (shop_name);

ALTER TABLE tb_productcategory CHANGE pc_shop_id sid int(11);

ALTER TABLE tb_img CHANGE img_product_id pid int(11);

ALTER TABLE tb_product CHANGE product_shop_id sid int(11);

ALTER TABLE tb_product CHANGE product_category_id pcid int(11);

ALTER TABLE tb_img MODIFY priority int(2) DEFAULT '0'