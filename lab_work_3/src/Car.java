import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;

public class Car extends Transport {

    private static final BufferedImage image;

    static {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("car.jpg"));
        } catch (Exception e) {
            System.out.println("Image load error!");
        }
        image = img;
    }

    public Car(float x, float y) {
        super(x, y);
        name="Car";
        Image img = image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);
        this.setIcon(new ImageIcon(img));
        System.out.println("Car: (" + x + ";" + y + ") ");
    }
}
