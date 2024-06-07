-- 清除現有課程表
TRUNCATE TABLE course;

-- 一年級，上學期
INSERT INTO course (course_dep, course_id, course_name, course_required, course_year, course_semester, course_class_id, course_of_week, course_start, course_end, course_locate, teacher_id, course_content) VALUES
-- 1年級甲班
('IM', '001', '程式設計導論', '1', 113, '1', 1, 'Monday', 1, 3, 'A101', 'TE113001', 'Introduction to Programming'),
('IM', '002', '計算機概論', '1', 113, '1', 1, 'Tuesday', 1, 3, 'A102', 'TE113002', 'Introduction to Computer Science'),
('IM', '003', '微積分', '1', 113, '1', 1, 'Wednesday', 1, 3, 'A103', 'TE113003', 'Calculus'),
('IM', '004', '英文', '1', 113, '1', 1, 'Thursday', 1, 3, 'A104', 'TE113004', 'English'),
-- 1年級乙班
('IM', '001', '程式設計導論', '1', 113, '1', 2, 'Monday', 3, 5, 'A101', 'TE113001', 'Introduction to Programming'),
('IM', '002', '計算機概論', '1', 113, '1', 2, 'Tuesday', 3, 5, 'A102', 'TE113002', 'Introduction to Computer Science'),
('IM', '003', '微積分', '1', 113, '1', 2, 'Wednesday', 3, 5, 'A103', 'TE113003', 'Calculus'),
('IM', '004', '英文', '1', 113, '1', 2, 'Thursday', 3, 5, 'A104', 'TE113004', 'English'),
-- 1年級丙班
('IM', '001', '程式設計導論', '1', 113, '1', 3, 'Monday', 5, 7, 'A101', 'TE113001', 'Introduction to Programming'),
('IM', '002', '計算機概論', '1', 113, '1', 3, 'Tuesday', 5, 7, 'A102', 'TE113002', 'Introduction to Computer Science'),
('IM', '003', '微積分', '1', 113, '1', 3, 'Wednesday', 5, 7, 'A103', 'TE113003', 'Calculus'),
('IM', '004', '英文', '1', 113, '1', 3, 'Thursday', 5, 7, 'A104', 'TE113004', 'English');

-- 一年級，下學期
INSERT INTO course (course_dep, course_id, course_name, course_required, course_year, course_semester, course_class_id, course_of_week, course_start, course_end, course_locate, teacher_id, course_content) VALUES
-- 1年級甲班
('IM', '005', '資料結構', '1', 113, '2', 1, 'Monday', 1, 3, 'A102', 'TE113005', 'Data Structures'),
('IM', '006', '演算法', '1', 113, '2', 1, 'Tuesday', 1, 3, 'A102', 'TE113006', 'Algorithms'),
('IM', '007', '作業系統', '1', 113, '2', 1, 'Wednesday', 1, 3, 'A103', 'TE113007', 'Operating Systems'),
('IM', '008', '數位邏輯設計', '1', 113, '2', 1, 'Thursday', 1, 3, 'A104', 'TE113008', 'Digital Logic Design'),
-- 1年級乙班
('IM', '005', '資料結構', '1', 113, '2', 2, 'Monday', 3, 5, 'A101', 'TE113005', 'Data Structures'),
('IM', '006', '演算法', '1', 113, '2', 2, 'Tuesday', 3, 5, 'A102', 'TE113006', 'Algorithms'),
('IM', '007', '作業系統', '1', 113, '2', 2, 'Wednesday', 3, 5, 'A103', 'TE113007', 'Operating Systems'),
('IM', '008', '數位邏輯設計', '1', 113, '2', 2, 'Thursday', 3, 5, 'A104', 'TE113008', 'Digital Logic Design'),
-- 1年級丙班
('IM', '005', '資料結構', '1', 113, '2', 3, 'Monday', 5, 7, 'A101', 'TE113005', 'Data Structures'),
('IM', '006', '演算法', '1', 113, '2', 3, 'Tuesday', 5, 7, 'A102', 'TE113006', 'Algorithms'),
('IM', '007', '作業系統', '1', 113, '2', 3, 'Wednesday', 5, 7, 'A103', 'TE113007', 'Operating Systems'),
('IM', '008', '數位邏輯設計', '1', 113, '2', 3, 'Thursday', 5, 7, 'A104', 'TE113008', 'Digital Logic Design');

