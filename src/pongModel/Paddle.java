package pongModel;

import java.awt.Color;
import java.awt.Graphics;

public class Paddle {

	protected int paddleY;
	protected int pvelY;
	protected int paddleX;
	protected int paddleHeight;
	protected int paddleWidth;
	Board board = new Board();
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);
		
		//AI paddle and score
		//g.fillRect(aiPaddleX, aiPaddleY, paddleWidth, paddleHeight);
	}
	
	public void paddleWallLimits() {
		if (getPaddle_Y() < 0) {
			setPaddle_Y_velocity(0);
			setPaddle_Y(1);
		}
		
		if (getPaddle_Y() > board.getHeight() - getPaddleHeight()) {
			setPaddle_Y_velocity(0);
			setPaddle_Y(board.getHeight() - getPaddleHeight()- 20);
		}
	}
	
	public void move() {
		setPaddle_Y(getPaddle_Y()+ getPaddle_Y_velocity());
	}
	
	public void setPaddle_X(int d) {
		paddleX = d;
	}
	
	public void setPaddle_Y(int d) {
		paddleY = d;
	}
	
	public void setPaddle_Y_velocity(int d) {
		pvelY = d;
	}
	
	public void setPaddleHeight(int d) {
		paddleHeight = d;
	}
	
	public void setPaddleWidth(int d) {
		paddleWidth = d;
	}
	
	public int getPaddle_X() {
		return paddleX;
	}
	
	public int getPaddle_Y() {
		return paddleY;
	}
	
	public int getPaddle_Y_velocity() {
		return pvelY;
	}
	
	public int getPaddleHeight() {
		return paddleHeight;
	}
	
	public int getPaddleWidth() {
		return paddleWidth;
	}

}
