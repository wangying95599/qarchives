DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE public.users
(
  id         BIGSERIAL PRIMARY KEY NOT NULL,
  login_name VARCHAR(32)           NOT NULL,
  password   VARCHAR(32)           NOT NULL,
  name       VARCHAR(32)           NOT NULL,
  state      VARCHAR(2) DEFAULT 0  NOT NULL,
  created_dt TIMESTAMP,
  updated_dt TIMESTAMP
);
CREATE UNIQUE INDEX users_id_uindex
  ON public.users (id);

DROP TABLE IF EXISTS role CASCADE;
CREATE TABLE public.role
(
  id          BIGSERIAL PRIMARY KEY NOT NULL,
  name        VARCHAR(32)           NOT NULL,
  description VARCHAR(1024)
);
CREATE UNIQUE INDEX role_id_uindex
  ON public.role (id);

DROP TABLE IF EXISTS dept CASCADE;
CREATE TABLE public.dept
(
  id          BIGSERIAL PRIMARY KEY NOT NULL,
  name        VARCHAR(32)           NOT NULL,
  prt_id      BIGINT,
  description VARCHAR(1024)
);
CREATE UNIQUE INDEX dept_id_uindex
  ON public.dept (id);

DROP TABLE IF EXISTS link_users_dept_role CASCADE;
CREATE TABLE public.link_users_dept_role
(
  id      BIGSERIAL PRIMARY KEY NOT NULL,
  usr_id  BIGINT                NOT NULL,
  role_id BIGINT                NOT NULL,
  dept_id BIGINT                NOT NULL,
  CONSTRAINT link_users_dept_role_users_id_fk FOREIGN KEY (usr_id) REFERENCES users (id),
  CONSTRAINT link_users_dept_role_role_id_fk FOREIGN KEY (role_id) REFERENCES role (id),
  CONSTRAINT link_users_dept_role_dept_id_fk FOREIGN KEY (dept_id) REFERENCES dept (id)
);
CREATE UNIQUE INDEX link_users_dept_role_id_uindex
  ON public.link_users_dept_role (id);

DROP TABLE IF EXISTS link_dept_users CASCADE;
CREATE TABLE public.link_dept_users
(
  id      BIGSERIAL PRIMARY KEY NOT NULL,
  usr_id  BIGINT                NOT NULL,
  dept_id BIGINT                NOT NULL,
  CONSTRAINT link_dept_users_users_id_fk FOREIGN KEY (usr_id) REFERENCES users (id),
  CONSTRAINT link_dept_users_dept_id_fk FOREIGN KEY (dept_id) REFERENCES dept (id)
);
CREATE UNIQUE INDEX link_dept_users_id_uindex
  ON public.link_dept_users (id);

---删除的文档怎么处理 ？？？
DROP TABLE IF EXISTS "public"."documents" CASCADE;
CREATE TABLE "public"."documents" (
  "file_name"         VARCHAR(128) COLLATE "default" NOT NULL,
  "file_num"          VARCHAR(128) COLLATE "default",
  "id"                BIGSERIAL PRIMARY KEY          NOT NULL,
  "dept_id"           INT8                           NOT NULL,
  "title"             VARCHAR(128) COLLATE "default",
  "doc_attr"          VARCHAR(128) COLLATE "default",
  "responsible"       VARCHAR(128) COLLATE "default",
  "fonds_id"          VARCHAR(128) COLLATE "default",
  "importance"        VARCHAR(128) COLLATE "default",
  "appendices"        INT8,
  "archive_type"      VARCHAR(128) COLLATE "default",
  "deliver_user"      VARCHAR(128) COLLATE "default",
  "deliver_dept"      VARCHAR(128) COLLATE "default",
  "archive_year"      VARCHAR(128) COLLATE "default",
  "reserve_duration"  VARCHAR(128) COLLATE "default",
  "entity_num"        INT8,
  "file_createtime"   VARCHAR(128) COLLATE "default",
  "reserve_location"  VARCHAR(128) COLLATE "default",
  "before_num"        VARCHAR(128) COLLATE "default",
  "archive_date"      VARCHAR(128) COLLATE "default",
  "remark"            VARCHAR(1000) COLLATE "default",
  "record_flag"       VARCHAR(16)                        NOT NULL DEFAULT '00',--00为正常，01为删除进入回收站，02为彻底删除
  "document_local_id" VARCHAR(128),
  document_creatime   character varying(128),
  link_files_id       character varying(128) DEFAULT ''::character varying NOT NULL,
  is_archive          boolean DEFAULT false NOT NULL,
  user_id             bigint DEFAULT 0 NOT NULL,
  sava_location       CHARACTER VARYING(128),
  data_source         CHARACTER VARYING(128),
  deliver_date        TIMESTAMP,
  paginal_num         INT4,
  "arrange_flag"      BOOLEAN                                 DEFAULT FALSE,
  updated_dt          VARCHAR(32)    COLLATE "default"
)
WITH (OIDS = FALSE
);
--删除的文件呢  同理关于其他删除的呢
DROP TABLE IF EXISTS "public"."files" CASCADE;
CREATE TABLE "public"."files" (
  doc_id        character varying(128) NOT NULL,
  "id"          BIGSERIAL PRIMARY KEY          NOT NULL,
  "file_type"   VARCHAR(128) COLLATE "default" ,
  "location"    VARCHAR(256) COLLATE "default" ,
  "upload_date" TIMESTAMP,
  "file_name"   VARCHAR(256) COLLATE "default" NOT NULL,
  "record_flag" BOOLEAN                        NOT NULL DEFAULT TRUE,
  "file_id"     VARCHAR(128) COLLATE "default" NOT NULL
)
WITH (OIDS = FALSE
);

