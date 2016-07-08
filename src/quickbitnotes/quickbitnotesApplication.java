package quickbitnotes;

import java.util.Map;
import java.util.logging.Logger;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import quickbitnotes.resource.AboutResource;
import quickbitnotes.resource.BitNoteResource;
import quickbitnotes.resource.DeleteResource;
import quickbitnotes.resource.DropoffResource;
import quickbitnotes.resource.FeedbackResource;
import quickbitnotes.resource.LoginResource;
import quickbitnotes.resource.MissingResource;
import quickbitnotes.resource.PickupResource;
import quickbitnotes.resource.ReplyResource;
import quickbitnotes.resource.WelcomeResource;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;

public class quickbitnotesApplication extends Application {
    private final Logger logger = Logger.getLogger(quickbitnotesApplication.class.getName());

    public quickbitnotesApplication() {
    }

    @Override
    public void start() throws Exception {

        // todo:  we might consider moving all this to the public no-arg constructor above.
        logger.info("quickbitnotes Restlet startup");

        // get the underlying context (probably the Servlet context, but don't assume.  it's just a map either way).
        Map<String, Object> attrs = getContext().getAttributes();

        // get a FM Configuration instance and put it in the context
        Configuration fmc = new Configuration();
        ClassTemplateLoader templateLoader = new ClassTemplateLoader(getClass(), "templates");
        fmc.setTemplateLoader(templateLoader);
        attrs.put("fmc", fmc);

        super.start();
    }

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */

    @Override
    public synchronized Restlet createRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of  a given Resource.
        Router router = new Router(getContext());

        router.attach("/welcome", WelcomeResource.class);
        
        router.attach("/dropoff", DropoffResource.class);

        router.attach("/dropoff/{action}", DropoffResource.class);
        
        router.attach("/login", LoginResource.class);

        router.attach("/mybitnotes", PickupResource.class);
        
        router.attach("/bitnote/{id}", BitNoteResource.class);
        
        router.attach("/delete/{id}", DeleteResource.class);

        router.attach("/reply/{id}", ReplyResource.class);
        
        router.attach("/feedback", FeedbackResource.class);
        
        router.attach("/feedback/{action}", FeedbackResource.class);

        router.attach("/about/{page}", AboutResource.class);
        
        router.attach("/", WelcomeResource.class);
        
        router.attachDefault(MissingResource.class);

        return router;
    }
}
