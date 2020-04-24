import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class Drone extends Character {

    static public int beeDrone = 0;
    ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getResource(
            "/Images/Drone.png")).getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));

    public Drone(int x, int y, int lifeTime, int bornTime, HashSet<Integer> arr){
        JLabel jlb = new JLabel();
        jlb.setBounds(x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        jlb.setIcon(imageIcon);
        setJlb(jlb);
        MainProgram.bees.add(getJlb(),0,0);
        MainProgram.bees.repaint();
        setx(x);
        sety(y);
        setLifeTime(lifeTime);
        setBornTime(bornTime);
        setId(arr);
    }
}
