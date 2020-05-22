import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.ArrayList;


public class Habitat extends JPanel {

    private final int width = 600, height = 600;
    private final int size = 5;

    public long N1, N2;
    public float P1, P2;
    public float lifetime_car, lifetime_motocycle;
    private int countTime1, countTime2;
    private int count1, count2;
    public ArrayList<Transport> myObjects;
    private TreeSet<Integer> identifiers;
    private HashMap<Integer, Float> identifiers_lifetime;


    public Habitat() {
        N1 = 1000;
        N2 = 2000;
        lifetime_car = 10000;
        lifetime_motocycle = 20000;
        P1 = P2 = 0.5f;
        count1 = count2 = 0;
        countTime1 = countTime2 = 0;
        myObjects = new ArrayList<Transport>();
        identifiers = new TreeSet<Integer>();
        identifiers_lifetime = new HashMap<Integer, Float>();

        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setBackground(new Color(122, 122, 122));
    }

    private int setPos(int len) {
        return (int) (Math.random() * (len - 50));
    }

    private void put_in(Transport obj) {
        int index = index();

        obj.identifier = index;

        myObjects.add(obj);
        identifiers.add(index);
        identifiers_lifetime.put(index, obj.lifetime);

    }

    private int index() {
        int index = (int) (Math.random() * (count1 + count2 + 10));
        while (identifiers.contains(index))
            index = (int) (Math.random() * (count1 + count2 + 10));
        return index;
    }

    private void check() {
        for (int i = 0; i < myObjects.size(); i++)
            System.out.println(myObjects.get(i));
    }

    public void update(long time) {
        if (time - (countTime1 * N1) >= N1) {
            if (Math.random() <= P1) {
                Car car = new Car(setPos(width), setPos(height));
                add(car);
                car.time_of_birth = time;
                car.lifetime = lifetime_car;
                put_in(car);
                validate();
                repaint();
                count1++;
            }
            countTime1++;
        }

        if (time - (countTime2 * N2) >= N2) {
            if (Math.random() <= P2) {
                Motocycle motocycle = new Motocycle(setPos(width), setPos(height));
                add(motocycle);
                motocycle.time_of_birth = time;
                motocycle.lifetime = lifetime_motocycle;
                put_in(motocycle);
                validate();
                repaint();
                count2++;
            }
            countTime2++;
        }

    }

    public void showCurrentObjects() {
        int upd_table=JOptionPane.CANCEL_OPTION;
        if (myObjects.size() != 0) {
            while( upd_table==JOptionPane.CANCEL_OPTION)
            { Table table = new Table(myObjects);
                upd_table=JOptionPane.showConfirmDialog(this,
                        table.createTable(),
                        "Current objects",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            }
        } else
            JOptionPane.showConfirmDialog(null, "No objects",
                    "Current objects",
                    JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
    }


    public void visitingRound(float timeStep) {
        for (int i = 0; i < myObjects.size(); i++) {
            Transport obj = myObjects.get(i);
            obj.lifetime -= timeStep;
            if (obj.lifetime <= 0)
                delete(obj, i);
            else
                myObjects.set(i, obj);
        }
    }

    private void delete(Transport obj, int i) {
        if(obj.name=="Car") count1--;
        else count2--;

        remove(obj);
        validate();
        repaint();

        identifiers.remove(obj.identifier);
        identifiers_lifetime.remove(obj.identifier);
        myObjects.remove(i);
    }

    public boolean fin(long time, boolean statisticsIsVisible) {
        check();
        if (statisticsIsVisible) {
            int selectedValue = JOptionPane.showConfirmDialog(this,
                    String.format("<html>Cars: %d<br> Motocycles: %d<br>Time: %.02f</html>", count1, count2, time / 1000.f),
                    "Statistics",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (selectedValue == JOptionPane.CANCEL_OPTION) {
                return true;
            }
        }
        return false;

    }

    public void start() {
        count1 = count2 = 0;
        countTime1 = countTime2 = 0;
        removeAll();
        setLayout(null);
        revalidate();
        repaint();
    }
}
