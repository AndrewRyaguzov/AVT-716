
import Buildings.BuildingFactory;
import Buildings.CapitalAI;
import Buildings.WoodAI;
import SubClasses.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

public class App extends JFrame {
    private Timer _simulationTimer;
    private LogDialog logDialog;
    private GUI gui;
    private Habitat habitat;

    private JMenuBar jMenuBar = new JMenuBar();
    private JMenu jmCommands;
    private JMenuItem jmiStart;
    private JMenuItem jmiStop;
    private JMenuItem jmiTime;

    private BuildingFactory _buildingFactory;

    private WoodAI _woodAi;
    private CapitalAI _capitalAi;
    private JLayeredPane _habbitViewLayeredPane = new JLayeredPane();

    public App() {
        super("Town");
        _simulationTimer = new Timer(System.currentTimeMillis());
        _buildingFactory = new BuildingFactory(_simulationTimer, _habbitViewLayeredPane);
        _woodAi = new WoodAI(_simulationTimer, _buildingFactory);
        _capitalAi = new CapitalAI(_simulationTimer, _buildingFactory);
        InitGui();
    }

    private void InitGui()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        logDialog = new LogDialog(this);
        habitat = new Habitat(_buildingFactory, _habbitViewLayeredPane);
        gui = new GUI(_buildingFactory, _woodAi, _capitalAi);

        jmCommands = new JMenu("Комманды");
        jmiStart = new JMenuItem("Старт");
        jmiStop = new JMenuItem("Стоп");
        jmiTime = new JMenuItem("Cкрыть время симуляции");

        setLayout(new BorderLayout());
        add(gui, BorderLayout.WEST);
        add(habitat, BorderLayout.CENTER);
        this.pack();

