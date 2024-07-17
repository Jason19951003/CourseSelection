package course.auth.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import course.auth.dao.UserRequestMapper;
import course.auth.model.pojo.User;
import course.auth.service.RedisService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthFilter jwtAuthFilter;
	@Autowired
	private UserRequestMapper userMapper;
	@Autowired
	private RedisService redisService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf-> csrf.disable())
		.authorizeHttpRequests(
			(requests) -> requests
						.requestMatchers("/user/**")
						.permitAll()
						.requestMatchers("/api/**")
						.permitAll()
						.anyRequest()
						.authenticated()
		)
		.formLogin(login -> login.loginPage("http://127.0.0.1/index.html"))
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authenticationProvider(authenticationProvider())
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		//.httpBasic(withDefaults());
		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return (username -> {
			User user;
			try {
				// 查詢用戶訊息
				/*String cache = redisService.get(username);
				if (cache != null) {
					ObjectMapper objectMapper = new ObjectMapper();
		            Map<String, Object> resultMap = objectMapper.readValue(cache, new TypeReference<Map<String, Object>>() {});
		            user = objectMapper.readValue(resultMap.get("user")+"", new TypeReference<User>() {});	
				} else {
					user = userMapper.findUserByUserName(username);
				}*/
				user = userMapper.findUserByUserName(username);
				// 如果沒有User 就拋出異常
				if (user == null) {
					throw new RuntimeException("帳號或者密碼錯誤");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("帳號或者密碼錯誤");
			}
			return new SchoolUserDetails(user);
		});
	}
}
