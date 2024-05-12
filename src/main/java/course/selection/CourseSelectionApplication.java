package course.selection;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class CourseSelectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseSelectionApplication.class, args);
	}

}
