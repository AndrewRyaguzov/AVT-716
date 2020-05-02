package SubClasses;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Area {
    int _leftX;
    int _leftY;
    int _rightX;
    int _rightY;

    int _endX;
    public int GetEndX(){return  _endX;}
    int _endY;
    public int GetEndY(){return  _endY;}

    public Area(int leftX, int leftY, int rightX, int rightY)
    {
        _leftX = leftX;
        _leftY = leftY;

        _rightX = rightX;
        _rightY = rightY;
    }

    public boolean IsIn(int x, int y)
    {
        boolean xIsIn = false;
        boolean yIsIn = false;
        if(_leftX < x && _rightX > x) xIsIn = true;
        if(_leftY < y && _rightY > y) yIsIn = true;
        return  xIsIn && yIsIn;
    }

    public int GetHeight(){return Math.abs(_rightY - _leftY);}
    public int GetWidth(){return  Math.abs(_rightX - _leftX);}

    public void CalculateEndPoint(int width, int height)
    {
        Random myRand = new Random();
        _endX = myRand.nextInt(GetWidth() - width) +  _leftX;
        _endY = myRand.nextInt(GetHeight() - height) +  _leftY;
    }

    public void SetEndPoint(int x, int y)
    {
        _endX = x;
        _endY = y;
    }
}
