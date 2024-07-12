package course.select.dao;

import java.util.List;
import java.util.Map;

import course.select.model.pojo.CourseScore;

public interface SelectMapper {

    public List<CourseScore> checkCourseStatus();

    public Integer insertScore(Map<String, Object> param);

    public Integer deleteScore(Map<String, Object> param);
}
