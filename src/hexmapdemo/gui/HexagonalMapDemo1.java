package hexmapdemo.gui;

import javax.swing.*;

public class HexagonalMapDemo1 extends JFrame {
    static final int HEX_SIDE = 20;
    static final int MAP_WIDTH = 10;
    static final int MAP_HEIGHT = 10;
    HexagonalMap mapPanel;


    public HexagonalMapDemo1() {
        super("Hexagonal Map Demo");
        mapPanel = new HexagonalMap(MAP_WIDTH, MAP_HEIGHT, HEX_SIDE);
        add(mapPanel);
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
}
