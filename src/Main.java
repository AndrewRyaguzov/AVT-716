import javax.swing.*;
import java.awt.event.*;


public class Main {
    /*Panels*/
    static public JFrame window = new JFrame("Lab 2");
    static public JPanel bees = new JPanel();
    static public JPanel panel = new JPanel();
    /*Habitat*/
    static Habitat habitat;
    /*Time*/
    static private long timePrev = 0, startTime, timeStop = 0, timeContinue = 0;
    static private int time;
    /*Window's size*/
    static public int windowWIDTH = 1100, HEIGHT = 600, beesWIDTH = 600, panelWIDTH = 500;
    /*Boolean variables*/
    static boolean bText = false;
    static boolean bSimulation = false;
    static boolean slWorker = true;
    /*Text fields*/
    static JLabel textTimeSimulation = new JLabel(""), textPeriod = new JLabel("Введите период появления " + "Рабочих и Трутней");
    static JTextArea textResults = new JTextArea("");
    static JTextField intWorker = new JTextField(""), intDrone = new JTextField("");
    /*Buttons*/
    static JButton btStart = new JButton("Start"), btStop = new JButton("Stop"),
            btOk = new JButton("Ok"), btCancel = new JButton("Cancel");
    static JToggleButton btInfo = new JToggleButton("On/Off INFO");
    static JRadioButton timeOn = new JRadioButton("Показать время симуляции"),
            timeOff = new JRadioButton("Скрыть время симуляции");
    /*Sliders*/
    static String arrSliders[] = {"Процент появления рабочего", "'Процент' появления трутня"};
    static JComboBox<String> comboBoxSliders = new JComboBox<String>(arrSliders);
    static JSlider slBees = new JSlider(0, 100, 50);
    /*Menu*/
    static JMenuBar jMenuBar = new JMenuBar();
    static JMenu jmCommands = new JMenu("Комманды");
    static JMenuItem jmiStart = new JMenuItem("Старт"), jmiStop = new JMenuItem("Стоп"), jmiTime = new JMenuItem("Показать/скрыть время симуляции");
    static JToolBar jToolBar = new JToolBar();
    static JButton jtbStart = new JButton("Старт"), jtbStop = new JButton("Стоп"), jtbTime = new JButton("Показать/скрыть время симуляции");


