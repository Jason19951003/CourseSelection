INSERT INTO course_score (course_dep, course_id, course_year, course_semester, course_class_id, student_id, teacher_id)
SELECT
	b.course_dep,
	b.course_id,
	b.course_year,
	b.course_semester,
	b.course_class_id,
	a.user_id,
	b.teacher_id 
FROM
	user_info a
	LEFT JOIN course b ON a.class_id = b.course_class_id 
WHERE
	a.permission_id = 3