import javax.swing.*;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class Program extends JFrame {

    private boolean isWorking;
    private boolean statisticsIsVisible;
    public boolean isDone;
    private long startTime;
    private long timePrev;
    private float timeElapsed;

    private JMenuBar menuBar;
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
    private JTextField periodicity_carField, probability_motocycleField, lifetime_carField, lifetime_motocyclesField;

    private JSlider sliderProbability_car, sliderProbability_motocycle;

    private Habitat habitat;

    public Program() {
        isWorking = isDone = false;
        statisticsIsVisible = true;
        startTime = 0;
        timePrev = System.currentTimeMillis();
        timeElapsed = 0;

        createGUI();
    }

    private void createGUI() {
        setTitle("Vehicles on the road");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());

        JPanel guiPanel = new JPanel();
        guiPanel.setPreferredSize(new Dimension(250, 600));
        add(guiPanel);

        habitat = new Habitat();
        add(habitat);

        buttonStart = new JButton("Start");
        buttonStart.setVisible(true);
        buttonStart.setFont(buttonStart.getFont().deriveFont(21f));
        buttonStart.setFont(buttonStart.getFont().deriveFont(Font.PLAIN));
        guiPanel.add(buttonStart);

        buttonStop = new JButton("Stop");
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

        sliderProbability_car = new JSlider(0, 100, 50);
        sliderProbability_car.setPaintLabels(true);
        sliderProbability_car.setMajorTickSpacing(10);
        guiPanel.add(sliderProbability_car);

        periodicity_car = new JLabel("Periodicity of spawn: ");
        guiPanel.add(periodicity_car);

        periodicity_carField = new JTextField(10);
        periodicity_carField.setText("1");
        periodicity_carField.setToolTipText("Put here value in seconds in the range of 1..10");
        guiPanel.add(periodicity_carField);

        lifetime_car = new JLabel("Lifetime of car: ");
        guiPanel.add(lifetime_car);

        lifetime_carField = new JTextField(10);
        lifetime_carField.setText("10");
        lifetime_carField.setToolTipText("Put here value in seconds in the range of 10..50");
        guiPanel.add(lifetime_carField);

        motocycles = new JLabel("Trucks:                    ");
        guiPanel.add(motocycles);
        motocycles.setFont(motocycles.getFont().deriveFont(20f));

        probability_motocycle = new JLabel("Probability of spawn: ");
        guiPanel.add(probability_motocycle);

        sliderProbability_motocycle = new JSlider(0, 100, 50);
        sliderProbability_motocycle.setPaintLabels(true);
        sliderProbability_motocycle.setMajorTickSpacing(10);
        guiPanel.add(sliderProbability_motocycle);

        periodicity_motocycle = new JLabel("Periodicity of spawn: ");
        guiPanel.add(periodicity_motocycle);

        probability_motocycleField = new JTextField(10);
        probability_motocycleField .setText("2");
        probability_motocycleField.setToolTipText("Put here value in seconds in the range of 1..10");
        guiPanel.add(probability_motocycleField);

        lifetime_motocycle = new JLabel("Lifetime of truck: ");
        guiPanel.add(lifetime_motocycle);

        lifetime_motocyclesField = new JTextField(10);
        lifetime_motocyclesField.setText("20");
        lifetime_motocyclesField.setToolTipText("Put here value in seconds in the range of 10..50");
        guiPanel.add(lifetime_motocyclesField);

        timeTextLabel = new JLabel("Time: 0,00");
        timeTextLabel.setVisible(true);
        timeTextLabel.setFont(timeTextLabel.getFont().deriveFont(25f));
        timeTextLabel.setFont(timeTextLabel.getFont().deriveFont(Font.PLAIN));
        guiPanel.add(timeTextLabel);

        menuBar = new JMenuBar();
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
        float timeStep = 1 / 50f;
        long timeCurrent = System.currentTimeMillis();
        timeElapsed += (timeCurrent - timePrev) / 1000.f;
        timePrev = timeCurrent;

        while (timeElapsed >= timeStep) {
            if (isWorking) {
                long time = System.currentTimeMillis() - startTime;
                habitat.update(time);
                habitat.visitingRound(timeStep*1000);
                updateTime(time);
            }
            timeElapsed -= timeStep;
        }
    }

    private JMenu createSimulationMenu() {
        JMenu simulationMenu = new JMenu("Simulation");

        menuItemStart = new JMenuItem("Start");
        menuItemStop = new JMenuItem("Stop");

        menuItemShowStatistics = new JCheckBoxMenuItem("Show statistics");
        menuItemShowStatistics.setSelected(buttonShowStatistics.isSelected());

        menuItemShowTime = new JRadioButtonMenuItem("Show time");
        menuItemHideTime = new JRadioButtonMenuItem("Hide time");

        ButtonGroup bg1 = new ButtonGroup();
        menuItemShowTime.setSelected(buttonShowTime.isSelected());
        menuItemHideTime.setSelected(buttonHideTime.isSelected());
        bg1.add(menuItemShowTime);
        bg1.add(menuItemHideTime);
        menuItemCurrentObjects=new JMenuItem("Current objects");

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
                long value_periodicity_car;
                try {
                    value_periodicity_car = Integer.parseInt(periodicity_carField.getText());
                } catch (NumberFormatException exp) {
                    JOptionPane.showConfirmDialog(null, "Value must be integer",
                            "Wrong data type",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_periodicity_car = 1;
                    periodicity_carField.setText("1");
                }
                if (value_periodicity_car < 1 || value_periodicity_car > 10) {
                    JOptionPane.showConfirmDialog(null, "Value must be in the range of 1..10",
                            "Out of range error",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_periodicity_car = 1;
                    periodicity_carField.setText("1");
                } else {
                    habitat.N1 = value_periodicity_car * 1000;
                }

            }

        };
        ActionListener actionListener_probability_truckField = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long value_probability_motocycle;
                try {
                    value_probability_motocycle = Integer.parseInt(probability_motocycleField.getText());
                } catch (NumberFormatException exp) {
                    JOptionPane.showConfirmDialog(null, "Value must be integer",
                            "Wrong data type",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    //probability_truckField.setText(null);
                    value_probability_motocycle = 2;
                    probability_motocycleField.setText("2");
                }
                if (value_probability_motocycle < 1 || value_probability_motocycle > 10) {
                    JOptionPane.showConfirmDialog(null, "Value must be in the range of 1..10",
                            "Out of range error",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_probability_motocycle = 2;
                    probability_motocycleField.setText("2");
                } else {
                    habitat.N2 = value_probability_motocycle * 1000;
                }
            }

        };
        ActionListener actionListener_lifetime_carField = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long value_lifetime_car;
                try {
                    value_lifetime_car = Integer.parseInt(lifetime_carField.getText());
                } catch (NumberFormatException exp) {
                    JOptionPane.showConfirmDialog(null, "Value must be integer",
                            "Wrong data type",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_lifetime_car = 10;
                    lifetime_carField.setText("10");
                }
                if (value_lifetime_car < 10 || value_lifetime_car > 50) {
                    JOptionPane.showConfirmDialog(null, "Value must be in the range of 10..50",
                            "Out of range error",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    lifetime_carField.setText("10");
                } else {
                    habitat.lifetime_car = value_lifetime_car * 1000;
                }
            }

        };
        ActionListener actionListener_lifetime_truckField = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long value_lifetime_motocycle;
                try {
                    value_lifetime_motocycle = Integer.parseInt(lifetime_motocyclesField.getText());
                } catch (NumberFormatException exp) {
                    JOptionPane.showConfirmDialog(null, "Value must be integer",
                            "Wrong data type",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    //probability_truckField.setText(null);
                    value_lifetime_motocycle = 20;
                    lifetime_motocyclesField.setText("20");
                }
                if (value_lifetime_motocycle < 10 || value_lifetime_motocycle > 50) {
                    JOptionPane.showConfirmDialog(null, "Value must be in the range of 10..50",
                            "Out of range error",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    lifetime_motocyclesField.setText("20");
                } else {
                    habitat.lifetime_motocycle = value_lifetime_motocycle * 1000;
                }
            }

        };

        lifetime_motocyclesField.addActionListener(actionListener_lifetime_truckField);
        lifetime_carField.addActionListener(actionListener_lifetime_carField);
        periodicity_carField.addActionListener(actionListener_periodicity_carField);
        lifetime_motocyclesField.addActionListener(actionListener_probability_truckField);
    }

    private void slides() {
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value1 = sliderProbability_car.getValue();
                habitat.P1 = value1 / 100f;
                //int value = ((JSlider) e.getSource()).getValue();
                System.out.println("P1 value " + value1 + "%");
                int value2 = sliderProbability_motocycle.getValue();
                habitat.P2 = value2 / 100f;
                //int value = ((JSlider) e.getSource()).getValue();
                System.out.println("P2 value " + value2 + "%");
            }
        };
        sliderProbability_car.addChangeListener(changeListener);
        sliderProbability_motocycle.addChangeListener(changeListener);

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

            }
        };


        menuItemStart.addActionListener(actionListenerB);
        menuItemStop.addActionListener(actionListenerB);
        menuItemShowTime.addActionListener(actionListenerB);
        menuItemHideTime.addActionListener(actionListenerB);
        menuItemShowStatistics.addActionListener(actionListenerB);
        menuItemCurrentObjects.addActionListener(actionListenerB);

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
