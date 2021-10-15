import java.util.ArrayList;

// An abstraction of a poker game player.
public class PokerPlayer extends Player{
    private boolean isDealer;
    private double balance;
    private double bet;
    private ArrayList<PokerCard> hand;

    public PokerPlayer(String name){
        setName(name);
        setNonDealer();
        balance = 0;
        bet = 0;
        hand = new ArrayList<PokerCard>(0);
    }

    public boolean isDealer() {
        return isDealer;
    }

    public void setDealer() {
        isDealer = true;
    }

    public void setNonDealer() {
        isDealer = false;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void addBet(double bet) {
        if (balance < bet) {
            throw new IllegalArgumentException("Not enough balance!");
        }
        this.bet += bet;
        balance -= bet;
    }

    public double getBet() {
        return bet;
    }

    public void addCard(PokerCard card) {
        hand.add(card);
    }

    public void clearHand() {
        hand.clear();
    }

    public ArrayList<PokerCard> getHand() {
        return hand;
    }

    public String print_hand(){
        String hand_board = "";
        for(PokerCard card : hand){
            if (card.isVisible()) {
                hand_board = hand_board.concat(" [" + card.toString() + "] ");
            }
            else {
                hand_board = hand_board.concat(" [***] ");
            }
        }
        return hand_board;
    }

    public void clearBet(){
        bet = 0;
    }
}
