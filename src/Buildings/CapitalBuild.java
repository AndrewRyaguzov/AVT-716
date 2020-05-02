package Buildings;

import SubClasses.Area;

import javax.swing.*;

public class CapitalBuild extends BaseBuild {
    private static double DefaultP = 0.5;
    private static double DefaultN = 3;

    private static double _P = 0.5;
    private static double _N = 3;

    public CapitalBuild(double bornTime, double lifeTime, JLayeredPane habbitViewLayeredPane, int speed)
    {
        super("src/img/capitalHouse.png", bornTime, lifeTime, habbitViewLayeredPane, speed);

        int width = (int) habbitViewLayeredPane.getSize().getWidth();
        int height = (int) habbitViewLayeredPane.getSize().getHeight();

        _finishArea = new Area(0, 0, width/2, height/2);
        CalculateFinishPosition();
    }

    public static double GetDefaultN(){
        return DefaultN;
    }

    public static double GetN(){
        return _N;
    }
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
