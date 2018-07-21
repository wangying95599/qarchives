TRUNCATE TABLE public.link_users_oa_post
CONTINUE IDENTITY
RESTRICT;
TRUNCATE TABLE public.link_dept_oa_department
CONTINUE IDENTITY
RESTRICT;

INSERT INTO users (login_name, password, name, state, created_dt, updated_dt)
VALUES ('admin', '16fae64be8de2801dc3f3a7848a77a59', '管理员', '0', now(), NULL);

INSERT INTO role (name)
VALUES
  ('档案管理员'),
  ('档案整理员'),
  ('部门档案管理员'),
  ('部门档案整理员'),
  ('上传人员'),
  ('部门审批成员'),
  ('普通用户');

INSERT INTO dept (name) VALUES ('ALL');

INSERT INTO link_dept_users (usr_id, dept_id) VALUES (1, 1);

INSERT INTO link_users_dept_role (usr_id, role_id, dept_id) VALUES (1, 1, 1);

UPDATE dept set description = 'KYA001' WHERE NAME = '昆明航空有限公司';
UPDATE dept SET description = 'KYA001-001'WHERE name = '公司办公室' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-002'WHERE name = '规划发展部' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-003'WHERE name = '人力资源部' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-004'WHERE name = '财务部' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-005'WHERE name = '安全监察部' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-006'WHERE name = '飞行部' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-007'WHERE name = '客舱服务部' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-008'WHERE name = '维修工程部' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-009'WHERE name = '运行中心' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-010'WHERE name = '营销委员会' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-011'WHERE name = '地面服务部' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-012'WHERE name = '货运部' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-013'WHERE name = '信息中心' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-014'WHERE name = '保卫部' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-015'WHERE name = '飞机引进办公室' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-016'WHERE name = '创新服务办公室' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-017'WHERE name = '党群工作办公室' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-018'WHERE name = '标准管理部' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA001-019'WHERE name = '飞行管理部' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA002'WHERE name = '山西分公司' AND  prt_id ISNULL ;
UPDATE dept SET description = 'KYA003'WHERE name = '腾冲基地' AND  prt_id ISNULL ;