package Buildings;
import javax.swing.*;
import java.util.UUID;


public  abstract class BaseBuild implements IBehavior {
    private int _x, _y;
    protected UUID _id;
    protected double _bornTime;
    public double GetBornTime() {return _bornTime;}

    protected double _lifeTime = 5;
    public double GetLifeTime(){return _lifeTime;}
    public void SetLifeTime(double value){ _lifeTime = value;}

    private ImageIcon _image;
    private int Width;
    private int Height;
    public JLabel label = new JLabel();

    public void setX(int x)
    {
        _x = x;
        label.setBounds(_x, _y, Width, Height);
    }

    public void setY(int y)
    {
        _y = y;
        label.setBounds(_x, _y, Width, Height);
    }

    public int getX()
    {
        return _x;
    }

    public int getY()
    {
        return _y;
    }

    public int getWidth(){
        return Width;
    }

    public int getHeight(){
        return Height;
    }

    public void move(int dx, int dy) {
        _x += dx;
        _y += dy;
        label.setBounds(_x, _y, Width, Height);
    }

    public BaseBuild(String path, double bornTime, double lifeTime){
        _id = UUID.randomUUID();
        _bornTime = bornTime;
        _lifeTime = lifeTime;
        _x = 0;
        _y = 0;
        loadImage(path);
        label.setBounds(_x, _y, Width, Height);
        label.setIcon(_image);
    }
    public BaseBuild(String path, int x, int y){
        _id = UUID.randomUUID();
        _x = x;
        _y = y;
        loadImage(path);
        label.setBounds(_x, _y, Width, Height);
        label.setIcon(_image);
    }

    private void loadImage(String path)
    {
        try
        {
            _image = new ImageIcon(path);
        } catch (Exception e) {
            //TODO: handle exception
        }
        Width = _image.getIconWidth();
        Height = _image.getIconHeight();
    }

    @Override
    public String toString()
    {
        return this.getClass() + " Время рождения: " + _bornTime/1000 + " cек";
    }
}