        this.setBounds(270, 50, 1280, 920);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new MyDispatcher());
        gui.buttonStart.addActionListener(new Butlist());
        gui.tbStart.addActionListener(new Butlist());
        gui.tbStop.addActionListener(new Butlist());

        gui.showTimeButton.addItemListener(new TimerVisibleStatelist());
        gui.hideTimeButton.addItemListener(new TimerVisibleStatelist());

        gui.tbTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(gui.showTimeButton.isSelected())
                {
                    gui.hideTimeButton.setSelected(true);
                    gui.showTimeButton.setSelected(false);
                }else
                {

                    gui.hideTimeButton.setSelected(false);
                    gui.showTimeButton.setSelected(true);

                }
            }
        });
        jmiStart.addActionListener(new Butlist());
        jmiStop.addActionListener(new Butlist());
        jmiStop.setEnabled(false);

        jmiTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(gui.showTimeButton.isSelected())
                {
                    gui.hideTimeButton.setSelected(true);
                    gui.showTimeButton.setSelected(false);
                }else
                {
                    gui.hideTimeButton.setSelected(false);
                    gui.showTimeButton.setSelected(true);
                }
            }
        });

        jmCommands.add(jmiStart);
        jmCommands.add(jmiStop);
        jmCommands.add(jmiTime);
        jMenuBar.add(jmCommands);
        setJMenuBar(jMenuBar);

        logDialog.setVisible(false);
    }

    public void Run() {
        while (JFrame.getFrames() != null) {
            _simulationTimer.update(System.currentTimeMillis());
            habitat.Update(_simulationTimer.workTime);

            gui.workTime.SetTime(_simulationTimer.workTime);

            gui.changeProgressBars(_woodAi.GetProgress(), _capitalAi.GetProgress());
        }
    }

    private class Butlist implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (habitat.IsPaused()) {
                Start();
            } else {
                Pause();
            }
            habitat.repaint();
        }
    }

    private class TimerVisibleStatelist implements ItemListener{
        public void itemStateChanged(ItemEvent e){
            
            if(gui.showTimeButton.isSelected())
            {
                gui.workTime.setVisible(true);
                
                jmiTime.setText("Скрыть время симуляции");
                gui.tbTime.setText("Скрыть время симуляции");
            }else
            {
                gui.workTime.setVisible(false);
                
                jmiTime.setText("Показать время симуляции");
                gui.tbTime.setText("Показать время симуляции");
            }
        }
    }

    private class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_RELEASED) {
                if(e.getKeyCode() == KeyEvent.VK_B && habitat.IsPaused()){
                    Start();
                    habitat.RepaintLayered();
                }
                if (e.getKeyCode() == KeyEvent.VK_E && !habitat.IsPaused()) {
                    Pause();
                    habitat.RepaintLayered();
                }
    
                if (e.getKeyCode() == KeyEvent.VK_T){
                    if(!logDialog.isActive()){
                        boolean isVisible = gui.workTime.isVisible();
                        if (!isVisible)
                        {
                            gui.showTimeButton.setSelected(true);
                            gui.hideTimeButton.setSelected(false);
                        }
                        else{
                            gui.hideTimeButton.setSelected(true);
                            gui.showTimeButton.setSelected(false);
                        }
                    }
                }
    
            }
            return false;
        }
    }

    private void Start(){
        
        gui.buttonStart.setText("Pause");
        gui.buttonStart.setBackground(Color.RED);

        gui.tbStart.setEnabled(false);
        gui.tbStop.setEnabled(true);

        jmiStart.setEnabled(false);
        jmiStop.setEnabled(true);

        habitat.StopHandler(_simulationTimer.workTime);
        _simulationTimer.unpause(System.currentTimeMillis());
        _woodAi.Start();
        _capitalAi.Start();
        gui.SetThreadButtonEnable(true);
    }

    private void Pause() {
        gui.SetThreadButtonEnable(false);
        if (gui.buttonStart.isEnabled()) {
            _simulationTimer.pause(System.currentTimeMillis());

            if (gui.checkBox_showInfo.isSelected()) {
                _woodAi.Pause();
                _capitalAi.Pause();
                long woodBuildCount = _buildingFactory.GetAliveWoodBuildingsCount();
                long capitalBuildCount = _buildingFactory.GetAliveCapitalBuildingsCount();

                gui.buttonStart.setEnabled(false);
                gui.tbStart.setEnabled(false);
                gui.tbStop.setEnabled(false);
                jmiStart.setEnabled(false);
                jmiStop.setEnabled(false);

                logDialog.Update(_buildingFactory.GetAliveBuildings().size(), woodBuildCount, capitalBuildCount,
                        _simulationTimer.workTime);
                logDialog.setVisible(true);
            } else {
                _simulationTimer.stop(System.currentTimeMillis());
                _woodAi.Stop();
                _capitalAi.Stop();
                gui.buttonStart.setText("Start");
                gui.buttonStart.setBackground(Color.GREEN);

                gui.tbStart.setEnabled(true);
                gui.tbStop.setEnabled(false);
                jmiStart.setEnabled(true);
                jmiStop.setEnabled(false);

                habitat.StartHandler(_simulationTimer.workTime);
            }
        }
    }

    public void DialogResult(int res){
        
        gui.buttonStart.setEnabled(true);

        if ( res == 1 ){    
            gui.buttonStart.setText("Start");
            gui.buttonStart.setBackground(Color.GREEN);
            
            habitat.StartHandler( _simulationTimer.workTime );
            _woodAi.Stop();
            _capitalAi.Stop();
            gui.SetThreadButtonEnable(false);

            _simulationTimer.stop(System.currentTimeMillis());

            jmiStart.setEnabled(true);
            jmiStop.setEnabled(false);
            gui.tbStart.setEnabled(true);
            gui.tbStop.setEnabled(false);
        }
        if ( res == 0 ){
            _simulationTimer.unpause( System.currentTimeMillis() );
            habitat.ContinueHandler();
            _woodAi.Start();
            _capitalAi.Start();
            gui.SetThreadButtonEnable(true);
            jmiStart.setEnabled(false);
            jmiStop.setEnabled(true);
            gui.tbStart.setEnabled(false);
            gui.tbStop.setEnabled(true);
        }

    }
}
