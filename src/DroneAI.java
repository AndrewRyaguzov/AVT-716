import java.util.Random;

public class DroneAI extends BaseAI {
    DroneAI(){
        super("DroneAI");
        thread.start();
    }
    @Override
    synchronized public void run(){
        int N = 2000;
        int route = 0;
        long timePrev1 = 0;
        long timePrev2 = 0;
        while (true) {
            if(stop){
                pause();
            }
            long timeCur = System.currentTimeMillis();
            long step1 = timeCur - timePrev1;
            long step2 = timeCur - timePrev2;
            if (step1 >= 20) {
                if(step2 >= N)
                {
                    route =new Random().nextInt(7);
                    timePrev2 = timeCur;
                }
                for (Character bee : arrBees) {
                    if (bee instanceof Drone) {
                        switch (route){
                            case 0:
                            {
                                bee.sety(bee.gety() - bee.getV());
                                break;
                            }
                            case 1:
                            {
                                bee.sety(bee.gety() - bee.getV());
                                bee.setx(bee.getx() + bee.getV());
                                break;
                            }
                            case 2:
                            {
                                bee.setx(bee.getx() + bee.getV());
                                break;
                            }
                            case 3:
                            {
                                bee.sety(bee.gety() + bee.getV());
                                bee.setx(bee.getx() + bee.getV());
                                break;
                            }
                            case 4:
                            {
                                bee.sety(bee.gety() + bee.getV());
                                break;
                            }
                            case 5:
                            {
                                bee.sety(bee.gety() + bee.getV());
                                bee.setx(bee.getx() - bee.getV());
                                break;
                            }
                            case 6:
                            {
                                bee.setx(bee.getx() - bee.getV());
                                break;
                            }
                            case 7:
                            {
                                bee.sety(bee.gety() - bee.getV());
                                bee.setx(bee.getx() - bee.getV());
                                break;
                            }
                        }
                        bee.getJlb().setLocation(bee.getx(), bee.gety());
                    }
                }
                timePrev1 = timeCur;
            }
            try{
                thread.sleep(1);
            }catch (InterruptedException e){}
        }
    }
}

