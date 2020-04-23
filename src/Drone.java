import javax.swing.*;
import java.awt.*;

public class Drone extends Character {

    static public int beeDrone = 0;
    ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getResource(
            "/Images/Drone.png")).getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));

    public Drone(int x, int y){
        jlb = new JLabel();
        jlb.setBounds(x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        jlb.setIcon(imageIcon);
        Main.bees.add(jlb,0,0);
        Main.bees.repaint();
        setx(x);
        sety(y);
    }
}
