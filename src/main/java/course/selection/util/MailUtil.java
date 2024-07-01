package course.selection.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
    public static boolean sendMail (String email, String OTP) {
        String to = email;
        String from = "jason19951003@gmail.com";
		String host = "smtp.gmail.com";
		int port = 587;
		final String username = "jason19951003@gmail.com";//smtp帳號
		final String password = "qjmb zxwa gten zahg";//smtp密碼
		
		Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session); // email message
            message.setFrom(new InternetAddress(from)); // setting header fields
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("選課系統忘記密碼驗證碼", "utf-8"); // subject line
            //寄送html格式的email
            message.setContent("<!DOCTYPE html>\n"
            		+ "<html lang=\"zh-Hant\">\n"
            		+ "<head>\n"
            		+ "    <meta charset=\"UTF-8\">\n"
            		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
            		+ "    <title>OTP 驗證碼</title>\n"
            		+ "    <style>\n"
            		+ "        body {\n"
            		+ "            font-family: Arial, sans-serif;\n"
            		+ "            background-color: #f4f4f4;\n"
            		+ "            margin: 0;\n"
            		+ "            padding: 0;\n"
            		+ "            color: #333;\n"
            		+ "        }\n"
            		+ "        .container {\n"
            		+ "            width: 100%;\n"
            		+ "            max-width: 600px;\n"
            		+ "            margin: 50px auto;\n"
            		+ "            background-color: #fff;\n"
            		+ "            border-radius: 8px;\n"
            		+ "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
            		+ "            overflow: hidden;\n"
            		+ "        }\n"
            		+ "        .header {\n"
            		+ "            background-color: #3498db;\n"
            		+ "            color: #fff;\n"
            		+ "            padding: 20px;\n"
            		+ "            text-align: center;\n"
            		+ "        }\n"
            		+ "        .content {\n"
            		+ "            padding: 20px;\n"
            		+ "        }\n"
            		+ "        .otp-code {\n"
            		+ "            font-size: 24px;\n"
            		+ "            color: #e74c3c;\n"
            		+ "            font-weight: bold;\n"
            		+ "            text-align: center;\n"
            		+ "            margin: 20px 0;\n"
            		+ "        }\n"
            		+ "        .footer {\n"
            		+ "            background-color: #f4f4f4;\n"
            		+ "            color: #777;\n"
            		+ "            padding: 20px;\n"
            		+ "            text-align: center;\n"
            		+ "            font-size: 12px;\n"
            		+ "        }\n"
            		+ "    </style>\n"
            		+ "</head>\n"
            		+ "<body>\n"
            		+ "    <div class=\"container\">\n"
            		+ "        <div class=\"header\">\n"
            		+ "            <h2>忘記密碼驗證碼</h2>\n"
            		+ "        </div>\n"
            		+ "        <div class=\"content\">\n"
            		+ "            <p>親愛的學生，</p>\n"
            		+ "            <p>您正在使用選課系統的忘記密碼功能。請在驗證欄位中輸入以下驗證碼以重置您的密碼：</p>\n"
            		+ "            <div class=\"otp-code\">" + OTP +"</div>\n"
            		+ "            <p>該驗證碼有效期為五分鐘。請勿將驗證碼告訴他人。</p>\n"
            		+ "            <p>如果您未請求此驗證碼，請忽略此郵件。</p>\n"
            		+ "            <p>祝您一切順利！</p>\n"
            		+ "        </div>\n"
            		+ "    </div>\n"
            		+ "</body>\n"
            		+ "</html>\n"
            		+ "", "text/html; charset=utf-8");

            Transport.send(message);
            return true;
        } catch (MessagingException mex){
            mex.printStackTrace();
            return false;
        }
    }
}
