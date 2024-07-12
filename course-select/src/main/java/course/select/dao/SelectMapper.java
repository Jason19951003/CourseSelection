package course.select.dao;

import java.util.List;
import java.util.Map;

import course.select.model.pojo.CourseStatus;

public interface SelectMapper {

	public CourseStatus checkCourseStatus(Map<String, Object> param);
	
	public Integer updateEnrollment(Map<String, Object> param);
	
    public Integer insertScore(Map<String, Object> param);

    public Integer deleteScore(Map<String, Object> param);
    
    public List<Map<String, Object>> findCourseOfferingInfo(Map<String, Object> param);
}
