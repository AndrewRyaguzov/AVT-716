import Buildings.WoodBuild;

public class WoodBuildingsCreationFrequencyManager extends CreationFrequencyManager {
    public WoodBuildingsCreationFrequencyManager()
    {
        _slider.setValue((int) (WoodBuild.GetP() * 100));
        SetText("WoodBuilding");
    }

    @Override
    public void SetFrequency(int value) {
        WoodBuild.SetP(value);
    }
}
