import java.awt.*;

public class Fruit {
    double xFruit = 0.0;
    double yFruit = 0.0;
    GamePanel gp;
    public Fruit(GamePanel gp) {
        this.gp = gp;
        updateFruitCoordinates();
    }
    public void updateFruitCoordinates() {
        xFruit = Math.floor((Math.random() * (((double) (gp.width - 100) / 10) - 10))) * 10;
        yFruit = Math.floor((Math.random() * (((double) (gp.height - 100) / 10) - 10))) * 10;
    }
    public void draw(Graphics2D g2) {
        g2.drawLine((int) xFruit, (int) yFruit, (int) xFruit, (int) yFruit);
    }
}
