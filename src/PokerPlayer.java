import java.util.ArrayList;

// An abstraction of a poker game player.
public class PokerPlayer extends Player{
    private boolean isDealer;
    private double balance, bet;
    private ArrayList<PokerCard> hand;

    public PokerPlayer(String name){
        setName(name);
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

    public void setBet(double bet) {
        this.bet = bet;
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
}
