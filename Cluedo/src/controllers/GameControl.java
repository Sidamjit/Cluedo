package controllers;

import java.util.*;
import static java.util.Map.entry;

import views.*;
import model.*;

import javax.swing.text.BadLocationException;

public class GameControl {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    public static GameControl gameControl;

    private static GameView gameView;

    private List<Card> cards = new ArrayList<>();
    private final ArrayList<Card> cardsCopy = new ArrayList<>();

    private List<Player> players = new ArrayList<>();
    private final ArrayList<Estate> estates = new ArrayList<>(Arrays.asList(
            new Estate("Haunted House"), new Estate("Manic Manor"), new Estate("Villa Celia"),
            new Estate("Calamity Castle"), new Estate("Peril Palace")
    ));
    private List<Weapon> weapons = new ArrayList<>(Arrays.asList(
            new Weapon("Broom", 4, 4), new Weapon("Scissors",4, 19), new Weapon("Knife", 12, 11),
            new Weapon("Shovel", 19, 4), new Weapon("iPad", 19, 19)
    ));
    private List<String> characterNames = new ArrayList<>();

    public Board board = new Board(estates);

    private List<Card> estateCards = new ArrayList<>();
    private List<Card> weaponCards = new ArrayList<>();
    private List<Card> characterCards = new ArrayList<>();

    private List<Suggestion> suggestions = new ArrayList<>();

    private static final Map<String, Integer> playerPieceXs = new HashMap<String, Integer>() {{
        put("Lucilla", 11);
        put("Bert", 1);
        put("Maline", 9);
        put("Percy", 22);
    }};
    private static final Map<String, Integer> playerPieceYs = new HashMap<String, Integer>() {{
        put("Lucilla", 1);
        put("Bert", 9);
        put("Maline", 22);
        put("Percy", 14);
    }};

    private Card solutionCharacter;
    private Card solutionWeapon;
    private Card solutionEstate;

    public boolean won = false;
    private boolean lost = false;
    public boolean isInRoom = false;
    public int newRow;
    public int newCol;
    private int moves = 0;




    /**
     * The MurderMadness method sets up and plays the game. This involves creating the Board,
     * initialising the deck of cards, seeing how many people are playing, shuffling and
     * handing out cards to the players, creating the murder solution and giving the player
     * pieces their initial location on the Board.
     */
    public GameControl(){
        gameControl = this;
        gameView = new  GameView(this, playerPieceXs, playerPieceYs);
        gameView.createMainWindow();
    }

    /**
     * Initialises the necessary fields for game to operate and also makes a copy of
     * cards because the original cards gets tempered with during initialization to set
     * the solutions cards and then split between players.
     */
    public void startGame(){
        setWeapons();
        addCharacterNames();
        makeCards();
        this.cardsCopy.addAll(this.cards);
        play();
    }

    /**
     * Polls the answer entered into the text field of the GUI.
     * Keeps the while loop going until the user has entered an answer in.
     * @return
     */
    public String getAnswer(){
        String answer = "";
        boolean valid = false;
        while(!valid) {
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            if (!gameView.answers.isEmpty()){
                answer = gameView.answers.poll();
                valid = true;
            }
        }
        return answer;
    }

    /**
     * Polls which ever arrow character was clicked by the user.
     * Keeps the while loop going until the user has clicked a arrow.
     * @return
     */
    public String getArrowResponse(){
        String answer = "";
        boolean valid = false;

        while(!valid) {
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            if (!gameView.arrowResponse.isEmpty()){
                answer = gameView.arrowResponse.poll();
                valid = true;
            }
        }
        return answer;
    }


