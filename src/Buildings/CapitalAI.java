package Buildings;

import SubClasses.Timer;

public class CapitalAI extends BaseAI {
    public CapitalAI(Timer simulationTimer, BuildingFactory buildingFactory) {
        super(simulationTimer, buildingFactory);
    }

    @Override
    protected double GetN() {
        return CapitalBuild.GetN();
    }

    @Override
    protected double GetP() {
        return CapitalBuild.GetP();
    }

    @Override
    protected void Create() {
        _buildingFactory.CreateCapitalBuilding();
    }

    @Override
    protected BaseBuild[] GetBuildings() {
        // хрень полная, но не совсем понятно как правильно кастить
        BaseBuild[] temp = new BaseBuild[]{};
        return _buildingFactory.CapitalBuildings().toArray(temp);
    }
}