import javax.swing.*;

import Buildings.BaseAI;
import Buildings.BuildingFactory;
import SubClasses.NumberInput;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class SettingsManager extends JPanel {
    private static final long serialVersionUID = 1L;

    protected CreationFrequencyManager _creationFrequencyManager;
    protected LifeTimeManager _lifeTimeManager;
    protected NumberInput _numberInput;
    protected BuildingThreadManager _buildingThreadManager;
    protected BaseAI _ai;

    protected BuildingFactory _buildingFactory;
    protected String _type;

    private JButton _showHideButton;
    private boolean _isVisible = false;
    private JPanel _components;

    public SettingsManager(String type, BuildingFactory buildingFactory, BaseAI ai) {
        _buildingFactory = buildingFactory;
        _type = type;
        _ai = ai;
        InitGui();
    }

    protected abstract void InitGuiComponent();

    private void InitGui()
    {
        setLayout(new BorderLayout());
        _numberInput = new NumberInput(_type);
        _showHideButton = new JButton();

        InitGuiComponent();
        _components = new JPanel();
        _components.setLayout(new BoxLayout(_components, BoxLayout.Y_AXIS));
        _components.add(_numberInput);
        _components.add(_creationFrequencyManager);
        _components.add(_lifeTimeManager);
        _components.add(_buildingThreadManager);
        add(_showHideButton, BorderLayout.PAGE_START);
        add(_components);

        ConfigureButton();
        _components.setVisible(_isVisible);
    }

    protected void SetButtonText(String value)
    {
        _showHideButton.setText(value);
    }

    private void ConfigureButton()
    {
        _showHideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _isVisible = !_isVisible;
                _components.setVisible(_isVisible);
            }
        });
    }

    public void changeProgressBar(int progress) {
        _numberInput.SetSliderProgress(progress);
    }
    public void SetThreadButtonEnable(boolean value)
    {
        _buildingThreadManager.ChangeState(value);
    }
}

