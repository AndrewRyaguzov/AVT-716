import Buildings.BaseBuild;
import Buildings.BuildingFactory;
import sun.awt.image.BufferedImageDevice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class LifeTimeManager<T extends BaseBuild> extends JPanel
{
    protected BuildingFactory _buildingFactory;
    protected String _text;

    private static final long serialVersionUID = 1L;
    private JTextArea _textLifeTime = new JTextArea();
    private JTextField _inputLifeTime = new JTextField();
    private JButton _acceptButton = new JButton("Accept");
    private JPanel _inputArea = new JPanel();

    public LifeTimeManager(BuildingFactory buildingFactory)
    {
        _buildingFactory = buildingFactory;

        InitGui();
        ConfigureButton();
    }

    public abstract void SetLifeTime(double value);

    protected void SetText(String text)
    {
        _textLifeTime.setText(text);
    }
    private void InitGui()
    {
        setLayout(new GridLayout(2, 1, 2, 2));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        _textLifeTime.setAlignmentY(0.5f);
        _textLifeTime.setFocusable(false);
        _textLifeTime.setBackground(Color.LIGHT_GRAY);
        add(_textLifeTime);

        _inputArea.setLayout(new GridLayout(1, 2, 2, 2));
        _inputArea.add(_inputLifeTime);
        _inputArea.add(_acceptButton);
        add(_inputArea);
    }
    private void ConfigureButton()
    {
        _acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                double temp = Double.parseDouble(_inputLifeTime.getText());
                if (temp > 0)
                    SetLifeTime(temp);
                else
                    JOptionPane.showMessageDialog(null,
                            "Дома не Бенджамин Баттон",
                            "LifeTime Error",
                            JOptionPane.ERROR_MESSAGE);
                _inputLifeTime.setText("");
            }
        });
    }
}
