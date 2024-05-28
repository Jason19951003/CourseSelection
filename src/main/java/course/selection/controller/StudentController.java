package course.selection.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course.selection.model.ApiResponse;
import course.selection.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/findStudents")
    public ResponseEntity<ApiResponse<?>> findStudents(@RequestParam Map<String, Object> param) {
        List<Map<String, Object>> result = studentService.findStudents(param);
        ApiResponse<List<Map<String, Object>>> apiResponse = new ApiResponse<>(true, "查詢成功", result);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/findStudents/{userId}")
    public ResponseEntity<ApiResponse<?>> findStudent(@PathVariable("userId") String userId) {
        List<Map<String, Object>> result = studentService.findStudents(userId);
        ApiResponse<List<Map<String, Object>>> apiResponse = new ApiResponse<>(true, "查詢成功", result);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/findClassInfo")
    public ResponseEntity<ApiResponse<?>> findClassInfo(@RequestParam Map<String,Object> param) {
        List<Map<String, Object>> result = studentService.findClassInfo(param);
        ApiResponse<List<Map<String, Object>>> apiResponse = new ApiResponse<>(true, "查詢成功", result);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/insertStudent")
    public ResponseEntity<ApiResponse<?>> insertStudent(@RequestBody Map<String, Object> param) {
        Integer rowCount = studentService.insertStudent(param);
        Boolean state = rowCount > 0;
        String message = state ? "新增成功" : "新增失敗";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "新增");
        return ResponseEntity.ok(apiResponse);
    }
}
