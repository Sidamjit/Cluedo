package model;

import views.GameView;

import java.util.List;
/**
 * The Board class is responsible for drawing up the visual representation of the
 * Murder Madness game. At the start of each game, the Board is always drawn the same.
 */
public class Board {

    public Cell[][] cells;
    private String foundationStr;
    private List<Estate> estates;
    private List<Player> players;
    private List<Weapon> weapons;
    private GameView gameView;

    public Board(List<Estate> estates) {
        this.cells = new Cell[24][24];
        this.estates = estates;
//        this.players = players;
//        this.weapons = weapons;
        foundation();
//        placeCharacters();
//        placeWeapons();
//        draw();
    }

    public void setGameView(GameView gameView){
        this.gameView = gameView;
    }

    /**
     * The Board is comprised of different symbols that represent
     * a Board aspect (i.e. Ground cell, room cell, a Player's piece).
     * This method draws the specified aspect based on its symbol.
     */
    public void foundation() {
        foundationStr = "gggggggggggggggggggggggg\n" +
                "gggggggggggggggggggggggg\n" +
                "ggHHHHHggggggggggMMMMMgg\n" +
                "ggHHHHH1gggggggggMMMMMgg\n" +
                "ggHHHHHggggggggggMMMMMgg\n" +
                "ggHHHHHggggBBggg1MMMMMgg\n" +
                "ggHHHHHggggBBggggMMMMMgg\n" +
                "ggggg2gggggggggggggg2ggg\n" +
                "gggggggggggggggggggggggg\n" +
                "gggggggggggg1ggggggggggg\n" +
                "gggggggggVVVVVVggggggggg\n" +
                "gggggBBggVVVVVV2ggBBgggg\n" +
                "gggggBBg3VVVVVVgggBBgggg\n" +
                "gggggggggVVVVVVggggggggg\n" +
                "ggggggggggg4gggggggggggg\n" +
                "gggggggggggggggggggggggg\n" +
                "ggg1gggggggggggggg1ggggg\n" +
                "ggCCCCCggggBBggggPPPPPgg\n" +
                "ggCCCCC2gggBBggggPPPPPgg\n" +
                "ggCCCCCggggggggggPPPPPgg\n" +
                "ggCCCCCggggggggg2PPPPPgg\n" +
                "ggCCCCCggggggggggPPPPPgg\n" +
                "gggggggggggggggggggggggg\n" +
                "gggggggggggggggggggggggg\n";

        char currChar;
        int currCharIndex = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                currChar = foundationStr.charAt(currCharIndex);
                if (currChar == '\n') {
                    currCharIndex += 1;
                    j -= 1;
                    continue;
                }
                else if (currChar == 'g') {
                    Cell groundCell = new GroundCell(i, j);
                    cells[i][j] = groundCell;
                }
                else if (currChar == 'B') {
                    Cell greyCell = new GreyCell(i, j);
                    cells[i][j] = greyCell;
                }
                else if (currChar == '1' || currChar == '2' || currChar == '3' || currChar == '4') {
                    Cell doorCell = new DoorCell(i, j, Integer.parseInt(String.valueOf(currChar)));
                    cells[i][j] = doorCell;
                }
                else if (currChar == 'H') {
                    RoomCell roomCell = new RoomCell(i, j);
                    cells[i][j] = roomCell;
                    estates.get(0).setRoomCells(new RoomCell(i, j));
                    roomCell.setEstate(estates.get(0));
                }
                else if (currChar == 'M') {
                    RoomCell roomCell = new RoomCell(i, j);
                    cells[i][j] = roomCell;
                    estates.get(1).setRoomCells(roomCell);
                    roomCell.setEstate(estates.get(1));
                }
                else if (currChar == 'V') {
                    RoomCell roomCell = new RoomCell(i, j);
                    cells[i][j] = roomCell;
                    estates.get(2).setRoomCells(roomCell);
                    roomCell.setEstate(estates.get(2));
                }
                else if (currChar == 'C') {
                    RoomCell roomCell = new RoomCell(i, j);
                    cells[i][j] = roomCell;
                    estates.get(3).setRoomCells(roomCell);
                    roomCell.setEstate(estates.get(3));
                }
                else if (currChar == 'P') {
                    RoomCell roomCell = new RoomCell(i, j);
                    cells[i][j] = roomCell;
                    estates.get(4).setRoomCells(roomCell);
                    roomCell.setEstate(estates.get(4));
                }
                currCharIndex += 1;
            }
        }
    }

    /**
     * Method for placing the Players' pieces on the Board at the start
     * of the game.
     */
    public void placeCharacters() {
        for (Player player : players) {
            Piece playerPiece = player.getPlayerPiece();
            int row = playerPiece.getRow();
            int col = playerPiece.getCol();
            cells[row][col].setCellPiece(playerPiece);
            cells[row][col].setIsOccupied(true);
        }
    }

    /**
     * Method for placing weapons on the Board at the start
     * of the game.
     */
    public void placeWeapons() {
        for (Weapon weapon : weapons) {
            int half = (weapon.getEstate().roomCells.size()) / (2);
            int row = weapon.getEstate().roomCells.get(half).row;
            int col = weapon.getEstate().roomCells.get(half).col;
            weapon.setRow(row);
            weapon.setCol(col);
            cells[row][col].setCellPiece(weapon);
            cells[row][col].setIsOccupied(true);
//            weapon.draw(gameView.topPanel.getGraphics());
        }
    }

    /**
     * Draws cells according to the specified type of cells
     * they are in the cell array.
     */
    public void draw() {
        String str = "";
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] instanceof RoomCell) {
                    str += cells[i][j].toString();
                } else if (cells[i][j] instanceof GroundCell) {
                    str += cells[i][j].toString();
                } else if (cells[i][j] instanceof DoorCell) {
                    int doorNum = ((DoorCell) cells[i][j]).getDoorNum();
                    str += cells[i][j].toString();
                } else if (cells[i][j] instanceof GreyCell) {
                    str += cells[i][j].toString();
                }
            }
            str += "\n";
        }
    }

    /**
     * Method for moving player pieces on the Board once the game starts to unfold.
     * Moving the player pieces is required when their location changes.
     * @param playerPiece
     *        the player piece that is being moved
     * @param newRow
     *        the new row (x) coordinate that they will be moved to
     * @param newCol
     *        the new col (y) coordinate that they will be moved to
     */
    public void movePlayerPiece(PlayerPiece playerPiece, int newRow, int newCol) {
        int row = playerPiece.getRow();
        int col = playerPiece.getCol();
        Cell oldCell = cells[row][col];
        Cell newCell = cells[newRow][newCol];
        if (oldCell instanceof RoomCell && newCell instanceof DoorCell){
            playerPiece.setEstate(null);
        }
        Piece piece = oldCell.getCellPiece();
        cells[row][col].setCellPiece(null);
        cells[row][col].setIsOccupied(false);
        cells[newRow][newCol].setCellPiece(piece);
        playerPiece.setRow(newRow);
        playerPiece.setCol(newCol);
        cells[newRow][newCol].setIsOccupied(true);
        if (newCell instanceof RoomCell){
            playerPiece.setEstate(((RoomCell) newCell).getEstate());
        }
        gameView.updateGameBoard();

    }

    /**
     * Method for moving weapons on Board once the game starts to unfold.
     * Moving weapons around the Board is required once Player's start to make
     * suggestions.
     * @param weapon
     *        the weapon that is being moved
     * @param newRow
     *        the new row (x) coordinate that it will be moved to
     * @param newCol
     *        the new row (y) coordinate that it will be moved to
     */
    public void moveWeapons(Weapon weapon, int newRow, int newCol) {
        int row = weapon.getRow();
        int col = weapon.getCol();
        Cell oldCell = cells[row][col];
        Piece piece = oldCell.getCellPiece();
        cells[row][col].setCellPiece(null);
        cells[row][col].setIsOccupied(false);
        cells[newRow][newCol].setCellPiece(piece);
        weapon.setRow(newRow);
        weapon.setCol(newCol);
        cells[newRow][newCol].setIsOccupied(true);
        gameView.updateGameBoard();

    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public Cell[][] getCells() {
        return cells;
    }
}