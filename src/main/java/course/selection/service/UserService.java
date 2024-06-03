package course.selection.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Integer insertUser(Map<String, Object> param, MultipartFile sticker) {
        String userId = String.valueOf(param.get("userId"));
        if (!"".equals(sticker.getOriginalFilename().trim())) {
            param.put("sticker", FileUtil.getStickerName(sticker, userId));
            FileUtil.uploadFile(sticker, userId);
        }
        
        return userMapper.insertUser(param);
    }

    public Integer updateUser(Map<String, Object> param, MultipartFile sticker) {
        String userId = String.valueOf(param.get("userId"));
        if (!"".equals(sticker.getOriginalFilename().trim())) {
            param.put("sticker", FileUtil.getStickerName(sticker, userId));
            FileUtil.uploadFile(sticker, userId);
        }
        
        return userMapper.updateUser(param);
    }

    public Integer deleteUser(String userId) {
        return userMapper.deleteUser(userId);
    }

}