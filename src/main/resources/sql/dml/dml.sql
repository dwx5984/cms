
INSERT INTO `sys_role` VALUES (100,'管理员',0,0);
INSERT INTO `sys_role` VALUES (200,'秘书',0,1);
INSERT INTO `sys_role` VALUES (300,'教师',0,1);

INSERT INTO `sys_role_user` VALUES (1,1,100,'2020-12-09 22:55:27',1);
INSERT INTO `sys_role_user` VALUES (2,2,200,'2020-12-09 22:56:02',1);
INSERT INTO `sys_role_user` VALUES (3,3,300,'2020-12-09 22:56:10',1);
INSERT INTO `sys_role_user` VALUES (4,4,100,'2020-12-09 22:55:27',1);
INSERT INTO `sys_role_user` VALUES (5,5,300,'2021-01-09 14:57:44',NULL);
INSERT INTO `sys_role_user` VALUES (6,6,200,'2021-02-24 22:39:35',NULL);


INSERT INTO `sys_user` VALUES (1,0,'李小明','xmli','','18318685984','123@q.com','中国','广东省','广州市','从化区','太平镇20号',0,'2020-12-08 18:24:55');
INSERT INTO `sys_user` VALUES (2,0,'王青','qwang','','18318685985','124@qq.com','中国','广东省','广州市','从化区','太平镇21号',0,'2020-12-09 22:54:28');
INSERT INTO `sys_user` VALUES (3,0,'张云剑','yjzhang','123','18318685986','125@qq.com','中国','广东省','广州市','从化区','太平镇22号',0,'2020-12-09 22:54:28');
INSERT INTO `sys_user` VALUES (4,0,'admin','admin','123','18318685987','126@q.com','中国','广东省','广州市','从化区','太平镇20号',0,'2020-12-08 18:24:55');
INSERT INTO `sys_user` VALUES (5,1,'李小青','xqli','123','18318685988','127@q.com','中国','中国','广州市','从化区','太平镇24号',0,'2021-02-23 23:08:12');
INSERT INTO `sys_user` VALUES (6,1,'王小荷','xhwang','123456','18318685901','111@qq.com','中国','广东省','深圳市','龙岗区','龙联',0,'2021-02-24 22:39:35');





#菜单数据
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1000, '系统设置', null, 0, 0, ' ', 1, '2020-12-08 18:25:15');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1001, '用户管理', null, 0, 10, '', 1, '2020-12-08 21:17:56');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1003, '课程管理', null, 0, 30, '', 1, '2020-12-08 21:17:56');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1009, '课时管理', null, 0, 60, '', 1, '2020-12-09 22:15:47');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (10010, '在线答疑', null, 0, 70, '', 1, '2020-12-09 22:15:13');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1000001, '角色管理', 1000, 0, 0, 'role', 1, '2020-12-09 22:26:27');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1000002, '菜单管理', 1000, 0, 0, 'menu', 1, '2020-12-09 22:26:27');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1001001, '用户列表', 1001, 0, 0, 'user', 1, '2020-12-30 09:56:06');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1003001, '课程管理', 1003, 0, 0, 'course', 1, '2020-12-09 22:36:30');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1003002, '调课审核', 1003, 0, 0, 'exchange', 1, '2020-12-09 22:37:26');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1009001, '课时统计', 1009, 0, 0, 'attendCount', 1, '2020-12-09 22:17:57');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1009002, '周课时统计', 1009, 0, 0, 'attendWeek', 1, '2020-12-09 22:18:07');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1009004, '月课时查询', 1009, 0, 0, 'attendMonth', 1, '2020-12-09 22:28:59');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1009005, '课时确认', 1009, 0, 0, 'attendRecord', 1, '2020-12-09 22:30:54');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1009006, '我的课程', 1009, 0, 0, 'mycourse', 1, '2021-02-22 21:41:35');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1009007, '课时对比', 1009, 0, 0, 'attendCompare', 1, '2021-02-27 15:00:52');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1009008, '在线答疑', 10010, 0, 0, 'chat', 1, '2021-02-27 16:43:05');
INSERT INTO cms.sys_menu (id, name, parent_id, status, sort, path, create_by, create_time) VALUES (1009009, '答疑记录', 10010, 0, 0, 'chatRecord', 1, '2021-02-27 16:50:58');


# 角色菜单关联数据
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (30, 100, 1003003);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (29, 100, 1003002);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (28, 100, 1003001);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (27, 100, 1003);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (21, 100, 1000);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (26, 100, 1002);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (22, 100, 1000001);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (23, 100, 1000002);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (24, 100, 1001);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (25, 100, 1001001);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (32, 100, 1004);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (31, 100, 1006);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (33, 100, 1009);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (34, 100, 1009001);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (35, 100, 1009002);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (36, 100, 1009003);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (37, 100, 1009004);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (38, 100, 1009005);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (39, 100, 10010);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (42, 100, 1009008);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (45, 100, 1009009);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (6, 200, 1004);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (1, 200, 1003);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (2, 200, 1003001);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (3, 200, 1003002);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (4, 200, 1003003);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (5, 200, 1006);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (7, 200, 1009);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (8, 200, 1009001);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (41, 200, 1009007);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (10, 200, 1009003);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (13, 200, 10010);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (46, 200, 1009009);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (14, 300, 1009);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (20, 300, 10010);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (40, 300, 1009006);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (19, 300, 1009005);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (18, 300, 1009004);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (44, 300, 1009008);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (17, 300, 1009003);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (16, 300, 1009002);
INSERT INTO cms.sys_role_menu (id, role_id, menu_id) VALUES (47, 300, 1009009);


#答疑记录
INSERT INTO cms.cms_chat (id, sender_id, teacher_id, content, readed, create_time) VALUES (1, 3, 3, '请问怎么调课', 1, '2021-02-27 16:34:37');
INSERT INTO cms.cms_chat (id, sender_id, teacher_id, content, readed, create_time) VALUES (2, 2, 3, '在菜单，我的课程中选择课程调课', 0, '2021-02-27 16:36:12');
INSERT INTO cms.cms_chat (id, sender_id, teacher_id, content, readed, create_time) VALUES (14, 3, 3, '请问怎么录入课时', 1, '2021-02-27 19:01:41');
INSERT INTO cms.cms_chat (id, sender_id, teacher_id, content, readed, create_time) VALUES (15, 2, 3, '在菜单，课时确认中选择课时录入', 0, '2021-02-27 19:03:10');
INSERT INTO cms.cms_chat (id, sender_id, teacher_id, content, readed, create_time) VALUES (24, 5, 5, '请问怎么进行周课时统计？', 1, '2021-02-27 20:43:15');
INSERT INTO cms.cms_chat (id, sender_id, teacher_id, content, readed, create_time) VALUES (25, 2, 5, '在菜单课时管理中，点击周课时统计，点击生成本周课时统计或生成本周课时统计，进行统计本周或上周的课时.', 0, '2021-02-27 20:44:42');

