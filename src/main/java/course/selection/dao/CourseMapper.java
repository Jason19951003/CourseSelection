package course.selection.dao;

import java.util.List;
import java.util.Map;

public interface CourseMapper {
    public List<Map<String, Object>> findCourse(Map<String, Object> param);

    public Integer insertCourse(Map<String, Object> param);

    public List<Map<String, Object>> findDepartment();

    public List<Map<String, Object>> findTeacher(String courseDep);

    public Integer deleteCourse(Map<String, Object> param);

    public Integer updateCourse(Map<String, Object> param);
}
