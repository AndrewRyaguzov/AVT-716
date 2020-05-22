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
    private JMenuItem menuItemStart, menuItemStop;
    private JCheckBoxMenuItem menuItemShowStatistics;
    private JRadioButtonMenuItem menuItemShowTime, menuItemHideTime;

    private JLabel timeTextLabel, cars, motocycles, p1, p2, b1, b2;
    private JButton buttonStart, buttonStop;
    private ButtonGroup timeVisible;
    private JRadioButton buttonShowTime, buttonHideTime;
    private JCheckBox buttonShowStatistics;
    private JTextField N1Field, N2Field;

    private JSlider sliderP1, sliderP2;

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
        buttonStart.setFont(buttonStart.getFont().deriveFont(20f));
        buttonStart.setFont(buttonStart.getFont().deriveFont(Font.PLAIN));
        guiPanel.add(buttonStart);

        buttonStop = new JButton("Stop");
        buttonStop.setVisible(true);
        buttonStop.setFont(buttonStop.getFont().deriveFont(20f));
        buttonStop.setFont(buttonStop.getFont().deriveFont(Font.PLAIN));
        guiPanel.add(buttonStop);

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

        p1 = new JLabel("Probability of spawn: ");
        guiPanel.add(p1);

        sliderP1 = new JSlider(0, 100, 50);
        sliderP1.setPaintLabels(true);
        sliderP1.setMajorTickSpacing(10);
        guiPanel.add(sliderP1);

        b1 = new JLabel("Periodicity of spawn: ");
        guiPanel.add(b1);

        N1Field = new JTextField(10);
        N1Field.setText("1");
        N1Field.setToolTipText("Put here value in seconds in the range of 1..10");
        guiPanel.add(N1Field);

        motocycles = new JLabel("Motocycles:                    ");
        guiPanel.add(motocycles);
        motocycles.setFont(motocycles.getFont().deriveFont(20f));

        p2 = new JLabel("Probability of spawn: ");
        guiPanel.add(p2);

        sliderP2 = new JSlider(0, 100, 50);
        sliderP2.setPaintLabels(true);
        sliderP2.setMajorTickSpacing(10);
        guiPanel.add(sliderP2);

        b2 = new JLabel("Periodicity of spawn: ");
        guiPanel.add(b2);

        N2Field = new JTextField(10);
        N2Field.setText("2");
        N2Field.setToolTipText("Put here value in seconds in the range of 1..10");
        guiPanel.add(N2Field);

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
            startTime = System.currentTimeMillis();
            habitat.start();
            isWorking = true;
            System.out.println("Begin");
        }
    }

    private void end() {
        if (isWorking) {
            isWorking = false;
            long stopTime = System.currentTimeMillis() - startTime;
            isWorking = habitat.fin(System.currentTimeMillis() - startTime, statisticsIsVisible);
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

        menuItemShowTime = new JRadioButtonMenuItem("Show time");
        menuItemHideTime = new JRadioButtonMenuItem("Hide time");

        ButtonGroup bg1 = new ButtonGroup();
        menuItemShowTime.setSelected(buttonShowTime.isSelected());
        menuItemHideTime.setSelected(buttonHideTime.isSelected());
        bg1.add(menuItemShowTime);
        bg1.add(menuItemHideTime);

        simulationMenu.add(menuItemStart);
        simulationMenu.add(menuItemStop);
        simulationMenu.add(new JSeparator());
        simulationMenu.add(menuItemShowTime);
        simulationMenu.add(menuItemHideTime);
        simulationMenu.add(new JSeparator());
        simulationMenu.add(menuItemShowStatistics);

        return simulationMenu;
    }

    private void fields() {
        ActionListener actionListenerF1 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long value_N1;
                try {
                    value_N1 = Integer.parseInt(N1Field.getText());
                } catch (NumberFormatException exp) {
                    JOptionPane.showConfirmDialog(null, "Value must be integer",
                            "Wrong data type",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_N1 = 1;
                    N1Field.setText("1");
                }
                if (value_N1 < 1 || value_N1 > 10) {
                    JOptionPane.showConfirmDialog(null, "Value must be in the range of 1..10",
                            "Out of range error",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_N1 = 1;
                    N1Field.setText("1");
                } else {
                    habitat.N1 = value_N1 * 1000;
                }

            }

        };
        N1Field.addActionListener(actionListenerF1);

        ActionListener actionListenerF2 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long value_N2;
                try {
                    value_N2 = Integer.parseInt(N2Field.getText());
                } catch (NumberFormatException exp) {
                    JOptionPane.showConfirmDialog(null, "Value must be integer",
                            "Wrong data type",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    //N2Field.setText(null);
                    value_N2 = 2;
                    N2Field.setText("2");
                }
                if (value_N2 < 1 || value_N2 > 10) {
                    JOptionPane.showConfirmDialog(null, "Value must be in the range of 1..10",
                            "Out of range error",
                            JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    value_N2 = 2;
                    N2Field.setText("2");
                } else {
                    habitat.N2 = value_N2 * 1000;
                }
            }

        };
        N2Field.addActionListener(actionListenerF2);
    }

    private void slides() {
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value1 = sliderP1.getValue();
                habitat.P1 = value1 / 100f;
                //int value = ((JSlider) e.getSource()).getValue();
                System.out.println("P1 value " + value1 + "%");
                int value2 = sliderP2.getValue();
                habitat.P2 = value2 / 100f;
                //int value = ((JSlider) e.getSource()).getValue();
                System.out.println("P2 value " + value2 + "%");
            }
        };
        sliderP1.addChangeListener(changeListener);
        sliderP2.addChangeListener(changeListener);

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

            }
        };

        menuItemStart.addActionListener(actionListenerB);
        menuItemStop.addActionListener(actionListenerB);
        menuItemShowTime.addActionListener(actionListenerB);
        menuItemHideTime.addActionListener(actionListenerB);
        menuItemShowStatistics.addActionListener(actionListenerB);

        buttonStart.addActionListener(actionListenerB);
        buttonStop.addActionListener(actionListenerB);
        buttonShowTime.addActionListener(actionListenerB);
        buttonHideTime.addActionListener(actionListenerB);
        buttonShowStatistics.addActionListener(actionListenerB);
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
                    //e.setSource(buttonHideTime);
                    //buttons();
                    buttonHideTime.setSelected(true);
                    timeTextLabel.setVisible(false);
                } else {
                    //e.setSource(buttonShowTime);
                    buttonShowTime.setSelected(true);
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
