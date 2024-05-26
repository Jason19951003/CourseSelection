package course.selection.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course.selection.dao.TeacherMapper;
import course.selection.util.CamelCaseUtil;

@Service
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    public List<Map<String, Object>> findTeachers(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(teacherMapper.findTeachers(param));
    }
}
