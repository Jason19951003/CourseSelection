package course.selection.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import course.selection.dao.StudentMapper;
import course.selection.util.CamelCaseUtil;
import course.selection.util.FileUtil;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public List<Map<String, Object>> findStudents(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        return findStudents(map);
    }

    public List<Map<String, Object>> findStudents(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(studentMapper.findStudents(param));
    }

    public List<Map<String, Object>> findClassInfo(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(studentMapper.findClassInfo(param));
    }

    public Integer insertStudent(Map<String, Object> param, MultipartFile sticker) {
        String userId = String.valueOf(param.get("userId"));
        try {
            if (!"".equals(sticker.getOriginalFilename().trim())) {
                param.put("sticker", FileUtil.getStickerName(sticker, userId));
                FileUtil.uploadFile(sticker, userId);
            }
        } catch(IOException e) {
            e.printStackTrace();
            return 0;
        }
        
        return studentMapper.insertStudent(param);
    }

    public Integer updateStudent(Map<String, Object> param, MultipartFile sticker) {
        String userId = String.valueOf(param.get("userId"));
        try {
            if (!"".equals(sticker.getOriginalFilename().trim())) {
                param.put("sticker", FileUtil.getStickerName(sticker, userId));
                FileUtil.uploadFile(sticker, userId);
            }
        } catch(IOException e) {
            e.printStackTrace();
            return 0;
        }
        
        return studentMapper.updateStudent(param);
    }

    public Integer deleteStudent(String userId) {
        return studentMapper.deleteStudent(userId);
    }
}
