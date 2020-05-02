package Buildings;

public class CapitalBuild extends BaseBuild {
    private static double DefaultP = 0.5;
    private static double DefaultN = 3;

    private static double _P = 0.5;
    private static double _N = 3;

    public CapitalBuild(){
        super("src/img/capitalHouse.png", 0, 10);
    }

    public CapitalBuild(double bornTime, double lifeTime)
    {
        super("src/img/capitalHouse.png", bornTime, lifeTime);
    }

    public CapitalBuild(int x, int y){
        super("src/img/CapitalHouse.png", x, y);
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
