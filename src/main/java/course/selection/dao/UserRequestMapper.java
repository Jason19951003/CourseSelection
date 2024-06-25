package course.selection.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import course.selection.model.pojo.User;

@Mapper
public interface UserRequestMapper {
	
	@Select("select user_id, user_password, permission_id, user_name from user_info where user_id = #{userId}")
	public User findUserByUserName(String userId);
}
