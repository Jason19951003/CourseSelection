package course.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course.auth.model.ApiResponse;
import course.auth.service.RedisService;
import course.auth.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthenticationController {

	private final JwtUtil jwtUtil;

	@Autowired
	private RedisService redisService;

	@GetMapping("/test")
	public ResponseEntity<?> test() {
		try {
			String result= redisService.get("updateStatus");
			return ResponseEntity.ok(new ApiResponse<>(true, "", result));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ApiResponse<>(true, e.getMessage(), ""));
		}
	}
	
	/*
	 * 防止多開，如果有新的裝置登入，原本的裝置則會被踢下線 
	 * @param req
	 * @return
	 */
	@GetMapping("/singleSession")
	public ResponseEntity<?> singleSession(HttpServletRequest req) {
		// 取得 token
		String authHeader = req.getHeader("Authorization");
		final String userName;
		final String jwtToken;

		if (!(authHeader == null || !authHeader.startsWith("Bearer"))) {
			jwtToken = authHeader.substring(7);
			userName = jwtUtil.extractUsername(jwtToken);
			// 使用jwt token 的有效期限判斷登入的先後順序
			if (jwtUtil.isTokenExpired(jwtToken)) {
				return ResponseEntity.ok(new ApiResponse<>(false, "您的登入時間已超過15分鐘，請重新登入以繼續使用。", ""));
			} else if (jwtUtil.extractExpiration(jwtToken).before(jwtUtil.extractExpiration(redisService.get(userName)))) {
				return ResponseEntity.ok(new ApiResponse<>(false, "因為在另一個裝置進行了新的登入。如果這不是您的操作，請立即更改您的密碼並聯絡支援團隊。請重新登入以繼續使用服務。", ""));
			} else {
				return ResponseEntity.ok(new ApiResponse<>(true, "", ""));
			}
		}

		return ResponseEntity.ok(new ApiResponse<String>(false, "找不到token,驗證錯誤!", "錯誤"));
	}
}
