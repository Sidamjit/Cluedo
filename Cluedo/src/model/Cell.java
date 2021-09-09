package model;

import java.awt.*;

public interface Cell {
    void setIsOccupied(boolean occupied);
    boolean getIsOccupied();
    void setCellPiece(Piece cellItem);
    Piece getCellPiece();
    void draw(Graphics g);
}
