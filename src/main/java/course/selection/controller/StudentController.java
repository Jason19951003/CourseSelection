package course.selection.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import course.selection.model.ApiResponse;
import course.selection.service.StudentService;


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
    public ResponseEntity<ApiResponse<?>> insertStudent(@RequestParam Map<String, Object> param,
                                                        @RequestParam("sticker") MultipartFile sticker) {
        Integer rowCount = studentService.insertStudent(param, sticker);
        Boolean state = rowCount > 0;
        String message = state ? "新增成功" : "新增失敗";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "新增");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/updateStudent/{userId}")
    public ResponseEntity<ApiResponse<?>> updateStudent(@RequestParam Map<String, Object> param,
                                                        @RequestParam("sticker") MultipartFile sticker) {
        Integer rowCount = studentService.updateStudent(param, sticker);
        Boolean state = rowCount > 0;
        String message = state ? "修改成功" : "修改失敗";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "修改");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/deleteStudent/{userId}")
    public ResponseEntity<ApiResponse<?>> deleteStudent(@PathVariable("userId") String userId) {
        Integer rowCount = studentService.deleteStudent(userId);
        Boolean state = rowCount > 0;
        String message = state ? "刪除成功" : "刪除失敗";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "刪除");
        return ResponseEntity.ok(apiResponse);
    }
}
