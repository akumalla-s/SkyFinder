package neu.edu.skyfinder.email;

import java.io.File;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import neu.edu.skyfinder.entity.FlightInformation;
import neu.edu.skyfinder.entity.User;

@Service
public class FlightBookingEmailSenderService {

	@Autowired
    private JavaMailSender mailSender;
	
	public void sendEmail(String toEmail, String username,String name, FlightInformation flightInformation) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("skyfinder321@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Flight Booking Successfull - SkyFinder");
//            helper.setText("Hello " + name + ", your account with SkyFinder has been created successfully.");

            helper.setText(""
            		+ "<html>"
            			+"<body>"
            				+"<h1>Hello "+ name + "</h1>"
            				+"<p>"
            					+ "<br/>You have successfully booked your flight."
            					+"<br/>"
            					+"<br/> Flight Number: "+ flightInformation.getFlightNumber()
            					+"<br/> Origin: "+ flightInformation.getOrigin()
            					+"<br/> Destination: "+ flightInformation.getDestination()
            					+"<br/> Departure Time: "+ flightInformation.getDepartureTime()
            					+"<br/> Arrival Time: "+ flightInformation.getArrivalTime()
            					+"<br/> Duration: "+ flightInformation.getDuration()
            					+"<br/> Price: CAD "+ flightInformation.getPrice()
            					+"<br/> Status of Flight: "+ flightInformation.getStatus()
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

	public void sendEmailDetails(Optional<User> user, FlightInformation flightInformation) {
		User _user = user.get();
		
		String email = _user.getEmail();
		String name = _user.getName();
		String username = _user.getUsername();
		
		try {
			sendEmail(email, username,name, flightInformation);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FlightBookingEmailSenderService - Couldn't send email");
		}
		
	}
}
