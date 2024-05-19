package course.selection.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course.selection.dao.UserMapper;

@Service
public class LoginService {
	
	@Autowired
	private UserMapper userMapper;
	/**
	 * 將root,學生,老師資料表整合成一個所以不需要再判斷使用者權限可以直接查詢
	 * @param param
	 * @return
	 */
	public Map<String, Object> login(Map<String, Object> param) {
		return userMapper.findUser(param);
	}
}
