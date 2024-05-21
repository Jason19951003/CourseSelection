package course.selection.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import course.selection.model.ApiResponse;
import course.selection.service.CourseService;

@RestController
public class CourseController {
	
	@Autowired
	private CourseService courseService;

	@GetMapping("/findcourse")
	public ResponseEntity<ApiResponse<?>> findCourse(@RequestParam() Map<String, Object> param) {
		List<Map<String, Object>> result = courseService.findCourse(param);
		ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>(true, "查詢成功", result);
		return ResponseEntity.ok(response);
	}
}
