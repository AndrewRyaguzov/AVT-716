package SubClasses;

import Buildings.CapitalBuild;
import Buildings.WoodBuild;
import javafx.beans.property.SimpleDoubleProperty;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class NumberInput extends JPanel {

    private static final long serialVersionUID = 1L;
    private String _type;

    private JProgressBar _progressBar = new JProgressBar();
    private JTextArea _texttimeToCreate = new JTextArea();
    private JTextField _inputText = new JTextField();
    private JButton _acceptButton = new JButton("Accept");

    public NumberInput(String type) {
        setLayout(new GridLayout(3, 1, 5, 5));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        _type = type;
        _acceptButton.addActionListener(new Buttlist());

        _texttimeToCreate.setText("Время появления \n" + _type + " build");
        _texttimeToCreate.setFocusable(false);
        _texttimeToCreate.setBackground(Color.LIGHT_GRAY);
        add(_texttimeToCreate);

        if (_type == "Wood") { SetTimeText(WoodBuild.GetN()); }
        if (_type == "Capital") { SetTimeText(CapitalBuild.GetN()); }

        _progressBar.setStringPainted(true);

        JPanel supportPanel = new JPanel();
        supportPanel.setLayout(new GridLayout(1, 2, 2, 2));
        supportPanel.add(_inputText);
        supportPanel.add(_acceptButton);
        add(supportPanel);
        add(_progressBar);
    }

    public void SetSliderProgress(int value)
    {
        _progressBar.setValue(value);
    }

    private void SetTimeText(double value)
    {
        _texttimeToCreate.setText("Время появления \n" + _type + "building " + value + " сек");
    }

    private class Buttlist implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String regex = "\\d{1,10}\\.\\d{1,6}|\\d{1,10}";
            if(_inputText.getText().matches(regex)){
                double value = Double.parseDouble(_inputText.getText());
                SetValue(value);
            }
            else{
                double value = _type == "Wood" ? WoodBuild.GetDefaultN() : CapitalBuild.GetDefaultN();
                SetValue(value);
                //TODO: Dialog here
            }
            _inputText.setText("");
        }

        private void SetValue(double value)
        {
            SetTimeText(value);
            if (_type == "Wood") { WoodBuild.SetN(value); }
            if (_type == "Capital") { CapitalBuild.SetN(value); }
        }
    }

}