package nl.thewgbbroz.zombieshooter;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import nl.thewgbbroz.zombieshooter.input.KeyHandler;

public class Game extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 3;
	
	public static final int FPS = 60;
	public static final int TPS = FPS; // Ticks per second
	
	public static final Random RAND = new Random();
	
	private Thread thread;
	private PlayState ps;
	
	private BufferedImage img;
	private Graphics2D g;
	
	public Game() {
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) img.getGraphics();
		
		ps = new PlayState();
		
		JFrame window = new JFrame("Zombie Shooter");
		window.setContentPane(this);
		window.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		window.setResizable(true); // false
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		KeyHandler.register(window);
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void run() {
		int waitMS = 1000 / FPS;
		
		long start, wait;
		while(true) {
			start = System.currentTimeMillis();
			
			ps.update();
			ps.render(g);
			renderToScreen();
			
			KeyHandler.pull();
			
			wait = waitMS - (System.currentTimeMillis() - start);
			if(wait > 0) {
				try{
					Thread.sleep(wait);
				}catch(Exception e) {}
			}
		}
	}
	
	private void renderToScreen() {
		Graphics2D g2 = (Graphics2D) getGraphics();
		
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		
		g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}
}
