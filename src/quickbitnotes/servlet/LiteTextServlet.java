package quickbitnotes.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.toyz.litetext.FontUtils;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;

@SuppressWarnings("serial")
public class LiteTextServlet extends HttpServlet {

	
	private static final Logger log = Logger.getLogger(LiteTextServlet.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

        log.info(getServletName() + " started");
 
        String fontname = req.getParameter("f");
        if (fontname == null || fontname.length() == 0) {
        	fontname = "default";
        }
        log.info("using font " + fontname);

        String inputText = req.getParameter("t");
        FontUtils fm = new FontUtils();
        String gr = req.getParameter("gr");
        if (gr != null && gr.length() > 0) {
        	if (gr.equals("bluegr")) {
                int[][] gradar = new int[95][3];
                gradar[0][0] = 255;
                gradar[0][1] = 255;
                gradar[0][2] = 255;
                gradar[1][0] = 255;
                gradar[1][1] = 255;
                gradar[1][2] = 255;
                gradar[2][0] = 255;
                gradar[2][1] = 255;
                gradar[2][2] = 255;
                gradar[3][0] = 255;
                gradar[3][1] = 255;
                gradar[3][2] = 255;
                gradar[4][0] = 255;
                gradar[4][1] = 255;
                gradar[4][2] = 255;
                gradar[5][0] = 255;
                gradar[5][1] = 255;
                gradar[5][2] = 255;
                gradar[6][0] = 255;
                gradar[6][1] = 255;
                gradar[6][2] = 255;
                gradar[7][0] = 255;
                gradar[7][1] = 255;
                gradar[7][2] = 255;
                gradar[8][0] = 255;
                gradar[8][1] = 255;
                gradar[8][2] = 255;
                gradar[9][0] = 255;
                gradar[9][1] = 255;
                gradar[9][2] = 255;
                gradar[10][0] = 255;
                gradar[10][1] = 255;
                gradar[10][2] = 255;
                gradar[11][0] = 255;
                gradar[11][1] = 255;
                gradar[11][2] = 255;
                gradar[12][0] = 255;
                gradar[12][1] = 255;
                gradar[12][2] = 255;
                gradar[13][0] = 255;
                gradar[13][1] = 255;
                gradar[13][2] = 255;
                gradar[14][0] = 255;
                gradar[14][1] = 255;
                gradar[14][2] = 255;
                gradar[15][0] = 255;
                gradar[15][1] = 255;
                gradar[15][2] = 255;
                gradar[16][0] = 255;
                gradar[16][1] = 255;
                gradar[16][2] = 255;
                gradar[17][0] = 255;
                gradar[17][1] = 255;
                gradar[17][2] = 255;
                gradar[18][0] = 255;
                gradar[18][1] = 255;
                gradar[18][2] = 255;
                gradar[19][0] = 255;
                gradar[19][1] = 255;
                gradar[19][2] = 255;
                gradar[20][0] = 255;
                gradar[20][1] = 255;
                gradar[20][2] = 255;
                gradar[21][0] = 254;
                gradar[21][1] = 255;
                gradar[21][2] = 255;
                gradar[22][0] = 254;
                gradar[22][1] = 255;
                gradar[22][2] = 255;
                gradar[23][0] = 254;
                gradar[23][1] = 255;
                gradar[23][2] = 255;
                gradar[24][0] = 254;
                gradar[24][1] = 254;
                gradar[24][2] = 255;
                gradar[25][0] = 254;
                gradar[25][1] = 254;
                gradar[25][2] = 255;
                gradar[26][0] = 254;
                gradar[26][1] = 254;
                gradar[26][2] = 255;
                gradar[27][0] = 254;
                gradar[27][1] = 254;
                gradar[27][2] = 255;
                gradar[28][0] = 254;
                gradar[28][1] = 254;
                gradar[28][2] = 255;
                gradar[29][0] = 253;
                gradar[29][1] = 254;
                gradar[29][2] = 255;
                gradar[30][0] = 253;
                gradar[30][1] = 254;
                gradar[30][2] = 255;
                gradar[31][0] = 253;
                gradar[31][1] = 254;
                gradar[31][2] = 255;
                gradar[32][0] = 253;
                gradar[32][1] = 254;
                gradar[32][2] = 255;
                gradar[33][0] = 253;
                gradar[33][1] = 253;
                gradar[33][2] = 255;
                gradar[34][0] = 252;
                gradar[34][1] = 253;
                gradar[34][2] = 255;
                gradar[35][0] = 252;
                gradar[35][1] = 253;
                gradar[35][2] = 255;
                gradar[36][0] = 252;
                gradar[36][1] = 253;
                gradar[36][2] = 255;
                gradar[37][0] = 252;
                gradar[37][1] = 253;
                gradar[37][2] = 255;
                gradar[38][0] = 252;
                gradar[38][1] = 253;
                gradar[38][2] = 255;
                gradar[39][0] = 251;
                gradar[39][1] = 252;
                gradar[39][2] = 255;
                gradar[40][0] = 251;
                gradar[40][1] = 252;
                gradar[40][2] = 255;
                gradar[41][0] = 250;
                gradar[41][1] = 252;
                gradar[41][2] = 255;
                gradar[42][0] = 250;
                gradar[42][1] = 252;
                gradar[42][2] = 255;
                gradar[43][0] = 250;
                gradar[43][1] = 252;
                gradar[43][2] = 255;
                gradar[44][0] = 250;
                gradar[44][1] = 252;
                gradar[44][2] = 255;
                gradar[45][0] = 249;
                gradar[45][1] = 251;
                gradar[45][2] = 255;
                gradar[46][0] = 249;
                gradar[46][1] = 251;
                gradar[46][2] = 255;
                gradar[47][0] = 249;
                gradar[47][1] = 251;
                gradar[47][2] = 255;
                gradar[48][0] = 248;
                gradar[48][1] = 251;
                gradar[48][2] = 255;
                gradar[49][0] = 248;
                gradar[49][1] = 251;
                gradar[49][2] = 255;
                gradar[50][0] = 248;
                gradar[50][1] = 250;
                gradar[50][2] = 255;
                gradar[51][0] = 248;
                gradar[51][1] = 250;
                gradar[51][2] = 255;
                gradar[52][0] = 247;
                gradar[52][1] = 250;
                gradar[52][2] = 255;
                gradar[53][0] = 247;
                gradar[53][1] = 250;
                gradar[53][2] = 255;
                gradar[54][0] = 247;
                gradar[54][1] = 250;
                gradar[54][2] = 255;
                gradar[55][0] = 246;
                gradar[55][1] = 249;
                gradar[55][2] = 255;
                gradar[56][0] = 246;
                gradar[56][1] = 249;
                gradar[56][2] = 255;
                gradar[57][0] = 246;
                gradar[57][1] = 249;
                gradar[57][2] = 255;
                gradar[58][0] = 245;
                gradar[58][1] = 249;
                gradar[58][2] = 255;
                gradar[59][0] = 245;
                gradar[59][1] = 248;
                gradar[59][2] = 255;
                gradar[60][0] = 245;
                gradar[60][1] = 248;
                gradar[60][2] = 255;
                gradar[61][0] = 244;
                gradar[61][1] = 248;
                gradar[61][2] = 255;
                gradar[62][0] = 244;
                gradar[62][1] = 248;
                gradar[62][2] = 255;
                gradar[63][0] = 243;
                gradar[63][1] = 247;
                gradar[63][2] = 255;
                gradar[64][0] = 243;
                gradar[64][1] = 247;
                gradar[64][2] = 255;
                gradar[65][0] = 243;
                gradar[65][1] = 247;
                gradar[65][2] = 255;
                gradar[66][0] = 242;
                gradar[66][1] = 247;
                gradar[66][2] = 255;
                gradar[67][0] = 242;
                gradar[67][1] = 247;
                gradar[67][2] = 255;
                gradar[68][0] = 242;
                gradar[68][1] = 246;
                gradar[68][2] = 254;
                gradar[69][0] = 241;
                gradar[69][1] = 246;
                gradar[69][2] = 254;
                gradar[70][0] = 241;
                gradar[70][1] = 246;
                gradar[70][2] = 254;
                gradar[71][0] = 241;
                gradar[71][1] = 246;
                gradar[71][2] = 254;
                gradar[72][0] = 240;
                gradar[72][1] = 245;
                gradar[72][2] = 254;
                gradar[73][0] = 240;
                gradar[73][1] = 245;
                gradar[73][2] = 254;
                gradar[74][0] = 240;
                gradar[74][1] = 245;
                gradar[74][2] = 254;
                gradar[75][0] = 239;
                gradar[75][1] = 245;
                gradar[75][2] = 254;
                gradar[76][0] = 239;
                gradar[76][1] = 244;
                gradar[76][2] = 254;
                gradar[77][0] = 238;
                gradar[77][1] = 244;
                gradar[77][2] = 254;
                gradar[78][0] = 238;
                gradar[78][1] = 244;
                gradar[78][2] = 254;
                gradar[79][0] = 238;
                gradar[79][1] = 244;
                gradar[79][2] = 254;
                gradar[80][0] = 237;
                gradar[80][1] = 243;
                gradar[80][2] = 254;
                gradar[81][0] = 237;
                gradar[81][1] = 243;
                gradar[81][2] = 254;
                gradar[82][0] = 237;
                gradar[82][1] = 243;
                gradar[82][2] = 254;
                gradar[83][0] = 236;
                gradar[83][1] = 243;
                gradar[83][2] = 254;
                gradar[84][0] = 236;
                gradar[84][1] = 243;
                gradar[84][2] = 254;
                gradar[85][0] = 236;
                gradar[85][1] = 242;
                gradar[85][2] = 254;
                gradar[86][0] = 236;
                gradar[86][1] = 242;
                gradar[86][2] = 254;
                gradar[87][0] = 229;
                gradar[87][1] = 238;
                gradar[87][2] = 254;
                gradar[88][0] = 229;
                gradar[88][1] = 238;
                gradar[88][2] = 254;
                gradar[89][0] = 229;
                gradar[89][1] = 238;
                gradar[89][2] = 254;
                gradar[90][0] = 198;
                gradar[90][1] = 205;
                gradar[90][2] = 216;
                gradar[91][0] = 255;
                gradar[91][1] = 255;
                gradar[91][2] = 255;
                gradar[92][0] = 255;
                gradar[92][1] = 255;
                gradar[92][2] = 255;
                gradar[93][0] = 255;
                gradar[93][1] = 255;
                gradar[93][2] = 255;
                gradar[94][0] = 255;
                gradar[94][1] = 255;
                gradar[94][2] = 255;
                fm.setGradient(gradar);
        	} else if (gr.equals("gray")) {
				int[][] graybg = new int[1][3];
				graybg[0][0] = 221;
				graybg[0][1] = 224;
				graybg[0][2] = 244;
				fm.setGradient(graybg);
			}
        } else {
			String bg = req.getParameter("bg");
			if (bg != null && bg.length() > 0) {
				int[][] bgcolor = new int[1][3];
				int x = Integer.parseInt(bg, 16);
				bgcolor[0][0] = (x / 65536) & 255;
				bgcolor[0][1] = (x / 256) & 255;
				bgcolor[0][2] = x & 255;
				fm.setGradient(bgcolor);
			}
		}

        String fg = req.getParameter("fg");
        if (fg != null && fg.length() > 0) {
        	int[] fgcolor = new int[3];
        	int x = Integer.parseInt(fg, 16);
        	fgcolor[0] = (x / 65536) & 255;
        	fgcolor[1] = (x / 256) & 255;
        	fgcolor[2] = x & 255;
        	fm.setFgcolor(fgcolor);
        }

        byte[] bmp_data = fm.doRender(inputText, fontname);
        
		log.info("BMP image created");
		
        com.google.appengine.api.images.ImagesService imagesService = ImagesServiceFactory.getImagesService();

		Image bmpImage = ImagesServiceFactory.makeImage(bmp_data);
        com.google.appengine.api.images.Transform flipit = ImagesServiceFactory.makeVerticalFlip();
        Image newImage = imagesService.applyTransform(flipit, bmpImage, com.google.appengine.api.images.ImagesService.OutputEncoding.JPEG);
    
		log.info("JPEG image created");

		resp.setContentType("image/jpeg");
		java.io.ByteArrayInputStream io = new ByteArrayInputStream(
				newImage.getImageData());

		ServletOutputStream svout = resp.getOutputStream();
		int c = -1;
		while ((c = io.read()) != -1) {
			svout.write(c);
		}
		io.close();
		svout.close();
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
		doGet(req, res);
	}
}
