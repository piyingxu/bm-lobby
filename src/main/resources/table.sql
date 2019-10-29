drop table if exists t_user_info;
CREATE TABLE `t_user_info` (
  `id` varchar(32) NOT NULL,
  `username` varchar(32) DEFAULT '',
  `pwd` varchar(32) DEFAULT '',
  `role` tinyint(1) NOT NULL COMMENT '1管理员2商家',
  `status` tinyint(1) NOT NULL COMMENT '0-冻结 1-正常',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';


drop table if exists t_merchant_info;
CREATE TABLE `t_merchant_info` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL COMMENT '关联用户',
  `merchant_id` varchar(32) NOT NULL COMMENT '商铺id',
  `name` varchar(100) NOT NULL COMMENT '商户名',
  `merchant_address` varchar(200) NOT NULL COMMENT '商铺地址',
  `merchant_name` varchar(64) NOT NULL COMMENT '商铺联系人',
  `merchant_phone` varchar(32) NOT NULL COMMENT '联系人电话',
  `total_table` int(10) NOT NULL COMMENT '台桌总算',
  `status` tinyint(1) NOT NULL COMMENT '状态（0-到期  1-正常）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户表';

drop table if exists t_dishes_info;
CREATE TABLE `t_dishes_info` (
  `id` varchar(32) NOT NULL,
  `merchant_id` varchar(32) NOT NULL COMMENT '关联商铺id',
  `dish_name` varchar(64) NOT NULL COMMENT '菜名',
  `dish_price` decimal(8,2) DEFAULT NULL COMMENT '菜价',
  `dish_description` varchar(64) DEFAULT NULL COMMENT '描述',
  `dish_icon` varchar(512) DEFAULT NULL COMMENT '小图(图片地址)',
  `dish_status` tinyint(1) DEFAULT '0' COMMENT '菜品状态,0正常1下架',
  `dish_stock` int(11) NOT NULL COMMENT '库存',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE   CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜品表';

drop table if exists t_dish_category;
CREATE TABLE `t_dish_category` (
  `id` varchar(32) NOT NULL,
  `merchant_id` varchar(32) NOT NULL COMMENT '关联商铺id',
  `category_name` varchar(64) NOT NULL COMMENT '分类名称',
  `category_order` int(11) NOT NULL COMMENT '类目编号(顺序)',
  `category_status` tinyint(1) DEFAULT '0' COMMENT '状态（0-隐藏 1-开放)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uqe_category_type` (`category_order`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='栏目表';

drop table if exists t_shelf_info;
CREATE TABLE `t_shelf_info` (
  `id` varchar(32) NOT NULL,
  `merchant_id` varchar(32) NOT NULL COMMENT '关联商铺id',
  `shelf_name` varchar(64) NOT NULL COMMENT '名称',
  `category_status` tinyint(1) DEFAULT '0' COMMENT '状态（0-未发布 1-已发布）ps:同一商家只能有一个货架为已发布',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='货架表';

drop table if exists t_shelf_detail;
CREATE TABLE `t_shelf_detail` (
  `id` varchar(32) NOT NULL,
  `shelf_id` varchar(32) NOT NULL COMMENT '货架id',
  `merchant_id` varchar(32) NOT NULL COMMENT '关联商铺id',
  `dish_id` varchar(32) NOT NULL COMMENT '关联菜品id',
  `catagory_id` varchar(32) NOT NULL COMMENT '关联栏目id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='货架明细表';

drop table if exists t_order_master;
CREATE TABLE `t_order_master` (
  `id` varchar(32) NOT NULL,
  `merchant_id` varchar(32) NOT NULL COMMENT '关联商铺id',
   `table_no` varchar(32) NOT NULL COMMENT '桌台号',
   `customer_num` int(10) NOT NULL COMMENT '人数',
  `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态,（0-未结算 1-已结算)',
   `customer_remark` varchar(200) NOT NULL COMMENT '客人备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

drop table if exists t_order_datail;
CREATE TABLE `t_order_datail` (
  `id` varchar(32) NOT NULL,
  `order_id` varchar(32) NOT NULL COMMENT '关联订单id',
  `dish_id` varchar(32) NOT NULL COMMENT '关联菜品id',
   `dish_num` int(10) NOT NULL COMMENT '份数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表';

drop table if exists t_evaluation_info;
CREATE TABLE `t_evaluation_info` (
   `id` varchar(32) NOT NULL,
  `merchant_id` varchar(32) NOT NULL COMMENT '关联商铺id',
   `grade` varchar(10) NOT NULL COMMENT '评分等级A,B,C,D',
   `comments`  varchar(200) NOT NULL COMMENT '评价留言',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户评价表';