package course.selection.dao;

import java.util.List;
import java.util.Map;

public interface StudentMapper {
    public List<Map<String, Object>> findStudents(Map<String,Object> param);

    public List<Map<String, Object>> findClassInfo(String courseDep);

    public Integer insertStudent(Map<String,Object> param);
}
