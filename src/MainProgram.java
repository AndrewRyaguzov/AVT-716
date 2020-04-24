import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MainProgram extends JFrame {

    /*Panels*/
    static public JPanel bees = new JPanel();
    static public JPanel panel = new JPanel();
    /*Window's size*/
    static public int windowWIDTH = 1100, HEIGHT = 600, beesWIDTH = 600, panelWIDTH = 500;
    /*Habitat*/
    Habitat habitat = new Habitat();
    /*Time*/
    private long timePrev = 0, startTime, timeStop = 0, timeContinue = 0;
    private int time;
    /*Boolean variables*/
    private boolean bText = false;
    private boolean bSimulation = false;
    private boolean slWorker = true;
    /*Text fields*/
    private JLabel textTimeSimulation = new JLabel(""), textPeriod = new JLabel("Введите период появления Рабочих и Трутней");
    private JTextArea textResults = new JTextArea("");
    private JTextField intWorker = new JTextField(""), intDrone = new JTextField("");
    private JLabel textLifeTime = new JLabel("Введите время жизни Рабочих и Трутней");
    private JTextField workerLifeTime = new JTextField(""), droneLifeTime = new JTextField("");
    /*Buttons*/
    private JButton btStart = new JButton("Start"), btStop = new JButton("Stop"),
            btOk = new JButton("Ok"), btCancel = new JButton("Cancel");
    private JToggleButton btInfo = new JToggleButton("On/Off INFO");
    private JRadioButton timeOn = new JRadioButton("Показать время симуляции"),
            timeOff = new JRadioButton("Скрыть время симуляции");
    private JButton btShowObjects = new JButton("Show objects");
    /*Sliders*/
    private String arrSliders[] = {"Процент появления рабочего", "'Процент' появления трутня"};
    private JComboBox<String> comboBoxSliders = new JComboBox<String>(arrSliders);
    private JSlider slBees = new JSlider(0, 100, 50);
    /*Menu*/
    private JMenuBar jMenuBar = new JMenuBar();
    private JMenu jmCommands = new JMenu("Комманды");
    private JMenuItem jmiStart = new JMenuItem("Старт"), jmiStop = new JMenuItem("Стоп"), jmiTime = new JMenuItem("Показать/скрыть время симуляции");
    private JToolBar jToolBar = new JToolBar();
    private JButton jtbStart = new JButton("Старт"), jtbStop = new JButton("Стоп"), jtbTime = new JButton("Показать/скрыть время симуляции");

    public void run(){
        init();
        event_handling();
        game_loop();
    }

    private void game_loop(){

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

    private void init(){
        this.setLayout(null);
        this.setSize(windowWIDTH, HEIGHT);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.requestFocus();

        bees.setLayout(null);
        bees.setBounds(0,0,beesWIDTH, HEIGHT);

        panel.setLayout(null);
        panel.setBounds(beesWIDTH,0, panelWIDTH, HEIGHT);
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
        textLifeTime.setBounds(0, 465, 300, 15);
        panel.add(textLifeTime);
        workerLifeTime.setBounds(0, 490, 100, 15);
        panel.add(workerLifeTime);
        droneLifeTime.setBounds(200, 490, 100, 15);
        panel.add(droneLifeTime);
        btShowObjects.setBounds(300, 140, 120, 50);
        panel.add(btShowObjects);

        jmCommands.add(jmiStart);
        jmCommands.add(jmiStop);
        jmCommands.add(jmiTime);
        jMenuBar.add(jmCommands);

        jToolBar.add(jtbStart);
        jToolBar.add(jtbStop);
        jToolBar.add(jtbTime);
        jToolBar.setBounds(300, 25, 100, 100);
        panel.add(jToolBar);

        textResults.setEditable(false);
        btStop.setEnabled(false);
        btInfo.setEnabled(false);
        btOk.setEnabled(false);
        btCancel.setEnabled(false);

        this.add(bees);
        this.add(panel);
        this.setJMenuBar(jMenuBar);
        this.setResizable(false);
    }

    private  void event_handling(){
        this.addKeyListener(new KeyAdapter() {
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
            this.requestFocus();
        });
        btStop.addActionListener(e -> {
            change_E();
            this.requestFocus();
        });
        btInfo.addActionListener(e -> {
            this.requestFocus();
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
            this.requestFocus();
        });
        timeOff.addActionListener(e -> {
            bText = false;
            this.requestFocus();
        });
        btOk.addActionListener(e -> {
            this.requestFocus();
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
            this.requestFocus();
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
            try {
                int tmp = Integer.parseInt(intWorker.getText());
                if(tmp > 0){
                    Habitat.N2 = tmp;
                    JOptionPane.showMessageDialog(this, "Установлен период появления\n" +
                            "рабочих, равный " + Habitat.N2 + " секунд.");
                }else{
                    throw new Exception();

                }
            }catch (Exception e1){
                Habitat.N2 = 1;
                JOptionPane.showMessageDialog(this, "Период не может быть отрицательным!" +
                        "\nУстановлено значение по умолчанию = 1.");
            }
            intWorker.setText("");
            this.requestFocus();
        });
        intDrone.addActionListener(e -> {
            try {
                int tmp = Integer.parseInt(intDrone.getText());
                if (tmp > 0) {
                    Habitat.N1 = tmp;
                    JOptionPane.showMessageDialog(this, "Установлен период появления\n" +
                            "трутней, равный " + Habitat.N1 + " секунд.");
                } else {
                    throw new Exception();
                }
            }catch (Exception e1){
                Habitat.N1 = 1;
                JOptionPane.showMessageDialog(this, "Период не может быть отрицательным!" +
                        "\nУстановлено значение по умолчанию = 1.");
            }
            intDrone.setText("");
            this.requestFocus();
        });
        comboBoxSliders.addActionListener(e -> {
            this.requestFocus();
            String tmp = (String)comboBoxSliders.getSelectedItem();
            if(tmp.equals("Процент появления рабочего")){
                slWorker = true;
            }else{
                slWorker = false;
            }
        });
        slBees.addChangeListener(e -> {
            this.requestFocus();
            if(slWorker){
                Habitat.P = slBees.getValue();
            }else{
                Habitat.K = slBees.getValue();
            }
        });
        jmiStart.addActionListener(e -> {
            change_B();
            this.requestFocus();
        });
        jmiStop.addActionListener(e -> {
            change_E();
            this.requestFocus();
        });
        jmiTime.addActionListener(e -> {
            change_T();
            this.requestFocus();
        });
        jtbStart.addActionListener(e -> {
            change_B();
            this.requestFocus();
        });
        jtbStop.addActionListener(e -> {
            change_E();
            this.requestFocus();
        });
        jtbTime.addActionListener(e -> {
            change_T();
            this.requestFocus();
        });
        workerLifeTime.addActionListener(e -> {
            try {
                int tmp = Integer.parseInt(workerLifeTime.getText());
                if(tmp > 0){
                    Habitat.workerLifeTime = tmp;
                    JOptionPane.showMessageDialog(this, "Установлено время жизни\n" +
                            "рабочих, равное " + Habitat.workerLifeTime + " секунд.");
                }else{
                    throw new Exception();

                }
            }catch (Exception e1){
                Habitat.workerLifeTime = 5;
                JOptionPane.showMessageDialog(this, "Время жизни не может быть отрицательным!" +
                        "\nУстановлено значение по умолчанию = 5.");
            }
            workerLifeTime.setText("");
            this.requestFocus();
        });
        droneLifeTime.addActionListener(e -> {
            try {
                int tmp = Integer.parseInt(droneLifeTime.getText());
                if (tmp > 0) {
                    Habitat.droneLifeTime = tmp;
                    JOptionPane.showMessageDialog(this, "Установлено время жизни\n" +
                            "трутней, равное " + Habitat.droneLifeTime + " секунд.");
                } else {
                    throw new Exception();
                }
            }catch (Exception e1){
                Habitat.droneLifeTime = 5;
                JOptionPane.showMessageDialog(this, "Время жизни не может быть отрицательным!" +
                        "\nУстановлено значение по умолчанию = 5.");
            }
            droneLifeTime.setText("");
            this.requestFocus();
        });
        btShowObjects.addActionListener(e -> {
            String obj[] = new String[habitat.getArrBees().size() + 1];
            int count = 0;
            obj[count++] = "Класс Время рождения ID";
            for(Character bee : habitat.getArrBees()){
                if(bee instanceof Worker){
                    obj[count++] = "Рабочий " + Integer.toString(bee.getBornTime()) + " " + Integer.toString(bee.getId());
                }
                else{
                    obj[count++] = "Трутень " + Integer.toString(bee.getBornTime()) + " " + Integer.toString(bee.getId());
                }
            }
            JList<String> jlst = new JList<String>(obj);
            JScrollPane jscrl = new JScrollPane(jlst);
            JOptionPane.showMessageDialog(this, jscrl);

            this.requestFocus();
        });
    }

    private void change_T(){
        bText = !bText;
    }

    private void change_B(){
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

    private void change_E(){
        bSimulation = false;
        timeStop = System.currentTimeMillis();
        btStop.setEnabled(false);
        jtbStop.setEnabled(false);
        jmiStop.setEnabled(false);
        btInfo.setEnabled(true);
    }

    private void print_time_simulation(){
        if(bText){
            textTimeSimulation.setText(time+" секунд после начала симуляции");
        }else {
            textTimeSimulation.setText("");
        }
        panel.repaint();
    }

    private void print_results(boolean TF){
        if(TF) {
            textResults.setText("Количество сгенерированных пчёл-рабочих: " + Worker.beeWorker +
                    "\nКоличество сгенерированных пчёл-трутней: " + Drone.beeDrone +
                    "\nОбщее количество сгенерированных пчёл: " + (Drone.beeDrone + Worker.beeWorker) +
                    "\nВремя симуляции: " + time);
        }else{
            textResults.setText("");
        }
    }
}
