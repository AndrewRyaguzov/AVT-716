package Buildings;

public interface IBehavior
{
    void setX(int x);
    void setY(int y);
    int getX();
    int getY();

    void move(int dx, int dy); //смещение относительно текущей позиции
}