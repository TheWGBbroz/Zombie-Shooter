package nl.thewgbbroz.zombieshooter.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * @author Wouter Gritter
 * 
 * Copyright 2017 Wouter Gritter
 */
public class KeyHandler {
	private static List<Integer> pressed = new ArrayList<>();
	private static List<Integer> released = new ArrayList<>();
	
	private static boolean[] down = new boolean[65536];
	
	public static void register(JFrame frame) {
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				pressed.add(e.getKeyCode());
				
				down[e.getKeyCode()] = true;
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				released.add(e.getKeyCode());
				
				down[e.getKeyCode()] = false;
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}
	
	public static boolean isDown(int key) {
		return down[key];
	}
	
	public static boolean isJustPressed(int key) {
		return pressed.contains(key);
	}
	
	public static boolean isJustReleased(int key) {
		return released.contains(key);
	}
	
	public static void pull() {
		pressed.clear();
		released.clear();
	}
}
