import java.util.ArrayList;

public class PokerPlayer extends Player{
    private String identity;
    private int score, balance,bet;
    private ArrayList<ArrayList<PokerCard>> handCard;
    public PokerPlayer(String name, String identity, int score, int balance,int bet){
        this.name = name;
        setIdentity(identity);
        setScore(score);
        setBalance(balance);
        setBet(bet);
        ArrayList<ArrayList<PokerCard>> handCard = new ArrayList<>(0);
        setHandCard(handCard);

    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public ArrayList<ArrayList<PokerCard>> getHandCard() {
        return handCard;
    }

    public void setHandCard(ArrayList<ArrayList<PokerCard>> handCard) {
        this.handCard = handCard;
    }
}
