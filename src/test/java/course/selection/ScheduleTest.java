package course.selection;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.ArgumentMatchers.matches;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import course.selection.dao.ScheduleMapper;

@SpringBootTest
public class ScheduleTest {

	@Autowired
	private ScheduleMapper scheduleMapper;
	
	@Test
	void testSchedule() {
		List<Map<String, Object>> classInfos = scheduleMapper.findAllClassInfo();
		List<Map<String, Object>> courseInfos = scheduleMapper.findAllCourseInfo();
		// List<Map<String, Object>> userInfo = scheduleMapper.findAllCurrentStudent();
		List<String> resultSQL = new ArrayList<>();
		int courseYear = 113;
		
		String weeks[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		String timeSlots[] = {"1", "3", "5", "7"};
		String locatinos[] = {"A101", "A102", "A103", "A104",
							  "A201", "A202", "A203", "A204",
							  "A301", "A302", "A303", "A304",
							  "A401", "A402", "A403", "A404"};
		
		
		
		for (Map<String, Object> courseInfo : courseInfos) {
		    int courseRequired = Integer.parseInt(courseInfo.get("course_required").toString());

			Stream<Map<String, Object>> filteredClassInfos = classInfos.stream()
											.filter(map -> 
												(""+map.get("department_id")).equals(courseInfo.get("course_dep")+"") &&
												(""+map.get("grade")).equals(courseInfo.get("course_grade")+"") &&
												(courseRequired == 1 ? !"選修".equals(map.get("class_name")+"") : "選修".equals(map.get("class_name")+"")));
			
			System.out.println("課程: " + courseInfo);
			filteredClassInfos.forEach(classInfo -> System.out.println(classInfo));
			System.out.println("-----------------------");
		}
			
		
	}
}
