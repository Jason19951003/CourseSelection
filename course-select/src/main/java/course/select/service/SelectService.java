package course.select.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course.select.dao.SelectMapper;
import course.select.model.pojo.CourseScore;

@Service
public class SelectService {

    @Autowired
    private SelectMapper selectMapper;


    public List<CourseScore> checkCourseStatus() {
        return selectMapper.checkCourseStatus();
    }

    public Integer insertScore(Map<String, Object> param) {
    	return 0;
    }

    public Integer deleteScore(Map<String, Object> param) {
    	return 0;
    }

}