    /**
     * The play method continuously loops until someone wins the game OR everyone loses. It is constantly
     * going around the circle of players and letting them take their turns accordingly.
     */
    public void play(){

        while(!won && !lost){

            for (Player player : this.players) {
                gameView.arrowResponse.clear();
                gameView.answers.clear();
                moves = 0;
                if (player.getLost()){
                    if (checkAllPlayerLost()){
                        gameView.gameplayTextArea.append("Game over!\n");
                        gameView.gameplayTextArea.append("The murder solution was that " + solutionCharacter.getName() + " committed murder using the " + solutionWeapon.getName() + " weapon in " + solutionEstate.getName() + ".\n");
                        this.lost = true;
                        return;
                    }
                    continue;
                }
                printCards(player);

                gameView.addToTurnTextArea( " " + player.getName() + "'s turn.\n");
                boolean validAnswer = false;
                while(!validAnswer) {

                    gameView.gameplayTextArea.append("Would you like to move, suggest, accuse or end?\n");

                    String answer = "";
                    answer = getAnswer();

                    if (answer.equals("move")) {
                        boolean moveMore = false;
                        while (!moveMore) {
                            validAnswer = true;
                            move(player);
                            if (player.getPlayerPiece().getEstate()==null){
                                isInRoom = false;
                            }
                            if (isInRoom) {
                                boolean correctAnswer = false;
                                while (!correctAnswer) {
                                    gameView.gameplayTextArea.append("Would you like to move, suggest, accuse or end?\n");
                                    answer = getAnswer();
                                    correctAnswer = checkAnswer(answer, player);
                                    if (moves==0 && correctAnswer && answer.equals("move")){
                                        correctAnswer = true;
                                        moveMore = true;
                                        gameView.gameplayTextArea.append("You ran out of moves.\n");
                                    }
                                    if (!answer.equals("move") && correctAnswer) {
                                        moveMore = true;
                                    }
                                }
                            } else {
                                gameView.gameplayTextArea.append("Your ran out of moves.\n");
                                moveMore = true;
                            }
                        }
                    }
                    if (answer.equals("suggest")) {
                        if (player.getPlayerPiece().getEstate() instanceof Estate){
                            validAnswer = true;
                            suggest(player);
                        } else {
                            gameView.gameplayTextArea.append("You must be in an estate to make a suggestion.\n");
                        }
                        continue;
                    }
                    if (answer.equals("accuse")) {
                        if (player.getPlayerPiece().getEstate() instanceof Estate){
                            validAnswer = true;
                            accuse(player);
                            if (won) {
                                return;
                            }
                        } else {
                            gameView.gameplayTextArea.append("You must be in an estate to make an accusation.\n");
                        }
                        continue;
                    }
                    if (answer.equals("end")) {
                        validAnswer = true;
                        gameView.gameplayTextArea.append("Turn changed.\n");
                        continue;
                    }
                    if (!validAnswer) {
                        gameView.inputArea.removeAll();
                        gameView.gameplayTextArea.append("Not a valid response. Please type either 'move', 'suggest' or 'accuse'!\n");
                        gameView.arrowResponse.clear();
                        gameView.answers.clear();
                    }
                }
            }
        }
    }

    private void printCards(Player player) {
        gameView.cardTextArea.setText("");
        int counter = 0;
        for(Card card : player.getHand()){
            counter++;
            gameView.cardTextArea.append("\nCard " + counter + ": " + card.getName() + "\n");
        }
    }

    /**
     * Checks if all the players in the game have been eliminated as a result
     * of accusing incorrectly. If this is the case, return true.
     * @return
     */
    public boolean checkAllPlayerLost() {
        boolean allPlayersLost = false;
        int counter = 0;
        for (Player player : this.players){
            if (player.getLost()){
                counter++;
            }
        }
        if (counter==players.size()){
            allPlayersLost = true;
        }
        return allPlayersLost;
    }

    /**
     *Checks to make sure the user entered the correct response to move, suggest, accuse or end when
     * it is their turn to response.
     * @param answer
     * @param player
     * @return
     */
    public boolean checkAnswer(String answer, Player player) {
        if(answer.equals("move") || answer.equals("end") || answer.equals("accuse") || answer.equals("suggest")){
            return true;
        }
        else {
            gameView.gameplayTextArea.append("Please choose from 'move', 'suggest', 'accuse' or 'end'.\n");
            return false;
        }
    }

