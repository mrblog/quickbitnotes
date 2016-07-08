package quickbitnotes.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import freemarker.template.Configuration;

public class FeedbackResource extends AuthResource {

    private final static Logger logger = Logger.getLogger(FeedbackResource.class.getName());

    @Get
    public Representation represent() {
        Configuration fmc = (Configuration) getContextAttribute("fmc");
        
        logger.log(Level.INFO, "Entered FeedbackResource");

        String action = (String) getRequestAttributes().get("action");
        if (action != null) {
        	logger.log(Level.INFO, "action: " + action);
        	dataModel.put("action", action);
        }
        
        dataModel.put("thepage", "feedback.html");
        return new TemplateRepresentation("master.html", fmc, dataModel, MediaType.TEXT_HTML);
    }

}
