package course.selection.dao;

import java.util.List;
import java.util.Map;

public interface TeacherMapper {
    public List<Map<String, Object>> findTeachers(Map<String, Object> param); 
}