    /**
     * The move method enables the players to change their x/y location on the board when
     * they play the game. This is done by rolling a dice, calculating their total moves
     * allowed and changing their row and col fields.
     * @param player
     *        the player whose turn it currently is.
     */
    public void move(Player player) {
        gameView.arrowResponse.clear();
        gameView.answers.clear();
        if (moves==0) {
            this.moves = rollDice();
            gameView.gameplayTextArea.append("We automatically rolled the dice for you!\n");
            gameView.gameplayTextArea.append("Total moves allowed: " + moves + "\n");
        }
        String move = "";

        PlayerPiece playerPiece = player.getPlayerPiece();
        int oldRow = playerPiece.getRow();
        int oldCol = playerPiece.getCol();
        gameView.gameplayTextArea.append("Current location of your player is:\nrow: " + oldRow + "\ncol: " + oldCol + "\n");
        while (moves > 0 ) {
            boolean validEntry = false;
            boolean validMove = false;
            while (!validEntry) {
                gameView.gameplayTextArea.append("Use the arrows to move.\n");
                move = getArrowResponse();
                gameView.arrowResponse.clear();
                gameView.answers.clear();
                oldRow = playerPiece.getRow();
                oldCol = playerPiece.getCol();
                validEntry = checkValidEntry(playerPiece.getRow(), playerPiece.getCol(), move);
                if(validEntry) {
                    validEntry = checkValid(oldRow, oldCol);
                }
            }
            if (player.getPlayerPiece().getEstate()==null) {
                moves = moves - 1;
            }
            board.movePlayerPiece(player.getPlayerPiece(), newRow, newCol);
            player.getPlayerPiece().move(newRow, newCol);
            gameView.gameplayTextArea.append("Remaining moves left: " + moves + "\n");
            Cell newCell = board.getCell(newRow, newCol);
            if (newCell instanceof RoomCell) {
                player.getPlayerPiece().setEstate(((RoomCell) newCell).estate);
                gameView.gameplayTextArea.append("You are now in the " + player.getPlayerPiece().getEstateName() + " estate.\n");
                isInRoom = true;
                gameView.gameplayTextArea.append("Current location of your player is:\nrow: " + newRow + "\ncol: " + newCol + "\n");
                return;
            }
            gameView.gameplayTextArea.append("Your current location on the board is:\nrow: " + newRow + "\ncol: " + newCol + "\n");
        }
    }

    /**
     * Checks to see if the player's piece is using the doors to enter/exit estates,
     * not going out of bounds or occupying grey cells. Much of this is done by comparing
     * their old x/y location to their new one, to see if they took a "correct path"
     * or an "incorrect shortcut".
     * @param oldRow
     *        the player piece's x location on the board
     * @param oldCol
     *        the player piece's y location on the board
     * @return
     */
    public boolean checkValid(int oldRow, int oldCol) {
        if (newRow>23 || newRow<0 || newCol<0 || newCol>23){
            gameView.gameplayTextArea.append("You cannot move out of bounds.\n");
            return false;
        }
        Cell oldCell = board.getCell(oldRow, oldCol);
        Cell newCell = board.getCell(newRow, newCol);
        if (newCell instanceof RoomCell && oldCell instanceof GroundCell){
            gameView.gameplayTextArea.append("You must go through a door to enter an estate.\n");
            return false;
        }
        if (newCell.getCellPiece() instanceof PlayerPiece || newCell.getCellPiece() instanceof Weapon){
            gameView.gameplayTextArea.append("The cell you are trying to move to is occupied.\n");
            return false;
        }
        if (newCell instanceof GroundCell && oldCell instanceof RoomCell){
            gameView.gameplayTextArea.append("You must go through a door to exit an estate.\n");
            return false;
        }
        if (newCell instanceof GreyCell){
            gameView.gameplayTextArea.append("You are not allowed to occupy grey cells.\n");
            return false;
        }

        return true;
    }

    /**
     * For a player piece to move around the Board, the corresponding Player must enter
     * the correct inputs. checkValidEntry is responsible for checking if any
     * user's move inputs are correct.
     * @param oldRow
     *        the player piece's x location on the board
     * @param oldCol
     *        the player piece's y location on the board
     * @param move
     *        the direction the player wants to move
     * @return
     */
    public boolean checkValidEntry(int oldRow, int oldCol, String move) {
        newRow = oldRow;
        newCol = oldCol;
        if (move.equals("←")) {
            newRow = newRow - 1;
            return true;
        }
        else if (move.equals("→")) {
            newRow = newRow + 1;
            return true;
        }
        else if (move.equals("↑")) {
            newCol = newCol - 1;
            return true;
        }
        else if (move.equals("↓")) {
            newCol = newCol + 1;
            return true;
        }
        gameView.gameplayTextArea.append("Not a valid Move.\n");
        return false;
    }