CREATE UNIQUE INDEX "files_id_uindex"
  ON "public"."files" USING BTREE (id);

DROP TABLE IF EXISTS "public"."records" CASCADE;
CREATE TABLE "public"."records" (
  "id"               BIGSERIAL PRIMARY KEY          NOT NULL, --id
  "dept_id"          INT8                           NOT NULL, --部门id
  "file_num"         VARCHAR(128) COLLATE "default", --档号
  "title"            VARCHAR(256) COLLATE "default", --题名
  "theme_word"       VARCHAR(128) COLLATE "default", --主题词
  "responsible"      VARCHAR(128) COLLATE "default", --归档人
  "fonds_id"         VARCHAR(128) COLLATE "default", --全宗号
  "importance"       VARCHAR(128) COLLATE "default", --重要程度
  "appendices"       INT8, --卷内文件数
  "archive_type"     VARCHAR(128) COLLATE "default", --档案种类
  "deliver_user"     VARCHAR(128) COLLATE "default", --移交人
  "deliver_office"   VARCHAR(128) COLLATE "default", --移交科室
  "deliver_dept"     VARCHAR(128) COLLATE "default", --移交部门
  "archive_year"     VARCHAR(128) COLLATE "default", --归档年度
  "reserve_duration" VARCHAR(128) COLLATE "default", --保管期限
  "save_num"         INT8, --数量
  "item_num"         VARCHAR(128) COLLATE "default",
  "reserve_location" VARCHAR(128) COLLATE "default", --存放位置
  "archive_date"     VARCHAR(128) COLLATE "default", --归档日期
  "remark"           VARCHAR(1000) COLLATE "default", --备注
  "record_flag"      BOOLEAN                        NOT NULL          DEFAULT TRUE,
  is_archive         BOOLEAN DEFAULT FALSE          NOT NULL, --是否归档
  user_id            BIGINT DEFAULT 0               NOT NULL, --创建人
  deliver_date       TIMESTAMP, --移交日期
  created_dt         TIMESTAMP                      NOT NULL          DEFAULT now(),
  updated_dt         TIMESTAMP
)
WITH (OIDS = FALSE
);

DROP TABLE IF EXISTS "public"."archives" CASCADE;
CREATE TABLE "public"."archives" (
  "id"               BIGSERIAL PRIMARY KEY           NOT NULL,
  "title"            VARCHAR(128) COLLATE "default"  NOT NULL,
  "importance"       VARCHAR(128) COLLATE "default"  NOT NULL,
  "reel_num"         VARCHAR(128) COLLATE "default"  NOT NULL, --档号
  "records_location" VARCHAR(128) COLLATE "default"  NOT NULL,
  "file_num"         INT8                            NOT NULL,
  "responsible"      VARCHAR(128) COLLATE "default"  NOT NULL,
  "remark"           VARCHAR(1000) COLLATE "default",
  "dept_id"          INT8                            NOT NULL,
  create_time character varying(128),
    is_archive boolean DEFAULT false NOT NULL,
    user_id bigint DEFAULT 0 NOT NULL,
    dept_name character varying(128),
      "record_flag"       BOOLEAN                        NOT NULL DEFAULT TRUE
)
WITH (OIDS = FALSE
);

