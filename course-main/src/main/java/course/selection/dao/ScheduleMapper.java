package course.selection.dao;

import java.util.List;
import java.util.Map;

public interface ScheduleMapper {

    public List<Map<String, Object>> findAllCourseInfo(List<Integer> coruseIndex);

    public List<Map<String, Object>> findAllClassInfo();
    
    public List<Map<String, Object>> findAllCurrentStudent();

    public Integer insertAllCourseOfferings(Map<String, Object> param);

    public Integer countStudentNum(String classId);

    public List<Map<String, Object>> checkCourseDuplicate(Integer courseYear);
    
    public Map<String, Object> checkCourseYear(Map<String, Object> param);

    public Integer importRequiredCourse(Map<String, Object> param);
}
