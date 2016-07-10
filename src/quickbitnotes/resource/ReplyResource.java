package quickbitnotes.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import quickbitnotes.Util.PMF;
import quickbitnotes.model.BitNote;
import freemarker.template.Configuration;

public class ReplyResource extends AuthResource {

    private final static Logger logger = Logger.getLogger(ReplyResource.class.getName());

    @Get
    public Representation represent() {
        Configuration fmc = (Configuration) getContextAttribute("fmc");
        PersistenceManager pm = PMF.get().getPersistenceManager();

        logger.log(Level.INFO, "Entered ReplyResource");
        String id = (String) getRequestAttributes().get("id");
        logger.log(Level.INFO, "ReplyResource user: " + user.getEmail() + " id: " + id);
		BitNote b;
		try {
			b = pm.getObjectById(BitNote.class, Long.parseLong(id));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "No such note: " + id);
	        getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
	        dataModel.put("errmsg", "No such note");
	        dataModel.put("thepage", "error.html");
	        return new TemplateRepresentation("master.html", fmc, dataModel, MediaType.TEXT_HTML);
		}
		String To = b.getRecipient();
		if (!user.getEmail().equalsIgnoreCase(To)) {
			logger.log(Level.INFO, "ReplyResource user: " + user.getEmail() + " trying to read message " + id + " written to " + To);
			getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
	        dataModel.put("errmsg", "Permission Denied");
	        dataModel.put("thepage", "error.html");
	        return new TemplateRepresentation("master.html", fmc, dataModel, MediaType.TEXT_HTML);
		}
		dataModel.put("To", b.getAuthor());
		
        int sport = this.getServerInfo().getPort();
        logger.info("DropoffResource ServerInfo port:  " + sport);
        dataModel.put("ssl", (sport == 443));
        if (sport != 8080) {
        	dataModel.put("appengine", 1);
			dataModel.put("posturl", "https://" + SystemProperty.applicationId.get() + ".appspot.com/dropper");
        }
        logger.info("HostDomain: " + this.getHostRef().getHostDomain());
        
        dataModel.put("thepage", "dropoff.html");
        return new TemplateRepresentation("master.html", fmc, dataModel, MediaType.TEXT_HTML);
    }

}
