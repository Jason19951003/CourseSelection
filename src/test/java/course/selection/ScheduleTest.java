package course.selection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import course.selection.dao.ScheduleMapper;

@SpringBootTest
public class ScheduleTest {

	@Autowired
	private ScheduleMapper scheduleMapper;
	
	// 排課程式
	@Test
	void testSchedule() {
		List<Map<String, Object>> classInfos = scheduleMapper.findAllClassInfo();
		List<Map<String, Object>> courseInfos = scheduleMapper.findAllCourseInfo();
		List<String> resultSQL = new ArrayList<>();
		int courseYear = 113;

		String weeks[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		String timeSlots[] = {"1", "3", "5", "7"};
		String locations[] = {"A101", "A102", "A103", "A104",
		                      "A201", "A202", "A203", "A204",
		                      "A301", "A302", "A303", "A304",
		                      "A401", "A402", "A403", "A404"};

		Set<String> assignSet = new HashSet<>();

		for (Map<String, Object> courseInfo : courseInfos) {
		    int courseRequired = Integer.parseInt(courseInfo.get("course_required").toString());

		    Stream<Map<String, Object>> filteredClassInfos = classInfos.stream()
		            .filter(map ->
		                    ("" + map.get("department_id")).equals(courseInfo.get("course_dep") + "") &&
		                            ("" + map.get("grade")).equals(courseInfo.get("course_grade") + "") &&
		                            (courseRequired == 1 ? !"選修".equals(map.get("class_name") + "") : "選修".equals(map.get("class_name") + "")));

		    System.out.println("課程: " + courseInfo);

		    filteredClassInfos.forEach(classInfo -> {
		        System.out.println("班級: " + classInfo);
		        StringBuffer sql = new StringBuffer();
		        sql.append("INSERT INTO course_schedule (course_index, course_year, course_semester, course_class_id, course_of_week, course_start, course_end, course_locate, course_content, teacher_id) VALUES (");
		        sql.append(courseInfo.get("course_index"));
		        sql.append(", ");
		        sql.append(courseYear);
		        sql.append(", ");
		        sql.append(new Random().nextInt(2) + 1);
		        sql.append(", ");
		        sql.append(classInfo.get("class_id"));
		        sql.append(", ");

		        while (true) {
		            String week = weeks[new Random().nextInt(5)];
		            String timeSlot = timeSlots[new Random().nextInt(4)];
		            String location = locations[new Random().nextInt(16)];
		            String classId = classInfo.get("class_id").toString();
		            String key = week + "-" + timeSlot + "-" + location;
		            String classKey = week + "-" + timeSlot + "-" + classId;

		            if (!assignSet.contains(key) && !assignSet.contains(classKey)) {
		                sql.append("'").append(week).append("'");
		                sql.append(", ");
		                sql.append(timeSlot);
		                sql.append(", ");
		                sql.append(Integer.parseInt(timeSlot) + 1);
		                sql.append(", ");
		                sql.append("'").append(location).append("'");
		                sql.append(", ");
		                assignSet.add(key);
		                assignSet.add(classKey);
		                break;
		            }
		        }
		        sql.append("'").append(courseInfo.get("course_name")).append("'");
		        sql.append(", ");
		        sql.append(courseInfo.get("teacher_id"));
		        sql.append(");");

		        resultSQL.add(sql.toString());
		        System.out.println(sql);
		    });

		    System.out.println("-----------------------");
		}
	}
}
