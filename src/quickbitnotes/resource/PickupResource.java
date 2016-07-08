package quickbitnotes.resource;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import quickbitnotes.Util.PMF;
import quickbitnotes.model.BitNote;
import freemarker.template.Configuration;

public class PickupResource extends AuthResource {

    private final static Logger logger = Logger.getLogger(PickupResource.class.getName());

    private PersistenceManager pm;
    
	private List<BitNote> notesQuery() {
        String query = "select from " + BitNote.class.getName() +
    	" where recipient == '" + user.getEmail() + "'";
        logger.log(Level.INFO, "PickupResource query: " + query);
        @SuppressWarnings("unchecked")
        List<BitNote> notes = (List<BitNote>) pm.newQuery(query).execute();
        return notes;
    }
    
    @Get
    public Representation represent() {
        Configuration fmc = (Configuration) getContextAttribute("fmc");
        pm = PMF.get().getPersistenceManager();
        
        logger.log(Level.INFO, "PickupResource for " + user.getEmail());
 
        List<BitNote> notes = notesQuery();
        logger.log(Level.INFO, "PickupResource notes: " + notes.size());
        if (!notes.isEmpty()) {
        	dataModel.put("notes", notes);
        }
        dataModel.put("thepage", "pickup.html");
        
        return new TemplateRepresentation("master.html", fmc, dataModel, MediaType.TEXT_HTML);
    }

}
