package groovejaar;

import java.awt.Image;
import java.awt.Toolkit;


import javax.swing.ImageIcon;

public class ImageUtil {

	private String IMAGE_DIR =  "images/";



	/**
	 * returns an ImageIcon object matching the given name
	 */
	public ImageIcon getImgIcon(String name) {

		return new ImageIcon((ClassLoader.getSystemResource(IMAGE_DIR +name)));
	}

	/**
	 * returns the icon.png as an Image object
	 */
	public Image getLogo() {
		return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(IMAGE_DIR+"icon.png"));
	}

}
