package zeldaminiclone;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	public static BufferedImage spritesheet;
	public static BufferedImage[] playersFront;
	public static BufferedImage[] enemiesFront;
	public static BufferedImage tileWall;
	
	public Spritesheet() {
		try {
			spritesheet = ImageIO.read(getClass().getResource("/spritesheet.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		playersFront = new BufferedImage[2];
		playersFront[0] = Spritesheet.getSprite(0, 11, 16, 16);
		playersFront[1] = Spritesheet.getSprite(16, 11, 16, 16);
		
		enemiesFront = new BufferedImage[2];
		enemiesFront[0] = Spritesheet.getSprite(298, 221, 16, 16);
		enemiesFront[1] = Spritesheet.getSprite(316, 221, 16, 16);
		
		tileWall = Spritesheet.getSprite(280, 221, 16, 16);
	}
	
	public static BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
}
