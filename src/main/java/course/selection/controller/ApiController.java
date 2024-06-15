package course.selection.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api")
public class ApiController {

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
