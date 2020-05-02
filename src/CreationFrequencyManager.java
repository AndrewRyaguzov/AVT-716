import javax.swing.*;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class CreationFrequencyManager extends JPanel {
    private static final long serialVersionUID = 1L;

    protected JSlider _slider = new JSlider();
    private JTextArea _textChanceToCreate = new JTextArea();

    public CreationFrequencyManager()
    {
        InitGui();
    }

    protected void SetText(String value)
    {
        _textChanceToCreate.setText("Шанс появления " + value);
    }

    private void InitGui()
    {
        setLayout(new GridLayout(2, 1, 5, 5));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        _textChanceToCreate.setAlignmentY(0.5f);
        _textChanceToCreate.setFocusable(false);
        _textChanceToCreate.setBackground(Color.LIGHT_GRAY);

        _slider.setPaintLabels(true);
        _slider.setMajorTickSpacing(25);

        add(_textChanceToCreate);
        add(_slider);

        ConfigureButton();
    }

    public abstract void SetFrequency(int value);

    private void ConfigureButton()
    {
        _slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                SetFrequency(((JSlider) e.getSource()).getValue() / 100);
            }
        });
    }
}