    public static void main(String[] args){
        init();
        event_handling();

        habitat = new Habitat();

        while(true){
            print_time_simulation();
            if(bSimulation) {
                long timeCur = System.currentTimeMillis() - startTime;
                long step = timeCur - timePrev;
                if (step >= 1000) {
                    time = (int) (timeCur / 1000);
                    habitat.update(time);
                    timePrev = timeCur;
                }
            }
        }
    }
    static void change_T(){
        bText = !bText;
    }
    static void change_B(){
        startTime = System.currentTimeMillis();
        bSimulation = true;
        btStart.setEnabled(false);
        btStop.setEnabled(true);
        jmiStart.setEnabled(false);
        jmiStop.setEnabled(true);
        jtbStart.setEnabled(false);
        jtbStop.setEnabled(true);
        btOk.setEnabled(false);
        btCancel.setEnabled(false);
        btInfo.setEnabled(false);
        Worker.beeWorker = Drone.beeDrone = 0;
        habitat.clear();
        timePrev = 0;
    }
    static void change_E(){
        bSimulation = false;
        timeStop = System.currentTimeMillis();
        btInfo.setEnabled(true);
    }
    static void print_time_simulation(){
        if(bText){
            textTimeSimulation.setText(time+" секунд после начала симуляции");
        }else {
            textTimeSimulation.setText("");
        }
        panel.repaint();
    }
    static void print_results(boolean TF){
        if(TF) {
            textResults.setText("Количество сгенерированных пчёл-рабочих: " + Worker.beeWorker +
                    "\nКоличество сгенерированных пчёл-трутней: " + Drone.beeDrone +
                    "\nОбщее количество сгенерированных пчёл: " + (Drone.beeDrone + Worker.beeWorker) +
                    "\nВремя симуляции: " + time);
        }else{
            textResults.setText("");
        }
    }
    static void event_handling(){
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(String.valueOf(e.getKeyChar()).equals("t") || String.valueOf(e.getKeyChar()).equals("T")){
                    change_T();
                }
                if(String.valueOf(e.getKeyChar()).equals("b") || String.valueOf(e.getKeyChar()).equals("B")){
                    change_B();
                }
                if(String.valueOf(e.getKeyChar()).equals("e") || String.valueOf(e.getKeyChar()).equals("E")){
                    change_E();
                }
            }
        });
        btStart.addActionListener(e -> {
            change_B();
            window.requestFocus();
        });
        btStop.addActionListener(e -> {
            change_E();
            window.requestFocus();
        });
        btInfo.addActionListener(e -> {
            window.requestFocus();
            if(!bSimulation) {
                if (btInfo.isSelected()) {
                    print_results(true);
                    btOk.setEnabled(true);
                    btCancel.setEnabled(true);
                } else {
                    print_results(false);
                    btOk.setEnabled(false);
                    btCancel.setEnabled(false);
                }
            }
        });
        timeOn.addActionListener(e -> {
            bText = true;
            window.requestFocus();
        });
        timeOff.addActionListener(e -> {
            bText = false;
            window.requestFocus();
        });
        btOk.addActionListener(e -> {
            window.requestFocus();
            bSimulation = false;
            btStart.setEnabled(true);
            btStop.setEnabled(false);
            jmiStart.setEnabled(true);
            jmiStop.setEnabled(false);
            jtbStart.setEnabled(true);
            jtbStop.setEnabled(false);
            btOk.setEnabled(false);
            btCancel.setEnabled(false);
            btInfo.setEnabled(false);
            btInfo.setSelected(false);
            timeStop = timeContinue = 0;
            habitat.clear();
            print_results(false);
            time = 0;
        });
        btCancel.addActionListener(e -> {
            window.requestFocus();
            timeContinue = System.currentTimeMillis();
            startTime += timeContinue - timeStop;
            bSimulation = true;
            btStart.setEnabled(false);
            btStop.setEnabled(true);
            jmiStart.setEnabled(false);
            jmiStop.setEnabled(true);
            jtbStart.setEnabled(false);
            jtbStop.setEnabled(true);
            btOk.setEnabled(false);
            btCancel.setEnabled(false);
            btInfo.setEnabled(false);
            btInfo.setSelected(false);
            print_results(false);
        });
        intWorker.addActionListener(e -> {
            window.requestFocus();
            try {
                int tmp = Integer.parseInt(intWorker.getText());
                if(tmp > 0){
                    Habitat.N2 = tmp;
                    JOptionPane.showMessageDialog(window, "Установлен период появления\n" +
                            "рабочих, равный " + Habitat.N2 + " секунд.");
                }else{
                    throw new Exception();

                }
            }catch (Exception e1){
                Habitat.N2 = 1;
                JOptionPane.showMessageDialog(window, "Период не может быть отрицательным!" +
                        "\nУстановлено значение по умолчанию = 1.");
            }

        });
        intDrone.addActionListener(e -> {
            window.requestFocus();
            try {
                int tmp = Integer.parseInt(intDrone.getText());
                if (tmp > 0) {
                    Habitat.N1 = tmp;
                    JOptionPane.showMessageDialog(window, "Установлен период появления\n" +
                            "трутней, равный " + Habitat.N1 + " секунд.");
                } else {
                    throw new Exception();
                }
            }catch (Exception e1){
                Habitat.N1 = 1;
                JOptionPane.showMessageDialog(window, "Период не может быть отрицательным!" +
                        "\nУстановлено значение по умолчанию = 1.");
            }
        });
        comboBoxSliders.addActionListener(e -> {
            window.requestFocus();
            String tmp = (String)comboBoxSliders.getSelectedItem();
            if(tmp.equals("Процент появления рабочего")){
                slWorker = true;
            }else{
                slWorker = false;
            }
        });
        slBees.addChangeListener(e -> {
            window.requestFocus();
            if(slWorker){
                Habitat.P = slBees.getValue();
            }else{
                Habitat.K = slBees.getValue();
            }
        });
        jmiStart.addActionListener(e -> {
            change_B();
            window.requestFocus();
        });
        jmiStop.addActionListener(e -> {
            change_E();
            window.requestFocus();
        });
        jmiTime.addActionListener(e -> {
            change_T();
            window.requestFocus();
        });
        jtbStart.addActionListener(e -> {
            change_B();
            window.requestFocus();
        });
        jtbStop.addActionListener(e -> {
            change_E();
            window.requestFocus();
        });
        jtbTime.addActionListener(e -> {
            change_T();
            window.requestFocus();
        });
    }
    static void init(){
        window.setLayout(null);
        window.setSize(windowWIDTH, HEIGHT);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.requestFocus();

        bees.setLayout(null);
        bees.setBounds(0,0,beesWIDTH, HEIGHT);

        panel.setLayout(null);
        panel.setBounds(beesWIDTH,0,panelWIDTH,HEIGHT);
        textTimeSimulation.setBounds(0,0, 300, 20);
        panel.add(textTimeSimulation);
        btStart.setBounds(45, 30, 100, 50);
        panel.add(btStart);
        btStop.setBounds(155, 30, 100, 50);
        panel.add(btStop);
        timeOn.setBounds(0, 90, 300, 15);
        panel.add(timeOn);
        timeOff.setBounds(0, 115, 300, 15);
        panel.add(timeOff);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(timeOn);
        buttonGroup.add(timeOff);
        btInfo.setBounds(100, 140, 100, 50);
        panel.add(btInfo);
        textResults.setBounds(0, 200, 300, 65);
        panel.add(textResults);
        btOk.setBounds(45, 275, 100, 50);
        panel.add(btOk);
        btCancel.setBounds(155, 275, 100, 50);
        panel.add(btCancel);
        textPeriod.setBounds(0, 335, 300, 15);
        panel.add(textPeriod);
        intWorker.setBounds(0, 360, 100, 15);
        panel.add(intWorker);
        intDrone.setBounds(200, 360, 100, 15);
        panel.add(intDrone);
        comboBoxSliders.setBounds(50, 385, 200, 20);
        panel.add(comboBoxSliders);
        slBees.setBounds(20, 415, 260, 50);
        slBees.setMajorTickSpacing(10);
        slBees.setPaintLabels(true);
        slBees.setSnapToTicks(true);
        panel.add(slBees);

        jmCommands.add(jmiStart);
        jmCommands.add(jmiStop);
        jmCommands.add(jmiTime);
        jMenuBar.add(jmCommands);

        jToolBar.add(jtbStart);
        jToolBar.add(jtbStop);
        jToolBar.add(jtbTime);
        jToolBar.setBounds(20, 475, 100, 100);
        panel.add(jToolBar);

        textResults.setEditable(false);
        btStop.setEnabled(false);
        btInfo.setEnabled(false);
        btOk.setEnabled(false);
        btCancel.setEnabled(false);

        window.add(bees);
        window.add(panel);
        window.setJMenuBar(jMenuBar);
        window.setResizable(false);
    }
}
