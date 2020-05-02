package SubClasses;

import javax.swing.*;

public class TimePanel extends JPanel{
    private static final long serialVersionUID = 1L;
    
    private JLabel textWorkTime = new JLabel("Текущее время симуляции:");
    private JLabel valueWorkTime = new JLabel();

    public TimePanel(){
        textWorkTime.setBounds(0, 0, 200, 40);
        add(textWorkTime);
        valueWorkTime.setBounds(0, 0, 150, 40);
        add(valueWorkTime);   
    }

    public void SetTime(double currentTime){
        valueWorkTime.setText(String.valueOf(((int)currentTime)/1000) + " сек");
    }

    public boolean ChangeVisibleState(){
        setVisible(!isVisible());
        return isVisible();
    }

}