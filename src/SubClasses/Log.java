package SubClasses;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Log extends JPanel{
    private static final long serialVersionUID = 1L;
    private final String head = "   LOG:\n";
    private JTextArea textArea = new JTextArea(head, 5, 25);
    private Font font = new Font("Verdana", Font.PLAIN, 12);
    
    public Log(){
        textArea.setBackground(Color.darkGray);
        textArea.setEditable(false);
        textArea.setFont(font);
        textArea.setForeground(Color.LIGHT_GRAY);
        textArea.setTabSize(4);
        add(textArea);
    }

    public void appendIntAtrib(String str, long X){
        textArea.append("       ");
        textArea.append(str);
        textArea.append("  --->   ");
        textArea.append(String.valueOf(X));
        textArea.append("\n");
    }

    public void clear(){
        textArea.setText(head);
    }

}