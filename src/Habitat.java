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
    private ArrayList<BaseBuild> _buildList = new ArrayList<BaseBuild>();
    private boolean _isPaused = true;

    private int woodTimerProgressValue;
    private int capitalTimerProgressValue;


    private double currentTime = 0;
    private Timer woodTimer = new Timer( currentTime );
    private Timer capitalTimer = new Timer( currentTime );
    private JLayeredPane _habbitViewLayeredPane;

    private BuildingFactory _buidingFactory;

    private Timer _simulationTimer;

    public void StartHandler(double currentTime ) {
        this.currentTime = currentTime; 
        _isPaused = true;
        woodTimer.pause( currentTime );
        capitalTimer.pause( currentTime );
        _habbitViewLayeredPane.removeAll();
        woodTimerProgressValue = 0;
        capitalTimerProgressValue = 0;
    }

    public void StopHandler(double currentTime ) {
        this.currentTime = currentTime; 
        _buildList.clear();
        woodTimer.restart( currentTime );
        capitalTimer.restart( currentTime );
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

    public int GetWoodProgress() {
        return woodTimerProgressValue;
    }

    public int GetCapitalProgress() {
        return capitalTimerProgressValue;
    }

    public boolean IsPaused(){
        return _isPaused;
    }

    public ArrayList<BaseBuild> GetBuildList(){
        return _buildList;
    }

    public Habitat(BuildingFactory buidingFactory, JLayeredPane habbitViewLayeredPane) {
        _buidingFactory = buidingFactory;
        _habbitViewLayeredPane = habbitViewLayeredPane;
        setLayout(new GridLayout(1, 1));
        setBounds(0, 0, 250, 250);
        setBackground(Color.gray);
        add(_habbitViewLayeredPane);
    }


    public void Update(double currentTime) {
        this.currentTime = currentTime; 
        if (!_isPaused) {
            _buidingFactory.RemoveOld();

            Random myRand = new Random();

            woodTimer.update( currentTime );
            capitalTimer.update( currentTime );

            woodTimerProgressValue = (int) (woodTimer.workTime / 10 / WoodBuild.GetN());
            capitalTimerProgressValue = (int) (capitalTimer.workTime / 10 / CapitalBuild.GetN());

            if (woodTimerProgressValue >= 100) {
                if (myRand.nextDouble() < WoodBuild.GetP()) {
                    AddObj(_buidingFactory.GetWoodBuilding());
                }
                woodTimer.restart(currentTime);
            }

            if (capitalTimerProgressValue >= 100) {
                if (myRand.nextDouble() < CapitalBuild.GetP()) {
                    AddObj(_buidingFactory.GetCapitalBuilding());
                }
                capitalTimer.restart( currentTime );
            }

        }
        
        _habbitViewLayeredPane.repaint();
    }

    private void AddObj(BaseBuild obj) {
        Random myRand = new Random();
        obj.setX(myRand.nextInt((int) _habbitViewLayeredPane.getSize().getWidth() - obj.getWidth()));
        obj.setY(myRand.nextInt((int) _habbitViewLayeredPane.getSize().getHeight() - obj.getHeight()));
        int intIndex = obj.getY() + obj.getHeight();
        Integer index = Integer.valueOf(intIndex);
        _habbitViewLayeredPane.add( obj.label, index, -1);
    }
}