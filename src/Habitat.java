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
    static public int N1 = 3, N2 = 2, K = 40, P = 80, workerLifeTime = 3, droneLifeTime = 3;
    private int imageSize = 50;
    private ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getResource(
            "/Images/sky2.png")).getImage().getScaledInstance(WIDTH,HEIGHT, Image.SCALE_DEFAULT));

    Habitat(){
        JLabel tmp = new JLabel();
        tmp.setBounds( 0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        tmp.setIcon(imageIcon);
        MainProgram.bees.add(tmp, 0, 0);
        MainProgram.bees.repaint();
    }
    void update(int time){
        removeBees(time);

        if(time % N2 == 0)
        {
            int random = (int)Math.floor(Math.random()*100);
            if(random <= P) {
                int x = (int) Math.floor(Math.random() * (WIDTH - imageSize));
                int y = (int) Math.floor(Math.random() * (HEIGHT - imageSize));
                Worker worker = new Worker(x, y, workerLifeTime, time, arrId);
                arrBees.add(worker);
                arrId.add(worker.getId());
                arrBornTime.put(worker.getId(), worker.getBornTime());
                Worker.beeWorker++;
            }
        }
        if(time % N1 == 0 && Worker.beeWorker != 0){
            int dronePercent = 100*Drone.beeDrone/(Drone.beeDrone + Worker.beeWorker);
            if(dronePercent < K){
                int x = (int)Math.floor(Math.random()*(WIDTH-imageSize));
                int y = (int)Math.floor(Math.random()*(HEIGHT-imageSize));
                Drone drone = new Drone(x, y, droneLifeTime, time, arrId);
                arrBees.add(drone);
                arrId.add(drone.getId());
                arrBornTime.put(drone.getId(), drone.getBornTime());
                Drone.beeDrone++;
            }
        }
    }
    void clear(){
        MainProgram.bees.removeAll();
        arrBees.clear();
        arrId.clear();
        arrBornTime.clear();
        new Habitat();
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
}
