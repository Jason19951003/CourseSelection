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
import course.selection.util.CamelCaseUtil;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

	public Boolean checkCourseYear(Map<String, Object> param) {
		return scheduleMapper.checkCourseYear(param) == null;
	}

    @Transactional
    public Boolean insertAllCourseOfferings(Integer courseYear, List<Integer> coruseIndex) {
        Map<String, Object> param = new HashMap<>();

        List<Map<String, Object>> classInfos = CamelCaseUtil.underlineToCamel(scheduleMapper.findAllClassInfo());
		// 改只匯入前端有勾選的課程
		List<Map<String, Object>> courseInfos = CamelCaseUtil.underlineToCamel(scheduleMapper.findAllCourseInfo(coruseIndex));

        String weeks[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		String timeSlots[] = {"1", "3", "5", "7"};
		String locations[] = {"A101", "A102", "A103", "A104",
		                      "A201", "A202", "A203", "A204",
		                      "A301", "A302", "A303", "A304",
		                      "A401", "A402", "A403", "A404"};
		// 因為現在改為一次只能匯入一個科系的課程，所以要判斷之前的上課地點和新匯入的有沒有衝突
		List<Map<String, Object>> courseLocate = CamelCaseUtil.underlineToCamel(scheduleMapper.checkCourseDuplicate(courseYear));
		Set<String> assignSet = new HashSet<>();
		courseLocate.forEach(map-> {
			String week = String.valueOf(map.get("courseOfWeek"));
			String timeSlot = String.valueOf(map.get("courseStart"));
			String location = String.valueOf(map.get("courseLocate"));
			String classId = String.valueOf(map.get("courseClassId"));
            // 課程上課地點和時間不能重複
            String key = week + "-" + timeSlot + "-" + location;
            // 要再加上同一個班級不能在同一個時間上課
            String classKey = week + "-" + timeSlot + "-" + classId;
            assignSet.add(key);
            assignSet.add(classKey);
		});
		
        param.put("courseYear", courseYear);

        for (Map<String, Object> courseInfo : courseInfos) {
		    int courseRequired = Integer.parseInt(courseInfo.get("courseRequired").toString());

		    // 查出所有要匯入的課程後，如果是必修課就去class_info資料表查班級資料表，把班級裡所有的學生加入課程
		    Stream<Map<String, Object>> filteredClassInfos = classInfos.stream()
		            .filter(map ->
		                    ("" + map.get("departmentId")).equals(courseInfo.get("courseDep") + "") &&
		                            ("" + map.get("grade")).equals(courseInfo.get("courseGrade") + "") &&
		                            // 移除class_info選修的班級
		                            (courseRequired == 1 ? !"選修".equals(map.get("className") + "") : "選修".equals(map.get("className") + "")));

			param.put("courseIndex", courseInfo.get("courseIndex"));
			param.put("courseSemester", new Random().nextInt(2) + 1);
					
		    filteredClassInfos.forEach(classInfo -> {
                param.put("classId", classInfo.get("classId"));

		        while (true) {
		            String week = weeks[new Random().nextInt(5)];
		            String timeSlot = timeSlots[new Random().nextInt(4)];
		            String location = locations[new Random().nextInt(16)];
		            String classId = classInfo.get("classId").toString();
		            // 課程上課地點和時間不能重複
		            String key = week + "-" + timeSlot + "-" + location;
		            // 要再加上同一個班級不能在同一個時間上課
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
		        param.put("courseContent", courseInfo.get("courseName"));
	            param.put("userId", courseInfo.get("teacherId"));
				// 匯入必修課程時，加上該班級的人數
				param.put("enrollment", scheduleMapper.countStudentNum(param.get("classId")+""));

	            int rowCount = scheduleMapper.insertAllCourseOfferings(param);
	            
	            if (!(rowCount > 0)) {
	                throw new RuntimeException("匯入課程發生錯誤");
	            }
		    });
		}
        // 如果是Duplicate代表之前已經匯入過，可以跳過這個錯誤，其他錯誤要rollback
        try {
        	if (!(scheduleMapper.importRequiredCourse(param) > 0))
    			throw new RuntimeException("匯入課程時，發生錯誤");
		} catch (Exception e) {
			e.printStackTrace();
			if (!e.getMessage().contains("Duplicate")) {
				throw new RuntimeException(e);
			}
		}
        return true;
    }
}
