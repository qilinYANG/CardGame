// The abstraction of a poker card.
public class PokerCard extends Card{
<<<<<<< HEAD
    private int value;
    private String type;
    private boolean facestatus;

    public PokerCard(int value, String type,boolean facestatus){
        setValue(value);
        setFacestatus(facestatus);
        setType(type);
    }
    public int getValue() {
        return value;
=======
    private String suit;
    private String value;    

    public PokerCard(String suit, String value) {
        setSuit(suit);
        setValue(value);
        setVisible();
>>>>>>> 1dc4d49114e22977e743a637e9fcc769341a7c46
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

<<<<<<< HEAD
    public boolean isFacestatus() {
        return facestatus;
    }

    public void setFacestatus(boolean facestatus) {
        this.facestatus = facestatus;
=======
    private void setValue(String value) {
        if (value.equals("jack") ||
            value.equals("queen") ||
            value.equals("king")) {
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
>>>>>>> 1dc4d49114e22977e743a637e9fcc769341a7c46
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return suit + "-" + value;
    }
}
