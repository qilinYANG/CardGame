import java.util.ArrayList;
import java.util.Scanner;

public class PointGame extends Game {

    // 21 or 31 depending on which game
    protected int points;
    Deck deck = new Deck(1);

    /**
     *
     * @param points
     */
    public PointGame(int points) {
        super();
        this.points = points;
    }

    public boolean isBust(PokerPlayer ppl) {

        if (ppl.getScore() == -1) return true;
        return false;
    }



    /**
     * Action player take in each turn
     * @param ppl
     */
    public void PlayerAction(PokerPlayer ppl) {
        out:while(true){
            System.out.println("Please select your action: 1. hit  2. split 3. double up  4. stand");
            int index = Utils.safeIntInput("Please input your selection (1 to 4): ", 1, 4);
            switch (index){
                case 1:
                    ppl.addCard(deck.pop());
                    optimalPoint(ppl);
                    if(isBust(ppl)){break out;}
                    break;
                case 2: break;
                case 3:
                    ppl.setBet(2*ppl.getBet());
                    ppl.addCard(deck.pop());
                    optimalPoint(ppl);
                    if(isBust(ppl)){break out;}
                    break;
                case 4: break out;
            }

        }

    }




    /**
     * Calculate optimal total hand point
     * @param ppl
     * @return -1(bust) sum(point not BlackJack), 22 or 32 (BlackJack)
     * updated by Junyi at Oct 12.
     */

    public void optimalPoint(PokerPlayer ppl) {
        int sum = 0;
        boolean hasAce = false;
        ArrayList<PokerCard> hand = ppl.getHand();

        for (PokerCard pc : hand) {
            String value = pc.getValue();
            if (value.equals("1")) hasAce = true;

            if (value.equals("jack") ||
                value.equals("queen") ||
                value.equals("king")){
                    sum += 10;
            }
            else {
                sum += Double.parseDouble(value);
            }
        }

        // If bust
        if (sum > points) {
            ppl.setScore(-1);
            return;
        }

        // note: "If the hand consists of more than one Ace, only one Ace can count as 1."

        // If already optimal
        if ((!hasAce) || (sum + 10 > points)) {  // 10 = -1 + 11 (Ace can be 1 or 11)
            ppl.setScore(sum);
            return;
        }

        sum += 10;  // Make it optimal

        // If deserves a bonus
        // note: A natural Blackjack is defined as the starting hand having a value of 21 (i.e. an Ace and any face card).
        // note: A natural Trianta Ena is defined as a hand having a value of 31 (i.e. an Ace and two face cards).
        // note: A face card is a playing card that is a king, queen, or jack of a suit.
        boolean condition_1 = sum == points;
        boolean condition_2 = (points - 1) / 10 == (float) hand.size();
        if (condition_1 && condition_2) {
            ppl.setScore(sum + 0.5);
            return;
        }
        
        ppl.setScore(sum);
        return;
    }

    /**
     * Print list of cards and points at hand for each player
     * @param player
     */

    public void printBoard(PokerPlayer player) {
        System.out.println("card list: ");
        for (PokerCard pc : player.getHand()) {
            System.out.print(pc.getValue());
        }
        System.out.println("Points: " + player.getScore());

    }

    /**
     *
     * @param dealer
     * @param threshold
     * @return
     */
    public double dealerHit(PokerPlayer dealer, int threshold) {
        optimalPoint(dealer);
        double curPoint = dealer.getScore();
        while(curPoint < threshold && curPoint != -1){
            dealer.addCard(deck.pop());
            optimalPoint(dealer);
            curPoint = dealer.getScore();
        }
        return curPoint;
    }

    /**
     *
     * @param player
     * @param dealer
     * @return
     */
    public String compare(PokerPlayer player, PokerPlayer dealer, boolean dealerAdvantage) {
        // note: (31) "In the case of a tie, the Dealer wins."
        // note: (31) "A natural 31 of the Banker results in the Banker winning the bets from all players."
        // note: (21) "In the case of a tie, the Player bet is returned and a new round starts."
        // 21: no dealerAdvantage; 31: dealerAdvantage
        if (player.getScore() > dealer.getScore()) return "Player";
        if (dealer.getScore() > player.getScore()) return "Dealer";
        if (dealerAdvantage) {
            return "Dealer";
        }
        return "Draw";

    }

    /**
     * update Balance account
     * @param player
     * @param dealer
     */
    public void updateBalance(PokerPlayer player, PokerPlayer dealer){
        String winner = compare(player,dealer);

        if (winner.equals("Player")) {
            player.setBalance(player.getBalance() + 2 * player.getBet());
            dealer.setBalance(dealer.getBalance() - 2 * player.getBet());
        }

        if (winner.equals("Dealer")) {
            player.setBalance(player.getBalance() - player.getBet());
            dealer.setBalance(dealer.getBalance() + player.getBet());
        }

    }

}

