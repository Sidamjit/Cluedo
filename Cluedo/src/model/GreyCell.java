package model;

import java.awt.*;
/**
 * In the game, a GreyCell cannot be occupied by a player's piece
 * or a weapon.
 */
public class GreyCell implements Cell{

    boolean isOccupied = true;
    public int row;
    public int col;
    public Piece cellItem;

    private final int CELL_WIDTH = 25;
    private final int CELL_HEIGHT = 25;
    private final int CELL_GAP = 2;

    public GreyCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "|&&&|";
    }

    @Override
    public void setIsOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    @Override
    public void setCellPiece(Piece cellItem) {
        this.cellItem = cellItem;
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
    public void draw(Graphics g) {
        g.setColor(new Color(150, 150, 150));
        g.fillRect(CELL_GAP + row * CELL_WIDTH + 10, CELL_GAP + col * CELL_HEIGHT + 10,
                CELL_WIDTH - CELL_GAP * CELL_GAP, CELL_HEIGHT - CELL_GAP * CELL_GAP);
    }
}