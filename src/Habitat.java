import javax.swing.*;
import java.awt.*;

public class Habitat {

    private int WIDTH = Main.beesWIDTH, HEIGHT = Main.HEIGHT;
    static public int N1 = 3, N2 = 2, K = 40, P = 80;
    private int imageSize = 50;
    private ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getResource(
            "/Images/sky2.png")).getImage().getScaledInstance(WIDTH,HEIGHT, Image.SCALE_DEFAULT));

    Habitat(){
        JLabel tmp = new JLabel();
        tmp.setBounds( 0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        tmp.setIcon(imageIcon);
        Main.bees.add(tmp, 0, 0);
        Main.bees.repaint();
    }
    void update(int time){
        if(time % N2 == 0)
        {
            int random = (int)Math.floor(Math.random()*100);
            if(random <= P) {
                int x = (int) Math.floor(Math.random() * (WIDTH - imageSize));
                int y = (int) Math.floor(Math.random() * (HEIGHT - imageSize));
                Worker worker = new Worker(x, y);
                Worker.beeWorker++;
            }
        }
        if(time % N1 == 0 && Worker.beeWorker != 0){
            int dronePercent = 100*Drone.beeDrone/(Drone.beeDrone + Worker.beeWorker);
            if(dronePercent < K){
                int x = (int)Math.floor(Math.random()*(WIDTH-imageSize));
                int y = (int)Math.floor(Math.random()*(HEIGHT-imageSize));
                Drone drone = new Drone(x, y);
                Drone.beeDrone++;
            }
        }
    }
    void clear(){
        Main.bees.removeAll();
        new Habitat();
    }
}
