import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;

    public class Motocycle extends Transport {

        private static final BufferedImage image;

        static {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("moto.png"));
            } catch (Exception e) {
                System.out.println("Image load error!");
            }
            image = img;
        }

        public Motocycle(float x, float y) {
            super(x, y);
            Image img = image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);
            this.setIcon(new ImageIcon(img));
            System.out.println("Motocycle: (" + x + ";" + y + ") ");
        }

    }

