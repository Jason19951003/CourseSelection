package course.selection.dao;

import java.util.List;
import java.util.Map;

import course.selection.model.pojo.CourseScore;

public interface SelectMapper {

    public List<CourseScore> checkCourseStatus();

    public List<Map<String,Object>> findCourseCapacity(Integer courseYear);

    public Integer insertScore(Map<String, Object> param);

    public Integer deleteScore(Map<String, Object> param);
}
