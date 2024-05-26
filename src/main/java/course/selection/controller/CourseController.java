package course.selection.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import course.selection.model.ApiResponse;
import course.selection.service.CourseService;


@RestController
@RequestMapping("/course")
public class CourseController {
	
	@Autowired
	private CourseService courseService;

	@GetMapping("/findCourse")
	public ResponseEntity<ApiResponse<?>> findCourse(@RequestParam() Map<String, Object> param) {
		List<Map<String, Object>> result = courseService.findCourse(param);
		ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>(true, "查詢成功", result);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/findDepartment")
	public ResponseEntity<ApiResponse<?>> findDepartment() {
		List<Map<String, Object>> result = courseService.findDepartment();
		ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>(true, "查詢成功", result);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/findTeacher/{courseDep}")
	public ResponseEntity<ApiResponse<?>> findTeacher(@PathVariable("courseDep") String courseDep) {
		List<Map<String, Object>> result = courseService.findTeacher(courseDep);
		ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>(true, "查詢成功", result);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/insertCourse")
	public ResponseEntity<ApiResponse<?>> insertCourse(@RequestBody Map<String, Object> param) {
		try {
			Integer rowCount = courseService.insertCourse(param);
			boolean state = rowCount > 0;
			String message = state ? "新增成功" : "新增失敗";
			ApiResponse<String> result = new ApiResponse<>(state, message, "成功");
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			String message = "新增失敗:";
			if (e.getMessage().contains("Duplicate")) {
				message += "該年份已經有此課程了";
			} else {
				message += e.getMessage();
			}
			ApiResponse<String> result = new ApiResponse<>(false, message, "失敗");
			return ResponseEntity.ok(result);
		}
	}

	@PutMapping("/updateCourse/{courseIndex}")
	public ResponseEntity<ApiResponse<?>> updateCourse(@RequestBody Map<String, Object> param) {
		Integer rowCount = courseService.updateCourse(param);
		boolean state = rowCount > 0;
		String message = state ? "修改成功" : "修改失敗";
		ApiResponse<String> result = new ApiResponse<>(state, message, "成功");
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/deleteCourse/{courseIndex}")
	public ResponseEntity<ApiResponse<?>> deleteCourse(@PathVariable("courseIndex") Integer courseIndex) {
		Integer rowCount = courseService.deleteCourse(courseIndex);
		boolean state = rowCount > 0;
		String message = state ? "刪除成功" : "刪除失敗";
		ApiResponse<String> result = new ApiResponse<>(state, message, "成功");
		return ResponseEntity.ok(result);
	}

}
