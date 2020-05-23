import javax.swing.*;
import java.awt.*;

public abstract class Transport extends JLabel implements IBehaviour {
    private float x, y;
    public long time_of_birth;
    public float lifetime;
    public int identifier;
    public String name;

    public Transport(float x, float y, long time, float lifetime) {
        this.x = x;
        this.y = y;
        time_of_birth = time;
        this.lifetime = lifetime;
        this.setBounds((int) this.x, (int) this.y, 90, 65);
        this.setVisible(true);
    }
    public boolean update(float delta) {
        lifetime -= delta;
        if (lifetime <= 0)
            return true;
        return false;
    }

    public float getTime() {
        return time_of_birth;
    }

    public float getLifeTime() {
        return lifetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        identifier = ID;
    }

    public int getID() {
        return identifier;
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



