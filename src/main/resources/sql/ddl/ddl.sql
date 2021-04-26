# 数据库：cms
# create schema cms collate utf8_general_ci;
# use cms;

CREATE TABLE cms_attend_record
(
    id                INT AUTO_INCREMENT
        PRIMARY KEY,
    user_id           INT                                NULL COMMENT '老师id',
    course_id         INT                                NULL COMMENT '课程id',
    status            SMALLINT                           NULL COMMENT '记录状态',
    hour              INT                                NULL COMMENT '时长',
    attend_start_time DATETIME                           NULL,
    attend_end_time   DATETIME                           NULL,
    create_time       DATETIME DEFAULT CURRENT_TIMESTAMP NULL
)
    COMMENT '上课记录表（课时记录）';

CREATE TABLE cms_attend_week
(
    id          INT AUTO_INCREMENT
        PRIMARY KEY,
    start_time  DATETIME                           NULL COMMENT '统计开始时间',
    end_time    DATETIME                           NULL COMMENT '统计结束时间',
    user_id     INT                                NULL COMMENT '老师id',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '导入时间',
    course_ids  VARCHAR(1000)                      NULL,
    hours       INT                                NULL
)
    COMMENT '周课时统计';

CREATE TABLE cms_class
(
    id            INT AUTO_INCREMENT
        PRIMARY KEY,
    speciality_id INT         NULL COMMENT '专业id',
    name          VARCHAR(64) NULL COMMENT '班级名称'
)
    COMMENT '班级表';

CREATE TABLE cms_course
(
    id          INT AUTO_INCREMENT
        PRIMARY KEY,
    no          VARCHAR(64)                        NOT NULL COMMENT '课程代码',
    name        VARCHAR(64)                        NOT NULL,
    credit      DOUBLE                             NOT NULL COMMENT '学分',
    required    INT      DEFAULT 0                 NULL COMMENT '是否必修',
    PERIOD DOUBLE NOT NULL COMMENT '课时',
    subject     VARCHAR(64)                        NOT NULL COMMENT '学科',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    del_flag    SMALLINT DEFAULT 0                 NOT NULL COMMENT '删除标记'
);

CREATE TABLE cms_exchange_record
(
    id             INT AUTO_INCREMENT
        PRIMARY KEY,
    type           INT                                NULL COMMENT '调课类型：请假、调课',
    status         INT                                NULL COMMENT '状态',
    create_time    DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
    user_id        INT                                NULL COMMENT '某个老师的课程',
    target_user_id INT                                NULL COMMENT '调课给哪个老师',
    start_time     DATETIME                           NULL,
    end_time       DATETIME                           NULL,
    course_id      INT                                NULL
)
    COMMENT '调课记录';

CREATE TABLE cms_speciality
(
    id   INT AUTO_INCREMENT
        PRIMARY KEY,
    name VARCHAR(64) NOT NULL COMMENT '专业名称'
)
    COMMENT '专业表';

CREATE TABLE cms_teacher_class
(
    id       INT AUTO_INCREMENT
        PRIMARY KEY,
    user_id  INT NULL COMMENT '老师id',
    class_id INT NULL COMMENT '班级id'
)
    COMMENT '老师班级关联表';

CREATE TABLE cms_teacher_course
(
    id               INT AUTO_INCREMENT
        PRIMARY KEY,
    course_id        INT                                NOT NULL COMMENT '课程id',
    user_id          INT                                NULL COMMENT '老师id',
    original_user_id INT                                NULL COMMENT '原来被分配的老师id',
    create_time      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
    status           SMALLINT                           NULL COMMENT '状态'
)
    COMMENT '老师课程关联表';

CREATE TABLE sys_menu
(
    id          INT AUTO_INCREMENT
        PRIMARY KEY,
    name        VARCHAR(64)                        NULL COMMENT '菜单名',
    parent_id   INT                                NULL COMMENT '上级菜单',
    status      SMALLINT DEFAULT 0                 NOT NULL,
    sort        INT      DEFAULT 0                 NOT NULL,
    path        VARCHAR(128)                       NULL COMMENT '页面地址',
    create_by   BIGINT                             NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP NULL
)
    COLLATE = utf8mb4_bin;

CREATE TABLE sys_role
(
    id     INT AUTO_INCREMENT
        PRIMARY KEY,
    name   VARCHAR(64)        NOT NULL,
    status SMALLINT DEFAULT 0 NOT NULL,
    type   SMALLINT           NULL COMMENT '角色类型'
)
    COMMENT '用户角色表' COLLATE = utf8mb4_bin;

CREATE TABLE sys_role_menu
(
    id      INT AUTO_INCREMENT
        PRIMARY KEY,
    role_id BIGINT NOT NULL COMMENT '角色id',
    menu_id BIGINT NOT NULL
)
    COLLATE = utf8mb4_bin;

CREATE TABLE sys_role_user
(
    id          INT AUTO_INCREMENT
        PRIMARY KEY,
    user_id     BIGINT                             NOT NULL COMMENT '用户id',
    role_id     BIGINT                             NOT NULL COMMENT '角色id',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    create_by   BIGINT                             NULL COMMENT '创建人'
)
    COMMENT '用户角色关联表' COLLATE = utf8mb4_bin;

CREATE TABLE sys_user
(
    id          INT AUTO_INCREMENT
        PRIMARY KEY,
    gender      SMALLINT                               NOT NULL COMMENT '性别',
    name        VARCHAR(11)                            NOT NULL COMMENT '用户名称',
    number      VARCHAR(64)                            NULL COMMENT '工号',
    password    VARCHAR(128) DEFAULT '000000'          NOT NULL,
    mobile      VARCHAR(11)                            NOT NULL COMMENT '手机号码',
    email       VARCHAR(64)                            NOT NULL,
    country     VARCHAR(64)                            NULL COMMENT '国家',
    province    VARCHAR(64)                            NULL COMMENT '省',
    city        VARCHAR(64)                            NULL COMMENT '市',
    district    VARCHAR(128)                           NULL COMMENT '区',
    address     VARCHAR(256)                           NULL COMMENT '详细地址',
    status      SMALLINT     DEFAULT 0                 NOT NULL COMMENT '状态',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP NOT NULL
)
    COLLATE = utf8mb4_bin;

CREATE TABLE cms_chat
(
    id          INT AUTO_INCREMENT
        PRIMARY KEY,
    sender_id   INT                                NULL,
    teacher_id  INT                                NULL,
    content     TEXT                               NULL,
    readed      INT                                NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP NULL
);
