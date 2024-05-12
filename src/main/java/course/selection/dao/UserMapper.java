package course.selection.dao;

import java.util.Map;

public interface UserMapper {
	public Integer findPermission(String depId);
	
	public Map<String, Object> findUser(Map<String, Object> param);
}
