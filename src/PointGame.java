import java.util.ArrayList;
import java.util.Scanner;

public class PointGame extends Game {

    // 21 or 31 depending on which game
    protected int points;
    protected PokerPlayer dealer;
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
    public void PlayerAction(PokerPlayer ppl,boolean tri) {
        out:while(true){
            System.out.println("Please select your action: 1. hit  2. split 3. double up  4. stand");
            int index = Utils.safeIntInput("Please input your selection (1 to 4): ", 1, 4);
            switch (index){
                case 1:
                    ppl.addCard(deck.pop());
                    optimalPoint(ppl,tri);
                    if(isBust(ppl)){break out;}
                    break;
                case 2: break;
                case 3:
                    ppl.setBet(2*ppl.getBet());
                    ppl.addCard(deck.pop());
                    optimalPoint(ppl,tri);
                    if(isBust(ppl)){break out;}
                    break;
                case 4: break out;
            }
            printBoard();

        }

    }




    /**
     * Calculate optimal total hand point
     * @param ppl
     * @return -1(bust) sum(point not BlackJack), 22 or 32 (BlackJack)
     * updated by Junyi at Oct 12.
     */

    public void optimalPoint(PokerPlayer ppl,boolean tri) {
        int sum = 0, AceNum = 0, sumWthAc = 0;
        boolean hasAce = false;
        ArrayList<PokerCard> hand = ppl.getHand();

        for (PokerCard pc : hand) {
            String value = pc.getValue();
            if (value.equals("1")) {
                hasAce = true;
                AceNum += 1;
            }

            if (value.equals("jack") ||
                    value.equals("queen") ||
                    value.equals("king")) {
                sum += 10;
            } else {
                sum += Double.parseDouble(value);
            }
        }

        // If bust
        if (sum > points) {
            ppl.setScore(-1);
            return;
        }

        if (tri) {
            sum += 10 * (AceNum - 1);
            if (sum > points) {
                ppl.setScore(-1);
                return;
            } else {
                sum += 10;
            }
        } else {
            sumWthAc = sum;
            int sumtmp = 0;
            for (int i = 0; i < AceNum; i++) {
                sumtmp = sumWthAc;
                sumWthAc += 10;
                if (sumWthAc > points) break;
            }
            ppl.setScore(sumtmp);
            return;
        }
    }

    /**
     * Print list of cards and points at hand for each player
     */

    public void printBoard() {
        System.out.println("Dealer: "+dealer.getName()+"   Balance: "+dealer.getBalance());
        System.out.println(dealer.print_hand()+"\n");

        for(PokerPlayer ppl: players){
            if(!ppl.isDealer()){
                System.out.println("Player "+ppl.getName()+"   Balance: "+ppl.getBalance());
                System.out.println(ppl.print_hand());
                System.out.println();
            }
        }

    }

    /**
     *
     * @param dealer
     * @return
     */
    public double dealerHit(PokerPlayer dealer,boolean tri) {
        optimalPoint(dealer,tri);
        int threshold = points-4;
        double curPoint = dealer.getScore();
        while(curPoint < threshold && curPoint != -1){
            dealer.addCard(deck.pop());
            optimalPoint(dealer,tri);
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
    public void updateBalance(PokerPlayer player, PokerPlayer dealer, String winner){

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

