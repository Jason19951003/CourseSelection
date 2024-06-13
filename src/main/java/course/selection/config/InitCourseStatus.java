package course.selection.config;

import java.util.List;
import java.util.Map;

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
            
        }
        return status;
    }
}
