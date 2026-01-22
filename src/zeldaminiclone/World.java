package zeldaminiclone;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class World {
	
	public static final int TILE_SIZE = 32;
	public static List<Block> blocks = new ArrayList<Block>();
	
	public World() {
		int tilesX = Game.WIDTH / TILE_SIZE;
		int tilesY = Game.HEIGHT / TILE_SIZE;
		
		for(int xx = 0; xx < tilesX; xx++) {
			blocks.add(new Block(xx * TILE_SIZE, 0));
			blocks.add(new Block(xx * TILE_SIZE, Game.HEIGHT - TILE_SIZE));
		}
		
		for(int yy = 0; yy < tilesY; yy++) {
			blocks.add(new Block(0, yy * TILE_SIZE));
			blocks.add(new Block(Game.WIDTH - TILE_SIZE, yy * TILE_SIZE));
		}
		
		blocks.add(new Block(220, 100));
	}
	
	public static boolean isFree(int x, int y) {
		Rectangle playerBox = new Rectangle(x, y, TILE_SIZE, TILE_SIZE);
		
		for (Block block : blocks) {
			if (block.intersects(playerBox)) return false;
		}
		return true;
	}
	
	public void render(Graphics g) {
		for (Block block : blocks) block.render(g);
	}
}
