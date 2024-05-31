package course.selection.dao;

import java.util.List;
import java.util.Map;

public interface StudentMapper {
    public List<Map<String, Object>> findStudents(Map<String,Object> param);

    public List<Map<String, Object>> findClassInfo(Map<String,Object> depId);

    public Integer insertStudent(Map<String,Object> param);

    public Integer updateStudent(Map<String,Object> param);

    public Integer deleteStudent(String userId);
}
