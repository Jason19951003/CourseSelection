package course.selection.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/file")
public class FileController {

    @GetMapping("/img/{fileName}")
    public void getImage(@PathVariable("fileName") String fileName, HttpServletResponse res) {
        File imgFile = new File("src/main/resources/static/img", fileName);
        
        // 根據副檔名來決定Contentype
        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            res.setContentType("image/jpeg");
        } else if (fileName.toLowerCase().endsWith(".png")) {
            res.setContentType("image/png");
        } else {
            res.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }

        try (FileInputStream input = new FileInputStream(imgFile);
            OutputStream out = res.getOutputStream()) {
                byte[] buffer = new byte[(int)imgFile.length()];  // 使用文件長度來決定緩衝大小
                int bytesRead = input.read(buffer);
                out.write(buffer, 0, bytesRead);
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
