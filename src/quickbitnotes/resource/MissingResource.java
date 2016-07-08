package quickbitnotes.resource;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.ext.servlet.internal.ServletCall;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import freemarker.template.Configuration;
import quickbitnotes.resource.BaseResource;

public class MissingResource extends BaseResource {

    private final static Logger logger = Logger.getLogger(MissingResource.class.getName());

    @Get
    public Representation represent() {
        Configuration fmc = (Configuration) getContextAttribute("fmc");

        logger.log(Level.INFO, "Entered MissingResource");

        final Map<String, Object> dataModel = new TreeMap<String, Object>();
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) {
        	dataModel.put("login_url", userService.createLoginURL(ServletCall.getRequest(getRequest()).getRequestURI()));
        } else {
            dataModel.put("user_email", user.getEmail());
            dataModel.put("logout_url", userService.createLogoutURL("/"));
        }
        getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
        dataModel.put("thepage", "404error.html");

        return new TemplateRepresentation("master.html", fmc, dataModel, MediaType.TEXT_HTML);
    }

}
