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
            int index = Utils.safeIntInput("Please input your selection (1 to 4): ",1,4);
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
        int sum = 0,sumWthAc = 0,AcNum = 0,sumtmp = 0;

        for (PokerCard pc : ppl.getHand()) {
            sum += Double.parseDouble(pc.getValue());
            if (pc.getValue().equals("1")) AcNum+=1;
        }
        if(sum > points) {
            ppl.setScore(-1);}
        else {
            sumWthAc = sum;

            for (int i = 0; i < AcNum; i++) {
                sumtmp = sumWthAc;
                sumWthAc += 10;
                if (sumWthAc > points) break;
            }
            int sumOpt = sumWthAc > points? sumtmp:sumWthAc;
            if(sumOpt == points && AcNum == 1)
                ppl.setScore(points+0.5);
            else
                ppl.setScore(sumOpt);
        }
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
    public String compare(PokerPlayer player, PokerPlayer dealer) {
        if (player.getScore() > dealer.getScore()) return "Player";
        if (dealer.getScore() > player.getScore()) return "Dealer";
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

