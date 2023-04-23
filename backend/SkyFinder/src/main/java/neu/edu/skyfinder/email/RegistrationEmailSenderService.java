package neu.edu.skyfinder.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class RegistrationEmailSenderService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String toEmail, String username, String name) {
		SimpleMailMessage message = new SimpleMailMessage();
		String subject = "A new message from SkyFinder";
		String body = "Hello "+name+", account with skyfinder has been created successfully";
		message.setFrom("skyfinder.srinredd@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		
		try {
			mailSender.send(message);
			System.out.println("Registration mail sent successfully....");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
