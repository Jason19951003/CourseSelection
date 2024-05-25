package course.selection.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course.selection.dao.CourseMapper;
import course.selection.util.CamelCaseUtil;

@Service
public class CourseService {
    @Autowired
    private CourseMapper courseMapper;

    public List<Map<String, Object>> findCourse(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(courseMapper.findCourse(param));
    }

    public Integer insertCourse(Map<String, Object> param) {
        return courseMapper.insertCourse(param);
    }

    public List<Map<String, Object>> findDepartment() {
        return CamelCaseUtil.underlineToCamel(courseMapper.findDepartment());
    }

    public List<Map<String, Object>> findTeacher(String courseDep) {
        return CamelCaseUtil.underlineToCamel(courseMapper.findTeacher(courseDep));
    }

    public Integer deleteCourse(Map<String, Object> param) {
        return courseMapper.deleteCourse(param);
    }

    public Integer updateCourse(Map<String, Object> param) {
        return courseMapper.updateCourse(param);
    }
}
