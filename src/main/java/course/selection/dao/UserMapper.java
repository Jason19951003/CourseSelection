package course.selection.dao;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    public List<Map<String, Object>> findUsers(Map<String, Object> param);

    public List<Map<String, Object>> findClassInfo(Map<String,Object> param);

    public Integer insertUser(Map<String,Object> param);

    public Integer updateUser(Map<String,Object> param);

    public Integer deleteUser(String userId);

    public Map<String, Object> findUserByEmail(String emaiil);

    public Integer updatePassword(Map<String,Object> param);

    public Integer updateCurrentStudent();

    public List<Map<String, Object>> checkGrade();
}
