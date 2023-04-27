package neu.edu.skyfinder.email;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import neu.edu.skyfinder.entity.FlightBooking;
import neu.edu.skyfinder.entity.User;

@Service
public class FlightCancelEmailSenderService {
	@Autowired
    private JavaMailSender mailSender;
	
	public void sendEmail(String toEmail, String username,String name, FlightBooking flightBooking) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("skyfinder321@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Flight Ticket Successfully Cancelled - SkyFinder");
//            helper.setText("Hello " + name + ", your account with SkyFinder has been created successfully.");

            helper.setText(""
            		+ "<html>"
            			+"<body>"
            				+"<h1>Hello "+ name + "</h1>"
            				+"<p>"
            					+ "<br/>Your have successfully cancelled your flight journey."
            					+"<br/>"
            					+ "<br/>Your refund will be credited to your bank account in 2-3 business days."
            					+"<br/>"
            					+"<br/> Flight Number: "+ flightBooking.getFlightNumber()
            					+"<br/> Origin: "+ flightBooking.getOrigin()
            					+"<br/> Destination: "+ flightBooking.getDestination()
            					+"<br/> Departure Time: "+ flightBooking.getDepartureTime()
            					+"<br/> Arrival Time: "+ flightBooking.getArrivalTime()
            					+"<br/> Duration: "+ flightBooking.getDuration()
            					+"<br/> Price: CAD "+ flightBooking.getPrice()
            				+ "</p>"
            			+ "</body>"
            		+ "</html>", true);
            // Add image as attachment
            FileSystemResource file = new FileSystemResource(new File("C:/Users/srini/OneDrive/Desktop/images/registration.jpg"));
            helper.addAttachment("registration.jpg", file);

            mailSender.send(message);
            System.out.println("Flight booking email sent successfully....");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

	public void sendEmailDetails(User user, FlightBooking flightBooking) {
		
		
		String email = user.getEmail();
		String name = user.getName();
		String username = user.getUsername();
		
		try {
			sendEmail(email, username,name, flightBooking);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FlightCancelEmailSenderService - Couldn't send email");
		}
		
	}

}
