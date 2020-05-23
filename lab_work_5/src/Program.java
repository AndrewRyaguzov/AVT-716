import javax.swing.*;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Program extends JFrame {
    private final float timeStep = 1 / 60f;

    private float N1_d, N2_d;
    private float lifetime_car_d, lifetime_motocycle_d;
    private float P1_d, P2_d;

    private boolean isWorking;
    private boolean statisticsIsVisible;
    public boolean isDone;
    private long startTime;
    private long timePrev;
    private float timeElapsed;

    private JMenuBar menuBar;
    private JMenuItem menuItemConsole;
    private JMenuItem menuItemSave, menuItemOpen, menuItemExit;
    private JMenuItem menuItemCurrentObjects;
    private JMenuItem menuItemStart, menuItemStop;
    private JCheckBoxMenuItem menuItemShowStatistics;
    private JRadioButtonMenuItem menuItemShowTime, menuItemHideTime;

    private JLabel timeTextLabel, cars, motocycles, probability_car, probability_motocycle, periodicity_car, periodicity_motocycle;
    private JLabel lifetime_car, lifetime_motocycle;
    private JButton buttonStart, buttonStop;
    private JButton buttonCurrentObjects;
    private ButtonGroup timeVisible;
    private JRadioButton buttonShowTime, buttonHideTime;
    private JCheckBox buttonShowStatistics;
    private JTextField periodicity_carField, periodicity_motocycleField, lifetime_carField, lifetime_motocycleField;

    private JSlider sliderProbability_car, sliderProbability_truck;

    private FileNameExtensionFilter filter;

    private Habitat habitat;

    public Program(float N1, float N2, float lifetime_car, float lifetime_motocycle, float P1, float P2) {

        N1_d = N1;
        N2_d = N2;
        lifetime_car_d = lifetime_car;
        lifetime_motocycle_d = lifetime_motocycle;
        P1_d = P1;
        P2_d = P2;
       /* N1_d =1;
        N2_d = 2;
        lifetime_car_d = 10;
        lifetime_truck_d = 20;
        P1_d = 0.5f;
        P2_d = 0.5f;*/
        isWorking = isDone = false;
        statisticsIsVisible = true;
        startTime = 0;
        timePrev = System.currentTimeMillis();
        timeElapsed = 0;

        filter = new FileNameExtensionFilter("SER file", "ser");
        habitat = new Habitat();

        habitat.N1 = N1_d;
        habitat.N2 = N2_d;
        habitat.lifetime_car = lifetime_car_d;
        habitat.lifetime_motocycle = lifetime_motocycle_d;
        habitat.P1 = P1_d;
        habitat.P2 = P2_d;

        createGUI();
    }

    public String[] getSettings() {
        String[] arr = new String[6];
        arr[0] = Float.toString(N1_d);
        arr[1] = Float.toString(N2_d);
        arr[2] = Float.toString(lifetime_car_d);
        arr[3] = Float.toString(lifetime_motocycle_d);
        arr[4] = Float.toString(P1_d);
        arr[5] = Float.toString(P2_d);
        return arr;
    }

    private void createGUI() {
        setTitle("Vehicles on the road");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());

        JPanel guiPanel = new JPanel();
        guiPanel.setPreferredSize(new Dimension(250, 600));
        add(guiPanel);


        add(habitat);

        buttonStart = new JButton("Start");
        buttonStart.setVisible(true);
        buttonStart.setFont(buttonStart.getFont().deriveFont(21f));
        buttonStart.setFont(buttonStart.getFont().deriveFont(Font.PLAIN));
        guiPanel.add(buttonStart);

        buttonStop = new JButton("Stop");
        buttonStop.setEnabled(false);
        buttonStop.setVisible(true);
        buttonStop.setFont(buttonStop.getFont().deriveFont(21f));
        buttonStop.setFont(buttonStop.getFont().deriveFont(Font.PLAIN));
        guiPanel.add(buttonStop);


        buttonCurrentObjects = new JButton("Current objects ");
        buttonCurrentObjects.setVisible(true);
        buttonCurrentObjects.setFont(buttonCurrentObjects.getFont().deriveFont(18f));
        buttonCurrentObjects.setFont(buttonCurrentObjects.getFont().deriveFont(Font.PLAIN));
        guiPanel.add(buttonCurrentObjects);

        buttonShowTime = new JRadioButton("Show time");
        buttonShowTime.setVisible(true);
        buttonShowTime.setSelected(true);
        guiPanel.add(buttonShowTime);

        buttonHideTime = new JRadioButton("Hide time");
        buttonHideTime.setVisible(true);
        guiPanel.add(buttonHideTime);

        buttonShowStatistics = new JCheckBox("Show statistics");
        buttonShowStatistics.setSelected(true);
        guiPanel.add(buttonShowStatistics);

        timeVisible = new ButtonGroup();
        timeVisible.add(buttonShowTime);
        timeVisible.add(buttonHideTime);

        cars = new JLabel("Cars:                       ");
        guiPanel.add(cars);
        cars.setFont(cars.getFont().deriveFont(20f));

        probability_car = new JLabel("Probability of spawn: ");
        guiPanel.add(probability_car);

        sliderProbability_car = new JSlider(0, 100, ((int) (P1_d * 100)));
        sliderProbability_car.setPaintLabels(true);
        sliderProbability_car.setMajorTickSpacing(10);
        guiPanel.add(sliderProbability_car);

        periodicity_car = new JLabel("Periodicity of spawn: ");
        guiPanel.add(periodicity_car);

        periodicity_carField = new JTextField(10);
        periodicity_carField.setText(Float.toString(N1_d));
        periodicity_carField.setToolTipText("Put here value in seconds in the range of 1..10");
        guiPanel.add(periodicity_carField);

        lifetime_car = new JLabel("Lifetime of car: ");
        guiPanel.add(lifetime_car);

        lifetime_carField = new JTextField(10);
        lifetime_carField.setText(Float.toString(lifetime_car_d));
        lifetime_carField.setToolTipText("Put here value in seconds in the range of 10..50");
        guiPanel.add(lifetime_carField);

        motocycles = new JLabel("Motocycles:                    ");
        guiPanel.add(motocycles);
        motocycles.setFont(motocycles.getFont().deriveFont(20f));

        probability_motocycle = new JLabel("Probability of spawn: ");
        guiPanel.add(probability_motocycle);

        sliderProbability_truck = new JSlider(0, 100, ((int) (P2_d * 100)));
        sliderProbability_truck.setPaintLabels(true);
        sliderProbability_truck.setMajorTickSpacing(10);
        guiPanel.add(sliderProbability_truck);

        periodicity_motocycle= new JLabel("Periodicity of spawn: ");
        guiPanel.add(periodicity_motocycle);

        periodicity_motocycleField = new JTextField(10);
        periodicity_motocycleField.setText(Float.toString(N2_d));
        periodicity_motocycleField.setToolTipText("Put here value in seconds in the range of 1..10");
        guiPanel.add(periodicity_motocycleField);

        lifetime_motocycle = new JLabel("Lifetime of motocycle: ");
        guiPanel.add(lifetime_motocycle);

        lifetime_motocycleField = new JTextField(10);
        lifetime_motocycleField.setText(Float.toString(lifetime_motocycle_d));
        lifetime_motocycleField.setToolTipText("Put here value in seconds in the range of 10..50");
        guiPanel.add(lifetime_motocycleField);

        timeTextLabel = new JLabel("Time: 0,00");
        timeTextLabel.setVisible(true);
        timeTextLabel.setFont(timeTextLabel.getFont().deriveFont(25f));
        timeTextLabel.setFont(timeTextLabel.getFont().deriveFont(Font.PLAIN));
        guiPanel.add(timeTextLabel);

        menuBar = new JMenuBar();

        menuBar.add(createFileMenu());
        menuBar.add(createSimulationMenu());

        setJMenuBar(menuBar);

        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pack();
        setVisible(true);

        fields();
        slides();
        buttons();
        keys();

    }

    private void start() {
        if (!isWorking) {
            buttonStart.setEnabled(false);
            menuItemStart.setEnabled(false);
            buttonStop.setEnabled(true);
            menuItemStop.setEnabled(true);
            startTime = System.currentTimeMillis();
            habitat.start();
            isWorking = true;
            System.out.println("Begin");
        }
    }

    private void end() {
        if (isWorking) {
            buttonStop.setEnabled(false);
            menuItemStop.setEnabled(false);
            buttonStart.setEnabled(true);
            menuItemStart.setEnabled(true);
            isWorking = false;
            long stopTime = System.currentTimeMillis() - startTime;
            isWorking = habitat.fin(System.currentTimeMillis() - startTime, statisticsIsVisible);
            if (isWorking) {
                buttonStart.setEnabled(false);
                menuItemStart.setEnabled(false);
                buttonStop.setEnabled(true);
                menuItemStop.setEnabled(true);
            }
            startTime += (System.currentTimeMillis() - startTime) - stopTime;
        }
    }

    private void updateTime(long time) {
        timeTextLabel.setText("Time: " + String.format("%.02f", time / 1000f));
    }

    public void run() {
        long timeCurrent = System.currentTimeMillis();
        timeElapsed += (timeCurrent - timePrev) / 1000.f;
        timePrev = timeCurrent;

        while (timeElapsed >= timeStep) {
            if (isWorking) {
                long time = System.currentTimeMillis() - startTime;
                habitat.update(startTime, timeStep);
                updateTime(time);
            }
            timeElapsed -= timeStep;
        }
    }


    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");

        menuItemSave = new JMenuItem("Save As...");
        menuItemOpen = new JMenuItem("Open...");
        menuItemExit = new JMenuItem("Exit");

        fileMenu.add(menuItemSave);
        fileMenu.add(menuItemOpen);
        fileMenu.add(new JSeparator());
        fileMenu.add(menuItemExit);
        return fileMenu;
    }

    private JMenu createSimulationMenu() {
        JMenu simulationMenu = new JMenu("Simulation");

        menuItemStart = new JMenuItem("Start");
        menuItemStop = new JMenuItem("Stop");
        menuItemStop.setEnabled(true);

        menuItemShowStatistics = new JCheckBoxMenuItem("Show statistics");
        menuItemShowStatistics.setSelected(buttonShowStatistics.isSelected());

        menuItemShowTime = new JRadioButtonMenuItem("Show time");
        menuItemHideTime = new JRadioButtonMenuItem("Hide time");

        ButtonGroup bg1 = new ButtonGroup();
        menuItemShowTime.setSelected(buttonShowTime.isSelected());
        menuItemHideTime.setSelected(buttonHideTime.isSelected());
        bg1.add(menuItemShowTime);
        bg1.add(menuItemHideTime);
        menuItemCurrentObjects = new JMenuItem("Current objects");

        simulationMenu.add(menuItemStart);
        simulationMenu.add(menuItemStop);
        simulationMenu.add(new JSeparator());
        simulationMenu.add(menuItemShowTime);
        simulationMenu.add(menuItemHideTime);
        simulationMenu.add(new JSeparator());
        simulationMenu.add(menuItemShowStatistics);
        simulationMenu.add(new JSeparator());
        simulationMenu.add(menuItemCurrentObjects);

        return simulationMenu;
    }

    private void fields() {
        ActionListener actionListener_periodicity_carField = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int value_periodicity_car;
                try {
                    value_periodicity_car = Integer.parseInt(periodicity_carField.getText());
                } catch (NumberFormatException exp) {
                    JOptionPane.showConfirmDialog(null, "Value must be integer",
                            "Wrong data type",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_periodicity_car = (int) N1_d;
                    periodicity_carField.setText(Float.toString(value_periodicity_car));
                }
                if (value_periodicity_car < 1 || value_periodicity_car > 10) {
                    JOptionPane.showConfirmDialog(null, "Value must be in the range of 1..10",
                            "Out of range error",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_periodicity_car = (int) N1_d;
                    periodicity_carField.setText(Float.toString(value_periodicity_car));
                } else {
                    N1_d = habitat.N1 = value_periodicity_car * 1000;
                }

            }

        };
        ActionListener actionListener_periodicity_truckField = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int value_periodocity_truck;
                try {
                    value_periodocity_truck = Integer.parseInt(periodicity_motocycleField.getText());
                } catch (NumberFormatException exp) {
                    JOptionPane.showConfirmDialog(null, "Value must be integer",
                            "Wrong data type",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    //periodicity_truckField.setText(null);
                    value_periodocity_truck = (int) N2_d;
                    periodicity_motocycleField.setText(Float.toString(value_periodocity_truck));
                }
                if (value_periodocity_truck < 1 || value_periodocity_truck > 10) {
                    JOptionPane.showConfirmDialog(null, "Value must be in the range of 1..10",
                            "Out of range error",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_periodocity_truck = (int) N2_d;
                    periodicity_motocycleField.setText(Float.toString(value_periodocity_truck));
                } else {
                    N2_d = habitat.N2 = value_periodocity_truck;
                }
            }

        };
        ActionListener actionListener_lifetime_carField = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                float value_lifetime_car = lifetime_car_d;
                try {
                    value_lifetime_car = Integer.parseInt(lifetime_carField.getText());
                } catch (NumberFormatException exp) {
                    JOptionPane.showConfirmDialog(null, "Value must be integer",
                            "Wrong data type",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_lifetime_car = lifetime_car_d;
                    lifetime_carField.setText(Float.toString(value_lifetime_car));
                }
                if (value_lifetime_car < 1 || value_lifetime_car > 50) {
                    JOptionPane.showConfirmDialog(null, "Value must be in the range of 1..50",
                            "Out of range error",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_lifetime_car = lifetime_car_d;
                    lifetime_carField.setText(Float.toString(value_lifetime_car));
                } else {
                    habitat.setCarTime(value_lifetime_car);
                    lifetime_car_d = value_lifetime_car;
                }
            }

        };
        ActionListener actionListener_lifetime_truckField = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                float value_lifetime_motocycle = lifetime_motocycle_d;
                try {
                    value_lifetime_motocycle = Integer.parseInt(lifetime_motocycleField.getText());
                } catch (NumberFormatException exp) {
                    JOptionPane.showConfirmDialog(null, "Value must be integer",
                            "Wrong data type",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_lifetime_motocycle = lifetime_motocycle_d;
                    lifetime_motocycleField.setText(Float.toString(value_lifetime_motocycle));
                }
                if (value_lifetime_motocycle < 1 || value_lifetime_motocycle > 50) {
                    JOptionPane.showConfirmDialog(null, "Value must be in the range of 1..50",
                            "Out of range error",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_lifetime_motocycle = lifetime_motocycle_d;
                    lifetime_motocycleField.setText(Float.toString(value_lifetime_motocycle));
                } else {
                    habitat.setMotoTime(value_lifetime_motocycle);
                    lifetime_motocycle_d = value_lifetime_motocycle;
                }
            }

        };

        lifetime_motocycleField.addActionListener(actionListener_lifetime_truckField);
        lifetime_carField.addActionListener(actionListener_lifetime_carField);
        periodicity_carField.addActionListener(actionListener_periodicity_carField);
        periodicity_motocycleField.addActionListener(actionListener_periodicity_truckField);
    }

    private void slides() {
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value1 = sliderProbability_car.getValue();
                P1_d = habitat.P1 = value1 / 100f;
                //int value = ((JSlider) e.getSource()).getValue();
                System.out.println("P1 value " + value1 + "%");
                int value2 = sliderProbability_truck.getValue();
                P2_d = habitat.P2 = value2 / 100f;
                //int value = ((JSlider) e.getSource()).getValue();
                System.out.println("P2 value " + value2 + "%");
            }
        };
        sliderProbability_car.addChangeListener(changeListener);
        sliderProbability_truck.addChangeListener(changeListener);

    }


    private void buttons() {
        ActionListener actionListenerB = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buttonStart || e.getSource() == menuItemStart) {
                    start();
                }
                if (e.getSource() == buttonStop || e.getSource() == menuItemStop) {
                    end();
                }
                if (e.getSource() == buttonShowTime || e.getSource() == menuItemShowTime) {
                    buttonShowTime.setSelected(true);
                    menuItemShowTime.setSelected(true);
                    timeTextLabel.setVisible(true);
                }
                if (e.getSource() == buttonHideTime || e.getSource() == menuItemHideTime) {
                    buttonHideTime.setSelected(true);
                    menuItemHideTime.setSelected(true);
                    timeTextLabel.setVisible(false);
                }

                if (e.getSource() == buttonShowStatistics) {
                    if (buttonShowStatistics.isSelected()) {
                        buttonShowStatistics.setSelected(true);
                        menuItemShowStatistics.setSelected(true);
                        statisticsIsVisible = true;
                    } else {
                        buttonShowStatistics.setSelected(false);
                        menuItemShowStatistics.setSelected(false);
                        statisticsIsVisible = false;
                    }
                }

                if (e.getSource() == menuItemShowStatistics) {
                    if (menuItemShowStatistics.isSelected()) {
                        buttonShowStatistics.setSelected(true);
                        menuItemShowStatistics.setSelected(true);
                        statisticsIsVisible = true;
                    } else {
                        buttonShowStatistics.setSelected(false);
                        menuItemShowStatistics.setSelected(false);
                        statisticsIsVisible = false;
                    }

                }

                if (e.getSource() == buttonCurrentObjects || e.getSource() == menuItemCurrentObjects) {
                    habitat.showCurrentObjects();
                }

                if (e.getSource() == menuItemSave) {
                    JFileChooser fc = new JFileChooser();
                    fc.setDialogTitle("Save file as");
                    fc.setFileFilter(filter);
                    fc.showSaveDialog(null);
                    File selFile = fc.getSelectedFile();
                    if(selFile!=null){
                        habitat.save(selFile);
                    }
                }

                if (e.getSource() == menuItemOpen) {

                    try {
                        JFileChooser fileopen = new JFileChooser();
                        fileopen.setDialogTitle("Open file");
                        fileopen.setFileFilter(filter);
                        fileopen.showOpenDialog(null);
                        File file = fileopen.getSelectedFile();
                        if(file!=null)habitat.open(startTime, file);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }

                if (e.getSource() == menuItemExit) {isDone = true;}
            }
        };


        menuItemStart.addActionListener(actionListenerB);
        menuItemStop.addActionListener(actionListenerB);
        menuItemShowTime.addActionListener(actionListenerB);
        menuItemHideTime.addActionListener(actionListenerB);
        menuItemShowStatistics.addActionListener(actionListenerB);
        menuItemCurrentObjects.addActionListener(actionListenerB);
        menuItemSave.addActionListener(actionListenerB);
        menuItemOpen.addActionListener(actionListenerB);
        menuItemExit.addActionListener(actionListenerB);

        buttonStart.addActionListener(actionListenerB);
        buttonStop.addActionListener(actionListenerB);
        buttonShowTime.addActionListener(actionListenerB);
        buttonHideTime.addActionListener(actionListenerB);
        buttonShowStatistics.addActionListener(actionListenerB);
        buttonCurrentObjects.addActionListener(actionListenerB);
    }


    private void keys() {
        habitat.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("B"), "menuItemStart");
        habitat.getActionMap().put("menuItemStart", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        habitat.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("E"), "finish");
        habitat.getActionMap().put("finish", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                end();
            }
        });
        habitat.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("T"), "time");
        habitat.getActionMap().put("time", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeTextLabel.isVisible()) {
                    buttonHideTime.setSelected(true);
                    menuItemHideTime.setSelected(true);
                    timeTextLabel.setVisible(false);
                } else {
                    buttonShowTime.setSelected(true);
                    menuItemShowTime.setSelected(true);
                    timeTextLabel.setVisible(true);
                }
            }
        });
        habitat.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
        habitat.getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDone = true;
            }
        });
    }
}
