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
   ��Ʒid                 varchar(32) comment '��Ʒid',
   ��Ӧ��id                varchar(32) comment '��Ӧ��id'
);

alter table commodity_suppliers_ref comment '��Ʒ��Ӧ�̹�����';

/*==============================================================*/
/* Table: "order"                                               */
/*==============================================================*/
create table "order"
(
   id                   varchar(32) comment '����id',
   user_id              varchar(32) comment '�û�id',
   amount               varchar(10) comment '���',
   status               varchar(2) comment '״̬',
   create_time          datetime comment '����ʱ��',
   update_time          datetime comment '�޸�ʱ��'
);

alter table "order" comment '������';

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
   ���ﳵid                varchar(32) comment '���ﳵid',
   �û�id                 varchar(32) comment '�û�id'
);

alter table shopping_card comment '���ﳵ';

/*==============================================================*/
/* Table: suppliers                                             */
/*==============================================================*/
create table suppliers
(
   ��Ӧ��id                varchar(32) comment '��Ӧ��id',
   ����                   varchar(20) comment '����',
   ע���                  varchar(32) comment 'ע���',
   ��ϵ��ʽ                 varchar(11) comment '��ϵ��ʽ'
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

