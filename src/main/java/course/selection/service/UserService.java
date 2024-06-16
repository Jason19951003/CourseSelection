package course.selection.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import course.selection.dao.UserMapper;
import course.selection.util.CamelCaseUtil;
import course.selection.util.FileUtil;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<Map<String, Object>> findUsers(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        return findUsers(map);
    }

    public List<Map<String, Object>> findUsers(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(userMapper.findUsers(param));
    }

    public List<Map<String, Object>> findClassInfo(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(userMapper.findClassInfo(param));
    }

    public Integer insertUser(Map<String, Object> param, MultipartFile avatar) {
        String userId = String.valueOf(param.get("userId"));
        if (!"".equals(avatar.getOriginalFilename().trim())) {
            param.put("avatar", FileUtil.getAvatarName(avatar, userId));
            FileUtil.uploadFile(avatar, userId);
        }
        
        return userMapper.insertUser(param);
    }

    public Integer updateUser(Map<String, Object> param, MultipartFile avatar) {
        String userId = String.valueOf(param.get("userId"));
        if (!"".equals(avatar.getOriginalFilename().trim())) {
            param.put("avatar", FileUtil.getAvatarName(avatar, userId));
            FileUtil.uploadFile(avatar, userId);
        }
        
        return userMapper.updateUser(param);
    }

    public Integer deleteUser(String userId) {
        return userMapper.deleteUser(userId);
    }

    public Map<String, Object> findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    public Integer updatePassword(Map<String, Object> param) {
        // 將密碼加密存進資料庫
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        param.put("password", encoder.encode(param.get("password")+""));
        return userMapper.updatePassword(param);
    }
}
