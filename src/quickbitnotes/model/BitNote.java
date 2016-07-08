package quickbitnotes.model;

import java.io.IOException;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.toyz.litetext.FontUtils;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class BitNote {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    
    @Persistent
    private String author;
    
    @Persistent
    private String recipient;

    @Persistent(serialized = "true")
    private ImageFile subject;
    
    @Persistent(serialized = "true")
    private ImageFile content;

    @Persistent
    private Date date;

    public BitNote(String author, String recipient)  {
		this.author = author;
		this.recipient = recipient;
		this.date = new Date();
	}
    
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	public String getIdstr() {
		return id.toString().replaceAll(",","");
	}


	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setSubject(ImageFile subject) {
		this.subject = subject;
	}
	
	public void setSubject(String subject, String fontname, int[] fgcolor, int[][] gradient) throws IOException {
		FontUtils fm = new FontUtils();
		if (fgcolor != null) {
			fm.setFgcolor(fgcolor);
		}
		if (gradient != null) {
			fm.setGradient(gradient);
		}
		if (fontname == null) {
			fontname = "default";
		}
        byte[] bmp_data = fm.doRender(subject, fontname);
        com.google.appengine.api.images.ImagesService imagesService = ImagesServiceFactory.getImagesService();

		Image bmpImage = ImagesServiceFactory.makeImage(bmp_data);
        com.google.appengine.api.images.Transform flipit = ImagesServiceFactory.makeVerticalFlip();
        Image newImage = imagesService.applyTransform(flipit, bmpImage, com.google.appengine.api.images.ImagesService.OutputEncoding.JPEG);
    
        ImageFile subject_img = new ImageFile(newImage.getImageData(), "subject.jpg", "image/jpeg");
		this.subject = subject_img;
	}
	
	public void setSubject(String subject, String fontname) throws IOException {
		setSubject(subject, fontname, null, null);
	}
	
	public void setSubject(String subject, String fontname, int[] fgcolor) throws IOException {
		setSubject(subject, fontname, fgcolor, null);
	}

	public void setSubject(String subject, int[] fgcolor) throws IOException {
		setSubject(subject, null, fgcolor, null);
	}
	public void setSubject(String subject, int[][] gradient) throws IOException {
		setSubject(subject, null, null, gradient);
	}
	public void setSubject(String subject) throws IOException {
		setSubject(subject, null, null, null);
	}
	
	public ImageFile getSubject() {
		return subject;
	}

	public void setContent(ImageFile content) {
		this.content = content;
	}
	public void setContent(String content, String fontname, int[] fgcolor, int[][] gradient) throws IOException {
		FontUtils fm = new FontUtils();
		if (fgcolor != null) {
			fm.setFgcolor(fgcolor);
		}
		if (gradient != null) {
			fm.setGradient(gradient);
		}
		if (fontname == null) {
			fontname = "default";
		}
        byte[] bmp_data = fm.doRender(content, fontname);
        com.google.appengine.api.images.ImagesService imagesService = ImagesServiceFactory.getImagesService();

		Image bmpImage = ImagesServiceFactory.makeImage(bmp_data);
        com.google.appengine.api.images.Transform flipit = ImagesServiceFactory.makeVerticalFlip();
        Image newImage = imagesService.applyTransform(flipit, bmpImage, com.google.appengine.api.images.ImagesService.OutputEncoding.JPEG);
    
        ImageFile content_img = new ImageFile(newImage.getImageData(), "content.jpg", "image/jpeg");
		this.content = content_img;
	}
	
	public void setContent(String content, String fontname) throws IOException {
		setSubject(content, fontname, null, null);
	}
	
	public void setContent(String content, String fontname, int[] fgcolor) throws IOException {
		setSubject(content, fontname, fgcolor, null);
	}

	public void setContent(String content, int[] fgcolor) throws IOException {
		setSubject(content, null, fgcolor, null);
	}
	public void setContent(String content, int[][] gradient) throws IOException {
		setSubject(content, null, null, gradient);
	}
	public void setContent(String content) throws IOException {
		setSubject(content, null, null, null);
	}
	
	public ImageFile getContent() {
		return content;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
}
