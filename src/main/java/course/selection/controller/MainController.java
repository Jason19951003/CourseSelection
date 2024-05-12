package course.selection.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	
	@PostMapping("/main")
	public String main(@RequestParam() Map<String, Object> param) {
		String result = "";
		int permissionId = Integer.parseInt(String.valueOf(param.get("permissionId")));
		if (permissionId == 1) {
			result = "root";
		}else if (permissionId == 2) {
			result = "teacher";
		} else {
			result = "student";
		}
		return result;
	}
}
