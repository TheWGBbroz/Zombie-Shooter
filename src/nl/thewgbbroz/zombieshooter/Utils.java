package nl.thewgbbroz.zombieshooter;

/**
 * @author Wouter Gritter
 * 
 * Copyright 2017 Wouter Gritter
 */
public class Utils {
	private Utils() {
	}
	
	public static double lerpRot(double theta, double target, double factor) {
		double curX = Math.cos(theta);
		double curY = Math.sin(theta);
		
		double tarX = Math.cos(target);
		double tarY = Math.sin(target);
		
		double x = lerp(curX, tarX, factor);
		double y = lerp(curY, tarY, factor);
		
		return Math.atan2(y, x);
	}
	
	public static double lerp(double n, double target, double factor) {
		return n + (target - n) * factor;
	}
	
	public static double distSq(double x1, double y1, double x2, double y2) {
		return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
	}
	
	public static double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt(distSq(x1, y1, x2, y2));
	}
}
