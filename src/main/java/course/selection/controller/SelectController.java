package course.selection.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course.selection.model.ApiResponse;
import course.selection.service.SelectService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/select")
public class SelectController {

    @Autowired
    private SelectService selectService;

    @PostMapping("/insertCourse")
    public ResponseEntity<ApiResponse<?>> insertScore(@RequestBody Map<String, Object> param) {
        Integer rowCount = selectService.insertScore(param);
        boolean state = rowCount > 0;
        String message = state ? "加選成功" : "課程已滿";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message , "選課");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/deleteCourse")
    public ResponseEntity<ApiResponse<?>> deleteCourse(@RequestBody Map<String, Object> param) {
        Integer rowCount = selectService.insertScore(param);
        boolean state = rowCount > 0;
        String message = state ? "退選成功" : "刪除失敗";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message , "選課");
        return ResponseEntity.ok(apiResponse);
    }
    
}
