import java.util.ArrayList;

// The abstraction of a deck of poker cards.
public class Deck {
    private ArrayList<PokerCard> deck;
    private ArrayList<PokerCard> deckOri;

    public Deck() {
        this(1);
    }

    // deckMultiplier allows you to include multiple sets of the standard 52 poker cards in one deck.
    public Deck(int deckMultiplier) {
        if (deckMultiplier < 1) {
            throw new IllegalArgumentException("deckMultiplier should be >= 1.");
        }

        deck = new ArrayList<PokerCard>();
        for (int idx_1 = 0; idx_1 < deckMultiplier; idx_1++) {
            for (int idx_2 = 1; idx_2 <= 13; idx_2++) {
                String valueTmp;
                switch (idx_2) {
                    case 13:
                        valueTmp = "king";
                        break;

                    case 12:
                        valueTmp = "queen";
                        break;

                    case 11:
                        valueTmp = "jack";
                        break;
                
                    default:
                        valueTmp = ((Integer)(idx_2)).toString();
                        break;
                }

                deck.add(new PokerCard("club", valueTmp));
                deck.add(new PokerCard("diamond", valueTmp));
                deck.add(new PokerCard("heart", valueTmp));
                deck.add(new PokerCard("spade", valueTmp));
            }
        }

        deckOri = new ArrayList<PokerCard>(deck);
    }

    public void shuffle() {
        java.util.Collections.shuffle(deck);
    }

    public PokerCard pop() {
        return deck.remove(0);
    }
    
    public void reset() {
        deck = new ArrayList<PokerCard>(deckOri);
    }

    public String toString() {
        String tmp = "";
        for (int idx = 0; idx < deck.size(); idx++) {
            tmp += deck.get(idx) + "\n";
        }
        return tmp;
    }
}
