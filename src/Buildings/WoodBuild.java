package Buildings;

public class WoodBuild extends BaseBuild {
    private static double DefaultP = 0.5;
    private static double DefaultN = 2;

    private static double _P = 0.5;
    private static double _N = 2;



    public WoodBuild(){
        super("src/img/woodHouse.png", 0, 10);
    }

    public WoodBuild(double bornTime, double lifeTime){
        //this(500, 0);
        super("src/img/woodHouse.png", bornTime, lifeTime);

    }

    public WoodBuild(int x, int y){
        super("src/img/woodHouse.png", x, y);
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