package quickbitnotes.servlet;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
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

import quickbitnotes.Util.PMF;
import quickbitnotes.model.BitNote;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class DropoffServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(DropoffServlet.class
			.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) {
        	resp.sendRedirect("/login");
        	return;
        }
		String To = req.getParameter("To").trim().toLowerCase();
		String Subject = req.getParameter("Subject").trim();
		String Message = req.getParameter("Message");
		String Notify = req.getParameter("Notify");
		log.info("To: " + To + " Subject: " + Subject + 
				" len: " + Message.length() + 
				" Notify: " + Notify);
		int[][] bgcolor = new int[1][3];
		bgcolor[0][0] = 45; ;bgcolor[0][1] = 49; bgcolor[0][2] = 52;
		int [] fgcolor = new int[3];
		fgcolor[0] = 255; fgcolor[1] = 255; fgcolor[2] = 255;
		BitNote bitnote = new BitNote(user.getEmail(), To);
		bitnote.setSubject(Subject, "Lucida-Medium", fgcolor, bgcolor);
		bitnote.setContent(Message, "Lucida-Medium", fgcolor, bgcolor);
		
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(bitnote);
        } finally {
            pm.close();
        }	
       	// FIXME it would be nice to get this from  LocalName, but doesn't work on App Engine
    	String thisDomain = getServletConfig().getInitParameter("thisDomain");

        if (Notify != null && Notify.equals("on")) {
        	notify_recipient(thisDomain, bitnote);
        }
		log.info("Saved Id: " + bitnote.getId());
        log.info("DropoffServlet LocalName: " + req.getLocalName());
        log.info("DropoffServlet LocalPort: " + req.getLocalPort());

        // FIXME stupid App Engine returns 0 for port!
        // and it doesn't give us LocalName so we get host from init-param above
        if (req.getLocalPort() == 8080) {
            resp.sendRedirect("/dropoff/thanks");
        } else {
            resp.sendRedirect("http://" + thisDomain + "/dropoff/thanks");
        }
	}

	private void notify_recipient(String thisDomain, BitNote bitnote) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = "I sent you a new Bit Note.\n\nYou can retrieve it by clicking the following link:\n\nhttp://" + thisDomain + "/mybitnotes\n";

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(bitnote.getAuthor()));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(bitnote.getRecipient()));
            msg.setSubject("Your New Bit Note");
            msg.setText(msgBody);
            Transport.send(msg);

        } catch (AddressException e) {
            log.warning("AddressException on From: " + bitnote.getAuthor() + " To: " + bitnote.getRecipient());
        } catch (MessagingException e) {
            log.warning("MessagingException on From: " + bitnote.getAuthor() + " To: " + bitnote.getRecipient());
        }
		
	}
}
