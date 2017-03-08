package pongModel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class pongGame extends JPanel implements ActionListener, KeyListener {

	//sleep for 5 ms, this refers to ActionListener
	Timer tm = new Timer(5, this);
	Ball ball = new Ball();
	Paddle paddle = new Paddle();
	Paddle AIpaddle = new Paddle();
	static Board board = new Board();

	//static int width;
	//static int height;
	int score = 0;
	int aiScore = 0;
	double maxBounceAngle = 75;
	double maximumSpeed = 3;
	int paddleX;
	int paddleY;
	int paddleH;
	int paddleW;

	
	public pongGame() {
		//width = 600;
		//height = 400;
		
		ball.setRadius(10);
		
		paddle.setPaddle_Y(200);
		paddle.setPaddle_Y_velocity(0);
		paddle.setPaddle_X(30);
		paddle.setPaddleHeight(100);
		paddle.setPaddleWidth(10);
		
		AIpaddle.setPaddle_X(board.getWidth() - 3*paddle.getPaddle_X());
		AIpaddle.setPaddle_Y(paddle.getPaddle_Y());
		AIpaddle.setPaddleHeight(100);
		AIpaddle.setPaddleWidth(10);
		AIpaddle.setPaddle_Y_velocity(0);
		
		tm.start();
		//this refers to the KeyListener
		addKeyListener(this);
		//enables KeyListener
		setFocusable(true);
		//shift, tab usage
		setFocusTraversalKeysEnabled(false);
		reset();
		
	}
	
	//Graphics class makes shapes
	//paintComponent has double buffering
	public void paintComponent(Graphics g) {
		//ensure it's displayed properly, repaints background
		super.paintComponent(g);
		
		ball.draw(g);
		
		paddle.draw(g);
		g.drawString(Integer.toString(score), 0, 10);
		
		//AI paddle and score
		AIpaddle.draw(g);
		g.drawString(Integer.toString(aiScore), board.getWidth()-25, 10);
		
		
		//telling Java to start timer, then start ActionListener
		tm.start();
		
		if (ball.getX() < 0) {
			repaint();
			aiScore += 1;
			g.drawString(Integer.toString(aiScore), (board.getWidth()-100)/2, board.getHeight()/2);
			reset();
		}
		if (ball.ballRight() > board.getWidth()) {
			repaint();
			score += 1;
			g.drawString(Integer.toString(score), (board.getWidth()-100)/2, board.getHeight()/2);
			reset();
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		//only method that animates movement
		// every tm, do this
	
		ball.ballWallCollision();
		
		paddle.paddleWallLimits();
		
		paddle.move();
		

		if (collision()) {
			side();
			if (ball.getX() < board.getWidth()/2) {
				ball.setX(paddleX + paddleW + ball.getRadius());
			}
			else {
				ball.setX(AIpaddle.getPaddle_X() - AIpaddle.getPaddleWidth() - 1);
			}
			ball.setX_velocity(-ball.getX_velocity());
			double relativeIntersectY = (paddleY + (paddleH/2)) - ball.getY();
			double normalizedRelativeIntersectY = (relativeIntersectY/(paddleH/2));
			ball.ballPaddleCollision(normalizedRelativeIntersectY, maxBounceAngle);
		}
		
		//ai intelligence
		if (ball.ballTop() < AIpaddle.getPaddle_Y()) {
			AIpaddle.setPaddle_Y(AIpaddle.getPaddle_Y() - 5);
		}
		if (ball.ballBottom() > AIpaddle.getPaddle_Y() + AIpaddle.getPaddleHeight()) {
			AIpaddle.setPaddle_Y(AIpaddle.getPaddle_Y() + 5);
		}
		
		repaint();
	}
	


	public static void main(String[] args) {
		// p is a JPanel
		pongGame p = new pongGame();
		
		JFrame jf = new JFrame();
		
		jf.setTitle("Pong");
		jf.setSize(board.getWidth(), board.getHeight());
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//add JPanel onto frame
		jf.add(p);
		
	}

	public void keyPressed(KeyEvent e) {
		// gets the code from the keyboard
		int c = e.getKeyCode();
		
		if (c == KeyEvent.VK_UP) {
			paddle.setPaddle_Y_velocity(-3);
		}
		else if (c == KeyEvent.VK_DOWN) {
			paddle.setPaddle_Y_velocity(3);
		}
		else if (c == KeyEvent.VK_R) {
			reset();
		}
	}

	public void keyReleased(KeyEvent e) {
		// just need to say what to do when key is released
		paddle.setPaddle_Y_velocity(0);
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean collision() {
		boolean returnValue = false;
		double ballSide;
		side();
		if (ball.getX() < board.getWidth()/2) {
			ballSide = ball.ballLeft();
		}
		else {
			ballSide = ball.ballRight();
		}
		if ((ballSide >= paddleX) && (ballSide <= paddleX + paddleW)) {
			if ((ball.ballTop() >= paddleY)&&(ball.ballBottom() <= paddleY + paddleH)){
				returnValue = true;
			}
		}
		return returnValue;	
	}
	
	public void reset() {
		ArrayList<Double> direction = new ArrayList<Double>(); 
		direction.add((double) -2);
		direction.add((double) -3);
		direction.add((double) 2);
		direction.add((double) 3);
		
		Random r = new Random();
		double Low = 0;
		double High = 3;
		double Result = r.nextInt((int) (High-Low)) + Low;
		double newXDirection = direction.get((int) Result);
		
		ball.setX_velocity(newXDirection);
		
		double yResult = r.nextInt((int) (High-Low)) + Low;
		double newYDirection = direction.get((int) yResult);
		
		ball.setY_velocity(newYDirection);
		
		ball.setX(board.getWidth()/2);
		ball.setXStart(ball.getX());
		
		Low = ball.getRadius();
		High = board.getHeight() - 3*ball.getRadius();
		double middleResult = r.nextInt((int) (High-Low)) + Low;
		
		ball.setY(middleResult);
		
		
	}
	//alters paddle variables depending on side of board
	public void side() {
		if (ball.getX() < board.getWidth()/2) {
			paddleX = paddle.getPaddle_X();
			paddleY = paddle.getPaddle_Y();
			paddleH = paddle.getPaddleHeight();
			paddleW = paddle.getPaddleWidth();
		}
		else {
			paddleX = AIpaddle.getPaddle_X();
			paddleY = AIpaddle.getPaddle_Y();
			paddleH = AIpaddle.getPaddleHeight();
			paddleW = AIpaddle.getPaddleWidth();
		}
	}
}
