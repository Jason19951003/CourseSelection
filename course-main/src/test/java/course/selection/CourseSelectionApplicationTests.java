package course.selection;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import course.selection.dao.SelectMapper;
import course.selection.model.pojo.CourseScore;
import course.selection.service.RedisService;

@SpringBootTest
class CourseSelectionApplicationTests {
	
	@Autowired
	private SelectMapper selectMapper;

	@Autowired
	private RedisService redisService;

	@Test
	void testRedis() {
		// 取得redis的資料
		System.out.println("name: " + redisService.get("name1"));
		redisService.save("message", "tingli");
	}
	
	@Test
	void contextLoads() {
		List<Map<String, Object>> listMap = selectMapper.findCourseCapacity(LocalDate.now().getYear()-1911);
		List<CourseScore> status = selectMapper.checkCourseStatus();
		for (Map<String, Object> map : listMap) {
			Optional<CourseScore> result = status.stream()
					.filter(CourseScore -> CourseScore.getCourseDep().equals(map.get("course_dep"))
							&& CourseScore.getCourseId().equals(map.get("course_id")))
					.findFirst();
			// 處理找到的結果
			if (result.isPresent()) {
				CourseScore courseScore = result.get();
				courseScore.setCourseCapacity(
						courseScore.getCourseCapacity() - Integer.parseInt(map.get("capacity") + ""));
				System.out.println("Found course: " + courseScore);
			} else {
				System.out.println("Course not found");
			}
		}

		System.out.println(status);
	}

}
