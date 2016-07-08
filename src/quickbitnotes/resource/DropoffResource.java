package quickbitnotes.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import freemarker.template.Configuration;

public class DropoffResource extends AuthResource {

    private final static Logger logger = Logger.getLogger(DropoffResource.class.getName());

    @Get
    public Representation represent() {
        Configuration fmc = (Configuration) getContextAttribute("fmc");

        logger.log(Level.INFO, "Entered DropoffResource");
        
        String action = (String) getRequestAttributes().get("action");
        if (action != null) {
        	logger.log(Level.INFO, "action: " + action);
        	dataModel.put("action", action);
        }
        // only use the HTTPS url if on appspot
      //  ServletContext ctx = (ServletContext) this.getContext().getAttributes().get("org.restlet.ex?t.servlet.ServletCon?text");
       // String sinfo = ctx.getServerInfo();
       // logger.info("DropoffResource getServerInfo:  " + sinfo);
       // if (!sinfo.contains("Development")) {
        	//dataModel.put("appengine", 1);
       // }        
        int sport = this.getServerInfo().getPort();
        logger.info("DropoffResource ServerInfo port:  " + sport);
        dataModel.put("ssl", (sport == 443));
        if (sport != 8080) {
        	dataModel.put("appengine", 1);
        	dataModel.put("posturl", "https://" + this.getHostRef().getHostDomain() + "/dropper");
        }
        logger.info("HostDomain: " + this.getHostRef().getHostDomain());
        
        dataModel.put("thepage", "dropoff.html");
        return new TemplateRepresentation("master.html", fmc, dataModel, MediaType.TEXT_HTML);
    }

}
