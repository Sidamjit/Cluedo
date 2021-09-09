package model;
/**
 * A Suggestion object is comprised of three cards - character, weapon and estate.
 * Suggestions cam be made by Players when they are located in an estate. Other players
 * then refute one of the cards by saying if they own one of them.
 */
public class Suggestion {

    private final Card estateCard;
    private final Card weaponCard;
    private final Card characterCard;
    private final Player suggester; //Player who is suggesting

    public Suggestion(Player suggester, Card weapon, Card character, Card estate){
        this.weaponCard = weapon;
        this.estateCard = estate;
        this.characterCard = character;
        this.suggester = suggester;
    }

    public Card getEstateCard() {
        return estateCard;
    }

    public Card getWeaponCard() {
        return weaponCard;
    }

    public Card getCharacterCard() {
        return characterCard;
    }

    public Player getSuggester() {
        return suggester;
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "estateCard=" + estateCard +
                ", weaponCard=" + weaponCard +
                ", characterCard=" + characterCard +
                ", suggester=" + suggester +
                '}';
    }
}
