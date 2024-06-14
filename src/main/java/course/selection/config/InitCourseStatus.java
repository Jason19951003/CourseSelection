package course.selection.config;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import course.selection.dao.SelectMapper;
import course.selection.model.pojo.CourseScore;

@Configuration
public class InitCourseStatus {

    @Autowired
    private SelectMapper selectMapper;
    
    @Bean
    public List<CourseScore> getStatus() {
        List<Map<String, Object>> listMap = selectMapper.findCourseCapacity();
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
			}
		}
		System.out.println(status);
        return status;
    }
}