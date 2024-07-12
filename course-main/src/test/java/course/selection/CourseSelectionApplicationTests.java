package course.selection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import course.selection.service.RedisService;

@SpringBootTest
class CourseSelectionApplicationTests {
	

	@Autowired
	private RedisService redisService;

	@Test
	void testRedis() {
		// 取得redis的資料
		System.out.println("name: " + redisService.get("name1"));
		redisService.save("message", "tingli");
	}
	
	@Test
	void contextLoads() {
	}

}
