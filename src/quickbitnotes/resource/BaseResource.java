// created Sep 24, 2009 6:23:43 PM
// by petrovic

package quickbitnotes.resource;

import java.util.Map;
import java.util.logging.Logger;

import org.restlet.resource.ServerResource;

public class BaseResource extends ServerResource {
    @SuppressWarnings("unused")
    private static Logger LOG = Logger.getLogger(BaseResource.class.getName());

    protected Object getContextAttribute(String key) {
        Map<String, Object> attrs = getContext().getAttributes();
        return attrs.get(key);
    }
}
