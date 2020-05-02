import SubClasses.Log;

import javax.swing.JDialog;

import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

// 1 - была нажата "ОК"
// 2 - была нажата "Отмена"

public class LogDialog extends JDialog
{
    private Log log;
    private JButton ok;
    private JButton cansel;
    private Container container;

    public LogDialog(App owner)
	{
		super(owner, "Log", true);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setLayout(new FlowLayout());

        log = new Log();
        ok = new JButton("OK");
        cansel = new JButton("Отмена");

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                setVisible(false);
                log.clear();
                owner.DialogResult(1);
            }
        });

        cansel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                setVisible(false);
                log.clear();
                owner.DialogResult(0);
            }
        });

        container = getContentPane();
        container.add(log );
        container.add(ok);
        container.add(cansel);

        this.pack();
        setBounds(600, 300, 316, 195);
    }
    
    public void Update(int buildCount, long woodCount, long capitalCount, double simulationTime)
    {
        log.setVisible(true);
        log.appendIntAtrib( "Buildings count", buildCount);
        log.appendIntAtrib( "Wood count", woodCount);
        log.appendIntAtrib( "Capital count", capitalCount);
        log.appendIntAtrib( "Total simulation time (sec.)", (int)(simulationTime / 1000) );
    }
}