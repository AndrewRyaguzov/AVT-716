import javax.swing.*;

import Buildings.BaseBuild;
import Buildings.BuildingFactory;
import Buildings.CapitalBuild;
import Buildings.WoodBuild;
import SubClasses.Timer;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

public class Habitat extends JPanel{
    private static final long _serialVersionUID = 1L;
    private boolean _isPaused = true;

    private JLayeredPane _habbitViewLayeredPane;
    private BuildingFactory _buildingFactory;
    private Timer _simulationTimer;

    public void StartHandler(double currentTime ) {
        _isPaused = true;
        _habbitViewLayeredPane.removeAll();
    }

    public void StopHandler(double currentTime ) {
        _isPaused = false;
    }

    public void PauseHandler(){
        _isPaused = true;
    }

    public void ContinueHandler(){
        _isPaused = false;
    }

    public void RepaintLayered() {
        _habbitViewLayeredPane.repaint();
    }

    public boolean IsPaused(){
        return _isPaused;
    }

    public Habitat(BuildingFactory buildingFactory, JLayeredPane habbitViewLayeredPane) {
        _buildingFactory = buildingFactory;
        _habbitViewLayeredPane = habbitViewLayeredPane;
        setLayout(new GridLayout(1, 1));
        setBounds(0, 0, 250, 250);
        setBackground(Color.gray);
        add(_habbitViewLayeredPane);
    }


    public void Update(double currentTime) {
        if (!_isPaused) {
            _buildingFactory.RemoveOld();

            //_buidingFactory.MoveAll();
        }
        
        _habbitViewLayeredPane.repaint();
    }
}