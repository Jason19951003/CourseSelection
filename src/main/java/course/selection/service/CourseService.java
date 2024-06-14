package course.selection.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import course.selection.dao.CourseMapper;
import course.selection.model.pojo.CourseScore;
import course.selection.util.CamelCaseUtil;

@Service
public class CourseService {
    @Autowired
    private CourseMapper courseMapper;


    public List<Map<String, Object>> findCourseInfo(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(courseMapper.findCourseInfo(param));
    }

    public List<Map<String, Object>> findCourse(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(courseMapper.findCourse(param));
    }

    public Integer insertCourseInfo(Map<String, Object> param) {
        return courseMapper.insertCourseInfo(param);
    }

    public List<Map<String, Object>> findDepartment() {
        return CamelCaseUtil.underlineToCamel(courseMapper.findDepartment());
    }

    public List<Map<String, Object>> findTeacher(String depId) {
        return CamelCaseUtil.underlineToCamel(courseMapper.findTeacher(depId));
    }

    public Integer deleteCourseInfo(Integer courseIndex) {
        return courseMapper.deleteCourseInfo(courseIndex);
    }

    public Integer updateCourseInfo(Map<String, Object> param) {
        return courseMapper.updateCourseInfo(param);
    }

    public Integer updateCourseOfferings(Map<String, Object> param) {
        return courseMapper.updateCourseOfferings(param);
    }

    public List<Map<String, Object>> findScore(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(courseMapper.findScore(param));
    }

    public List<Map<String, Object>> findStudentScore(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(courseMapper.findStudentScore(param));
    }

    public List<Map<String, Object>> findTeacherCourseById(String userId) {
        return CamelCaseUtil.underlineToCamel(courseMapper.findTeacherCourseById(userId));
    }
    
    @Transactional
    public Integer updateScore(List<Map<String, Object>> listMap) {
        return courseMapper.updateScore(listMap);
    }

    public List<Map<String, Object>> findSchedule(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(courseMapper.findSchedule(param));
    }

    public List<Map<String, Object>> findCourseYear(String userId) {
        return CamelCaseUtil.underlineToCamel(courseMapper.findCourseYear(userId));
    }

    public List<Map<String, Object>> findCourseOfferingInfo(Map<String, Object> param) {
        List<Map<String, Object>> result = CamelCaseUtil.underlineToCamel(courseMapper.findCourseOfferingInfo(param));
        
        for (Map<String, Object> map : result) {
            Optional<CourseScore> op =  SelectService.status.stream()
                                .filter(cs -> cs.getCourseDep().equals(map.get("courseDep")) &&
                                              cs.getCourseId().equals(map.get("courseId")))
                                .findFirst();
            if (op.isPresent()) {
                map.put("enrolledStudent", Integer.parseInt(map.get("courseCapacity")+"") - op.get().getCourseCapacity());
            }
        }
        return result;
    }
 
    public List<Map<String, Object>> findAllCourseYear() {
        return CamelCaseUtil.underlineToCamel(courseMapper.findAllCourseYear());
    }
}