DROP TABLE IF EXISTS "public"."box" CASCADE;
CREATE TABLE "public"."box" (
  "id"               BIGSERIAL PRIMARY KEY          NOT NULL,
  "title"            VARCHAR(128) COLLATE "default" NOT NULL,
  "importance"       VARCHAR(128) COLLATE "default" NOT NULL,
  "reel_num"         VARCHAR(128) COLLATE "default" NOT NULL, --档号
  "records_location" VARCHAR(128) COLLATE "default" NOT NULL,
  "file_num"         INT8                           NOT NULL,
  "responsible"      VARCHAR(128) COLLATE "default" NOT NULL,
  "remark"           VARCHAR(1000) COLLATE "default",
  "dept_id"          INT8                           NOT NULL,
  create_time character varying(128),
  is_archive boolean DEFAULT false,
  user_id bigint DEFAULT 0 NOT NULL,
  dept_name character varying(128),
    "record_flag"       BOOLEAN                        NOT NULL DEFAULT TRUE
)
WITH (OIDS = FALSE
);

DROP TABLE IF EXISTS "public"."link_achives_records" CASCADE;
CREATE TABLE "public"."link_achives_records" (
  "archive_guid" INT8 NOT NULL,
  "record_id"    INT8 NOT NULL
)
WITH (OIDS = FALSE
);

DROP TABLE IF EXISTS "public"."link_box_achives" CASCADE;
CREATE TABLE "public"."link_box_achives" (
  "box_guid"     INT8 NOT NULL,
  "archive_guid" INT8 NOT NULL
)
WITH (OIDS = FALSE
);

DROP TABLE IF EXISTS "public"."link_records_documents" CASCADE;
CREATE TABLE "public"."link_records_documents" (
  "record_guid" INT8 NOT NULL,
  "doc_id"      INT8 NOT NULL,
  "inside_num"  INT4
)
WITH (OIDS = FALSE
);

DROP TABLE IF EXISTS flows CASCADE;
CREATE TABLE flows (
  id          BIGSERIAL PRIMARY KEY NOT NULL,
  type        VARCHAR(10)           NOT NULL, -- LENDING , DESTORY
  status      VARCHAR(12)           NOT NULL          DEFAULT 'IN_PROCESS', -- IN_PROCESS,COMPLETE
  result      VARCHAR(12)           NOT NULL          DEFAULT '', --'',ACCEPT,REJECT
  created_dt  TIMESTAMP             NOT NULL          DEFAULT now(),
  dead_line   TIMESTAMP             NOT NULL,
  end_dt      TIMESTAMP,
  created_by  BIGINT                NOT NULL,
  record_flag BOOLEAN               NOT NULL          DEFAULT TRUE,
  CONSTRAINT flows_created_by_fk FOREIGN KEY (created_by) REFERENCES users (id)
);

DROP TABLE IF EXISTS flow_nodes CASCADE;
--只会存储正在进行中的流程节点.
CREATE TABLE flow_nodes (
  id                  BIGSERIAL PRIMARY KEY NOT NULL,
  type                VARCHAR(10)           NOT NULL, -- LENDING , DESTORY
  flow_id             BIGINT                NOT NULL,
  assignee_id         BIGINT                NOT NULL,
  created_dt          TIMESTAMP             NOT NULL       DEFAULT now(),
  dead_line           TIMESTAMP             NOT NULL,
  assignee_by         BIGINT                NOT NULL,
  need_assigne_action BOOLEAN               NOT NULL       DEFAULT FALSE, -- 是否可以指定下个节点
  record_flag         BOOLEAN               NOT NULL       DEFAULT TRUE,
  CONSTRAINT flow_nodes_assignee_by_fk FOREIGN KEY (assignee_by) REFERENCES users (id),
  CONSTRAINT flow_nodes_assignee_id_fk FOREIGN KEY (assignee_id) REFERENCES users (id),
  CONSTRAINT flow_nodes_flows_fk FOREIGN KEY (flow_id) REFERENCES flows (id)
);

