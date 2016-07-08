package quickbitnotes.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quickbitnotes.Util.PMF;
import quickbitnotes.model.BitNote;
import quickbitnotes.model.ImageFile;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@SuppressWarnings("serial")
public class GetContentServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(GetContentServlet.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		BitNote b;
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendError(403, "Forbidden");
			return;
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String id = req.getParameter("id");
		if (id != null) {
			id = id.replaceAll(",","");
		}
		log.log(Level.INFO, "GetContentServlet id: " + id);
		try {
			b = pm.getObjectById(BitNote.class, Long.parseLong(id));
		} catch (Exception e) {
			log.log(Level.SEVERE, "No such note: " + id);
			resp.sendError(404, "Not Found");
			return;
		}
		String To = b.getRecipient();
		if (!user.getEmail().equals(To)) {
			resp.sendError(403, "Unauthorized");
			return;
		}
		ImageFile image = b.getContent();
		java.io.ByteArrayInputStream io;

		String xarg = req.getParameter("x");
		if (xarg != null && xarg.length() > 0) {
			int maxwid = Integer.parseInt(xarg);
			com.google.appengine.api.images.ImagesService imagesService = ImagesServiceFactory.getImagesService();
			Image newImage = ImagesServiceFactory.makeImage(image.getContent());
			log.info("GetContentServlet width: " + newImage.getWidth());
			if (newImage.getWidth() > maxwid) {
				com.google.appengine.api.images.Transform cropit
				= ImagesServiceFactory.makeCrop(0.0, 0.0, (double)maxwid/(double)(newImage.getWidth()), 1.0);
				newImage = imagesService.applyTransform(cropit, newImage, com.google.appengine.api.images.ImagesService.OutputEncoding.JPEG);
				io = new ByteArrayInputStream(
	        		newImage.getImageData());
			} else {
				io = new ByteArrayInputStream(
					image.getContent());
			}
		} else {
			io = new ByteArrayInputStream(
					image.getContent());
		}
		resp.setContentType(image.getMimeType());

		ServletOutputStream sout = resp.getOutputStream();
		int c = -1;
		while ((c = io.read()) != -1) {
			sout.write(c);
		}
		io.close();
		sout.close();
		
	}
}

