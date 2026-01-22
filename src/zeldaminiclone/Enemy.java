package zeldaminiclone;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends Rectangle {

	public int spd = 1;
	public int right = 1, up = 0, down = 0, left = 0;
	
	public int curAnimation = 0;
	public int curFrames = 0, targetFrames = 15;
	
	public static List<Bullet> bullets = new ArrayList<Bullet>();
	public boolean shoot = false;
	public int dir = 1;
	public boolean atacar = true;
	
	public Enemy(int x, int y, boolean atacar) {
		super(x,y,32,32);
		this.atacar = atacar;
	}
	
	public void chasePlayer() {
		Player p = Game.player;
		if (x < p.x && World.isFree(x + spd, y)) {
			if (new Random().nextInt(100) < 50)
				x+= spd;
		}
		else if (x > p.x && World.isFree(x - spd, y)) {
			if (new Random().nextInt(100) < 50)
				x-= spd;
		}
		
		if (y < p.y && World.isFree(x, y + spd)) {
			if (new Random().nextInt(100) < 50)
				y+= spd;
		}
		else if (y > p.y && World.isFree(x,  y - spd)) {
			if (new Random().nextInt(100) < 50)
				y-= spd;
		}
	}
	
	public void tick() {
		boolean moved = true;
		
		if (this.atacar) chasePlayer();

		if (moved) {
			curFrames++;
			if (curFrames == targetFrames) {
				curFrames = 0;
				curAnimation++;
				if (curAnimation == Spritesheet.enemiesFront.length) {
					curAnimation = 0;
				}
			}
		}
		
		if (shoot) {
			shoot = false;
			bullets.add(new Bullet(x, y, dir));
		}
		
		for (Bullet bullet : bullets) bullet.tick();
	}
	
	public void render(Graphics g) {
		g.drawImage(Spritesheet.enemiesFront[curAnimation], x, y, width, height, null);
		
		for (Bullet bullet : bullets) bullet.render(g);
	}
}
