import javax.swing.*;
public abstract class Character implements iBehaviour {
    int x, y;
    JLabel jlb;
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
}