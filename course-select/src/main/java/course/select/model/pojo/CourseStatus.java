package course.select.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseStatus {
    private Integer courseCapacity;
    private Integer enrollment;
    
    // 檢查選課狀態，如果課程人數和選課人數一樣則不可選
    public boolean isEnrollmentFull() {
    	return courseCapacity.equals(enrollment);
    }
}
