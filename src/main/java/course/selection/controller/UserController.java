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
import course.selection.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/findUsers")
    public ResponseEntity<ApiResponse<?>> findUsers(@RequestParam Map<String, Object> param) {
        List<Map<String, Object>> result = userService.findUsers(param);
        ApiResponse<List<Map<String, Object>>> apiResponse = new ApiResponse<>(true, "查詢成功", result);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/findUser")
    public ResponseEntity<ApiResponse<?>> findUser(@RequestParam Map<String, Object> param) {
        List<Map<String, Object>> result = userService.findUsers(param);
        ApiResponse<List<Map<String, Object>>> apiResponse = new ApiResponse<>(true, "查詢成功", result);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/findClassInfo")
    public ResponseEntity<ApiResponse<?>> findClassInfo(@RequestParam Map<String,Object> param) {
        List<Map<String, Object>> result = userService.findClassInfo(param);
        ApiResponse<List<Map<String, Object>>> apiResponse = new ApiResponse<>(true, "查詢成功", result);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/insertUser")
    public ResponseEntity<ApiResponse<?>> insertUser(@RequestParam Map<String, Object> param, @RequestParam("avatar") MultipartFile avatar) {
        Integer rowCount = userService.insertUser(param, avatar);
        Boolean state = rowCount > 0;
        String message = state ? "新增成功" : "新增失敗";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "新增");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/insertUserFromExcel")
    public ResponseEntity<ApiResponse<?>> insertUserFromExcel(@RequestParam Map<String, Object> param, @RequestParam("userFile") MultipartFile file) {
        try {
            Integer rowCount = userService.insertUserFromExcel(file);
            Boolean state = rowCount > 0;
            String message = state ? "新增成功" : "新增失敗";
            ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "新增");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<String> apiResponse = new ApiResponse<>(false, e.getMessage(), "新增");
            return ResponseEntity.ok(apiResponse);
        }
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<ApiResponse<?>> updateUser(@RequestParam Map<String, Object> param, @RequestParam("avatar") MultipartFile avatar) {
    	Integer rowCount = userService.updateUser(param, avatar);
    	Boolean state = rowCount > 0;
        String message = state ? "修改成功" : "修改失敗";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "修改");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable("userId") String userId) {
        try {
        	Integer rowCount = userService.deleteUser(userId);
            Boolean state = rowCount > 0;
            String message = state ? "刪除成功" : "刪除失敗";
            ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "刪除");
            return ResponseEntity.ok(apiResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(new ApiResponse<>(false, "無法刪除該人員!", "刪除失敗"));
		}
    }
}
