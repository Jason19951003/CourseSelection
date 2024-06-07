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
    department_id char(2) not null,
    grade int not null,
    class_name varchar(255) not null,
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
    user_id varchar(255) primary key,
    user_password varchar(255) not null,
    permission_id tinyint not null,
    user_name varchar(255) not null,
    birth_date Date,
    sex ENUM('1','2'),
    email varchar(255),
    phone varchar(255),
    department_id char(2),
    class_id int,
    admission_date Date,
    sticker varchar(255),
    foreign key (permission_id) references account_permissions(permission_id),
    foreign key (department_id) references department(department_id),
    foreign key (class_id) references class_info(class_id)
);

insert into user_info (user_id, user_password, permission_id, user_name) values ('root', '$2a$10$opaMvYKj/O1TJ7.dZ5hvduNq.oo78VC35RTZAqJ6MZ/BpK96xulxi', 1, '管理員');

create table if not exists course (
    course_index int auto_increment primary key,
	course_dep char(2) not null,
	course_id char(3) not null,
    course_name varchar(255) not null,
    course_required ENUM('1','2') not null,
    course_year smallint not null,
    course_semester ENUM('1','2') not null,
    course_of_week ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday') NOT NULL,
    course_start tinyint not null,
    course_end tinyint not null,
    course_locate varchar(255) not null,
    teacher_id varchar(255) not null,
    course_content varchar(255),
    foreign key (course_dep) references department(department_id),
    foreign key (teacher_id) references user_info(user_id),
    CONSTRAINT unique_dep_id_year_semester UNIQUE(course_dep, course_id, course_year, course_semester)
);

create table if not exists course_grade (
	course_dep char(2) not null,
	course_id char(3) not null,
	course_year smallint not null,
    course_semester ENUM('1','2') not null,
    student_id VARCHAR(255) not null,
    teacher_id VARCHAR(255) not null,
    grade tinyint,
	foreign key (student_id) references user_info(user_id),
	foreign key (teacher_id) references user_info(user_id)
);

INSERT INTO course (course_dep, course_id, course_name, course_required, course_year, course_semester, course_of_week, course_start, course_end, course_locate, teacher_id, course_content) VALUES 
('IM', '101', '程式設計導論', '1', 1, '113', 'Monday', 5, 8, '101教室', 'TE113001', '基礎程式設計概念'),
('IM', '102', '計算機概論', '1', 1, '113', 'Tuesday', 1, 4, '102教室', 'TE113002', '計算機基礎概念'),
('IM', '103', '微積分', '1', 1, '113', 'Wednesday', 5, 8, '103教室', 'TE113003', '微積分基礎'),
('IM', '104', '英文', '1', 1, '113', 'Thursday', 5, 7, '104教室', 'TE113004', '英文閱讀與寫作'),

('IM', '201', '資料結構', '1', 2, '113', 'Monday', 7, 8, '201教室', 'TE113005', '資料結構的基本概念'),
('IM', '202', '演算法', '1', 2, '113', 'Tuesday', 3, 4, '202教室', 'TE113006', '基礎演算法'),
('IM', '203', '作業系統', '1', 2, '113', 'Wednesday', 1, 4, '203教室', 'TE113007', '作業系統原理'),
('IM', '204', '數位邏輯設計', '1', 2, '113', 'Thursday', 3, 4, '204教室', 'TE113008', '數位邏輯設計'),

('IM', '301', '資料庫系統', '1', 3, '113', 'Monday', 1, 4, '301教室', 'TE113009', '資料庫基礎'),
('IM', '302', '軟體工程', '1', 3, '113', 'Tuesday', 5, 8, '302教室', 'TE113010', '軟體開發方法'),
('IM', '303', '計算機網路', '1', 3, '113', 'Wednesday', 5, 8, '303教室', 'TE113011', '網路基本原理'),
('IM', '304', '人工智慧', '1', 3, '113', 'Thursday', 7, 8, '304教室', 'TE113012', '人工智慧基礎'),

('IM', '401', '專題製作', '1', 4, '113', 'Monday', 1, 2, '401教室', 'TE113001', '專題製作'),
('IM', '402', '專題討論', '1', 4, '113', 'Tuesday', 3, 4, '402教室', 'TE113002', '專題討論'),
('IM', '403', '資訊安全', '1', 4, '113', 'Wednesday', 5, 8, '403教室', 'TE113003', '資訊安全基礎'),
('IM', '404', '數據分析', '1', 4, '113', 'Thursday', 7, 8, '404教室', 'TE113004', '數據分析方法');