-- 二年級，上學期
INSERT INTO course (course_dep, course_id, course_name, course_required, course_year, course_semester, course_class_id, course_of_week, course_start, course_end, course_locate, teacher_id, course_content) VALUES
-- 2年級甲班
('IM', '009', '資料庫系統', '1', 113, '1', 4, 'Monday', 1, 3, 'A201', 'TE113009', 'Database Systems'),
('IM', '010', '軟體工程', '1', 113, '1', 4, 'Tuesday', 1, 3, 'A202', 'TE113010', 'Software Engineering'),
('IM', '011', '計算機網路', '1', 113, '1', 4, 'Wednesday', 1, 3, 'A203', 'TE113011', 'Computer Networks'),
-- 2年級乙班
('IM', '009', '資料庫系統', '1', 113, '1', 5, 'Monday', 3, 5, 'A201', 'TE113009', 'Database Systems'),
('IM', '010', '軟體工程', '1', 113, '1', 5, 'Tuesday', 3, 5, 'A202', 'TE113010', 'Software Engineering'),
('IM', '011', '計算機網路', '1', 113, '1', 5, 'Wednesday', 3, 5, 'A203', 'TE113011', 'Computer Networks'),
-- 2年級丙班
('IM', '009', '資料庫系統', '1', 113, '1', 6, 'Monday', 5, 7, 'A201', 'TE113009', 'Database Systems'),
('IM', '010', '軟體工程', '1', 113, '1', 6, 'Tuesday', 5, 7, 'A202', 'TE113010', 'Software Engineering'),
('IM', '011', '計算機網路', '1', 113, '1', 6, 'Wednesday', 5, 7, 'A203', 'TE113011', 'Computer Networks');

-- 二年級，下學期
INSERT INTO course (course_dep, course_id, course_name, course_required, course_year, course_semester, course_class_id, course_of_week, course_start, course_end, course_locate, teacher_id, course_content) VALUES
-- 2年級甲班
('IM', '012', '人工智慧', '1', 113, '2', 4, 'Monday', 1, 3, 'A204', 'TE113012', 'Artificial Intelligence'),
('IM', '013', '資訊安全', '1', 113, '2', 4, 'Tuesday', 1, 3, 'A205', 'TE113010', 'Information Security'),
-- 2年級乙班
('IM', '012', '人工智慧', '1', 113, '2', 5, 'Monday', 3, 5, 'A204', 'TE113012', 'Artificial Intelligence'),
('IM', '013', '資訊安全', '1', 113, '2', 5, 'Tuesday', 3, 5, 'A205', 'TE113011', 'Information Security'),
-- 2年級丙班
('IM', '012', '人工智慧', '1', 113, '2', 6, 'Monday', 5, 7, 'A204', 'TE113012', 'Artificial Intelligence'),
('IM', '013', '資訊安全', '1', 113, '2', 6, 'Tuesday', 5, 7, 'A205', 'TE113011', 'Information Security');

-- 三年級，上學期
INSERT INTO course (course_dep, course_id, course_name, course_required, course_year, course_semester, course_class_id, course_of_week, course_start, course_end, course_locate, teacher_id, course_content) VALUES
-- 3年級甲班
('IM', '014', '數據分析', '1', 113, '1', 7, 'Monday', 1, 3, 'A301', 'TE113001', 'Data Analysis'),
('IM', '015', '專題製作', '1', 113, '1', 7, 'Tuesday', 1, 3, 'A302', 'TE113002', 'Project Production'),
('IM', '016', '專題討論', '1', 113, '1', 7, 'Wednesday', 1, 3, 'A303', 'TE113003', 'Project Discussion'),
-- 3年級乙班
('IM', '014', '數據分析', '1', 113, '1', 8, 'Monday', 3, 5, 'A301', 'TE113004', 'Data Analysis'),
('IM', '015', '專題製作', '1', 113, '1', 8, 'Tuesday', 3, 5, 'A302', 'TE113005', 'Project Production'),
('IM', '016', '專題討論', '1', 113, '1', 8, 'Wednesday', 3, 5, 'A303', 'TE113006', 'Project Discussion'),
-- 3年級丙班
('IM', '014', '數據分析', '1', 113, '1', 9, 'Monday', 5, 7, 'A301', 'TE113007', 'Data Analysis'),
('IM', '015', '專題製作', '1', 113, '1', 9, 'Tuesday', 5, 7, 'A302', 'TE113008', 'Project Production'),
('IM', '016', '專題討論', '1', 113, '1', 9, 'Wednesday', 5, 7, 'A303', 'TE113009', 'Project Discussion');

-- 三年級，下學期
INSERT INTO course (course_dep, course_id, course_name, course_required, course_year, course_semester, course_class_id, course_of_week, course_start, course_end, course_locate, teacher_id, course_content) VALUES
-- 3年級甲班
('IM', '015', '專題製作', '1', 113, '2', 7, 'Monday', 1, 3, 'A304', 'TE113010', 'Project Production'),
('IM', '016', '專題討論', '1', 113, '2', 7, 'Tuesday', 1, 3, 'A305', 'TE113011', 'Project Discussion'),
-- 3年級乙班
('IM', '015', '專題製作', '1', 113, '2', 8, 'Monday', 3, 5, 'A304', 'TE113012', 'Project Production'),
('IM', '016', '專題討論', '1', 113, '2', 8, 'Tuesday', 3, 5, 'A305', 'TE113001', 'Project Discussion'),
-- 3年級丙班
('IM', '015', '專題製作', '1', 113, '2', 9, 'Monday', 5, 7, 'A304', 'TE113002', 'Project Production'),
('IM', '016', '專題討論', '1', 113, '2', 9, 'Tuesday', 5, 7, 'A305', 'TE113003', 'Project Discussion');
