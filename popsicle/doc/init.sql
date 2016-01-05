


/*==============================================================*/
/* Table: ref_role_resource                                     */
/*==============================================================*/
create table ref_role_resource
(
   role_id              bigint(32) not null,
   resource_id          bigint(32) not null,
   primary key (role_id, resource_id)
);

/*==============================================================*/
/* Index: ref_role_resource_role_id                             */
/*==============================================================*/
create index ref_role_resource_role_id on ref_role_resource
(
   role_id
);

/*==============================================================*/
/* Index: ref_role_resource_resource_id                         */
/*==============================================================*/
create index ref_role_resource_resource_id on ref_role_resource
(
   resource_id
);

/*==============================================================*/
/* Table: ref_user_role                                         */
/*==============================================================*/
create table ref_user_role
(
   role_id              bigint(32) not null,
   user_id              bigint(32) not null,
   primary key (role_id, user_id)
);

/*==============================================================*/
/* Index: ref_u_r_role_id                                       */
/*==============================================================*/
create index ref_u_r_role_id on ref_user_role
(
   role_id
);

/*==============================================================*/
/* Index: ref_u_r_user_id                                       */
/*==============================================================*/
create index ref_u_r_user_id on ref_user_role
(
   user_id
);

/*==============================================================*/
/* Table: t_kschema                                             */
/*==============================================================*/
create table t_kschema
(
   ticker               varchar(20) not null,
   time                 datetime not null,
   day_str              varchar(10),
   time_str             varchar(10),
   open                 numeric(10,4) not null,
   close                numeric(10,4) not null,
   high                 numeric(10,4) not null,
   low                  numeric(10,4) not null,
   volume               int not null
);

/*==============================================================*/
/* Index: t_kschema_ticker                                      */
/*==============================================================*/
create index t_kschema_ticker on t_kschema
(
   ticker
);

/*==============================================================*/
/* Index: t_kschema_time                                        */
/*==============================================================*/
create index t_kschema_time on t_kschema
(
   time
);

/*==============================================================*/
/* Table: t_kschema_15M                                         */
/*==============================================================*/
create table t_kschema_15M as select * from t_kschema;

/*==============================================================*/
/* Table: t_kschema_1D                                          */
/*==============================================================*/
create table t_kschema_1D as select * from t_kschema;

/*==============================================================*/
/* Table: t_kschema_1H                                          */
/*==============================================================*/
create table t_kschema_1H as select * from t_kschema;

/*==============================================================*/
/* Table: t_kschema_1Month                                      */
/*==============================================================*/
create table t_kschema_1Month as select * from t_kschema;

/*==============================================================*/
/* Table: t_kschema_1W                                          */
/*==============================================================*/
create table t_kschema_1W as select * from t_kschema;

/*==============================================================*/
/* Table: t_kschema_30M                                         */
/*==============================================================*/
create table t_kschema_30M as select * from t_kschema;

/*==============================================================*/
/* Table: t_kschema_4H                                          */
/*==============================================================*/
create table t_kschema_4H as select * from t_kschema;

/*==============================================================*/
/* Table: t_kschema_5M                                          */
/*==============================================================*/
create table t_kschema_5M as select * from t_kschema;

/*==============================================================*/
/* Table: t_resource                                            */
/*==============================================================*/
create table t_resource
(
   id                   bigint(32) not null auto_increment,
   create_time          datetime,
   creator              varchar(64),
   modified_time        datetime,
   modifier             varchar(64),
   valid                boolean not null,
   name                 varchar(32) not null,
   code                 varchar(32) not null,
   url                  varchar(128),
   auth_url             varchar(128),
   has_any_roles        varchar(256) not null,
   order_no             int(10) not null,
   type                 int(10) not null,
   parent_code          varchar(32) not null,
   primary key (id)
);

/*==============================================================*/
/* Index: t_resource_code                                       */
/*==============================================================*/
create unique index t_resource_code on t_resource
(
   code
);

/*==============================================================*/
/* Table: t_role                                                */
/*==============================================================*/
create table t_role
(
   id                   bigint(32) not null auto_increment,
   code                 varchar(32) not null,
   name                 varchar(64) not null,
   remark               varchar(255),
   create_time          datetime,
   creator              varchar(64),
   modified_time        datetime,
   modifier             varchar(64),
   valid                boolean not null,
   primary key (id)
);

/*==============================================================*/
/* Index: t_role_code                                           */
/*==============================================================*/
create unique index t_role_code on t_role
(
   code
);

/*==============================================================*/
/* Table: t_transact_group                                      */
/*==============================================================*/
create table t_transact_group
(
   id                   bigint(32) not null auto_increment,
   creator              varchar(64),
   create_time          datetime,
   name                 varchar(255) not null,
   file_name            varchar(255),
   broker               varchar(125) not null,
   type                 varchar(125),
   account_name         varchar(64) not null,
   address              varchar(255),
   analysis_amount      bigint(32),
   analysis_p_l         bigint(32),
   parent_id            bigint(32),
   user_id              bigint(32) not null,
   primary key (id)
);

/*==============================================================*/
/* Index: t_transact_group_user_id                              */
/*==============================================================*/
create index t_transact_group_user_id on t_transact_group
(
   user_id
);

/*==============================================================*/
/* Table: t_transaction                                         */
/*==============================================================*/
create table t_transaction
(
   id                   bigint(32) not null auto_increment,
   ticket               varchar(32) not null,
   currency             varchar(32) not null,
   volume               int,
   lots                 float,
   open_time            datetime,
   close_time           datetime,
   open_type            varchar(32),
   close_type           varchar(32),
   direction            varchar(32),
   open_position        numeric(10,5),
   close_position       numeric(10,5),
   net_p_l              int,
   gross_p_l            int,
   group_id             bigint(32) not null,
   status               smallint,
   primary key (id)
);

/*==============================================================*/
/* Index: t_transaction_group_id                                */
/*==============================================================*/
create index t_transaction_group_id on t_transaction
(
   group_id
);

/*==============================================================*/
/* Index: t_transaction_currency                                */
/*==============================================================*/
create index t_transaction_currency on t_transaction
(
   currency
);

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   id                   bigint(32) not null auto_increment,
   password             varchar(64) not null,
   salt                 varchar(64),
   email                varchar(64),
   create_time          datetime,
   creator              varchar(64),
   modified_time        datetime,
   modifier             varchar(64),
   valid                boolean not null,
   login_name           varchar(128) not null,
   name                 varchar(64) not null,
   img_url              varchar(128),
   is_admin             boolean,
   status               int(5) not null,
   primary key (id)
);

/*==============================================================*/
/* Index: t_user_login_name                                     */
/*==============================================================*/
create unique index t_user_login_name on t_user
(
   login_name
);

