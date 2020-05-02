import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Buildings.BuildingFactory;
import SubClasses.TimePanel;

public class GUI extends JPanel {
    public JButton buttonStart = new JButton("Start");
    public JButton _showAliveBuildings = new JButton("Текущие объекты");

    private ButtonGroup showTimeGroup;
    public JRadioButton showTimeButton;
    public JRadioButton hideTimeButton;

    public JCheckBox checkBox_showInfo;

    private SettingsManager _settingsManagerWoodBuild;
    private SettingsManager _settingsManagerCapitalBuild;
    private BuildingFactory _buildingFactory;

    public TimePanel workTime;
    
    private JToolBar toolBar;
    public JButton tbStart,
                    tbStop,
                    tbTime;

    GUI(BuildingFactory buildingFactory)
    {
        _buildingFactory = buildingFactory;
        _settingsManagerWoodBuild = new WoodBuildingsSettingsManager(_buildingFactory);
        _settingsManagerCapitalBuild = new CapitalBuildingsSettingsManager(_buildingFactory);

        buttonStart.setBackground(Color.GREEN);
        
        checkBox_showInfo = new JCheckBox("Показывать информацию");
        
        workTime = new TimePanel();
        showTimeGroup = new ButtonGroup();
        showTimeButton = new JRadioButton("Показывать", true);
        hideTimeButton = new JRadioButton("Скрывать", false);
    
        toolBar = new JToolBar("Toolbar", JToolBar.VERTICAL);
        tbStart = new JButton("Старт");
        tbStop = new JButton("Стоп");
        tbStop.setEnabled(false);
        tbTime = new JButton("Показать время симуляции");

        showTimeGroup.add(showTimeButton);
        showTimeGroup.add(hideTimeButton);
        
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT); 
 
        setLayout(new GridBagLayout()); 
        GridBagConstraints constraints = new GridBagConstraints(); 

        int gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets    = new Insets(15, 10, 0, 10);
        constraints.ipady = 10;
        constraints.gridy = gridy++;
        add(buttonStart, constraints);

        constraints.insets    = new Insets(15, 10, 0, 10);
        constraints.gridy = gridy++;
        constraints.ipady = 7;
        add(_showAliveBuildings, constraints);

        constraints.insets    = new Insets(2, 10, 0, 10);
        constraints.ipady = 7;
        constraints.gridy = gridy++;
        add(checkBox_showInfo, constraints);

        constraints.insets    = new Insets(10, 10, 0, 10);
        constraints.ipady = 5;
        constraints.gridy = gridy++;
        add(workTime, constraints);

        constraints.insets    = new Insets(0, 10, 0, 10);
        constraints.gridy = gridy++;
        add(showTimeButton, constraints);

        constraints.insets    = new Insets(0, 10, 0, 10);
        constraints.gridy = gridy++;
        add(hideTimeButton, constraints);

        constraints.insets    = new Insets(20, 10, 0, 10);
        constraints.gridy = gridy++;
        add(_settingsManagerCapitalBuild, constraints);

        constraints.insets    = new Insets(15, 10, 0, 10);
        constraints.gridy = gridy++;
        add(_settingsManagerWoodBuild, constraints);

        constraints.insets    = new Insets(20, 10, 0, 10);
        constraints.gridy = gridy;

        toolBar.add(tbStart);
        toolBar.add(tbStop);
        toolBar.add(tbTime);
        add(toolBar, constraints);

        ConfigureButtons();
    }

    private void ConfigureButtons()
    {
        _showAliveBuildings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        //Момент с возвращаемым типом коллекции не особо ясен, верну как это было бы логичным
                        new JList(_buildingFactory.GetAliveBuildings()),
                        "Текущие здания",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });
    }

    public void changeProgressBars(int woodBuildProgress, int capitalBuildProgress)
    {
        _settingsManagerWoodBuild.changeProgressBar(woodBuildProgress);
        _settingsManagerCapitalBuild.changeProgressBar(capitalBuildProgress);
    }
}