DROP TABLE IF EXISTS flow_node_histories CASCADE;
--只会存储正在进行中的流程节点.
CREATE TABLE flow_node_histories (
  node_id     BIGINT      NOT NULL,
  type        VARCHAR(10) NOT NULL, -- LENDING , DESTORY
  flow_id     BIGINT      NOT NULL,
  action      VARCHAR(12) NOT NULL, --'',ACCEPT,REJECT
  description VARCHAR(2048),
  assignee_id BIGINT      NOT NULL,
  created_dt  TIMESTAMP   NOT NULL       DEFAULT now(),
  dead_line   TIMESTAMP   NOT NULL,
  assignee_by BIGINT      NOT NULL,
  record_flag BOOLEAN     NOT NULL       DEFAULT TRUE,
  CONSTRAINT flow_node_histories_assignee_by_fk FOREIGN KEY (assignee_by) REFERENCES users (id),
  CONSTRAINT flow_node_histories_assignee_id_fk FOREIGN KEY (assignee_id) REFERENCES users (id),
  CONSTRAINT flow_node_histories_flows_fk FOREIGN KEY (flow_id) REFERENCES flows (id)
);

DROP TABLE IF EXISTS flow_form_lending CASCADE;
CREATE TABLE flow_form_lending (
  flow_id            BIGINT       NOT NULL,
  title              VARCHAR(256) NOT NULL,
  reel_num           VARCHAR(128) NOT NULL, --档号
  reel_type          VARCHAR(20)  NOT NULL, --档案类型 (表名) : document, record,archive,box
  dep_id             BIGINT       NOT NULL,
  lending_user       VARCHAR(40)  NOT NULL,
  lending_permission VARCHAR(20)  NOT NULL, -- ONlINE_VIEW,ONLINE_DOWNLOAD,OFFLINE_COPY
  description        VARCHAR(2048),
  record_flag        BOOLEAN      NOT NULL       DEFAULT TRUE,
  lending_type      VARCHAR(40) NOT NULL ,  --'01'本部门  '02'其他部门
  manager_id       BIGINT  ,
  host_id          BIGINT,
  loan_dept_id     BIGINT,
  CONSTRAINT flow_form_lending_dep_id_fk FOREIGN KEY (dep_id) REFERENCES dept (id),
  CONSTRAINT flow_form_lending_flow_id_fk FOREIGN KEY (flow_id) REFERENCES flows (id)
);

DROP TABLE IF EXISTS flow_form_destory CASCADE;
CREATE TABLE flow_form_destory (
  flow_id        BIGINT       NOT NULL,
  title          VARCHAR(256) NOT NULL,
  reel_num       VARCHAR(128) NOT NULL, --档号
  reel_type      VARCHAR(20)  NOT NULL, --档案类型 (表名) : documents, records,archives,box
  reel_size      INT          NOT NULL,
  destory_user   VARCHAR(64)       NOT NULL, --销毁人
  supervise_user VARCHAR(64)  , --- 监管人
  description    VARCHAR(2048),
  record_flag    BOOLEAN      NOT NULL       DEFAULT TRUE,
  CONSTRAINT flow_form_destory_flow_id_fk FOREIGN KEY (flow_id) REFERENCES flows (id)
);

DROP TABLE IF EXISTS flow_form_deliver CASCADE ;
CREATE TABLE flow_form_deliver
(
  flow_id INT8 NOT NULL,
  title VARCHAR(256),
  deliver_user VARCHAR(64),   --移交人
  receive_user INT8,           --接收人
  deliver_office VARCHAR(64),   --移交科室
  deliver_dept_id INT8 NOT NULL,  --移交部门
  receive_dept_id INT8,           --接收部门
  description VARCHAR(2048),
  record_flag VARCHAR(16) DEFAULT 00
);

DROP TABLE IF EXISTS link_flow_doc CASCADE ;
CREATE TABLE link_flow_doc
(
  flow_id INT8 NOT NULL,
  doc_id INT8 NOT NULL,
  file_num VARCHAR(128) NOT NULL,
  file_type VARCHAR(32) NOT NULL
);

DROP TABLE IF EXISTS access_log;
CREATE TABLE access_log(
  usr_id      BIGINT,
  obj_id      BIGINT,
  obj_type    VARCHAR(20),  -- 文档 用户 档案 卷宗
  access_type VARCHAR(20), -- UPDATE DELETE
  description VARCHAR(20),
  time        TIMESTAMP,
  str_id      VARCHAR(64)
);

