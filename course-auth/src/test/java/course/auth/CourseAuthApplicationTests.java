package course.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import course.auth.service.RedisService;

@SpringBootTest
class CourseAuthApplicationTests {

	@Autowired
	private RedisService redisService;
	
	@Test
	void contextLoads() {
		System.out.println(redisService.get("updateStatus"));
	}

}
