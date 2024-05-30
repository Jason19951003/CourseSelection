package course.selection.dao;

import java.util.List;
import java.util.Map;

public interface TeacherMapper {
    public List<Map<String, Object>> findTeachers(Map<String, Object> param);

    public List<Map<String, Object>> findClassInfo(Map<String,Object> courseDep);

    public Integer insertTeacher(Map<String,Object> param);

    public Integer updateTeacher(Map<String,Object> param);

    public Integer deleteTeacher(String userId);
}
