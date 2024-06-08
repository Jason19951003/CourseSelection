create database if not exists school;
-- use school;

drop table if exists course_grade;
drop table if exists course;
drop table if exists user_info;
drop table if exists class_info;
drop table if exists department;
drop table if exists account_permissions;

create table if not exists account_permissions (
	permission_id tinyint primary key,
    permission_name varchar(255) not null
);

insert into account_permissions (permission_id, permission_name) values (1, 'root');
insert into account_permissions (permission_id, permission_name) values (2, 'teacher');
insert into account_permissions (permission_id, permission_name) values (3, 'student');

create table if not exists department (
	department_id char(2) primary key,
    department_name varchar(255) not null
);

insert into department (department_id, department_name) values('IM', '資訊管理系');
insert into department (department_id, department_name) values('GE','通識');
insert into department (department_id, department_name) values('TE','老師');

create table if not exists class_info(
    class_id int AUTO_INCREMENT primary key,
    department_id char(2) not null COMMENT '科系',
    grade int not null COMMENT '年級',
    class_name varchar(255) not null COMMENT '班級',
    foreign key (department_id) references department(department_id)
);

INSERT INTO class_info (department_id, grade, class_name) VALUES ('IM', 1, '甲');
INSERT INTO class_info (department_id, grade, class_name) VALUES ('IM', 1, '乙');
INSERT INTO class_info (department_id, grade, class_name) VALUES ('IM', 1, '丙');

INSERT INTO class_info (department_id, grade, class_name) VALUES ('IM', 2, '甲');
INSERT INTO class_info (department_id, grade, class_name) VALUES ('IM', 2, '乙');
INSERT INTO class_info (department_id, grade, class_name) VALUES ('IM', 2, '丙');

INSERT INTO class_info (department_id, grade, class_name) VALUES ('IM', 3, '甲');
INSERT INTO class_info (department_id, grade, class_name) VALUES ('IM', 3, '乙');
INSERT INTO class_info (department_id, grade, class_name) VALUES ('IM', 3, '丙');

INSERT INTO class_info (department_id, grade, class_name) VALUES ('IM', 4, '甲');
INSERT INTO class_info (department_id, grade, class_name) VALUES ('IM', 4, '乙');
INSERT INTO class_info (department_id, grade, class_name) VALUES ('IM', 4, '丙');

create table if not exists user_info(
    user_id varchar(255) primary key COMMENT '帳號',
    user_password varchar(255) not null COMMENT '密碼',
    permission_id tinyint not null COMMENT '權限',
    user_name varchar(255) not null COMMENT '姓名',
    birth_date Date COMMENT '生日',
    sex ENUM('1','2') COMMENT '性別',
    email varchar(255) COMMENT '信箱',
    phone varchar(255) COMMENT '手機',
    department_id char(2) COMMENT '科系',
    class_id int COMMENT '班級',
    admission_date Date COMMENT '入學日期',
    sticker varchar(255) COMMENT '大頭貼',
    foreign key (permission_id) references account_permissions(permission_id),
    foreign key (department_id) references department(department_id),
    foreign key (class_id) references class_info(class_id)
);

insert into user_info (user_id, user_password, permission_id, user_name) values ('root', '$2a$10$opaMvYKj/O1TJ7.dZ5hvduNq.oo78VC35RTZAqJ6MZ/BpK96xulxi', 1, '管理員');

create table if not exists course (
    course_index int auto_increment primary key COMMENT '索引',
	course_dep char(2) not null COMMENT '科系',
	course_id char(3) not null COMMENT '課程代號',
    course_name varchar(255) not null COMMENT '課程名稱',
    course_required ENUM('1','2') not null COMMENT '選/必修',
    course_year smallint not null COMMENT '年分',
    course_semester ENUM('1','2') not null COMMENT '學期',
    course_class_id int COMMENT '課程班級',
    course_of_week ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday') NOT NULL COMMENT '星期',
    course_start tinyint not null,
    course_end tinyint not null,
    course_locate varchar(255) not null COMMENT '上課地點',
    teacher_id varchar(255) not null COMMENT '老師',
    course_content varchar(255) COMMENT '課程介紹',
    foreign key (course_dep) references department(department_id),
    foreign key (teacher_id) references user_info(user_id),
    foreign key (course_class_id) references class_info(class_id),
    CONSTRAINT unique_dep_id_year_semester UNIQUE(course_year, course_semester, course_of_week, course_start, course_end, course_locate)
);

create table if not exists course_grade (
	course_dep char(2) not null,
	course_id char(3) not null,
	course_year smallint not null,
    course_semester ENUM('1','2') not null,
    course_class_id int not null,
    student_id VARCHAR(255) not null,
    teacher_id VARCHAR(255) not null,
    grade tinyint,
	foreign key (student_id) references user_info(user_id),
	foreign key (teacher_id) references user_info(user_id)
);