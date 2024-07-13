package course.selection;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import course.selection.service.ScheduleService;

@SpringBootTest
public class ScheduleImport {

	@Autowired
	private ScheduleService scheduleService;
	
	@Test
	void testImport() {
		System.out.println(scheduleService.insertAllCourseOfferings(111, new ArrayList<Integer>()));
	}
}
