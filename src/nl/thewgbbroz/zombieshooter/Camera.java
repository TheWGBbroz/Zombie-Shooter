package nl.thewgbbroz.zombieshooter;

public class Camera {
	private double x, y;
	
	public Camera() {
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getXI() {
		return (int) x;
	}
	
	public int getYI() {
		return (int) y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
}
