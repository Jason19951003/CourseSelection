create database if not exists school;
use school;

drop table if exists course_grade;
drop table if exists course;
drop table if exists student_info;
drop table if exists teacher_info;
drop table if exists root_info;
drop table if exists department;
drop table if exists account_permissions;

create table if not exists account_permissions (
	permission_id tinyint primary key,
    permission_name varchar(255) not null
);

insert into account_permissions (permission_id, permission_name) values (1, 'root');
insert into account_permissions (permission_id, permission_name) values (2, 'teacher');
insert into account_permissions (permission_id, permission_name) values (3, 'student');

create table if not exists root_info (
	root_account varchar(255) not null primary key,
    root_password varchar(255) not null,
    permission_id tinyint not null,
    root_name varchar(255) not null,
    foreign key (permission_id) references account_permissions(permission_id)
);

insert into root_info(root_account, root_password, permission_id, root_name) values('root', 'root', 1, '管理員');

create table if not exists department (
	department_id char(2) primary key,
    department_name varchar(255) not null,
    department_type tinyint not null,
    foreign key (department_type) references account_permissions(permission_id)
);

insert into department (department_id, department_name, department_type) values('IM', '資訊管理系',3);
insert into department (department_id, department_name, department_type) values('GE','通識', 3);
insert into department (department_id, department_name, department_type) values('TE','老師', 2);

create table if not exists student_info (
	student_id char(8) primary key,
    permission_id tinyint not null,
    student_password varchar(255) not null,
    student_name varchar(10) not null,
    birthdate Date not null,
    department_id char(2) not null, 
    class tinyint not null,
    admission_time int not null,
    sticker varchar(255),
    foreign key (permission_id) references account_permissions(permission_id),
    foreign key (department_id) references department(department_id)
);

insert into student_info (student_id, permission_id, student_password, student_name, birthdate, department_id, class, admission_time) values('IM103089', 3, 'IM103089', '游智翔', '1995-10-03', 'IM', 2, 103);

create table if not exists teacher_info (
	teacher_id char(8) primary key,
    permission_id tinyint not null,
    teacher_password varchar(255) not null,
    teacher_name varchar(10) not null,
    birthdate Date not null,
    department_id char(2) not null,
    class varchar(10),
    admission_date Date not null,
    sticker varchar(255),
    foreign key (permission_id) references account_permissions(permission_id),
    foreign key (department_id) references department(department_id)
);

insert into teacher_info (teacher_id, permission_id, teacher_password, teacher_name, birthdate, department_id, class, admission_date) values ('TE100001', 2, 'TE100001', '方仁威', '1966-06-06', 'IM', '103-1', '2000-01-01');

create table if not exists course (
	course_dep char(2) not null,
	course_id char(3) not null,    
    course_name varchar(255) not null,
    course_required ENUM('1','2') not null,
    course_year smallint not null,
    course_semester ENUM('1','2') not null,
    course_of_week ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday') NOT NULL,
    course_start tinyint not null,
    course_end tinyint not null,
    teacher_id char(8) not null,
    course_content varchar(255),
    foreign key (course_dep) references department(department_id),
    foreign key (teacher_id) references teacher_info(teacher_id),
    primary key (course_dep, course_id),
    CONSTRAINT unique_course_id_and_course_dep UNIQUE(course_id, course_dep)
);

insert into course (course_dep, course_id, course_name, course_required, course_year, course_semester, course_of_week, course_start, course_end, teacher_id, course_content) values ('IM', '001', '程式設計', '1', 107, '1', 'Monday', 1, 4, 'TE100001', 'Java物件導向程式設計');

create table if not exists course_grade (
	course_dep char(2) not null,
	course_id char(3) not null,
	course_year smallint not null,
    course_semester ENUM('1','2') not null,
    student_id char(8) not null,
    teacher_id char(8) not null,
    grade tinyint,
    primary key (course_dep, course_id),
	foreign key (course_dep) references department(department_id),
	foreign key (course_id) references course(course_id),
    CONSTRAINT unique_course_id_and_course_dep UNIQUE(course_id, course_dep)
);

insert into course_grade (course_dep, course_id, course_year, course_semester, student_id, teacher_id, grade) values ('IM', '001', 107, '1', 'IM103089', 'TE100001', 80);