package course.selection.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import course.selection.dao.TeacherMapper;
import course.selection.util.CamelCaseUtil;
import course.selection.util.FileUtil;

@Service
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    public List<Map<String, Object>> findTeachers(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        return findTeachers(map);
    }

    public List<Map<String, Object>> findTeachers(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(teacherMapper.findTeachers(param));
    }

    public List<Map<String, Object>> findClassInfo(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(teacherMapper.findClassInfo(param));
    }

        public Integer insertTeacher(Map<String, Object> param, MultipartFile sticker) {
        String userId = String.valueOf(param.get("userId"));
        if (!"".equals(sticker.getOriginalFilename().trim())) {
            param.put("sticker", FileUtil.getStickerName(sticker, userId));
            FileUtil.uploadFile(sticker, userId);
        }
        
        return teacherMapper.insertTeacher(param);
    }

    public Integer updateTeacher(Map<String, Object> param, MultipartFile sticker) {
        String userId = String.valueOf(param.get("userId"));
        if (!"".equals(sticker.getOriginalFilename().trim())) {
            param.put("sticker", FileUtil.getStickerName(sticker, userId));
            FileUtil.uploadFile(sticker, userId);
        }
        
        return teacherMapper.updateTeacher(param);
    }

    public Integer deleteTeacher(String userId) {
        return teacherMapper.deleteTeacher(userId);
    }

}
