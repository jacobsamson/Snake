import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (600*600)/25;
    static final int DELAY = 75; //game pace
    //arrays to record x and y coordinates of snake parts
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int snakeParts = 6;
    int score = 0;
    int pointX;
    int pointY;
    char direction = 'R';
    boolean gameRunning = false;
    Timer timer;
    Random rand;

    GamePanel(){
        rand = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true); //JPANEL is focusable by default
        this.addKeyListener(new myKeyAdapter());
        startGame();

    }

    public void startGame(){
        gameRunning = true;
        newPoint();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        for(int i = 0; i <SCREEN_HEIGHT/UNIT_SIZE; i ++){
            g.drawLine(i*UNIT_SIZE, 0,i *UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }

        g.setColor(Color.red);
        g.fillOval(pointX, pointY, UNIT_SIZE, UNIT_SIZE);

        for(int i =0; i < snakeParts; i ++){
            if(i == 0){
                g.setColor(Color.green);
                g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
            }else{
                g.setColor(Color.orange);
                g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    public void newPoint(){
        pointX = rand.nextInt((int)SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        pointY = rand.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }

    public void move(){
        for(int i = snakeParts; i > 0; i --){
            System.out.println(i);
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        System.out.println("");

        switch(direction){
            case'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
        }

    }

    public void checkPoint(){

    }

    public void checkCollision(){

    }

    public void gameOver(Graphics g){

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameRunning){
            move();
            checkPoint();
            checkCollision();

        }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){

        }
    }
}
