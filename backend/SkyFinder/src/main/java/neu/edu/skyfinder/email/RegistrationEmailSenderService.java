package neu.edu.skyfinder.email;

import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class RegistrationEmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String username, String name) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("skyfinder321@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("A new message from SkyFinder");
//            helper.setText("Hello " + name + ", your account with SkyFinder has been created successfully.");

            helper.setText(""
            		+ "<html>"
            			+"<body>"
            				+"<h1>Welcome to SkyFinder</h1>"
            				+"<p>Hello " + name + ","
            					+ "<br/>your account with skyfinder has been created successfully"
            				+ "</p>"
            			+ "</body>"
            		+ "</html>", true);
            // Add image as attachment
            FileSystemResource file = new FileSystemResource(new File("C:/Users/srini/OneDrive/Desktop/images/registration.jpg"));
            helper.addAttachment("registration.jpg", file);

            mailSender.send(message);
            System.out.println("Registration mail sent successfully....");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

	public void sendEmailDetails(String email, String username, String name) {
		try {
			sendEmail(email, username, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
