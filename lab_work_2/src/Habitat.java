import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


public class Habitat extends JPanel {

    private final int width = 600, height = 600;
    private final int size = 5;

    public long N1, N2;
    public float P1, P2;
    private int countTime1, countTime2;
    protected int count1, count2;
    private Transport[] myObjects;


    public Habitat() {
        N1 = 1000;
        N2 = 2000;
        P1 = P2 = 0.5f;
        count1 = count2 = 0;
        countTime1 = countTime2 = 0;
        myObjects = new Transport[size];

        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setBackground(new Color(75, 75, 75));
    }

    private int setPos(int len) {
        return (int) (Math.random() * (len - 50));
    }

    private void PutIn(Transport obj) {
        if (count1 + count2 + 1 == myObjects.length) {
            System.out.println("I'm here and I gonna copy your array");
            myObjects = Arrays.copyOf(myObjects, myObjects.length * 2);
        }
        myObjects[count1 + count2] = obj;
    }

    private void check() {
        System.out.println("myObjects:");
        for (int i = 0; myObjects[i] != null; i++)
            System.out.println(myObjects[i]);
    }

    public void update(long time) {
        if (time - (countTime1 * N1) >= N1) {
            if (Math.random() <= P1) {
                Car car = new Car(setPos(width), setPos(height));
                add(car);
                PutIn(car);
                validate();
                repaint();
                count1++;
            }
            countTime1++;
        }

        if (time - (countTime2 * N2) >= N2) {
            if (Math.random() <= P2) {
                Motocycle truck = new Motocycle(setPos(width), setPos(height));
                add(truck);
                PutIn(truck);
                validate();
                repaint();
                count2++;
            }
            countTime2++;
        }
    }

    public boolean fin(long time, boolean statisticsIsVisible) {
        check();
        if (statisticsIsVisible) {
            int selectedValue = JOptionPane.showConfirmDialog(this,
                    String.format("<html>Cars: %d<br> Motocycle: %d<br>Time: %.02f</html>", count1, count2, time / 1000.f),
                    "Statistics",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (selectedValue == JOptionPane.CANCEL_OPTION) {
                return true;
            }
        }
        return false;

        //revalidate();
        //repaint();
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

