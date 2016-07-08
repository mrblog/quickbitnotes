package quickbitnotes.resource;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.restlet.data.MediaType;
import org.restlet.ext.servlet.internal.ServletCall;
import org.restlet.representation.StringRepresentation;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AuthResource extends BaseResource {

	private static Logger LOG = Logger.getLogger(AuthResource.class.getName());

	protected UserService userService;
	protected User user;
	protected Map<String, Object> dataModel;

	public void doInit() {
		LOG.info("doInit");

		userService = UserServiceFactory.getUserService();
		user = userService.getCurrentUser();
		if (user == null) {
			getResponse().redirectTemporary(userService.createLoginURL(ServletCall.getRequest(getRequest()).getRequestURI()));
			getResponse().setEntity(
					new StringRepresentation("<h1>Auth Required</h1>",
							MediaType.TEXT_HTML));
		} else {
			dataModel = new TreeMap<String, Object>();

			dataModel.put("user_email", user.getEmail());
	    	dataModel.put("logout_url", userService.createLogoutURL("/"));
		}
	}
}
