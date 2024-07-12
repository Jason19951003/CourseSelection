package course.select.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import course.select.dao.SelectMapper;
import course.select.model.pojo.CourseStatus;
import course.select.util.CamelCaseUtil;

@Service
public class SelectService {

    @Autowired
    private SelectMapper selectMapper;

    @Transactional
    public synchronized Integer insertScore(Map<String, Object> param) {
    	param.put("status", "add");
    	CourseStatus courseStatus = selectMapper.checkCourseStatus(param);
    	
    	if (!courseStatus.isEnrollmentFull()) {
    		if (selectMapper.insertScore(param) > 0 && selectMapper.updateEnrollment(param) > 0) {
    			return 1;
    		}
    		throw new RuntimeException("加選課程發生錯誤!");
    	}
    	throw new RuntimeException("該課程人數已滿!");
    }

    @Transactional
    public synchronized Integer deleteScore(Map<String, Object> param) {
    	param.put("status", "delete");
    	if (selectMapper.updateEnrollment(param) > 0 && selectMapper.deleteScore(param) > 0) {
    		return 1;
    	}
    	throw new RuntimeException("退選課程發生錯誤!");
    }
    
    public List<Map<String, Object>> findCourseOfferingInfo(Map<String, Object> param) {
        return CamelCaseUtil.underlineToCamel(selectMapper.findCourseOfferingInfo(param));
    }


}
