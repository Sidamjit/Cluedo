package model;
/**
 * A PlayerPiece is a figurine on the Board which is controlled by a Player. The figurine
 * is named after the Player's name and can be moved around to play the game.
 */

public class PlayerPiece implements Piece {
    private final String playerName;
    // Position on the board
    public int row;
    public int col;
    private Estate currentEstate; // The estate they're in IF they are in one.

    public PlayerPiece(String name, int col, int row) {
        this.playerName = name;
        this.row = row;
        this.col = col;
    }

    public void move(int row, int col){
        this.row = row;
        this.col = col;
    }

    public String getEstateName() { return currentEstate.getEstateName();}

    public Estate getEstate() { return currentEstate;}

    @Override
    public String getName() {
        return playerName;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getCol() {
        return this.col;
    }

    public void setEstate(Estate estate) { this.currentEstate = estate;}

    public void setRow(int row) { this.row = row; }

    public void setCol(int col) { this.col = col;}
}
