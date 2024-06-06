package course.selection.model.pojo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private String userId;
	private String userPassword;
	private Integer permissionId;
	private String userName;
}
