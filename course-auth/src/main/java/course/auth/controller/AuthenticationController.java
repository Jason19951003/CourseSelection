package course.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import course.auth.config.SchoolUserDetails;
import course.auth.model.ApiResponse;
import course.auth.model.pojo.User;
import course.auth.service.RedisService;
import course.auth.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final UserDetailsService userDetailsService;

	private final JwtUtil jwtUtil;

	@Autowired
	private RedisService redisService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody Map<String, String> map) {
		String userName = map.get("username");
		String password = map.get("password");
        
		final SchoolUserDetails loginUser;
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			loginUser = (SchoolUserDetails) userDetailsService.loadUserByUsername(userName);
			if (loginUser != null) {
				Map<String, Object> result = new HashMap<>();
				String token = jwtUtil.generateToken(loginUser);
				ObjectMapper objectMapper = new ObjectMapper();
				
				result.put("userId", loginUser.getUser().getUserId());
				result.put("userName", loginUser.getUser().getUserName());
				result.put("token", token);
				result.put("permissionId", loginUser.getUser().getPermissionId());
				result.put("user", objectMapper.writeValueAsString(loginUser.getUser()));
				
				String jsonString = objectMapper.writeValueAsString(result);
				redisService.save(loginUser.getUser().getUserId(), jsonString);
				switch (loginUser.getPermission()) {
					case ADMIN:
						result.put("page", "./root.html");
						break;
					case STUDENT:
						result.put("page", "./student.html");
						break;
					case TEACHER:
						result.put("page", "./teacher.html");
						break;
					default:
						break;
				}
				ApiResponse<Map<String, Object>> response = new ApiResponse<>(true, "成功", result);
				return ResponseEntity.status(200).body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(e.getMessage());
		}
		return ResponseEntity.status(400).body("Some error occured");
	}

	/*
	 * 防止多開，如果有新的裝置登入，原本的裝置則會被踢下線 
	 * @param req
	 * @return
	 */
	@GetMapping("/singleSession")
	public ResponseEntity<?> singleSession(HttpServletRequest req) throws JsonMappingException, JsonProcessingException {
		// 取得 token
		String authHeader = req.getHeader("Authorization");
		final String userName;
		final String jwtToken;

		if (!(authHeader == null || !authHeader.startsWith("Bearer"))) {
			jwtToken = authHeader.substring(7);
			userName = jwtUtil.extractUsername(jwtToken);
			
			ObjectMapper objectMapper = new ObjectMapper();
			String user = redisService.get(userName);
			
            Map<String, Object> resultMap = objectMapper.readValue(user, new TypeReference<Map<String, Object>>() {});
			// 使用jwt token 的有效期限判斷登入的先後順序
			if (jwtUtil.isTokenExpired(jwtToken)) {
				return ResponseEntity.ok(new ApiResponse<>(false, "您的登入時間已超過15分鐘，請重新登入以繼續使用。", ""));
			} else if (jwtUtil.extractExpiration(jwtToken).before(jwtUtil.extractExpiration(resultMap.get("token")+""))) {
				return ResponseEntity.ok(new ApiResponse<>(false, "因為在另一個裝置進行了新的登入。如果這不是您的操作，請立即更改您的密碼並聯絡支援團隊。請重新登入以繼續使用服務。", ""));
			} else {
				return ResponseEntity.ok(new ApiResponse<>(true, "", ""));
			}
		}

		return ResponseEntity.ok(new ApiResponse<String>(false, "找不到token,驗證錯誤!", "錯誤"));
	}
	
	/*
	 * 防止多開，如果有新的裝置登入，原本的裝置則會被踢下線 
	 * @param req
	 * @return
	 */
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody User user) {
		// 移除token
		redisService.delete(user.getUserId());
		return ResponseEntity.ok(new ApiResponse<String>(true, "登出成功", "登出"));
	}
}
