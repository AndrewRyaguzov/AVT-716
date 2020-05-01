public class WorkerAI extends BaseAI {
    WorkerAI(){
        super("WorkerAI");
        thread.start();
    }
    @Override
    synchronized public void run(){
        long timePrev = 0;
        while (true) {
            if(stop){
                pause();
            }
            long timeCur = System.currentTimeMillis();
            long step = timeCur - timePrev;
            if (step >= 1000/60) {
                for (Character bee : arrBees) {
                    if (bee instanceof Worker) {
                        if (((Worker) bee).toAngle) {
                            if (bee.getx() > 0) {
                                bee.setx(bee.getx() - bee.getV());
                            }else if (bee.gety() > 0){
                                bee.sety(bee.gety() - bee.getV());
                            }else{
                                ((Worker) bee).toAngle = false;
                            }
                        }else{
                            if (bee.getx() < ((Worker) bee).x0) {
                                bee.setx(bee.getx() + bee.getV());
                            }else if (bee.gety() < ((Worker) bee).y0){
                                bee.sety(bee.gety() + bee.getV());
                            }else{
                                ((Worker) bee).toAngle = true;
                            }
                        }
                        bee.getJlb().setLocation(bee.getx(), bee.gety());
                    }
                }
                timePrev = timeCur;
            }
            try{
                thread.sleep(1);
            }catch (InterruptedException e){}
        }
    }
}
