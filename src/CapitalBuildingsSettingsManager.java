import Buildings.BuildingFactory;

public class CapitalBuildingsSettingsManager extends SettingsManager{
    public CapitalBuildingsSettingsManager(BuildingFactory buildingFactory) {
        super("Capital", buildingFactory);
    }

    @Override
    protected void InitGuiComponent() {
        _creationFrequencyManager = new CapitalBuildingsCreationFrequencyManager();
        _lifeTimeManager = new CapitalBuildingLifeTimeManager(_buildingFactory);
        SetButtonText("Настройки CapitalBuildings");
    }
}
