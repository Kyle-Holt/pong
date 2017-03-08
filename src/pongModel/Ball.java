package pongModel;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {

	protected static double startingX_location;
	protected double x_location;
	protected double velX;
	protected double y_location;
	protected double velY;
	protected double vX;
	protected double vY;
	protected static int radius;
	Board board = new Board();
	Paddle padddle = new Paddle();
	//double relativeIntersectY;
	//double normalizedRelativeIntersectY;
	double maxBounceAngle;
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		//args are x, y, width, height
		
		g.fillOval((int) x_location - radius, (int) y_location - radius, 2*radius, 2*radius);
	}
	
	public void ballWallCollision() {
		if (ballTop() < getRadius()) {
			setY_velocity(-getY_velocity());
		}
		if (ballBottom() > board.getHeight() - 3*getRadius()) {
			setY_velocity(-getY_velocity());
		}
		setX(getX()+getX_velocity());
		
		setY(getY()+getY_velocity());
	}
	
	public void ballPaddleCollision(double a, double b) {
		double bounceAngle = a * b;
		setX_acceleration(Math.cos(Math.toRadians(bounceAngle)));
		setY_acceleration(-Math.sin(Math.toRadians(bounceAngle)));
		if (getY_acceleration() <= 0.2 && getY_acceleration() >= -0.2) {
			setX_velocity(3*getX_acceleration()); 
		}
		setX_velocity(getX_velocity() + (0.5)*getX_acceleration());
		setY_velocity(3*getY_acceleration());
	}
	
	public void setRadius(int d) {
		radius = d;
	}
	
	public void setXStart(double d) {
		startingX_location = d;
	}
	
	public void setX(double d) {
		x_location = d;
	}
	
	public void setY(double d) {
		y_location = d;
	}
	
	public void setX_velocity(double d) {
		velX = d;
	}
	
	public void setY_velocity(double d) {
		velY = d;
	}
	
	public void setX_acceleration(double d) {
		vX = d;
	}
	
	public void setY_acceleration(double d) {
		vY = d;
	}
	
	public double getX_velocity() {
		return velX;
	}
	
	public double getY_velocity() {
		return velY;
	}
	
	public double getX_acceleration() {
		return vX;
	}
	
	public double getY_acceleration() {
		return vY;
	}
	
	public double getX() {
		return x_location;
	}
	
	public double getY() {
		return y_location;
	}
	
	public double getRadius() {
		return radius;
	}
	
	double ballLeft() {
		return x_location - radius;
	}
	
	double ballRight() {
		return x_location + radius;
	}
	
	double ballTop() {
		return y_location + radius;
	}
	
	double ballBottom() {
		return y_location - radius;
	}



}
