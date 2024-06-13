package course.selection.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course.selection.dao.SelectMapper;
import course.selection.model.pojo.CourseScore;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SelectService {

    @Autowired
    private SelectMapper selectMapper;

    private static List<CourseScore> status;

    @Autowired
    public void setStatus(List<CourseScore> status) {
        SelectService.status = status;
    }

    public List<CourseScore> checkCourseStatus() {
        return selectMapper.checkCourseStatus();
    }

    public Integer insertScore(Map<String, Object> param) {
        synchronized (status) {
            log.info("---------------------------------------------------------");
            log.info("before status: " + status.toString());
            String courseDep = String.valueOf(param.get("courseDep"));
            String courseId = String.valueOf(param.get("courseId"));

            for (CourseScore course : status) {
                Integer capacity = course.getCourseCapacity();
                if (capacity == 0) {
                    return 0;
                }
                if (course.getCourseDep().equals(courseDep) && course.getCourseId().equals(courseId)) {
                    selectMapper.insertScore(param);
                    course.setCourseCapacity(capacity - 1);
                    break;
                }
            }
        }
        log.info("after status: " + status.toString());
        log.info("---------------------------------------------------------");
        return 1;
    }

    public Integer deleteScore(Map<String, Object> param) {
        synchronized (status) {
            for (CourseScore course : status) {
                String courseDep = String.valueOf(param.get("depId"));
                String courseId = String.valueOf(param.get("courseId"));
                Integer capacity = course.getCourseCapacity();
                
                if (course.getCourseDep().equals(courseDep) && course.getCourseId().equals(courseId)) {
                    selectMapper.insertScore(param);
                    course.setCourseCapacity(capacity + 1);
                    break;
                }
            }
        }
        return 1;
    }

}
