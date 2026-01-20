package zeldaminiclone;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	public static BufferedImage spritesheet;
	public static BufferedImage playerFront;
	public static BufferedImage tileWall;
	
	public Spritesheet() {
		try {
			spritesheet = ImageIO.read(getClass().getResource("/spritesheet.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		playerFront = Spritesheet.getSprite(0, 11, 16, 16);
		tileWall = Spritesheet.getSprite(280, 221, 16, 16);
	}
	
	public static BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
}
