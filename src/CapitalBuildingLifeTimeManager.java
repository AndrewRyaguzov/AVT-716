import Buildings.BuildingFactory;

public class CapitalBuildingLifeTimeManager extends LifeTimeManager {
    public CapitalBuildingLifeTimeManager(BuildingFactory buildingFactory) {
        super(buildingFactory);
        SetText(TextBuilder(_buildingFactory.GetCapitalBuildingLifeTime()));
    }

    @Override
    public void SetLifeTime(double value)
    {
        _buildingFactory.SetCapitalBuildingLifeTime(value);
        SetText(TextBuilder(value));
    }

    private String TextBuilder(double value)
    {
        return "Время жизни CapitalBuildings: " + value + " сек";
    }
}

