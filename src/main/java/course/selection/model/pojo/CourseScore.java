package course.selection.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseScore {
    private String courseDep;
    private String courseId;
    private String courseName;
    private Integer courseCapacity;
}
