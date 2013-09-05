package hexmapdemo.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class HexagonalMapDemo1 extends JFrame {
    static final int HEX_SIDE = 25;
    static final int MAP_WIDTH = 10;
    static final int MAP_HEIGHT = 10;
    private HexagonalMap map;
    private MapInfo info;
    private JPanel mainPanel;


    public HexagonalMapDemo1() {
        super("Hexagonal Map Demo");
        info = new MapInfo();
        map = new HexagonalMap(MAP_WIDTH, MAP_HEIGHT, HEX_SIDE);
        map.addMouseMotionListener(new BoardMouseMotionListener());
        mainPanel = new JPanel();
        mainPanel.add(map);
        mainPanel.add(info);
        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HexagonalMapDemo1 frame = new HexagonalMapDemo1();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    private class BoardMouseMotionListener extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent me) {
            info.setMousePosition(me.getX(), me.getY());
            Point tileCoordinates = map.pixelToTile(me.getX(), me.getY());
            if (map.tileIsWithinBoard(tileCoordinates)) {
                info.setTileCoordinates(tileCoordinates);
            } else info.setTileCoordinates(null);
        }
    }
}
