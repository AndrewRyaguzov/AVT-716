package Buildings;
import SubClasses.Area;

import javax.swing.*;
import java.util.Random;
import java.util.UUID;


public  abstract class BaseBuild implements IBehavior {
    private int _speed;
    private int _x, _y;
    private JLayeredPane _habbitViewLayeredPane;

    protected UUID _id;
    protected double _bornTime;
    public double GetBornTime() {return _bornTime;}

    protected double _lifeTime;
    public double GetLifeTime(){return _lifeTime;}
    public void SetLifeTime(double value){ _lifeTime = value;}

    protected Area _finishArea;

    private boolean _isOnPlace;

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

    public BaseBuild(String path, double bornTime, double lifeTime, JLayeredPane habbitViewLayeredPane, int speed){
        _id = UUID.randomUUID();
        _bornTime = bornTime;
        _lifeTime = lifeTime;
        _habbitViewLayeredPane = habbitViewLayeredPane;
        _speed = speed;

        loadImage(path);
        CalculateCreationPosition();
        label.setBounds(_x, _y, Width, Height);
        label.setIcon(_image);

        int intIndex = _y + Height;
        Integer index = Integer.valueOf(intIndex);
        _habbitViewLayeredPane.add( label, index, -1);
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

    private void CalculateCreationPosition()
    {
        Random myRand = new Random();
        _x = myRand.nextInt((int) _habbitViewLayeredPane.getSize().getWidth() - Width);
        _y = myRand.nextInt((int) _habbitViewLayeredPane.getSize().getHeight() - Height);
    }

    protected void CalculateFinishPosition()
    {
        if(_finishArea.IsIn(_x, _y))
        {
            _finishArea.SetEndPoint(_x, _y);
            return;
        }

        _finishArea.CalculateEndPoint(Width, Height);
    }


    public void move(int dx, int dy) {
        if(_isOnPlace = true) return;

        if(_x != _finishArea.GetEndX())
        {
            if(_x > _finishArea.GetEndX())
                _x--;
            else
                _x++;
            move();
            return;
        }

        if(_y !=  _finishArea.GetEndY()) {
            if(_y > _finishArea.GetEndY())
                _y--;
            else
                _y++;
            move();
            return;
        }

        _isOnPlace = true;
    }

    private void move(){
        label.setBounds(_x, _y, Width, Height);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Thread has been interrupted");
        }
    }

    public void Move()
    {
        move(0, 0);
    }

    @Override
    public String toString()
    {
        return this.getClass() + " Время рождения: " + _bornTime/1000 + " cек";
    }
}