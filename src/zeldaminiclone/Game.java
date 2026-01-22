package zeldaminiclone;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

	public static int WIDTH = 640, HEIGHT = 480;
	
	public static Player player;
	public World world;
	public List<Enemy> enemies = new ArrayList<Enemy>();
	
	private final BufferedImage frameBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	private volatile int viewW = WIDTH;
	private volatile int viewH = HEIGHT;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus();
		
		new Spritesheet();
		player = new Player(32,32);
		
		enemies.add(new Enemy(32, 64, true));
		enemies.add(new Enemy(80, 230, false));
		world = new World();
		
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				viewW = Math.max(1, getWidth());
				viewH = Math.max(1, getHeight());
			}
		});
	}
	
	public void tick() {
		player.tick();
		
		for(Enemy enemy : enemies) enemy.tick();
	}
	
	private void renderGameToBuffer() {
		Graphics2D g = frameBuffer.createGraphics();
		
		g.setColor(new Color(0, 135, 13));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		player.render(g);
		for (Enemy enemy : enemies) enemy.render(g);
		
		g.dispose();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		this.renderGameToBuffer();
		
		Graphics g0 = bs.getDrawGraphics();
		Graphics2D g = (Graphics2D)g0;
		
		int cw = viewW;
		int ch = viewH;
		
		int scale = Math.max(1, Math.min(cw / WIDTH, ch / HEIGHT));
		
		int drawW = WIDTH * scale;
		int drawH = HEIGHT * scale;
		
		int x = (cw - drawW) / 2;
		int y = (ch - drawH) / 2;
		
		g.setColor(Color.black);
		g.fillRect(0, 0, cw, ch);
		
		g.setRenderingHint(
			java.awt.RenderingHints.KEY_INTERPOLATION,
			java.awt.RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
		);
		
		g.drawImage(frameBuffer, x, y, drawW, drawH, null);
		
		g.dispose();
		bs.show();
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			Game game = new Game();
			JFrame frame = new JFrame("Mini Zelda");
			
			frame.setIgnoreRepaint(true);
			game.setIgnoreRepaint(true);
			
			frame.add(game);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setResizable(true);
			frame.setVisible(true);
			
			game.createBufferStrategy(3);
			new Thread(game).start();
		});
	}
	
	@Override
	public void run() {
		final double nsPerTick = 1_000_000_000.0 / 60.0;
		long last = System.nanoTime();
		double delta = 0;
		
		while (true) {
			long now = System.nanoTime();
			delta += (now - last) / nsPerTick;
			last = now;
			
			while (delta >= 1) {
				tick();
				delta -= 1;
			}
			
			render();
			try { Thread.sleep(1); } catch (InterruptedException e) { return; }
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = true;
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) player.left = true;

		if (e.getKeyCode() == KeyEvent.VK_UP) player.up = true;
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) player.down = true;
		
		if (e.getKeyCode() == KeyEvent.VK_Z) player.shoot = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = false;
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) player.left = false;

		if (e.getKeyCode() == KeyEvent.VK_UP) player.up = false;
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) player.down = false;
	}

}
