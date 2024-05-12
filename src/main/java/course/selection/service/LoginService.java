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
	 * 根據帳號前兩碼到部門資料判斷使用者的權限,再根據權限去查對應的資料表,判斷帳號密碼是否正確
	 * @param param
	 * @return
	 */
	public Map<String, Object> login(Map<String, Object> param) {
		param.put("depId", String.valueOf(param.get("username")).substring(0, 2));
		Integer depType = userMapper.findPermission(String.valueOf(param.get("username")).substring(0, 2));
		
		param.put("depType", depType);
		return userMapper.findUser(param);
	}
}
