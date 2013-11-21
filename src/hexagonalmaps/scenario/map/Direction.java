package hexagonalmaps.scenario.map;

import hexagonalmaps.Util;

import javax.swing.*;
import java.awt.*;

public enum Direction {
    N(0, -1, -1),
    NE(1, 0, -1),
    SE(1, 1, 0),
    S(0, 1, 1),
    SW(-1, 1, 0),
    NW(-1, 0, -1),
    C(0, 0, 0);
    private final int incColumn;
    private final int incRowEven;
    private final int incRowOdd;

    private Direction(final int incI, final int incJEven, final int incJOdd) {
        this.incColumn = incI;
        this.incRowEven = incJEven;
        this.incRowOdd = incJOdd;
    }

    public int getIncColumn() {
        return incColumn;
    }

    public int getIncRowEven() {
        return incRowEven;
    }

    public int getIncRowOdd() {
        return incRowOdd;
    }

    public Point getNeighborCoordinates(Point coordinates) {
        int column = coordinates.x + getIncColumn();
        int row = coordinates.y + (Util.isEven(coordinates.x) ? getIncRowEven() : getIncRowOdd());
        return new Point(column, row);
    }

    /**
     * @author Mario Gómez Martínez <magomar@gmail.com>
     */
    public static class HexagonalMap extends JPanel {
        private int width; // Number of columns
        private int height; // Number of rows
        private int hexSide; // Side of the hexagon
        private int hexOffset; // Distance from left horizontal vertex to vertical axis
        private int hexApotheme; // Apotheme of the hexagon = radius of inscribed circumference
        private int hexRectWidth; // Width of the circumscribed rectangle
        private int hexRectHeight; // Height of the circumscribed rectangle
        private int hexGridWidth;  // hexOffset + hexSide (b + s)

        public HexagonalMap(int width, int height, int hexSide) {
            this.width = width;
            this.height = height;
            this.hexSide = hexSide;
            hexApotheme = (int) (hexSide * Math.cos(Math.PI / 6));
            hexOffset = (int) (hexSide * Math.sin(Math.PI / 6));
            hexGridWidth = hexOffset + hexSide;
            hexRectWidth = 2 * hexOffset + hexSide;
            hexRectHeight = 2 * hexApotheme;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    g.drawPolygon(buildHexagon(i, j));
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            int panelWidth = width * hexGridWidth + hexOffset;
            int panelHeight = height * hexRectHeight + hexApotheme + 1;
            return new Dimension(panelWidth, panelHeight);
        }

        Polygon buildHexagon(int column, int row) {
            Polygon hex = new Polygon();
            Point origin = tileToPixel(column, row);
            hex.addPoint(origin.x + hexOffset, origin.y);
            hex.addPoint(origin.x + hexGridWidth, origin.y);
            hex.addPoint(origin.x + hexRectWidth, origin.y + hexApotheme);
            hex.addPoint(origin.x + hexGridWidth, origin.y + hexRectHeight);
            hex.addPoint(origin.x + hexOffset, origin.y + hexRectHeight);
            hex.addPoint(origin.x, origin.y + hexApotheme);
            return hex;
        }

        Point tileToPixel(int column, int row) {
            Point pixel = new Point();
            pixel.x = hexGridWidth * column;
            if (Util.isOdd(column)) pixel.y = hexRectHeight * row;
            else pixel.y = hexRectHeight * row + hexApotheme;
            return pixel;
        }

        public Point pixelToTile(int x, int y) {
            double hexRise = (double) hexApotheme / (double) hexOffset;
            Point p = new Point(x / hexGridWidth, y / hexRectHeight);
            Point r = new Point(x % hexGridWidth, y % hexRectHeight);
            Direction direction;
            if (Util.isOdd(p.x)) { //odd column
                if (r.y < -hexRise * r.x + hexApotheme) {
                    direction = NW;
                } else if (r.y > hexRise * r.x + hexApotheme) {
                    direction = SW;
                } else {
                    direction = C;
                }
            } else { //even column
                if (r.y > hexRise * r.x && r.y < -hexRise * r.x + hexRectHeight) {
                    direction = NW;
                } else if (r.y < hexApotheme) {
                    direction = N;
                } else direction = C;
            }
            return new Point(direction.getNeighborCoordinates(p));
        }

        public boolean tileIsWithinBoard(Point coordinates) {
            int column = coordinates.x;
            int row = coordinates.y;
            return (column >= 0 && column < width) && (row >= 0 && row < height);
        }
    }
}
