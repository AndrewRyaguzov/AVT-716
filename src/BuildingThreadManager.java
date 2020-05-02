import Buildings.BaseAI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class BuildingThreadManager extends JPanel {
    private String[] _threadPriority = new String[]{"1","2","3","4","5","6","7","8","9", "10"};

    protected BaseAI _ai;
    private JButton _stopBuildings = new JButton();
    private JComboBox _combobox = new JComboBox(_threadPriority);
    private JLabel _label = new JLabel("Приоритет");
    private boolean _isEnable;

    public BuildingThreadManager(BaseAI ai)
    {
        _ai = ai;

        InitGui();
    }

    private void InitGui() {
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        _stopBuildings.setText("Остановаить");
        _stopBuildings.setEnabled(false);

        add(_stopBuildings);
        add(_label);
        add(_combobox);

        ConfigureButton();
    }

    private void ConfigureButton()
    {
        _stopBuildings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_ai.IsWorking())
                {
                    _ai.Pause();
                    _stopBuildings.setText("Возобновить");
                }
                else
                {
                    _ai.UnPause();
                    _stopBuildings.setText("Остановаить");
                }
            }
        });

        _combobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _ai.setPriority(Integer.parseInt((String)((JComboBox)e.getSource()).getSelectedItem()));
            }
        });
    }

    public void ChangeState(boolean isEnable)
    {
        _isEnable = isEnable;
        _stopBuildings.setEnabled(_isEnable);
    }
}
