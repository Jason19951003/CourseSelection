package course.selection.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course.selection.model.ApiResponse;
import course.selection.service.RedisService;
import course.selection.service.UserService;
import course.selection.util.MailUtil;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RedisService redisService;

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

    @GetMapping("/passcode")
    public void getPassCode(HttpServletResponse res) {
        String passCode = String.format("%04d", new Random().nextInt(10000));
        String passcodeId = UUID.randomUUID().toString();
        res.setContentType("image/jpeg");
        res.setHeader("Passcode-Id", passcodeId);

        try {
            BufferedImage img = getPassCodeImage(passCode);
            OutputStream os = res.getOutputStream();
            ImageIO.write(img, "JPEG", os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/findUserByEmail/{email}")
    public ResponseEntity<ApiResponse<?>> findUserByEmail(@PathVariable("email") String email) {
        Map<String, Object> userMap = userService.findUserByEmail(email);
        if (userMap == null) {
            return ResponseEntity.ok(new ApiResponse<>(false, "沒有此使用者", "沒有資料"));
        } else {
            return ResponseEntity.ok(new ApiResponse<Map<String, Object>>(true, "查詢成功", userMap));
        }
    }

    // 發送OPT驗證碼
    @GetMapping("/sendPassCode/{email}")
    public ResponseEntity<ApiResponse<?>> sendPassCode(@PathVariable("email") String email) {
        // 產生OPT驗證碼
        SecureRandom sec = new SecureRandom();
        String OPT = String.format("%06d", sec.nextInt(100000));
        // 將驗證碼儲存到Redis，時效五分鐘
        redisService.saveWithExpire(email, OPT, 5, TimeUnit.MINUTES);
        // 發送Email
        boolean state = MailUtil.sendMail(email, OPT);
        String message = state ? "發送成功" : "發送失敗";
        // 回傳至前端
        ApiResponse<String> apiResponse = new ApiResponse<>(state, message, "發送驗證碼");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/checkPassCode")
    public ResponseEntity<ApiResponse<?>> checkPassCode(@RequestBody Map<String, String> map) {
        String passCode = redisService.get(map.get("email"));
        if (redisService.get(map.get("email")) != null) {
            if (passCode.equals(map.get("passcode"))) {
                return ResponseEntity.ok(new ApiResponse<>(true, "驗證成功", "驗證成功"));
            } else {
                return ResponseEntity.ok(new ApiResponse<>(false, "驗證碼錯誤", "驗證碼錯誤"));
            }
        } else {
            return ResponseEntity.ok(new ApiResponse<>(false, "驗證碼已過期，請重新申請。", "驗證失敗"));
        }
    }

    // 修改密碼
    @PutMapping("/updatePassword")
    public ResponseEntity<ApiResponse<?>> updatePassword(@RequestBody Map<String, Object> map) {
        Integer rowCount = userService.updatePassword(map);
        boolean state = rowCount > 0;
        String message = state ? "修改成功" : "修改失敗";
        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(state, message, map);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/downloadUserInfo")
    public ResponseEntity<InputStreamResource> downloadUserInfo() throws IOException {
        File file = new File("src/main/resources/static/file/user_info.xlsx");

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        FileInputStream fis = new FileInputStream(file);
        InputStreamResource resource = new InputStreamResource(fis);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


    // 產生四個數字的圖檔(BufferedImage)
	private BufferedImage getPassCodeImage(String passCode) {
		int w = 80;// 圖寬
		int h = 30;// 圖高
		
		// 建立圖像暫存區
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		// 建立畫布
		Graphics g = img.getGraphics();
		// 設定顏色
		g.setColor(Color.GRAY);
		// 塗滿背景
		g.fillRect(0, 0, w, h);
		// 繪製文字
		g.setColor(Color.BLUE);
		g.setFont(new Font("新細明體", Font.BOLD, 30));
		g.drawString(passCode, 10, 25);
		// 加入干擾線
		g.setColor(Color.YELLOW);
		Random random = new Random();
		
		for (int i = 0; i < 10; i++) {
			int x1 = random.nextInt(w);
			int y1 = random.nextInt(h);
			int x2 = random.nextInt(w);
			int y2 = random.nextInt(h);
			g.drawLine(x1, y1, x2, y2);
		}
		
		return img;
	}

}
