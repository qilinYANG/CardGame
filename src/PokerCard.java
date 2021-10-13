// The abstraction of a poker card.
public class PokerCard extends Card{
    private String suit;
    private String value;

    public PokerCard(String suit, String value) {
        setSuit(suit);
        setValue(value);
        setVisible();
    }

    private void setSuit(String suit) {
        if (suit.equals("club") ||
                suit.equals("diamond") ||
                suit.equals("heart") ||
                suit.equals("spade")) {
            this.suit = suit;
            return;
        }
        throw new IllegalArgumentException("suit can only be one of club, diamond, heart, and spade.");
    }

    private void setValue(String value) {
        if (value.equals("jack") ||
                value.equals("queen") ||
                value.equals("king")){
            this.value = value;
            return;
        }
        try {
            int tmp = Integer.parseInt(value);
            if ((1 <= tmp) && (tmp <= 10)) {
                this.value = value;
                return;
            }
        } catch (java.lang.NumberFormatException e) {
        }
        throw new IllegalArgumentException("value can only be 1~10 or jack, queen, or king.");
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return suit + "-" + value;
    }
}