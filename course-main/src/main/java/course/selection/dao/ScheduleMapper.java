package course.selection.dao;

import java.util.List;
import java.util.Map;

public interface ScheduleMapper {

    public List<Map<String, Object>> findAllCourseInfo();

    public List<Map<String, Object>> findAllClassInfo();
    
    public List<Map<String, Object>> findAllCurrentStudent();

    public Integer insertAllCourseOfferings(Map<String, Object> param);

    public Integer checkCourseYear(Integer courseYear);

    public Integer importRequiredCourse(Map<String, Object> param);
}
