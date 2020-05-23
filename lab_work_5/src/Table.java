import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;


public class Table extends DefaultTableModel {
    private String[][] data;
    private String[] columnNames;
    private ArrayList<Transport> copyMyObjects;


    public Table(ArrayList myObjects) {
        copyMyObjects = (ArrayList<Transport>) myObjects.clone();
        columnNames = new String[3];
        data = new String[myObjects.size()][3];

    }

    public JPanel createTable() {

        int width = 300, height = 200;

        JPanel tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(width, height));

        columnNames[0] = "Type";
        columnNames[1] = "Time of birth";
        columnNames[2] = "Identifier";


        for (int i = 0; i < copyMyObjects.size(); i++) {
            Transport obj = copyMyObjects.get(i);
            data[i][0] = obj.name;
            data[i][1] = Float.toString(obj.time_of_birth / 1000f);
            data[i][2] = Integer.toString(obj.identifier);
        }

        JLabel text = new JLabel("Press 'Cancel' to update");
        tablePanel.add(text);

        JTable table = new JTable(data, columnNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);


        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(width, height-20));

        tablePanel.add(scrollPane);

        return tablePanel;
    }

}



