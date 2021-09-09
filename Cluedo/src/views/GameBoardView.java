package views;

import controllers.GameControl;
import model.Cell;
import model.Player;
import model.Weapon;

import javax.swing.*;
import java.awt.*;
/* =================================================================
   Rosanne's attempt to draw the game board using panel ( ∥￣■￣∥ )
================================================================= */
public class GameBoardView extends JPanel {
    private GameControl gameControl;
    private Cell[][] cells; // stores in cells from board

    /**
     * @param gc passed in gameController object
     *  The constructor sets the passed in gameController object and then sets the cells of the board as well
     */
    public GameBoardView(GameControl gc) {
        gameControl = gc;
        cells = gc.getBoard().getCells();
    }

    /**
     *
     * @param g graphics
     *  The painComponent method calls on the super class to draw the cells of the array (board) as well as the
     *  players and the weapons
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw the cells of the board
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j].draw(g);
            }
        }
        // rosanne's attempt to draw each player's player piece on the board
        if (!gameControl.getPlayers().isEmpty()) {
            for (Player player : gameControl.getPlayers()) {
                player.draw(g);
            }
        }

        // draws each weapon on the board
        if (!gameControl.getWeapons().isEmpty()) {
            for (Weapon weapon : gameControl.getWeapons()) {
                weapon.draw(g);
            }
        }
    }
}