package course.select;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import course.select.service.RedisService;

@SpringBootTest
class CourseSelectApplicationTests {

	@Autowired
	private RedisService redisService;
	
	@Test
	void contextLoads() {
		System.out.println(redisService.get("updateStatus"));
	}

}
