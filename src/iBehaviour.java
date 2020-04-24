import javax.swing.*;
import java.util.HashSet;

public interface iBehaviour {
    void movexy(int x, int y);
    void setx(int x);
    void sety(int y);
    int getx();
    int gety();
    int getLifeTime();
    void setLifeTime(int lifeTime);
    int getBornTime();
    void setBornTime(int bornTime);
    int getId();
    void setId(HashSet<Integer> arr);
    JLabel getJlb();
    void setJlb(JLabel jlb);
}
