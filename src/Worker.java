import javax.swing.*;
import java.awt.*;

public class Worker extends Character{

    static public int beeWorker = 0;
    ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getResource(
            "/Images/Worker.png")).getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));

    public Worker(int x, int y){
        jlb = new JLabel();
        jlb.setBounds(x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        jlb.setIcon(imageIcon);
        Main.bees.add(jlb,0,0);
        Main.bees.repaint();
        setx(x);
        sety(y);
    }
}
