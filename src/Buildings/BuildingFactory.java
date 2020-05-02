package Buildings;

import SubClasses.Timer;

import javax.swing.*;
import java.util.*;

public class BuildingFactory {
    private Timer _simulationTimer;
    private JLayeredPane _habbitViewLayeredPane;
    private BuildingCollection _buildings = new BuildingCollection();

    private double _woodBuildingLifeTime = 10000;
    public double GetWoodBuildingLifeTime(){return  _woodBuildingLifeTime / 1000;}
    public void SetWoodBuildingLifeTime(double value){ _woodBuildingLifeTime = value * 1000;}

    private double _capitalBuildingLifeTime = 10000;
    public double GetCapitalBuildingLifeTime(){return  _capitalBuildingLifeTime / 1000;}
    public void SetCapitalBuildingLifeTime(double value){ _capitalBuildingLifeTime = value * 1000;}

    public BuildingFactory(Timer simulationTimer, JLayeredPane habbitViewLayeredPane)
    {
        _simulationTimer = simulationTimer;
        _habbitViewLayeredPane = habbitViewLayeredPane;
    }

    public WoodBuild CreateWoodBuilding()
    {
        WoodBuild temp = new WoodBuild(_simulationTimer.workTime, _woodBuildingLifeTime, _habbitViewLayeredPane, 20);
        _buildings.AddWoodBuilding(temp);
        return temp;
    }

    public CapitalBuild CreateCapitalBuilding()
    {
        CapitalBuild temp = new CapitalBuild(_simulationTimer.workTime, _capitalBuildingLifeTime, _habbitViewLayeredPane, 1);
        _buildings.AddCapitalBuilding(temp);
        return temp;
    }

    public void RemoveOld()
    {
        List<BaseBuild> removed = _buildings.GetOldRemoved(_simulationTimer.workTime);
        for (BaseBuild build : removed)
        {
            _habbitViewLayeredPane.remove(build.label);
        }
    }

    public Vector<BaseBuild> GetAliveBuildings()
    {
        return _buildings.GetAliveBuildings();
    }

    public long GetAliveWoodBuildingsCount()
    {
        return _buildings.GetAliveBuildings().stream().filter(x -> x instanceof WoodBuild).count();
    }

    public long GetAliveCapitalBuildingsCount()
    {
        return _buildings.GetAliveBuildings().stream().filter(x -> x instanceof CapitalBuild).count();
    }

    public ArrayList<WoodBuild> WoodBuildings(){return _buildings.WoodBuildings();}
    public ArrayList<CapitalBuild> CapitalBuildings(){return _buildings.CapitalBuildings();}
}
