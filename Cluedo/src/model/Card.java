package model;
/**
 * Card objects represent the characters, weapons and estates in the games.
 * Each player has a unique deck of cards which can be used for refutations
 * and hypothesising about the murder scenario.
 */
public class Card {

    private final String name;
    private final String type;

    public Card(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
