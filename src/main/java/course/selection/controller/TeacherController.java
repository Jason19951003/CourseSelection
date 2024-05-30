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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import course.selection.model.ApiResponse;
import course.selection.service.TeacherService;


@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacheService;

    @GetMapping("/findTeachers")
    public ResponseEntity<ApiResponse<?>> findTeachers(@RequestParam Map<String, Object> param) {
        List<Map<String, Object>> result = teacheService.findTeachers(param);
        ApiResponse<List<Map<String, Object>>> apiResponse = new ApiResponse<>(true, "查詢成功", result);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/findTeacher/{userId}")
    public ResponseEntity<ApiResponse<?>> findTeacher(@PathVariable("userId") String userId) {
        List<Map<String, Object>> result = teacheService.findTeachers(userId);
        ApiResponse<List<Map<String, Object>>> apiResponse = new ApiResponse<>(true, "查詢成功", result);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/findClassInfo")
    public ResponseEntity<ApiResponse<?>> findClassInfo(@RequestParam Map<String,Object> param) {
        List<Map<String, Object>> result = teacheService.findClassInfo(param);
        ApiResponse<List<Map<String, Object>>> apiResponse = new ApiResponse<>(true, "查詢成功", result);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/insertTeacher")
    public ResponseEntity<ApiResponse<?>> insertTeacher(@RequestParam Map<String, Object> param,
                                                        @RequestParam("sticker") MultipartFile sticker) {
        Integer rowCount = teacheService.insertTeacher(param, sticker);
        Boolean state = rowCount > 0;
        String message = state ? "新增成功" : "新增失敗";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "新增");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/updateTeacher/{userId}")
    public ResponseEntity<ApiResponse<?>> updateTeacher(@RequestParam Map<String, Object> param,
                                                        @RequestParam("sticker") MultipartFile sticker) {
        Integer rowCount = teacheService.updateTeacher(param, sticker);
        Boolean state = rowCount > 0;
        String message = state ? "修改成功" : "修改失敗";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "修改");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/deleteTeacher/{userId}")
    public ResponseEntity<ApiResponse<?>> deleteTeacher(@PathVariable("userId") String userId) {
        Integer rowCount = teacheService.deleteTeacher(userId);
        Boolean state = rowCount > 0;
        String message = state ? "刪除成功" : "刪除失敗";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "刪除");
        return ResponseEntity.ok(apiResponse);
    }
}
