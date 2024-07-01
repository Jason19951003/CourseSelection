package course.selection.config;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import course.selection.dao.SelectMapper;
import course.selection.model.pojo.CourseScore;

@Configuration
public class InitCourseStatus {

    @Autowired
    private SelectMapper selectMapper;
    
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public List<CourseScore> getStatus() {
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
			}
		}
		System.out.println(status);
        return status;
    }
}
