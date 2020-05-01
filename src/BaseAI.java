import java.util.ArrayList;

public abstract class BaseAI implements Runnable {

    Thread thread;
    ArrayList<Character> arrBees = new ArrayList<Character>();
    boolean stop = true;


    BaseAI(String name){
        thread = new Thread(this, name);
    }

    public void setArray(ArrayList<Character> arr){
        arrBees = new ArrayList<Character>(arr);
    }
    public void pause(){
        try {
            wait();
        } catch (InterruptedException e) {}
    }
    synchronized public void _continue(){
        notify();
    }
    void setBoolStop(boolean stop){this.stop = stop;}
}
