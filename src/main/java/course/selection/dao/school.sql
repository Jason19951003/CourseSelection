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

insert into class_info (department_id, grade, class_name) values ('IM', 1, '甲');
insert into class_info (department_id, grade, class_name) values ('IM', 2, '甲');
insert into class_info (department_id, grade, class_name) values ('IM', 3, '甲');
insert into class_info (department_id, grade, class_name) values ('IM', 4, '甲');

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

insert into user_info (user_id, user_password, permission_id, user_name) values ('root', 'root', 1, '管理員');
insert into user_info (user_id, user_password, permission_id, user_name, birth_date, sex, email, phone, department_id, class_id, admission_date) values ('IM103001', 'IM103001', 3, '王小明', '1995-10-03', '1', 'abc123@gmai.com', '0912345678', 'IM', 1, '2014-09-01');
insert into user_info (user_id, user_password, permission_id, user_name, birth_date, sex, email, phone, department_id, class_id, admission_date) values ('IM103002', 'IM103002', 3, '黃小華', '1995-10-04', '1', 'abc123@gmai.com', '0912345678', 'IM', 1, '2014-09-01');
insert into user_info (user_id, user_password, permission_id, user_name, birth_date, sex, email, phone, department_id, class_id, admission_date) values ('TE100001', 'TE100001', 2, '方仁威', '1964-08-03', '1', 'abc123@gmai.com', '0912345678', 'IM', 1, '2008-09-01');
insert into user_info (user_id, user_password, permission_id, user_name, birth_date, sex, email, phone, department_id, class_id, admission_date) values ('TE100002', 'TE100002', 2, '林國滑', '1978-05-23', '1', 'abc123@gmai.com', '0912345678', 'GE', 1, '2018-09-01');


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

insert into course (course_dep, course_id, course_name, course_required, course_year, course_semester, course_of_week, course_start, course_end, course_locate, teacher_id, course_content) values ('IM', '001', '程式設計', '1', 107, '1', 'Monday', 1, 4, '101教室' ,'TE100001', 'Java物件導向程式設計');

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

insert into course_grade (course_dep, course_id, course_year, course_semester, student_id, teacher_id, grade) values ('IM', '001', 107, '1', 'IM103001', 'TE100001', 80);
insert into course_grade (course_dep, course_id, course_year, course_semester, student_id, teacher_id, grade) values ('IM', '001', 107, '1', 'IM103002', 'TE100001', 80);