import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddles paddles1;
    Paddles paddles2;
    Ball ball;
    Score score;


    GamePanel(){
        newPaddles();
        newBall();
        score = new Score(GAME_HEIGHT, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall(){
        random = new Random();
        ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
    }
    public void newPaddles(){
        paddles1 = new Paddles(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddles2 = new Paddles(GAME_WIDTH - PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }
    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0,0, this);
    }
    public void draw(Graphics g){
        paddles1.draw(g);
        paddles2.draw(g);
        ball.draw(g);
        score.draw(g);
    }
    public void move(){
        paddles1.move();
        paddles2.move();
        ball.move();
    }
    public void checkCollision(){
        //ball won't go out of panel
        if (ball.y <= 0){
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.y >= GAME_HEIGHT - BALL_DIAMETER){
            ball.setYDirection(-ball.yVelocity);
        }

        //ball will bounce off paddles
        if (ball.intersects(paddles1)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; //incises the speed of the ball times one when collided with paddle for difficult gaming
            if(ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if (ball.intersects(paddles2)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; //incises the speed of the ball times one when collided with paddle for difficult gaming
            if(ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        //paddles won't go out of panel
        if (paddles1.y<=0)
            paddles1.y = 0;
        if (paddles1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddles1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        if (paddles2.y<=0)
            paddles2.y = 0;
        if (paddles2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddles2.y = GAME_HEIGHT - PADDLE_HEIGHT;

        //lets the player score and creates new paddles and ball
        if (ball.x <= 0 ){
            score.player2++;
            newPaddles();
            newBall();
            System.out.println("Player 2: " + score.player2);
        }
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER ){
            score.player1++;
            newPaddles();
            newBall();
            System.out.println("Player 1: " + score.player1);
        }

    }
    public void run(){
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double nanoSec = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true){
            long now = System.nanoTime();
            delta += (now -lastTime) / nanoSec;
            lastTime = now;
            if (delta >= 1){
                move();
                checkCollision();
                repaint();
                delta --;
            }
        }
    }
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddles1.keyPressed(e);
            paddles2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            paddles1.keyReleased(e);
            paddles2.keyReleased(e);
        }
    }
}