    /**
     * This enables the player to choose a character and weapon card (the estate card
     * is the estate they're in). Then these three cards are passed around the group so
     * each player can refute accordingly.
     * @param suggester
     *        The player who is making the suggestion
     */
    public void suggest(Player suggester) {
        boolean cardsChosen = false;
        Card estateCard = null;
        Card characterCard = null;
        Card weaponCard = null;
        while(!cardsChosen) {
            gameView.gameplayTextArea.append("Which person would you like to suggest?\nYour choices are 'Lucilla', 'Bert', 'Maline', 'Percy'.\n");
            String characterName = getAnswer();
            gameView.gameplayTextArea.append("Which weapon would you like to suggest?\nYour choices are 'Broom', 'Scissors', 'Knife', 'Shovel', 'iPad'.\n");
            String weaponName = getAnswer();
            for (Card card : this.cardsCopy) {
                if (card.getName().equals(suggester.getPlayerPiece().getEstateName())) {
                    estateCard = card;
                }
                if (card.getName().equals(characterName)) {
                    characterCard = card;
                }
                if (card.getName().equals(weaponName)) {
                    weaponCard = card;
                }
            }
            if (weaponCard == null) {
                gameView.gameplayTextArea.append("The weapon you chose doesn't exist.\nYour choices are 'Broom', 'Scissors', 'Knife', 'Shovel', 'iPad'.\n");
            }
            if (characterCard == null) {
                gameView.gameplayTextArea.append("The person you chose doesn't exist.\nYour choices are 'Lucilla', 'Bert', 'Maline', 'Percy'.\n");
            }
            if (weaponCard != null && characterCard != null){
                cardsChosen = true;
            }
        }
        gameView.gameplayTextArea.append("Cards chosen are: " + characterCard.getName() + ", " + weaponCard.getName() + ", " + estateCard.getName() + ".\n");
        Suggestion suggestion = new Suggestion(suggester, weaponCard, characterCard, estateCard);
        suggestions.add(suggestion);
        passTheSuggestionAround(suggestion);
    }

    /**
     * The suggestion is automatically compared with each player's deck of cards - a
     * refutation is made if need be. If there is more than one card that a player can refute,
     * the method gives them the option to choose which card.
     * @param suggestion
     *        The three cards (character, weapon and estate) that
     *        make up the suggestion.
     */
    public void passTheSuggestionAround(Suggestion suggestion) {
        Player suggester = suggestion.getSuggester();
        Card characterCard = suggestion.getCharacterCard();
        Card weaponCard = suggestion.getWeaponCard();
        Card estateCard = suggestion.getEstateCard();

        for(Weapon weapon : weapons) {
            if (weapon.getWeaponName().equals(weaponCard.getName()) && !weapon.getEstateName().equals(estateCard.getName())) {
                weapon.setEstate(suggester.getPlayerPiece().getEstate());
                board.moveWeapons(weapon, suggester.getPlayerPiece().getEstate().roomCells.get(2).row, suggester.getPlayerPiece().getEstate().roomCells.get(2).col);
                gameView.gameplayTextArea.append("The " + weapon.getWeaponName() + " weapon was moved to " + weapon.getEstateName() + "\n");
            }
        }

        for (Player player : this.players){
            if (player.getName().equals(suggester.getName())){
                continue;
            }
            if (player.getName().equals(characterCard.getName())) {
                player.getPlayerPiece().setEstate(suggester.getPlayerPiece().getEstate());
                // set the coordinates of the suggested murderer
                board.movePlayerPiece(player.getPlayerPiece(), suggester.getPlayerPiece().getEstate().roomCells.get(1).row, suggester.getPlayerPiece().getEstate().roomCells.get(1).col);
                gameView.gameplayTextArea.append(player.getName() + " was moved to " + player.getPlayerPiece().getEstateName() + "\n");
            }

            List<Card> hand = player.getHand();
            List<Card> similarities = new ArrayList<>();
            for(Card card : hand){
                if (card.getName().equals(characterCard.getName()) || card.getName().equals(weaponCard.getName()) || card.getName().equals(estateCard.getName())){
                    similarities.add(card);
                }
            }
            if (similarities.size()>1){
                Card answerCard = null;
                boolean validAnswer = false;
                while(!validAnswer) {
                    gameView.gameplayTextArea.append(player.getName() + "'s turn to refute.\n");
                    gameView.gameplayTextArea.append("Your cards are:\n" + similarities + "\n");
                    gameView.gameplayTextArea.append("Which card would you like to suggest?\n");
                    String answer = getAnswer();
                    for (Card card : similarities){
                        if (card.getName().equals(answer)){
                            answerCard = card;
                        }
                    }
                    if (answerCard == null){
                        gameView.gameplayTextArea.append("You do not own that card.\n");
                    }
                    else {
                        validAnswer = true;
                    }
                }
                gameView.gameplayTextArea.append(player.getName() + " has refuted your suggestion as they have the " + answerCard.getName() + " card.\n");
            }
            else{
                if (similarities.size()==1){
                    gameView.gameplayTextArea.append(player.getName() + " has refuted your suggestion as they have the " + similarities.get(0).getName() + " card.\n");
                }
                else{
                    gameView.gameplayTextArea.append(player.getName() + " did not refute.\n");
                }
            }

        }
    }

