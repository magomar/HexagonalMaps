package hexmapdemo.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mario Gómez Martínez <magomar@gmail.com>
 * Date: 3/09/13
 * Time: 15:46
 */
public class TileInfo extends JPanel {
    private JTextField xField;
    private JTextField yField;
    private JTextField columnField;
    private JTextField rowField;

    public TileInfo() {
        xField = new JTextField("     ");
        yField = new JTextField("     ");
        columnField = new JTextField("  ");
        columnField.setEditable(false);
        rowField = new JTextField("   ");
        rowField.setEditable(false);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy=0;
        add(new JLabel("Mouse X:"),c);
        c.gridx = 1;
        c.gridy=0;
        add(xField,c);
        c.gridx = 0;
        c.gridy=1;
        add(new JLabel("Mouse Y:"),c);
        c.gridx = 1;
        c.gridy=1;
        add(yField,c);
        c.gridx = 0;
        c.gridy=2;
        add(new JLabel("Column:"),c);
        c.gridx = 1;
        c.gridy=2;
        add(columnField,c);
        c.gridx = 0;
        c.gridy=3;
        add(new JLabel("Row:"),c);
        c.gridx = 1;
        c.gridy=3;
        add(rowField,c);
    }

    public void setMousePosition(int x, int y) {
        this.xField.setText(String.valueOf(x));
        this.yField.setText(String.valueOf(y));
    }

    public void setTileCoordinates(Point coordinates) {
        columnField.setText(String.valueOf(coordinates.x));
        rowField.setText(String.valueOf(coordinates.y));
    }
}
