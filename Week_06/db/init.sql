/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/11/25 21:27:08                          */
/*==============================================================*/


drop table if exists commodity;

drop table if exists commodity_suppliers_ref;

drop table if exists "order";

drop table if exists order_commodity_ref;

drop table if exists shopping_card;

drop table if exists suppliers;

drop table if exists user;

/*==============================================================*/
/* Table: commodity                                             */
/*==============================================================*/
create table commodity
(
   id                   varchar(32) comment '��Ʒid',
   busi_code            varchar(32) comment '��Ʒ����',
   busi_name            varchar(20) comment '��Ʒ����',
   busi_classification  varchar(4) comment '��Ʒ����',
   busi_type            varchar(4) comment '��Ʒ����',
   busi_weight          varchar(10) comment '��Ʒ����'
);

alter table commodity comment '��Ʒ��';

/*==============================================================*/
/* Table: commodity_suppliers_ref                               */
/*==============================================================*/
create table commodity_suppliers_ref
(
   busi_id                varchar(32) comment '��Ʒid',
   supplier_id                varchar(32) comment '��Ӧ��id'
);

alter table commodity_suppliers_ref comment '��Ʒ��Ӧ�̹�����';

/*==============================================================*/
/* Table: "order"                                               */
/*==============================================================*/
create table order_t
(
   id                   varchar(32) comment '����id',
   user_id              varchar(32) comment '�û�id',
   amount               varchar(10) comment '���',
   status               varchar(2) comment '״̬',
   create_time          datetime comment '����ʱ��',
   update_time          datetime comment '�޸�ʱ��'
);

alter table order_t comment '������';

/*==============================================================*/
/* Table: order_commodity_ref                                   */
/*==============================================================*/
create table order_commodity_ref
(
   order_id             varchar(32) comment '����id',
   busi_id              varchar(32) comment '��Ʒid'
);

alter table order_commodity_ref comment '������Ʒ������';

/*==============================================================*/
/* Table: shopping_card                                         */
/*==============================================================*/
create table shopping_card
(
   id                varchar(32) comment '���ﳵid',
   user_id                 varchar(32) comment '�û�id'
);

alter table shopping_card comment '���ﳵ';

/*==============================================================*/
/* Table: suppliers                                             */
/*==============================================================*/
create table suppliers
(
   id                varchar(32) comment '��Ӧ��id',
   supplier_name                   varchar(20) comment '����',
   reg_num                  varchar(32) comment 'ע���',
   phone                 varchar(11) comment '��ϵ��ʽ'
);

alter table suppliers comment '��Ӧ��';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   varchar(32) comment '�û�id',
   user_name            varchar(16) comment '�û���',
   pwd                  varchar(16) comment '����',
   nick                 varchar(16) comment '�ǳ�',
   id_card              varchar(18) comment '���֤',
   phone                varchar(11) comment '�ֻ�����',
   create_time          datetime comment '����ʱ��'
);

alter table user comment '�û���';

