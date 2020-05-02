package Buildings;

import SubClasses.Timer;

public class WoodAI extends BaseAI
{
    public WoodAI(Timer simulationTimer, BuildingFactory buildingFactory) {
        super(simulationTimer, buildingFactory);
    }

    @Override
    protected double GetN() {
        return WoodBuild.GetN();
    }

    @Override
    protected double GetP() {
        return WoodBuild.GetP();
    }

    @Override
    protected void Create() {
        _buildingFactory.CreateWoodBuilding();
    }

    @Override
    protected BaseBuild[] GetBuildings()
    {
        // хрень полная, но не совсем понятно как правильно кастить
        BaseBuild[] temp = new BaseBuild[]{};
        return  _buildingFactory.WoodBuildings().toArray(temp);
    }
}