DROP TABLE IF EXISTS home_page;
CREATE TABLE home_page
(
  id          BIGSERIAL                  NOT NULL
    CONSTRAINT home_page_pkey
    PRIMARY KEY,
  content     VARCHAR(1024),
  type        VARCHAR(128),
  create_date TIMESTAMP(6) DEFAULT now() NOT NULL,
  record_flag BOOLEAN DEFAULT TRUE       NOT NULL,
  update_dt   TIMESTAMP(6)               NOT NULL
);

DROP TABLE IF EXISTS swift;
CREATE TABLE swift (
  "prefix"       VARCHAR(128) NOT NULL,
  "swift_number" INT4         NOT NULL,
  PRIMARY KEY ("prefix")
)
WITH (OIDS = FALSE
);

DROP TABLE IF EXISTS home_picture;
CREATE TABLE public.home_picture
(
  id          SERIAL PRIMARY KEY      NOT NULL,
  location    VARCHAR(128)            NOT NULL,
  content     VARCHAR(128),
  created_dt  TIMESTAMP DEFAULT now() NOT NULL,
  updated_dt  TIMESTAMP,
  record_flag BOOLEAN DEFAULT TRUE    NOT NULL
);
CREATE UNIQUE INDEX home_picture_id_uindex
  ON public.home_picture (id);

-- 声像类型文件扩张
CREATE TABLE "public"."acoustic_image" (
  "id"               INT4 NOT NULL, --对应的document id
  "place"            VARCHAR(128), --地点
  "figure"           VARCHAR(128), --图像
  "photographer"     VARCHAR(128), --摄影者
  "photography_time" TIMESTAMP, --摄影时间
  "number"           INT4, --数量
  "leader"           VARCHAR(128), --负责人
  "created_dt"       TIMESTAMP, --创建时间
  "updated_dt"       TIMESTAMP, --修改时间
  PRIMARY KEY ("id")
)
WITH (OIDS = FALSE
);

DROP TABLE IF EXISTS flow_form_assist CASCADE ;
CREATE TABLE flow_form_assist
(
  flow_id INT8 NOT NULL,
  apply_days VARCHAR(64),
  apply_user VARCHAR(64),   --申请人
  assit_user INT8,           --协查人
  apply_id VARCHAR(64),   --工号
  office VARCHAR(64),   --科室
  dept VARCHAR(64),           --部门
  assist_type VARCHAR(64), --协查类型
  assist_content VARCHAR(2048),--协查内容
  description VARCHAR(2048),
  record_flag VARCHAR(16) DEFAULT 00
);

CREATE TABLE "public"."link_assist_doc" (
	"flow_id" int8 NOT NULL,
	"record_id" int8,
	"document_local_id" varchar(128) COLLATE "default",
	"record_flag" varchar(16) COLLATE "default" DEFAULT '00'::character varying,
	"updated_dt" varchar(32) COLLATE "default"
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."link_assist_doc" OWNER TO "postgres";

ALTER TABLE "public"."link_assist_doc"
ADD COLUMN "doc_id" int8,
ADD COLUMN "lend_flag" varchar(16) COLLATE "default" DEFAULT '01'::character varying;

ALTER TABLE "public"."flow_form_assist"
ADD COLUMN "iphone" varchar(64) COLLATE "default";
ALTER TABLE link_flow_doc
  ADD COLUMN new_file_num VARCHAR(128);

DELETE FROM documents
WHERE document_local_id IN (SELECT document_local_id
                            FROM documents
                            GROUP BY document_local_id
                            HAVING count(1) > 1)
      AND id NOT IN (SELECT min(id)
                     FROM documents
                     GROUP BY document_local_id
                     HAVING count(1) > 1);
alter TABLE flow_form_destory alter COLUMN reel_num drop not NULL;
alter TABLE flow_form_destory alter COLUMN reel_type drop not NULL;

ALTER TABLE "public"."home_page" ADD COLUMN "file_id" int8;
ALTER  TABLE documents ADD  COLUMN oa_sync_type VARCHAR(128);

ALTER TABLE flow_form_lending ADD COLUMN is_propel VARCHAR(16) DEFAULT ('00');--00未推送 01 已推送 02 已还
ALTER TABLE flow_form_lending
  ADD COLUMN sdto_guid VARCHAR(128);
