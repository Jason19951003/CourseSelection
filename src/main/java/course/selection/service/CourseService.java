package course.selection.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course.selection.dao.CourseMapper;

@Service
public class CourseService {
    @Autowired
    private CourseMapper courseMapper;

    public List<Map<String, Object>> findCourse(Map<String, Object> param) {
        return courseMapper.findCourse(param);
    }
}
