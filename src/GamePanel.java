import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // VARIABLES
    int FPS = 15;
    public int width = 500;
    public int height = 500;
    public int score = 0;
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    public Fruit fruit = new Fruit(this);
    public Snake snake = new Snake(this, keyH);
    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void startGameThread() {
        gameThread = new Thread(this); // Repeatedly runs until the program closes
        gameThread.start(); // Calls the run method
    }
    public void stopGameThread() {
        System.out.println("Game ended! Your score was: " + score);
        System.exit(ABORT);
    }
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS; // 0.01666666 (1 / 60) seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) { // While gameThread exists
            // Wait for interval to finish
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                // Update information (EX: character positions)
                update();
                // Draw updated information to the screen
                repaint();
                delta--;
            }
        }
    }
    public void update() {
        snake.updateSnakeCoordinates();
        checkForFruit();
    }
    public void checkForFruit() {
        if (snake.xCor.get(snake.xCor.size() - 1) == fruit.xFruit && snake.yCor.get(snake.yCor.size() - 1) == fruit.yFruit) {
            score += 1;
            snake.xCor.add(0, snake.xCor.get(0));
            snake.yCor.add(0, snake.yCor.get(0));
            snake.numSegments++;
            fruit.updateFruitCoordinates();
            System.out.println("Score: " + score);
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; // Change g to Graphics2D class which has more control
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(10));
        snake.draw(g2);
        fruit.draw(g2);
        g2.dispose();
    }
}
