package course.selection.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course.selection.config.SchoolUserDetails;
import course.selection.model.ApiResponse;
import course.selection.util.JwtUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final UserDetailsService userDetailsService;

	private final JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody Map<String, String> map) {
		String userName = map.get("username");
		String password = map.get("password");
		
		final SchoolUserDetails loginUser;
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			loginUser = (SchoolUserDetails) userDetailsService.loadUserByUsername(userName);
			if (loginUser != null) {
				ApiResponse<String> response = new ApiResponse<>(true, "成功", jwtUtil.generateToken(loginUser));
				return ResponseEntity.status(200).body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(e.getMessage());
		}
		return ResponseEntity.status(400).body("Some error occured");
	}
}