    /**
     * A player can make an accusation and if it's correct then they win the game. This
     * ends the while loop in play() and ends the whole game. If all the players in the game
     * accuse incorrectly, this is counted as 'game over' and ends the while loop in play()
     * as well.
     * The accuser selects three cards that make up the murder scenario which is then
     * compared with the solution cards.
     * @param accuser
     *        The person who is completing the accusation.
     */
    public void accuse(Player accuser) {
        boolean cardsChosen = false;
        Card estateCard = null;
        Card characterCard = null;
        Card weaponCard = null;
        while(!cardsChosen) {
            gameView.gameplayTextArea.append("Which person would you like to accuse?\nYour choices are 'Lucilla', 'Bert', 'Maline', 'Percy'.\n");
            String characterName = getAnswer();
            gameView.gameplayTextArea.append("Which weapon do you think they used?\nYour choices are 'Broom', 'Scissors', 'Knife', 'Shovel', 'iPad'.\n");
            String weaponName = getAnswer();
            for (Card card : this.cardsCopy) {
                if (card.getName().equals(accuser.getPlayerPiece().getEstateName())) {
                    estateCard = card;
                }
                if (card.getName().equals(characterName)) {
                    characterCard = card;
                }
                if (card.getName().equals(weaponName)) {
                    weaponCard = card;
                }
            }
            if (weaponCard == null) {
                gameView.gameplayTextArea.append("The weapon you chose doesn't exist.\nYour choices are 'Broom', 'Scissors', 'Knife', 'Shovel', 'iPad'.\n");
            }
            if (characterCard == null) {
                gameView.gameplayTextArea.append("The estate you chose doesn't exist.\nYour choices are 'Lucilla', 'Bert', 'Maline', 'Percy'.\n");
            }
            if (weaponCard != null && characterCard != null){
                cardsChosen = true;
            }
        }
        gameView.gameplayTextArea.append("Your accusation is that " + characterCard.getName() + " committed murder using the " + weaponCard.getName() + " weapon in " + estateCard.getName() + ".\n");
        if(solutionWeapon.getName().equals(weaponCard.getName()) && solutionEstate.getName().equals(estateCard.getName()) && solutionCharacter.getName().equals(characterCard.getName())) {
            won = true;
            gameView.gameplayTextArea.append("You have figured out the murder case! You win!\n");
        }
        else {
            gameView.gameplayTextArea.append("Uh oh! Your accusation was wrong and now you may only refute.\n");
            accuser.setLost(true);
        }
    }


