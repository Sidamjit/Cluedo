package model;

import java.awt.*;
/**
 * A DoorCell are the exits and entries of estates. A player must occupy
 * a DoorCell before occupying a RoomCell. An estate has at least one door
 * but may have more than two.
 */
public class DoorCell implements Cell {
    public int row, col, doorNum;
    public boolean isOccupied = false;
    public Piece cellItem;

    private final int CELL_WIDTH = 25;
    private final int CELL_HEIGHT = 25;
    private final int CELL_GAP = 2;

    public DoorCell (int row, int col, int doorNum) {
        this.row = row;
        this.col = col;
        this.doorNum = doorNum;
    }

    public int getDoorNum() {
        return doorNum;
    }

    @Override
    public boolean getIsOccupied() {
        return isOccupied;
    }

    @Override
    public Piece getCellPiece() {
        return this.cellItem;
    }

    @Override
    public void setCellPiece(Piece cellItem) {
        this.cellItem = cellItem;
    }

    @Override
    public void setIsOccupied(boolean occupied) {
        this.isOccupied = occupied;
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
            else if(cellItem.getName().equals("Maline")) {
                return "| M |";
            }
            else if (cellItem.getName().equals("Percy")) {
                return "| P |";
            }
        }
        return "| " + this.doorNum + " |";
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(108, 145, 84));
        g.fillRect(CELL_GAP + row * CELL_WIDTH + 10, CELL_GAP + col * CELL_HEIGHT + 10,
                CELL_WIDTH - CELL_GAP * CELL_GAP, CELL_HEIGHT - CELL_GAP * CELL_GAP);
    }
}