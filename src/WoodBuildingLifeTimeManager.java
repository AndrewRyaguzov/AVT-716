import Buildings.BuildingFactory;
import Buildings.WoodBuild;

public class WoodBuildingLifeTimeManager extends LifeTimeManager<WoodBuild> {
    public WoodBuildingLifeTimeManager(BuildingFactory buildingFactory) {
        super(buildingFactory);
        SetText(TextBuilder(_buildingFactory.GetWoodBuildingLifeTime()));
    }

    @Override
    public void SetLifeTime(double value)
    {
        _buildingFactory.SetWoodBuildingLifeTime(value);
        SetText(TextBuilder(value));
    }

    private String TextBuilder(double value)
    {
        return "Время жизни WoodBuildings: " + value + " сек";
    }
}
