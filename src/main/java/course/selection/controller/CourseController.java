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

	@GetMapping("/findCourseInfo")
	public ResponseEntity<ApiResponse<?>> findCourseInfo(@RequestParam() Map<String, Object> param) {
		List<Map<String, Object>> result = courseService.findCourseInfo(param);
		ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>(true, "查詢成功", result);
		return ResponseEntity.ok(response);
	}

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

	@GetMapping("/findTeacher/{depId}")
	public ResponseEntity<ApiResponse<?>> findTeacher(@PathVariable("depId") String depId) {
		List<Map<String, Object>> result = courseService.findTeacher(depId);
		ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>(true, "查詢成功", result);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/insertCourseInfo")
	public ResponseEntity<ApiResponse<?>> insertCourseInfo(@RequestBody Map<String, Object> param) {
		try {
			Integer rowCount = courseService.insertCourseInfo(param);
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

	@PutMapping("/updateCourseInfo/{courseIndex}")
	public ResponseEntity<ApiResponse<?>> updateCourseInfo(@RequestBody Map<String, Object> param) {
		Integer rowCount = courseService.updateCourseInfo(param);
		boolean state = rowCount > 0;
		String message = state ? "修改成功" : "修改失敗";
		ApiResponse<String> result = new ApiResponse<>(state, message, "成功");
		return ResponseEntity.ok(result);
	}

	@PutMapping("/updateCourseOfferings/{courseIndex}")
	public ResponseEntity<ApiResponse<?>> updateCourseOfferings(@RequestBody Map<String, Object> param) {
		Integer rowCount = courseService.updateCourseOfferings(param);
		boolean state = rowCount > 0;
		String message = state ? "修改成功" : "修改失敗";
		ApiResponse<String> result = new ApiResponse<>(state, message, "成功");
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/deleteCourseInfo/{courseIndex}")
	public ResponseEntity<ApiResponse<?>> deleteCourseInfo(@PathVariable("courseIndex") Integer courseIndex) {
		Integer rowCount = courseService.deleteCourseInfo(courseIndex);
		boolean state = rowCount > 0;
		String message = state ? "刪除成功" : "刪除失敗";
		ApiResponse<String> result = new ApiResponse<>(state, message, "成功");
		return ResponseEntity.ok(result);
	}

	@GetMapping("/findScore")
	public ResponseEntity<ApiResponse<?>> findScore(@RequestParam() Map<String, Object> param) {
		List<Map<String, Object>> result = courseService.findScore(param);
		ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>(true, "查詢成功", result);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/findTeacherCourseById/{userId}")
	public ResponseEntity<ApiResponse<?>> findTeacherCourseById(@PathVariable("userId") String userId) {
		List<Map<String, Object>> result = courseService.findTeacherCourseById(userId);
		ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>(true, "查詢成功", result);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/updateScore")
	public ResponseEntity<ApiResponse<?>> updateScore(@RequestBody List<Map<String, Object>> listMap) {
		Integer rowCount = courseService.updateScore(listMap);
		boolean state = rowCount > 0;
		String message = state ? "修改成功" : "修改失敗";
		ApiResponse<String> result = new ApiResponse<>(state, message, "成功");
		return ResponseEntity.ok(result);
	}

	@GetMapping("/findSchedule")
	public ResponseEntity<ApiResponse<?>> findSchedule(@RequestParam Map<String, Object> param) {
		List<Map<String, Object>> result = courseService.findSchedule(param);
		ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>(true, "查詢成功", result);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/findCourseYear/{userId}")
	public ResponseEntity<ApiResponse<?>> findCourseYear(@PathVariable("userId") String userId) {
		List<Map<String, Object>> result = courseService.findCourseYear(userId);
		ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>(true, "查詢成功", result);
		return ResponseEntity.ok(response);
	}

}
