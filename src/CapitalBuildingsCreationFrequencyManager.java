import Buildings.CapitalBuild;

public class CapitalBuildingsCreationFrequencyManager extends CreationFrequencyManager {
    public CapitalBuildingsCreationFrequencyManager()
    {
        _slider.setValue((int)(CapitalBuild.GetP() * 100));
        SetText("CapitalBuilding");
    }
    @Override
    public void SetFrequency(int value) {
        CapitalBuild.SetP(value);
    }
}
