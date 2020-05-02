import Buildings.BuildingFactory;

public class WoodBuildingsSettingsManager extends SettingsManager{
    public WoodBuildingsSettingsManager(BuildingFactory buildingFactory) {
        super("Wood", buildingFactory);
    }

    @Override
    protected void InitGuiComponent() {
        _creationFrequencyManager = new WoodBuildingsCreationFrequencyManager();
        _lifeTimeManager = new WoodBuildingLifeTimeManager(_buildingFactory);
        SetButtonText("Настройки WoodBuildings");
    }
}
