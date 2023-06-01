import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Snake {
    // Snake is divided into small segments which are drawn and edited on each draw call
    int numSegments = 10;
    String direction = "right";
    final int xStart = 0; // Starting x coordinate
    final int yStart = 250; // Starting y coordinate
    final int diff = 10; // Change in position each frame

    ArrayList<Integer> xCor = new ArrayList<Integer>();
    ArrayList<Integer> yCor = new ArrayList<Integer>();

    GamePanel gp;
    KeyHandler keyH;
    public Snake(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        for (int i = 0; i < numSegments; i++) {
            xCor.add(xStart + i * diff);
            yCor.add(yStart);
        }
        updateSnakeCoordinates();
    }
    public void checkGameStatus() {
        if (xCor.get(xCor.size() - 1) > gp.width || xCor.get(xCor.size() - 1) < 0 || yCor.get(yCor.size() - 1) > gp.height || yCor.get(yCor.size() - 1) < 0 || checkSnakeCollision()) {
            gp.stopGameThread();
        }
    }
    public boolean checkSnakeCollision() {
        int snakeHeadX = xCor.get(xCor.size() - 1);
        int snakeHeadY = yCor.get(yCor.size() - 1);
        for(int i = 0; i < xCor.size() - 1; i++) {
            if (xCor.get(i) == snakeHeadX && yCor.get(i) == snakeHeadY) {
                return true;
            }
        }
        return false;
    }
    public void updateSnakeDirection() {
        if (keyH.upPressed && !Objects.equals(direction, "down")) {
            direction = "up";
        } else if (keyH.downPressed && !Objects.equals(direction, "up")) {
            direction = "down";
        } else if (keyH.leftPressed && !Objects.equals(direction, "right")) {
            direction = "left";
        } else if (keyH.rightPressed && !Objects.equals(direction, "left")) {
            direction = "right";
        }
    }
    public void updateSnakeCoordinates() {
        updateSnakeDirection();
        checkGameStatus();
        for (int i = 0; i < numSegments - 1; i++) {
            xCor.set(i, xCor.get(i + 1));
            yCor.set(i, yCor.get(i + 1));
        }
        switch (direction) {
            case "right" -> {
                xCor.set(numSegments - 1, xCor.get(numSegments - 2) + diff);
                yCor.set(numSegments - 1, yCor.get(numSegments - 2));
            }
            case "up" -> {
                xCor.set(numSegments - 1, xCor.get(numSegments - 2));
                yCor.set(numSegments - 1, yCor.get(numSegments - 2) - diff);
            }
            case "left" -> {
                xCor.set(numSegments - 1, xCor.get(numSegments - 2) - diff);
                yCor.set(numSegments - 1, yCor.get(numSegments - 2));
            }
            case "down" -> {
                xCor.set(numSegments - 1, xCor.get(numSegments - 2));
                yCor.set(numSegments - 1, yCor.get(numSegments - 2) + diff);
            }
        }
    }
    public void draw(Graphics2D g2) {
        for (int i = 0; i < numSegments - 1; i++) {
            g2.drawLine(xCor.get(i), yCor.get(i), xCor.get(i + 1), yCor.get(i + 1));
        }
    }
}
