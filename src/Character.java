import javax.swing.*;
import java.util.HashSet;
import java.util.Random;

public abstract class Character implements iBehaviour {

    private int x, y;
    private JLabel jlb;
    private int lifeTime, bornTime;
    private int id;

    @Override
    public void movexy(int x, int y) {}
    @Override
    public void setx(int x) {this.x = x;}
    @Override
    public void sety(int y) {this.y = y;}
    @Override
    public int getx() {return this.x;}
    @Override
    public int gety() {return this.y;}
    @Override
    public int getLifeTime(){return this.lifeTime;}
    @Override
    public int getBornTime(){return this.bornTime;}
    @Override
    public void setLifeTime(int lifeTime){this.lifeTime = lifeTime;}
    @Override
    public void setBornTime(int bornTime){this.bornTime = bornTime;}
    @Override
    public int getId(){return id;}
    @Override
    public void setId(HashSet<Integer> arr){
        while(true) {
            int tmp = new Random().nextInt();
            if(tmp < 0) tmp = -tmp;
            if(!arr.contains(tmp)){
                id = tmp;
                break;
            }
        }
    }
    @Override
    public JLabel getJlb(){return jlb;}
    @Override
    public void setJlb(JLabel jlb){this.jlb = jlb;}
}
