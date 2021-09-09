package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * The player is someone who controls a PlayerPiece in the game. The player is asked prompts
 * such as if they want to move or accuse.
 */
public class Player {
    private final String name;
    private List<Card> hand = new ArrayList<>();
    private PlayerPiece playerPiece;
    private boolean lost = false;

    private final int CELL_WIDTH = 25;
    private final int CELL_HEIGHT = 25;
    private final int CELL_GAP = 2;
    private final int PIECE_WIDTH = 19;
    private final int PIECE_HEIGHT = 19;

    public Player(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public PlayerPiece getPlayerPiece(){
        return playerPiece;
    }


    public void addToHand(Card card) {
        hand.add(card);
    }

    public void addPlayerPiece(PlayerPiece playerPiece){
        this.playerPiece = playerPiece;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    // rosanne's attempt to draw player piece of this player
    public void draw(Graphics g) {
        if (playerPiece.getName().equals("Lucilla")) {
            g.setColor(new Color(255,247,0));
        } else if (playerPiece.getName().equals("Bert")) {
            g.setColor(new Color(11, 61, 212));
        } else if (playerPiece.getName().equals("Maline")) {
            g.setColor(new Color(209, 36, 195));
        } else if (playerPiece.getName().equals("Percy")) {
            g.setColor(new Color(144, 0, 255));
        }
        g.fillOval(playerPiece.getRow() * CELL_WIDTH + CELL_GAP + ((CELL_WIDTH / 2) - 1),
                playerPiece.getCol() * CELL_HEIGHT + CELL_GAP + ((CELL_HEIGHT / 2) - 1), PIECE_WIDTH, PIECE_HEIGHT);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }

    public void setLost(boolean b) {
        this.lost = true;
    }

    public boolean getLost() {
        return lost;
    }
}
