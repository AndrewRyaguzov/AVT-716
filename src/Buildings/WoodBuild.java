package Buildings;

import SubClasses.Area;

import javax.swing.*;

public class WoodBuild extends BaseBuild {
    private static double DefaultP = 0.5;
    private static double DefaultN = 2;

    private static double _P = 0.5;
    private static double _N = 2;

    public WoodBuild(double bornTime, double lifeTime, JLayeredPane habbitViewLayeredPane, int speed){
        //this(500, 0);
        super("src/img/woodHouse.png", bornTime, lifeTime, habbitViewLayeredPane, speed);

        int width = (int) habbitViewLayeredPane.getSize().getWidth();
        int height = (int) habbitViewLayeredPane.getSize().getHeight();

        _finishArea = new Area(width/2, height/2, width, height);
        CalculateFinishPosition();
    }

    public static double GetDefaultN(){
        return DefaultN;
    }

    public static double GetN(){return _N;}
    public static void SetN(double N){
        _N = N;
    }

    public static double GetP(){
        return _P;
    }
    public static void SetP(double P){
        _P = P;
    }
}