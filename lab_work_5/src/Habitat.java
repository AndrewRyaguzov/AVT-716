import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;


public class Habitat extends JPanel {

    private final int width = 600, height = 600, indent = 50;


    public float N1, N2;
    public float P1, P2;
    public float lifetime_car, lifetime_motocycle;
    private float countTime1, countTime2;
    private int count1, count2;
    private long currentSimTime;
    private ArrayList<Transport> myObjects;
    private TreeSet<Integer> identifiers;
    private HashMap<Integer, Float> identifiers_lifetime;

    public Habitat() {
        count1 = count2 = 0;
        countTime1 = countTime2 = 0;
        myObjects = new ArrayList<Transport>();
        //identifiers = new TreeSet<Integer>();
        //identifiers_lifetime = new HashMap<Integer, Float>();


        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setBackground(new Color(122, 122, 122));
    }


    public ArrayList<Transport> getObjects() {
        return myObjects;
    }

    public void setCarTime(float value) {
        lifetime_car = value;
    }

    public void setMotoTime(float value) {
        lifetime_motocycle = value;
    }

    private int genRandPos(int l, int r) {
        return l + (int) (Math.random() * (r - l));
    }

    private void put_in(Transport obj) {
        int index = genIndex();

        obj.setID(index);
        myObjects.add(obj);

        //identifiers.add(index);
        //identifiers_lifetime.put(index, obj.getLifeTime());

    }

    private boolean check(int index) {
        for(int i=0; i<myObjects.size(); i++){
            if(myObjects.get(i).identifier==index)
                return false;
        }
        return true;
    }
    private int genIndex() {
        int index = (int) (Math.random() * (count1 + count2 + 10));
        while (!check(index))
            index = (int) (Math.random() * (count1 + count2 + 10));
        return index;
    }

    public void save(File file) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            SavedSimulation savedSimulation = new SavedSimulation(myObjects);
            objectOutputStream.writeObject(savedSimulation);
            objectOutputStream.close();
            outputStream.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void open(long startTime, File file) throws IOException {

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            SavedSimulation savedSimulation = (SavedSimulation) objectInputStream.readObject();
            myObjects.clear();
            myObjects = savedSimulation.myObjects;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        removeAll();
        repaint();
        countTime1=countTime2=0;
        count1=count2=0;
        currentSimTime = System.currentTimeMillis() - startTime;

        for(int i=0;i<myObjects.size();i++)
        {
            myObjects.get(i).time_of_birth=currentSimTime;
            if(myObjects.get(i).getName()=="Car")
                count1++;
            else
                count2++;
            add(myObjects.get(i));
        }
    }

    public void update(long startTime, float delta) {
        countTime1 += delta;
        countTime2 += delta;

        currentSimTime = System.currentTimeMillis() - startTime;

        if (countTime1 >= N1) {
            if (Math.random() <= P1) {

                Car car = new Car(genRandPos(0, width - indent), genRandPos(0, height - indent), currentSimTime, lifetime_car);
                add(car);
                put_in(car);

                revalidate();
                count1++;
            }

            countTime1 -= N1;
        }

        if (countTime2 >= N2) {
            if (Math.random() <= P2) {
                Motocycle motocycle = new Motocycle(genRandPos(0, width - indent), genRandPos(0, height - indent), currentSimTime, lifetime_motocycle);
                add(motocycle);
                put_in(motocycle);

                revalidate();
                count2++;
            }

            countTime2 -= N2;
        }

        updateTransports(delta);
        this.repaint();
    }

    public void showCurrentObjects() {
        int upd_table = JOptionPane.OK_OPTION;
        UIManager.put("OptionPane.okButtonText", "Update");
        if (myObjects.size() != 0) {
            while (upd_table == JOptionPane.OK_OPTION) {
                Table table = new Table(myObjects);
                upd_table = JOptionPane.showConfirmDialog(this,
                        table.createTable(),
                        "Current objects",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            UIManager.put("OptionPane.okButtonText", "Ok");
            JOptionPane.showConfirmDialog(null, "No objects",
                    "Current objects",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }

        UIManager.put("OptionPane.okButtonText", "Ok");
    }

    public void updateTransports(float delta) {
        for (int i = 0; i < myObjects.size(); i++) {
            if (myObjects.get(i).update(delta)) {
                delete(myObjects.get(i), i);
            }
        }
    }

    private void delete(Transport obj, int i) {
        if (obj.getName().equals("Car")) count1--;
        else count2--;

        remove(obj);
        validate();

//        identifiers.remove(obj.getID());
//       identifiers_lifetime.remove(obj.getID());
        myObjects.remove(i);
    }

    public boolean fin(long time, boolean statisticsIsVisible) {
        if (statisticsIsVisible) {
            int selectedValue = JOptionPane.showConfirmDialog(this,
                    String.format("<html>Cars: %d<br> Motocycle: %d<br>Time: %.02f</html>", count1, count2, time / 1000.f),
                    "Statistics",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (selectedValue == JOptionPane.CANCEL_OPTION) {
                return true;
            }
        }
        myObjects.clear();
        return false;

    }

    public void start() {
        count1 = count2 = 0;
        countTime1 = countTime2 = 0;
        removeAll();
        setLayout(null);
        revalidate();
    }


}

