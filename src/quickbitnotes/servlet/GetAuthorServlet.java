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

import org.toyz.litetext.FontUtils;

import quickbitnotes.Util.PMF;
import quickbitnotes.model.BitNote;
import quickbitnotes.model.ImageFile;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@SuppressWarnings("serial")
public class GetAuthorServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(GetAuthorServlet.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		BitNote b;
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String id = req.getParameter("id");
		if (id != null) {
			id = id.replaceAll(",","");
		}
		log.log(Level.INFO, "GetAuthorServlet id: " + id);
		if (user == null) {
			resp.sendError(403, "Must be signed in");
			return;
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
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
		FontUtils fm = new FontUtils();
		int[][] bgcolor = new int[1][3];
		bgcolor[0][0] = 45; ;bgcolor[0][1] = 49; bgcolor[0][2] = 52;
		fm.setGradient(bgcolor);
		int [] fgcolor = new int[3];
		fgcolor[0] = 255; fgcolor[1] = 255; fgcolor[2] = 255;
		fm.setFgcolor(fgcolor);
		log.log(Level.INFO, "GetAuthorServlet doRender: " + To);
        byte[] bmp_data = fm.doRender(b.getAuthor(), "Lucida-Medium");
		com.google.appengine.api.images.ImagesService imagesService = ImagesServiceFactory.getImagesService();
		Image bmpImage = ImagesServiceFactory.makeImage(bmp_data);
		com.google.appengine.api.images.Transform flipit = ImagesServiceFactory.makeVerticalFlip();
		Image newImage = imagesService.applyTransform(flipit, bmpImage, com.google.appengine.api.images.ImagesService.OutputEncoding.JPEG);
		String xarg = req.getParameter("x");
		if (xarg != null && xarg.length() > 0) {
			int maxwid = Integer.parseInt(xarg);
			if (newImage.getWidth() > maxwid) {
				com.google.appengine.api.images.Transform cropit
				= ImagesServiceFactory.makeCrop(0.0, 0.0, (double)maxwid/(double)(newImage.getWidth()), 1.0);
				newImage = imagesService.applyTransform(cropit, newImage, com.google.appengine.api.images.ImagesService.OutputEncoding.JPEG);
			}
		}
        ImageFile image = new ImageFile(newImage.getImageData(), "author.jpg", "image/jpeg");

        resp.setContentType(image.getMimeType());
		java.io.ByteArrayInputStream io = new ByteArrayInputStream(
				image.getContent());

		ServletOutputStream sout = resp.getOutputStream();
		int c = -1;
		while ((c = io.read()) != -1) {
			sout.write(c);
		}
		io.close();
		sout.close();
		
	}
}

