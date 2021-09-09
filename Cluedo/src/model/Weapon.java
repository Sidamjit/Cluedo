package model;

import java.awt.*;

/**
 * Weapons are placed in estates on a RoomCell. Each estate starts with one weapon inside, however
 * when suggestions are made about the murder weapoon, that suggested weapon will move
 * to the estate of the suggester.
 */
public class Weapon implements Piece {

    public String weaponName;
    public Estate estateName;
    public int row;
    public int col;

    private final int CELL_WIDTH = 25;
    private final int CELL_HEIGHT = 25;
    private final int CELL_GAP = 2;
    private final int PIECE_WIDTH = 15;
    private final int PIECE_HEIGHT = 15;

    public Weapon(String weaponName, int row, int col) {
        this.weaponName = weaponName;
        this.row = row;
        this.col = col;
    }

    public String getEstateName() {
        return this.estateName.getEstateName();
    }


    public Estate getEstate() { return this.estateName; }

    @Override
    public String getName() {
        return weaponName;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getCol() {
        return this.col;
    }

    public String getWeaponName(){
        return this.weaponName;
    }


    public void setEstate(Estate estate) {
        this.estateName = estate;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void draw(Graphics g) {
        if (weaponName.equals("Broom")) {
            g.setColor(new Color(128,0,0));
        } else if (weaponName.equals("Scissors")) {
            g.setColor(new Color(0,100,0));
        } else if (weaponName.equals("Knife")) {
            g.setColor(new Color(0,0,139));
        } else if (weaponName.equals("Shovel")) {
            g.setColor(new Color(105,53,156));
        } else if (weaponName.equals("iPad")) {
            g.setColor(new Color(0,139,139));
        }
        g.fillRect(this.row * CELL_WIDTH + CELL_GAP + ((CELL_WIDTH / 2) + 1),
                this.col * CELL_HEIGHT + CELL_GAP + ((CELL_HEIGHT / 2) + 1), PIECE_WIDTH, PIECE_HEIGHT);
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "estateName='" + estateName + '\'' +
                ", weaponName='" + weaponName + '\'' +
                '}';
    }
}
