package course.selection.dao;

import java.util.List;
import java.util.Map;

public interface CourseMapper {
    public List<Map<String, Object>> findCourse(Map<String, Object> param);

    public Integer insertCourse(Map<String, Object> param);

    public List<Map<String, Object>> findDepartment();

    public List<Map<String, Object>> findTeacher(String depId);

    public Integer deleteCourse(Integer courseIndex);

    public Integer updateCourse(Map<String, Object> param);

    public List<Map<String, Object>> findScore(Map<String, Object> param);

    public List<Map<String, Object>> findTeacherCourseById(String userId);

    public Integer updateScore(Map<String, Object> param);
}
