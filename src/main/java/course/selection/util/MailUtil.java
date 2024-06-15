package course.selection.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
    public static void main(String[] args) {
        String to = "jason19951003@gmail.com"; // sender email
        String from = "jason19951003@gmail.com";       // receiver email
		String host = "smtp.gmail.com";
		int port = 587;
		final String username = "jason19951003@gmail.com";//smtp帳號
		final String password = "";//smtp密碼
		
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

            message.setSubject("測試HTML Email", "utf-8"); // subject line
            //寄送html格式的email
            message.setContent("<h1>This is a test</h1>", "text/html; charset=utf-8");	

            Transport.send(message);
            System.out.println("Email Sent successfully....");
        } catch (MessagingException mex){
            mex.printStackTrace(); 
        }
    }
}
