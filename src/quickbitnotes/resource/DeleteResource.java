package quickbitnotes.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

import quickbitnotes.Util.PMF;
import quickbitnotes.model.BitNote;
import freemarker.template.Configuration;

public class DeleteResource extends AuthResource {

    private final static Logger logger = Logger.getLogger(DeleteResource.class.getName());

    @Get
    public Representation represent() {
        Configuration fmc = (Configuration) getContextAttribute("fmc");
        PersistenceManager pm = PMF.get().getPersistenceManager();

        logger.log(Level.INFO, "Entered DeleteResource");

        logger.log(Level.INFO, "DeleteResource for " + user.getEmail());
        String id = (String) getRequestAttributes().get("id");
        logger.log(Level.INFO, "DeleteResource user: " + user.getEmail() + " id: " + id);
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
		if (!user.getEmail().equals(To)) {
			getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
	        dataModel.put("errmsg", "Permission Denied");
	        dataModel.put("thepage", "error.html");
	        return new TemplateRepresentation("master.html", fmc, dataModel, MediaType.TEXT_HTML);
		}
		logger.info("Deleting note " + b.getId());
        pm.deletePersistent(b);
    	getResponse().redirectTemporary("/mybitnotes");
    	return new StringRepresentation("", MediaType.TEXT_HTML);
    }

}
