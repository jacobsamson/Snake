import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 500;
    static final int SCREEN_HEIGHT = 500;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_HEIGHT * SCREEN_WIDTH)/UNIT_SIZE;
    int delay = 100;
    //arrays to record x and y coordinates of snake parts
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int snakeParts = 6;
    int score = 0;
    //coordinates of the points/score the snake has to touch
    int pointX;
    int pointY;
    int quickPointX;
    int quickPointY;
    int slowPointX;
    int slowPointY;
    char direction = 'R';
    boolean gameRunning = false;
    Timer timer;
    Random rand;

    GamePanel(){
        rand = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }

    /**
     * Begins the timer and draws the first point. Sets gameRunning to true.
     */
    public void startGame(){
        gameRunning = true;
        newPoint();
        newQuickPoint();
        newSlowPoint();
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Draws a grid, the snake, and the points on the screen if gameRunning is true. If false, draws the game over screen.
     */
    public void draw(Graphics g){
        if(gameRunning) {
            //draws grid on game screen
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            //draws points
            g.setColor(Color.red);
            g.fillOval(pointX, pointY, UNIT_SIZE, UNIT_SIZE);

            g.setColor(Color.orange);
            g.fillRect(slowPointX + 3, slowPointY + 10,UNIT_SIZE - 5, UNIT_SIZE/4);

            g.setColor(Color.blue);
            g.fillRect(quickPointX + 3, quickPointY + 10,UNIT_SIZE - 5, UNIT_SIZE/4);
            g.fillRect(quickPointX + 10, quickPointY + 3,UNIT_SIZE/4, UNIT_SIZE - 5);

            //draws snake
            for (int i = 0; i < snakeParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    //draws eye and tongue on snake head depending on direction snake is facing
                    if(direction == 'R') {
                        g.setColor(Color.black);
                        g.fillRect(x[i] + 3, y[i] - 3, 10, 10);
                        g.setColor(Color.white);
                        g.fillRect(x[i] + 3, y[i] - 3, 5, 5);
                        g.setColor(Color.pink);
                        g.fillRect(x[i] + 20, y[i] + 10, 20, 5);
                    }
                    if(direction == 'L') {
                        g.setColor(Color.black);
                        g.fillRect(x[i] + 6, y[i] - 3, 10, 10);
                        g.setColor(Color.white);
                        g.fillRect(x[i] + 6, y[i] - 3, 5, 5);
                        g.setColor(Color.pink);
                        g.fillRect(x[i] - 20, y[i] + 10, 20, 5);
                    }
                    if(direction == 'U') {
                        g.setColor(Color.black);
                        g.fillRect(x[i] - 6, y[i] +10, 10, 10);
                        g.setColor(Color.white);
                        g.fillRect(x[i] - 6, y[i] +10, 5, 5);
                        g.setColor(Color.pink);
                        g.fillRect(x[i]+12, y[i] - 20, 5, 20);
                    }
                    if(direction == 'D') {
                        g.setColor(Color.black);
                        g.fillRect(x[i] - 6, y[i] + 10, 10, 10);
                        g.setColor(Color.white);
                        g.fillRect(x[i] - 6, y[i] + 10, 5, 5);
                        g.setColor(Color.pink);
                        g.fillRect(x[i]+12, y[i] + 20, 5, 20);
                    }
                }else {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Sans Serif", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score:" + score, ((SCREEN_WIDTH -  metrics.stringWidth("Score:" + score))/2), g.getFont().getSize());
        }else{
            gameOver(g);
        }
    }

    /**
     * Creates new points at random locations
     */
    public void newPoint(){
        pointX = rand.nextInt((int)SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        pointY = rand.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;

        while((slowPointX == pointX && slowPointY == pointY) || (pointX == quickPointX && pointY == quickPointY)) {
            pointX = rand.nextInt((int) SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            pointY = rand.nextInt((int) SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        }
    }
    public void newSlowPoint(){
        slowPointX = rand.nextInt((int) SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        slowPointY = rand.nextInt((int) SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;

        while((slowPointX == pointX && slowPointY == pointY) || (slowPointX == quickPointX && slowPointY == quickPointY)) {
            slowPointX = rand.nextInt((int) SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            slowPointY = rand.nextInt((int) SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        }
    }
    public void newQuickPoint(){
        quickPointX = rand.nextInt((int)SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        quickPointY = rand.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;

        while((quickPointX == pointX && quickPointY == pointY) || (quickPointX == slowPointX && quickPointY == slowPointY)) {
            quickPointX = rand.nextInt((int) SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            quickPointY = rand.nextInt((int) SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        }
    }

    /**
     * Moves the snake by updating the coordinates of each snake part
     */
    public void move(){
        for(int i = snakeParts - 1; i > 0; i --){
            //System.out.println(i);
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
        }
    }

    /**
     * Registers if the snake runs into the edge of the screen, a point, or itself
     */
    public void checkCollision(){
        //checks if snake runs into itself
        for(int i = snakeParts; i > 0; i --){
            if(x[0] == x[i] && y[0] == y[i]){
                gameRunning = false;
            }
        }
        //checks if snake runs into ends of screen
        if(x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT){
            gameRunning = false;
        }
        //checks if snake runs into point
        if(x[0] == pointX && y[0] == pointY){
            score++;
            snakeParts++;
            newPoint();
        }

        //checks if snake runs into quick point
        if(x[0] == quickPointX && y[0] == quickPointY){
            delay++;
            newQuickPoint();
            timer = new Timer(delay, this);
            timer.start();
        }

        //checks if snake runs into slow point
        if(x[0] == slowPointX && y[0] == slowPointY){
            snakeParts--;
            newSlowPoint();
        }
        if(!gameRunning)
            timer.stop();
    }

    /**
     * Draws the game over screen
     */
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Sans Serif", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH -  metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT /2);
        g.drawString("Score:" + score, ((SCREEN_WIDTH -  metrics.stringWidth("Score:" + score))/2), g.getFont().getSize());
    }

    @Override
    /**
     * Registers actions then moves the snake, checks for snake collisions, and updates screen.
     */
    public void actionPerformed(ActionEvent e) {
        if(gameRunning){
            move();
            checkCollision();
        }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter {
        @Override
        /**
         * Changes the direction of snake if the player presses the arrow keys or WASD.
         *
         * @param e the key pressed
         */
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    //stops player from making snake turn 180
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
