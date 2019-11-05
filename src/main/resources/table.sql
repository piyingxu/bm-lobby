drop table if exists t_player_info;
create table t_player_info (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  app_id varchar(100) NOT NULL  COMMENT 'appid',
  player_id varchar(100) NOT NULL COMMENT '玩家id',
  nick_name varchar(100) DEFAULT NULL COMMENT '玩家昵称',
  head_url varchar(1000)  DEFAULT NULL COMMENT '头像',
  gender tinyint(11) DEFAULT 0 COMMENT '性别：0-女 1-男',
  auth_type varchar(500) NOT NULL COMMENT '认证类型：1-QQ 2-weichat',
  auth_id varchar(500) NOT NULL COMMENT '认证唯一ID, 微信的openId，游客设备ID',
  client_type varchar(50) NOT NULL COMMENT '客户端类型：1-android, 2-ios, 3-H5 ',
  client_version varchar(50) NOT NULL COMMENT '注册时版本号',
  status tinyint(11) DEFAULT 1 COMMENT '状态：1-正常 2-冻结',
  create_time timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_time timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  UNIQUE KEY idx_player_id (player_id),
  UNIQUE KEY idx_auth (auth_id),
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='玩家基本信息表';

drop table if exists t_game_config;
create table t_game_config (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  app_id varchar(100) NOT NULL  COMMENT 'appid',
  game_id varchar(100) NOT NULL  COMMENT '游戏ID',
  game_name varchar(100) NOT NULL COMMENT '游戏名称',
  game_icon varchar(100) NOT NULL COMMENT '游戏icon',
  game_url varchar(100)  COMMENT '游戏url',
  show_order tinyint(11) NOT NULL  COMMENT '排序,数字越小越靠前',
  create_time timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_time timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='游戏配置信息表';

drop table if exists t_withdraw_order;
create table t_withdraw_order (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  player_id varchar(100) NOT NULL COMMENT '玩家id',
  order_no varchar(100) NOT NULL COMMENT '订单号',
  channel int(11) NOT NULL  COMMENT '渠道：1-微信 2-支付宝',
  account_num varchar(100) NOT NULL COMMENT '账号',
  account_name varchar(100) NOT NULL COMMENT '账号名称',
  amount int(11)  NOT NULL COMMENT '金额：单位分',
  status int(11) DEFAULT 1 COMMENT '状态：1-审核中 2-成功  3-失败',
  remark varchar(1000) COMMENT '订单号',
  create_time timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_time timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  UNIQUE KEY idx_order_no (order_no),
  KEY idx_player_id (player_id),
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='提现订单信息表';