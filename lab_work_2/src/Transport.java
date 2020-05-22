import javax.swing.*;

public abstract class Transport extends JLabel implements IBehaviour {
    public float x, y;

    public Transport(float x, float y) {
        this.x = x;
        this.y = y;
        this.setBounds((int) this.x, (int) this.y, 90 , 65 );
        this.setVisible(true);
    }

    @Override
    public void move() {

    }

    @Override
    public float getx() {
        return 0;
    }

    @Override
    public float gety() {
        return 0;
    }

    @Override
    public void setx(float x) {

    }

    @Override
    public void sety(float y) {

    }
}
