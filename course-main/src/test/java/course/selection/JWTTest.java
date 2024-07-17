package course.selection;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import course.selection.service.RedisService;
import course.selection.util.JwtUtil;

@SpringBootTest
public class JWTTest {
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Test
	public void test() {
		Map<String, Object> result = new HashMap<>();
		String token = jwtUtil.generateToken(new HashMap<>(), "root");

		result.put("userId", "root");
		result.put("userName", "root");
		result.put("token", token);
		result.put("permissionId", 1);
		
        // 將字符串轉回 Map
        try {
        	// 使用 ObjectMapper 将 Map 转换为 JSON 字符串
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(result);

    		redisService.save("root", jsonString);
    		
    		String user = redisService.get("root");
    		
    		System.out.println(user);
            Map<String, Object> resultMap = objectMapper.readValue(user, new TypeReference<Map<String, Object>>() {});

            // 驗證結果
            System.out.println(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

	}

}
