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
import course.selection.service.RedisService;
import course.selection.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

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
    public ResponseEntity<ApiResponse<?>> findClassInfo(@RequestParam Map<String, Object> param) {
        List<Map<String, Object>> result = userService.findClassInfo(param);
        ApiResponse<List<Map<String, Object>>> apiResponse = new ApiResponse<>(true, "查詢成功", result);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/insertUser")
    public ResponseEntity<ApiResponse<?>> insertUser(@RequestParam Map<String, Object> param,
            @RequestParam("avatar") MultipartFile avatar) {
        Integer rowCount = userService.insertUser(param, avatar);
        Boolean state = rowCount > 0;
        String message = state ? "新增成功" : "新增失敗";
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "新增");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/checkUserFile")
    public ResponseEntity<ApiResponse<?>> checkUserFile(@RequestParam Map<String, Object> param,
            @RequestParam("userFile") MultipartFile file) {
        try {
        	Boolean state = userService.checkUserFile(param.get("year")+"", file);
            String message = state ? "資料正確" : "資料錯誤";
            ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "檢查資料");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<String> apiResponse = new ApiResponse<>(false, e.getMessage(), "檢查資料");
            return ResponseEntity.ok(apiResponse);
        }
    }
    
    @PostMapping("/insertUserFromExcel")
    public ResponseEntity<ApiResponse<?>> insertUserFromExcel(@RequestParam Map<String, Object> param,
            @RequestParam("userFile") MultipartFile file) {
        try {
            Integer rowCount = userService.insertUserFromExcel(param.get("year")+"", file);
            Boolean state = rowCount > 0;
            String message = state ? "匯入成功" : "匯入失敗";
            ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "匯入");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<String> apiResponse = new ApiResponse<>(false, e.getMessage(), "匯入");
            return ResponseEntity.ok(apiResponse);
        }
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<ApiResponse<?>> updateUser(@RequestParam Map<String, Object> param,
            @RequestParam("avatar") MultipartFile avatar) {
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

    @PutMapping("/updateCurrentStudent")
    public ResponseEntity<ApiResponse<?>> updateCurrentStudent() {
        try {
            // 檢查是否可以更新
            if (!redisService.get("updateStatus").equals("true")) {
                throw new RuntimeException("現在不能更新學生年級!");
            }
            Integer rowCount = userService.updateCurrentStudent();
            Boolean state = rowCount > 0;
            // 更新成功後，就不能再更新了
            if (state) {
                redisService.save("updateStatus", "false");
            }
            String message = state ? "更新成功" : "更新失敗";
            ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "更新");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<String> apiResponse = new ApiResponse<>(false, e.getMessage(), "更新");
            return ResponseEntity.ok(apiResponse);
        }
    }
}
