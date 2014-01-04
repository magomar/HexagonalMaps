package hexagonalmaps.gui;

import hexagonalmaps.scenario.map.Board;
import hexagonalmaps.scenario.map.TerrainType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mario on 3/01/14.
 */
public class BoardToolBar extends JToolBar {
    private final HexagonalMap map;
    public BoardToolBar(final HexagonalMap map) {
        this.map = map;
        final JComboBox<TerrainType> terrainTypeComboBox = new JComboBox<>(TerrainType.values());
        terrainTypeComboBox.setSelectedIndex(TerrainType.FOREST.ordinal());
        JButton resetButton = new JButton("Regenerate");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = terrainTypeComboBox.getSelectedIndex();
                Board newBoard = Board.createRandomMap(map.getWidth(), map.getHeight(), terrainTypeComboBox.getItemAt(selectedIndex));
                map.update(newBoard);
            }
        });
        add(terrainTypeComboBox);
        add(resetButton);
    }
}
