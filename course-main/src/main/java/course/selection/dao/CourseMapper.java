package course.selection.dao;

import java.util.List;
import java.util.Map;

public interface CourseMapper {

    public List<Map<String, Object>> findCourseInfo(Map<String, Object> param);

    public List<Map<String, Object>> findCourse(Map<String, Object> param);

    public Integer insertCourseInfo(Map<String, Object> param);

    public List<Map<String, Object>> findDepartment();

    public List<Map<String, Object>> findTeacher(String depId);

    public Integer deleteCourseInfo(Integer courseIndex);

    public Integer updateCourseInfo(Map<String, Object> param);

    public Integer updateCourseOfferings(Map<String, Object> param);

    public List<Map<String, Object>> findScore(Map<String, Object> param);

    public List<Map<String, Object>> findStudentScore(Map<String, Object> param);

    public List<Map<String, Object>> findTeacherCourseById(String userId);

    public Integer updateScore(List<Map<String, Object>> listMap);

    public Integer updateScoreMap(Map<String, Object> param);

    public List<Map<String, Object>> findSchedule(Map<String, Object> param);

    public List<Map<String, Object>> findCourseYear(String userId);

    public List<Map<String, Object>> findCourseOfferingInfo(Map<String, Object> param);

    public List<Map<String, Object>> findAllCourseYear();
}
