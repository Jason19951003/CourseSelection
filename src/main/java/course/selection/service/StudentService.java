package course.selection.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course.selection.dao.StudentMapper;
import course.selection.util.CamelCaseUtil;

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

    public List<Map<String, Object>> findClassInfo(String courseDep) {
        return CamelCaseUtil.underlineToCamel(studentMapper.findClassInfo(courseDep));
    }

    public Integer insertStudent(Map<String, Object> param) {
        return studentMapper.insertStudent(param);
    }
}
