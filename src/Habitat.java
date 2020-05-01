import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

public class Habitat {

    private ArrayList<Character> arrBees = new ArrayList<Character>();
    private HashSet<Integer> arrId = new HashSet<Integer>();
    private TreeMap<Integer, Integer> arrBornTime = new TreeMap<Integer, Integer>();
    private int WIDTH = MainProgram.beesWIDTH, HEIGHT = MainProgram.HEIGHT;
    static public int N1 = 3, N2 = 2, K = 40, P = 80, workerLifeTime = 10, droneLifeTime = 10;
    private int imageSize = 50;
    private ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getResource(
            "/Images/sky2.png")).getImage().getScaledInstance(WIDTH,HEIGHT, Image.SCALE_DEFAULT));
    WorkerAI workerAI;
    DroneAI droneAI;

    Habitat(){
        createGUI();
        workerAI = new WorkerAI();
        droneAI = new DroneAI();
    }
    synchronized void update(int time){
        removeBees(time);
        workerAI.setArray(arrBees);
        droneAI.setArray(arrBees);
        if(time % N2 == 0)
        {
            int random = (int)Math.floor(Math.random()*100);
            if(random <= P) {
                int x = (int) Math.floor(Math.random() * (WIDTH - imageSize));
                int y = (int) Math.floor(Math.random() * (HEIGHT - imageSize));
                createWorker(x, y, workerLifeTime, time);
            }
        }
        if(time % N1 == 0 && Worker.beeWorker != 0){
            int dronePercent = 100*Drone.beeDrone/(Drone.beeDrone + Worker.beeWorker);
            if(dronePercent < K){
                int x = (int)Math.floor(Math.random()*(WIDTH-imageSize));
                int y = (int)Math.floor(Math.random()*(HEIGHT-imageSize));
                createDrone(x, y, droneLifeTime, time);
            }
        }
    }
    Worker createWorker(int x, int y, int lifeTime, int bornTime){
        Worker worker = new Worker(x, y, lifeTime, bornTime, arrId);
        arrBees.add(worker);
        arrId.add(worker.getId());
        arrBornTime.put(worker.getId(), worker.getBornTime());
        Worker.beeWorker++;
        return worker;
    }
    Drone createDrone(int x, int y, int lifeTime, int bornTime){
        Drone drone = new Drone(x, y, lifeTime, bornTime, arrId);
        arrBees.add(drone);
        arrId.add(drone.getId());
        arrBornTime.put(drone.getId(), drone.getBornTime());
        Drone.beeDrone++;
        return drone;
    }
    void createGUI(){
        JLabel tmp = new JLabel();
        tmp.setBounds( 0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        tmp.setIcon(imageIcon);
        MainProgram.bees.add(tmp, 0, 0);
        MainProgram.bees.repaint();
    }
    void clear(){
        MainProgram.bees.removeAll();
        arrBees.clear();
        arrId.clear();
        arrBornTime.clear();
        createGUI();
        Worker.beeWorker = Drone.beeDrone = 0;
    }
    void removeBees(int time){
        Iterator<Character> iterator = arrBees.iterator();
        while (iterator.hasNext()){
            Character bee = iterator.next();
            if((time - bee.getBornTime()) >= bee.getLifeTime()){
                MainProgram.bees.remove(bee.getJlb());
                MainProgram.bees.repaint();
                if(bee instanceof Worker){
                    Worker.beeWorker--;
                }
                else{
                    Drone.beeDrone--;
                }
                arrId.remove(bee.getId());
                arrBornTime.remove(bee.getId());
                iterator.remove();
            }
        }
    }
    ArrayList<Character> getArrBees(){return arrBees;}
    void pauseAI(){
        workerAI.setBoolStop(true);
        droneAI.setBoolStop(true);
    }
    void continueAI(){
        workerAI._continue();
        workerAI.setBoolStop(false);
        droneAI._continue();
        droneAI.setBoolStop(false);
    }
}
