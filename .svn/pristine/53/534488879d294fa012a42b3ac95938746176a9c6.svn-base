DROP TABLE IF EXISTS history_document CASCADE;
CREATE TABLE history_document
(
  id   VARCHAR(128)  PRIMARY KEY ,
  qzh  VARCHAR(128)  ,
  dh   VARCHAR(128)        ,
  zrz  VARCHAR(128),
  bgqx VARCHAR(128)  ,
  mj  VARCHAR(128)  ,
  ztc VARCHAR(128),
  bz VARCHAR(128),
  nd VARCHAR(128),
  stfs VARCHAR(128),
  jh  VARCHAR(128)  ,
  ys  VARCHAR(128),
  wh VARCHAR(128),
  tm VARCHAR(128),
  stms  VARCHAR(128),
  lbbh VARCHAR(128),
  ngbm VARCHAR(128),
  wjxcsj VARCHAR(128),
  stflh  VARCHAR(128),
  gdrq VARCHAR(128),
  ssbm VARCHAR(128),
  wjsx VARCHAR(128),
  lrr VARCHAR(128),
  wjlx VARCHAR(128),
  yjr VARCHAR(128),
  djh VARCHAR(128),
  sys_file_count  VARCHAR(128),
  swlx VARCHAR(128),
  swsm VARCHAR(128),
  xcsj VARCHAR(128),
  fs  VARCHAR(128),
  kh VARCHAR(128),
  ajh  VARCHAR(128),
  sl  VARCHAR(128),
  document_type VARCHAR(128)
);
DROP TABLE IF EXISTS history_file CASCADE;
CREATE TABLE history_file
(
  file_id    VARCHAR(128) PRIMARY KEY NOT NULL,
  file_type VARCHAR(128)   NOT NULL,
  entry_id    VARCHAR(128)        NOT NULL,
  file_title VARCHAR(128)  NOT NULL,
  file_size   VARCHAR(128)        NOT NULL,
  file_path VARCHAR(128)   NOT NULL,
  file_name VARCHAR(128)   NOT NULL,
  file_suffix VARCHAR(128) NOT NULL
);

--OA 用户表
DROP TABLE IF EXISTS oa_post;
CREATE TABLE oa_post
(
  operatorid   NUMERIC(18) NOT NULL  PRIMARY KEY,
  userid       VARCHAR(20) NOT NULL,   -- 用户名
  orgid        NUMERIC(10) NOT NULL,
  operatorname VARCHAR(100),
  oemail       VARCHAR(128),
  mobileno     VARCHAR(100),
  isopen       CHAR,
  gender       NUMERIC(5),
  empstatus    VARCHAR(16),
  duty         VARCHAR(100)
);
--OA 用户表 与档案用户关联表
DROP TABLE IF EXISTS link_users_oa_post;
CREATE TABLE link_users_oa_post (
  usr_id     BIGINT      NOT NULL,
  oa_post_id NUMERIC(10) NOT NULL
);

--OA 部门表
DROP TABLE IF EXISTS oa_department;
CREATE TABLE oa_department
(
  orgid       NUMERIC(10)  NOT NULL  PRIMARY KEY,
  orglevel    NUMERIC(10)  NOT NULL,
  parentorgid NUMERIC(10),
  orgseq      VARCHAR(512),
  orgname     VARCHAR(128) NOT NULL,
  orgno       VARCHAR(20)
);
--OA 部门表 与档案部门关联表
DROP TABLE IF EXISTS link_dept_oa_department;
CREATE TABLE link_dept_oa_department (
  dept_id     BIGINT      NOT NULL,
  oa_department_id NUMERIC(10) NOT NULL
);
--OA 密码表
DROP TABLE IF EXISTS oa_codesheet;
CREATE TABLE oa_codesheet
(
  operatorid   NUMERIC(18)  NOT NULL PRIMARY KEY,
  userid       VARCHAR(8)   NOT NULL,
  operatorname VARCHAR(100),
  password     VARCHAR(200) NOT NULL,
  flag         CHAR  -- 0
);
