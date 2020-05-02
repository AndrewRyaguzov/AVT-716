import Buildings.BaseAI;
import Buildings.BuildingFactory;
import Buildings.WoodAI;

public class WoodBuildingsSettingsManager extends SettingsManager{
    public WoodBuildingsSettingsManager(BuildingFactory buildingFactory, WoodAI _woodAi) {
        super("Wood", buildingFactory, _woodAi);
    }

    @Override
    protected void InitGuiComponent() {
        _buildingThreadManager = new WoodBuildingThreadManager(_ai);
        _creationFrequencyManager = new WoodBuildingsCreationFrequencyManager();
        _lifeTimeManager = new WoodBuildingLifeTimeManager(_buildingFactory);
        SetButtonText("Настройки WoodBuildings");
    }
}
