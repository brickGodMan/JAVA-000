### 表关系图
![](https://github.com/brickGodMan/JAVA-000/blob/main/Week_06/img/table.jpg)

### sql
```sql
/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/11/25 21:27:08                          */
/*==============================================================*/


drop table if exists commodity;

drop table if exists commodity_suppliers_ref;

drop table if exists "order_t";

drop table if exists order_commodity_ref;

drop table if exists shopping_card;

drop table if exists suppliers;

drop table if exists user;

/*==============================================================*/
/* Table: commodity                                             */
/*==============================================================*/
create table commodity
(
   id                   varchar(32) comment '商品id',
   busi_code            varchar(32) comment '商品编码',
   busi_name            varchar(20) comment '商品名称',
   busi_classification  varchar(4) comment '商品分类',
   busi_type            varchar(4) comment '商品类型',
   busi_weight          varchar(10) comment '商品质量'
);

alter table commodity comment '商品表';

/*==============================================================*/
/* Table: commodity_suppliers_ref                               */
/*==============================================================*/
create table commodity_suppliers_ref
(
   busi_id                varchar(32) comment '商品id',
   supplier_id                varchar(32) comment '供应商id'
);

alter table commodity_suppliers_ref comment '商品供应商关联表';

/*==============================================================*/
/* Table: "order"                                               */
/*==============================================================*/
create table order_t
(
   id                   varchar(32) comment '订单id',
   user_id              varchar(32) comment '用户id',
   amount               varchar(10) comment '金额',
   status               varchar(2) comment '状态',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '修改时间'
);

alter table order_t comment '订单表';

/*==============================================================*/
/* Table: order_commodity_ref                                   */
/*==============================================================*/
create table order_commodity_ref
(
   order_id             varchar(32) comment '订单id',
   busi_id              varchar(32) comment '商品id'
);

alter table order_commodity_ref comment '订单商品关联表';

/*==============================================================*/
/* Table: shopping_card                                         */
/*==============================================================*/
create table shopping_card
(
   id                varchar(32) comment '购物车id',
   user_id                 varchar(32) comment '用户id'
);

alter table shopping_card comment '购物车';

/*==============================================================*/
/* Table: suppliers                                             */
/*==============================================================*/
create table suppliers
(
   id                varchar(32) comment '供应商id',
   supplier_name                   varchar(20) comment '名称',
   reg_num                  varchar(32) comment '注册号',
   phone                 varchar(11) comment '联系方式'
);

alter table suppliers comment '供应商';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   varchar(32) comment '用户id',
   user_name            varchar(16) comment '用户名',
   pwd                  varchar(16) comment '密码',
   nick                 varchar(16) comment '昵称',
   id_card              varchar(18) comment '身份证',
   phone                varchar(11) comment '手机号码',
   create_time          datetime comment '创建时间'
);

alter table user comment '用户表';
```