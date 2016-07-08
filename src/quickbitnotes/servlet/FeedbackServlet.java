package quickbitnotes.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class FeedbackServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(FeedbackServlet.class
			.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) {
        	resp.sendRedirect("/login");
        	return;
        }
		String Message = req.getParameter("Message");
        email_feedback(user.getEmail(), user.getNickname(), Message);
        resp.sendRedirect("/feedback/thanks"); 
 
	}

	private void email_feedback(String sender, String nickname, String msgBody) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(sender, nickname));
            String adminEmail = getServletConfig().getInitParameter("adminEmail");
            log.info("adminEmail: " + adminEmail);
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(adminEmail));
            msg.setSubject("Quick Bit Note Feedback");
            msg.setText(msgBody);
            Transport.send(msg);

        } catch (AddressException e) {
            log.warning("AddressException on From: " + sender);
        } catch (MessagingException e) {
            log.warning("MessagingException on From: " + sender);
        } catch (UnsupportedEncodingException e) {
        	log.warning("UnsupportedEncodingException on From: " + sender);
		}
		
	}
}
