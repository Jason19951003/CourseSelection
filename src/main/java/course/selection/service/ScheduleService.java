package course.selection.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import course.selection.dao.ScheduleMapper;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Transactional
    public Boolean insertAllCourseOfferings(Integer courseYear) {
        Map<String, Object> param = new HashMap<>();

        List<Map<String, Object>> classInfos = scheduleMapper.findAllClassInfo();
		List<Map<String, Object>> courseInfos = scheduleMapper.findAllCourseInfo();

        String weeks[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		String timeSlots[] = {"1", "3", "5", "7"};
		String locations[] = {"A101", "A102", "A103", "A104",
		                      "A201", "A202", "A203", "A204",
		                      "A301", "A302", "A303", "A304",
		                      "A401", "A402", "A403", "A404"};

		Set<String> assignSet = new HashSet<>();

        param.put("courseYear", courseYear);

        for (Map<String, Object> courseInfo : courseInfos) {
		    int courseRequired = Integer.parseInt(courseInfo.get("course_required").toString());

		    Stream<Map<String, Object>> filteredClassInfos = classInfos.stream()
		            .filter(map ->
		                    ("" + map.get("department_id")).equals(courseInfo.get("course_dep") + "") &&
		                            ("" + map.get("grade")).equals(courseInfo.get("course_grade") + "") &&
		                            (courseRequired == 1 ? !"選修".equals(map.get("class_name") + "") : "選修".equals(map.get("class_name") + "")));

		    filteredClassInfos.forEach(classInfo -> {
                param.put("courseIndex", courseInfo.get("course_index"));
                param.put("courseSemester", new Random().nextInt(2) + 1);
                param.put("classId", classInfo.get("class_id"));

		        while (true) {
		            String week = weeks[new Random().nextInt(5)];
		            String timeSlot = timeSlots[new Random().nextInt(4)];
		            String location = locations[new Random().nextInt(16)];
		            String classId = classInfo.get("class_id").toString();
		            String key = week + "-" + timeSlot + "-" + location;
		            String classKey = week + "-" + timeSlot + "-" + classId;

		            if (!assignSet.contains(key) && !assignSet.contains(classKey)) {
                        param.put("courseOfWeek", week);
                        param.put("courseStart", timeSlot);
                        param.put("courseEnd", Integer.parseInt(timeSlot) + 1);
                        param.put("courseLocate", location);
		                assignSet.add(key);
		                assignSet.add(classKey);
		                break;
		            }
		        }
		        param.put("courseContent", courseInfo.get("course_name"));
	            param.put("userId", courseInfo.get("teacher_id"));

			    System.out.println("課程->" + param);
	            int rowCount = scheduleMapper.insertAllCourseOfferings(param);
	            
	            if (!(rowCount > 0)) {
	                throw new RuntimeException("匯入課程發生錯誤");
	            } else {
	            	System.out.println("新增成功");
	            }
		    });
		}
        return true;
    }
}
