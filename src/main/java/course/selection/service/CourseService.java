package course.selection.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Map<String, Object>> findTeacher(String depId) {
        return CamelCaseUtil.underlineToCamel(courseMapper.findTeacher(depId));
    }

    public Integer deleteCourse(Integer courseIndex) {
        return courseMapper.deleteCourse(courseIndex);
    }

    public Integer updateCourse(Map<String, Object> param) {
        return courseMapper.updateCourse(param);
    }

    public List<Map<String, Object>> findScore(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(courseMapper.findScore(param));
    }

    public List<Map<String, Object>> findTeacherCourseById(String userId) {
        return CamelCaseUtil.underlineToCamel(courseMapper.findTeacherCourseById(userId));
    }
    
    public Integer updateScore(List<Map<String, Object>> listMap) {
        int status = 0;
        for (Map<String, Object> map : listMap) {
            status = courseMapper.updateScore(map);
            if (!(status > 0)) throw new RuntimeException("修改成績失敗");
        }
        return 1;
    }
}
