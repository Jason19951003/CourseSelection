package course.select.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course.select.model.ApiResponse;
import course.select.model.pojo.CourseScore;
import course.select.service.SelectService;

@RestController
@RequestMapping("/select")
public class SelectController {

    @Autowired
    private SelectService selectService;

    @GetMapping("/checkStatus")
    public ResponseEntity<ApiResponse<?>> checkStatus() {
    	List<CourseScore> result = selectService.checkCourseStatus();
        ApiResponse<List<CourseScore>> apiResponse = new ApiResponse<>(true, "成功" , result);
        return ResponseEntity.ok(apiResponse);
    }
    
    @PostMapping("/insertCourse")
    public ResponseEntity<ApiResponse<?>> insertScore(@RequestBody Map<String, Object> param) {
        Integer rowCount = selectService.insertScore(param);
        boolean state = rowCount > 0;
        String message = state ? "加選成功" : "課程已滿";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message , "選課");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/deleteScore")
    public ResponseEntity<ApiResponse<?>> deleteScore(@RequestBody Map<String, Object> param) {
        Integer rowCount = selectService.deleteScore(param);
        boolean state = rowCount > 0;
        String message = state ? "退選成功" : "刪除失敗";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message , "選課");
        return ResponseEntity.ok(apiResponse);
    }
    
}
