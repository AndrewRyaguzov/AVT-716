import Buildings.BaseAI;
import Buildings.BuildingFactory;
import Buildings.CapitalAI;

public class CapitalBuildingsSettingsManager extends SettingsManager{
    public CapitalBuildingsSettingsManager(BuildingFactory buildingFactory, CapitalAI ai) {
        super("Capital", buildingFactory, ai);
    }

    @Override
    protected void InitGuiComponent() {
        _buildingThreadManager = new CapitalBuildingThreadManager(_ai);
        _creationFrequencyManager = new CapitalBuildingsCreationFrequencyManager();
        _lifeTimeManager = new CapitalBuildingLifeTimeManager(_buildingFactory);
        SetButtonText("Настройки CapitalBuildings");
    }
}