    /**
     * Creates the deck of cards for the game.
     */
    public void makeCards() {
        //Making Estates cards
        estateCards.add(new Card("Haunted House", "Estate"));
        estateCards.add(new Card("Manic Manor", "Estate"));
        estateCards.add(new Card("Villa Celia", "Estate"));
        estateCards.add(new Card("Calamity Castle", "Estate"));
        estateCards.add(new Card("Peril Palace", "Estate"));

        //Making Weapons cards
        weaponCards.add(new Card("Broom", "Weapon"));
        weaponCards.add(new Card("Scissors", "Weapon"));
        weaponCards.add(new Card("Knife", "Weapon"));
        weaponCards.add(new Card("Shovel", "Weapon"));
        weaponCards.add(new Card("iPad", "Weapon"));

        //Making Characters cards
        characterCards.add(new Card("Lucilla", "Character"));
        characterCards.add(new Card("Bert", "Character"));
        characterCards.add(new Card("Maline", "Character"));
        characterCards.add(new Card("Percy", "Character"));

        createSolution();
    }

    /**
     * Randomises the murder scenario and removes these cards
     * from the general deck used to hand out to the players.
     */
    public void createSolution() {
        Random random = new Random();
        int guess = random.nextInt(4);
        solutionCharacter = characterCards.get(guess);
        this.cardsCopy.add(solutionCharacter);
        characterCards.remove(guess);
        guess = random.nextInt(5);
        solutionWeapon = weaponCards.get(guess);
        this.cardsCopy.add(solutionWeapon);
        weaponCards.remove(guess);
        solutionEstate = estateCards.get(guess);
        this.cardsCopy.add(solutionEstate);
        estateCards.remove(guess);
        assignCards();
    }

    /**
     * Retrieves the deck of cards and assigns them
     * to each player in the game.
     */
    public void assignCards() {
        List<Card> assigningList = new ArrayList<>();
        assigningList.addAll(characterCards);
        assigningList.addAll(weaponCards);
        assigningList.addAll(estateCards);
        cards.addAll(assigningList);

        Random random = new Random();
        while (assigningList.size() > 1) {
            int guess = random.nextInt(assigningList.size());
            for (Player player : players) {
                player.addToHand(assigningList.get(guess));
                assigningList.remove(guess);
                if (assigningList.size() == 0) {
                    break;
                }
                guess = random.nextInt(assigningList.size());
            }
        }
    }

    /**
     * Creates the weapon objects that are on the Board and randomly
     * assigns each one in an estate.
     */
    public void setWeapons() {
        List<Estate> assigningEstates = new ArrayList<>(); // gather list of Weapons
        assigningEstates.addAll(estates);

        // the total number of Weapons and the total number of estates, respectively.
        // they will always be matching as there is one weapon per estate
        int estatesAndWeaponsTotal = 5;
        Random random = new Random();
        // keep looping until there are no more estates left to assign to a respective weapon
        while (assigningEstates.size() != 0) {
            // random number between 1-5
            int randomNum = random.nextInt(estatesAndWeaponsTotal);
            // for as many Weapons there are,
            for (int i = 0; i < weapons.size(); i++) {
                // set the current Weapon's Estate field to to a randomly chosen Estate from the Estate list
                weapons.get(i).setEstate(assigningEstates.get(randomNum));
                // remove the Estate once assigned,
                assigningEstates.remove(randomNum);
                // if there are no more Estates left, end everything
                if (assigningEstates.size() == 0) {
                    break;
                }
                // make the new random number range as the new size of the Estate list
                randomNum = random.nextInt(assigningEstates.size());
            }
        }
    }

    /**
     * Sets the names of the players and the player pieces.
     */
    public void addCharacterNames() {
        characterNames.add("Lucilla");
        characterNames.add("Bert");
        characterNames.add("Maline");
        characterNames.add("Percy");
    }

    /**
     * Method for virtual dice which tells the player
     * how many moves they get.
     * @return
     */
    public int rollDice() {
        Random dice = new Random();
        int sum = (dice.nextInt(5) + 1) + (dice.nextInt(5) + 1);
        return sum;
    }

    /**
     * Returns the board
     * @return
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Adds all the players.
     * @param player
     */
    public void addPlayers(Player player) {
        this.players.add(player);
    }

    /**
     * Returns the players field
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the weapon's field
     * @return
     */
    public List<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * Runner
     * @param args
     */
    public static void main(String args[]){
        gameControl = new GameControl();
    }
}