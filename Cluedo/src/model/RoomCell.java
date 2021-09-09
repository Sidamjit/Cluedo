package model;

import java.awt.*;
/**
 * A RoomCell represents a cell belonging to an Estate that is able to be occupied by a Piece.
 */
public class RoomCell implements Cell {
    public final int row;
    public final int col;
    public Estate estate = null;
    public boolean isOccupied = false;
    public Piece cellItem;

    private final int CELL_WIDTH = 25;
    private final int CELL_HEIGHT = 25;
    private final int CELL_GAP = 2;

    public RoomCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void setCellPiece(Piece cellItem) {
        this.cellItem = cellItem;
    }

    @Override
    public void setIsOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    @Override
    public boolean getIsOccupied() {
        return isOccupied;
    }

    @Override
    public Piece getCellPiece() {
        return this.cellItem;
    }

    public Estate getEstate() {
        return this.estate;
    }

    @Override
    public String toString() {
        if (cellItem != null) {
            if (cellItem.getName().equals("Lucilla")) {
                return "| L |";
            }
            else if (cellItem.getName().equals("Bert")) {
                return "| B |";
            }
            else if (cellItem.getName().equals("Maline")) {
                return "| M |";
            }
            else if (cellItem.getName().equals("Percy")) {
                return "| P |";
            }
            else if (cellItem.getName().equals("Broom")) {
                return "|!b!|";
            }
            else if (cellItem.getName().equals("Scissors")) {
                return "|!s!|";
            }
            else if (cellItem.getName().equals("Knife")) {
                return "|!k!|";
            }
            else if (cellItem.getName().equals("Shovel")) {
                return "|!S!|";
            }
            else if (cellItem.getName().equals("iPad")) {
                return "|!i!|";
            }
        }
        return "|***|";
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(138, 91, 59));
        g.fillRect(CELL_GAP + row * CELL_WIDTH + 10, CELL_GAP + col * CELL_HEIGHT + 10,
                CELL_WIDTH - CELL_GAP * CELL_GAP, CELL_HEIGHT - CELL_GAP * CELL_GAP);
        g.setColor(Color.BLACK);
        if (estate.getEstateName().equals("Haunted House")) {
            g.drawString("H", ((CELL_GAP + row * CELL_WIDTH + 16)),
                    ((CELL_GAP + col * CELL_HEIGHT + 25)));
        } else if (estate.getEstateName().equals("Manic Manor")) {
            g.drawString("M", ((CELL_GAP + row * CELL_WIDTH + 16)),
                    ((CELL_GAP + col * CELL_HEIGHT + 25)));
        } else if (estate.getEstateName().equals("Villa Celia")) {
            g.drawString("V", ((CELL_GAP + row * CELL_WIDTH + 16)),
                    ((CELL_GAP + col * CELL_HEIGHT + 25)));
        } else if (estate.getEstateName().equals("Calamity Castle")) {
            g.drawString("C", ((CELL_GAP + row * CELL_WIDTH + 16)),
                    ((CELL_GAP + col * CELL_HEIGHT + 25)));
        } else if (estate.getEstateName().equals("Peril Palace")) {
            g.drawString("P", ((CELL_GAP + row * CELL_WIDTH + 16)),
                    ((CELL_GAP + col * CELL_HEIGHT + 25)));
        }
    }
}