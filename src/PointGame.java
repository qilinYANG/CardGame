import java.util.ArrayList;
import java.util.Scanner;

public class PointGame extends Game {

    // 21 or 31 depending on which game
    protected int points;

    /**
     *
     * @param points
     */
    public PointGame(int points) {
        super();
        this.points = points;
    }

    public boolean isBust(PokerPlayer ppl) {

        if (optimalPoint(ppl) == -1) return true;
        return false;
    }



    /**
     * Dealer distribute card fist time
     * @param players
     * @param deck
     * @param num
     */
    public void initCard(ArrayList<PokerPlayer> players, Deck deck, int num) {
        for (PokerPlayer ppl : players) {
            for (int i = 0; i < num; i++) {
                ppl.addCard(deck.pop());
            }
        }
    }

    /**
     * Action player take in each turn
     * @param ppl
     * @param action
     * @param deck
     */
    public void gameAction(PokerPlayer ppl, String action, Deck deck) {
        switch (action) {
            case "hit":
                ppl.addCard(deck.pop());
                break;
            case "stand":
                break;
            case "doubleUp":
                ppl.setBet(2 * ppl.getBet());
                ppl.addCard(deck.pop());
                break;

        }

    }

    /**
     * Action player will take in each round
     * @param sc
     * @param ppl
     * @param deck
     */
    public void actionLoop(Scanner sc, PokerPlayer ppl, Deck deck){
        System.out.println("Take your next action 1.hit 2.stand 3. split 4 doubleUP: ");
        String action = sc.next();
        while(action.equals("hit")) gameAction(ppl,action,deck);
    }


    /**
     * Calculate optimal total hand point
     * @param ppl
     * @return -1(bust) sum(point not BlackJack), 22 or 32 (BlackJack)
     */

    public int optimalPoint(PokerPlayer ppl) {
        int sum = 0,sumWthAc = 0,AcNum = 0,sumtmp = 0;

        for (PokerCard pc : ppl.getHand()) {
            sum += Double.parseDouble(pc.getValue());
            if (pc.getValue().equals("1")) AcNum+=1;
        }
        if(sum > points) return -1;
        else {
            sumWthAc = sum;

            for (int i = 0; i < AcNum; i++) {
                sumtmp = sumWthAc;
                sumWthAc += 10;
                if (sumWthAc > points) break;
            }
            int sumOpt = sumWthAc > points? sumtmp:sumWthAc;
            if(sumOpt == points && AcNum == 1)
                return points+1;
            else
                return sumOpt;
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
        System.out.println("Points: " + optimalPoint(player));

    }

    /**
     *
     * @param dealer
     * @param threshold
     * @param deck
     * @return
     */
    public int dealerHit(PokerPlayer dealer, int threshold,Deck deck) {
        int curPoint = optimalPoint(dealer);
        while(curPoint < threshold && curPoint != -1){
            dealer.addCard(deck.pop());
            curPoint = optimalPoint(dealer);
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
        if (optimalPoint(player) > optimalPoint(dealer)) return "Player";
        if (optimalPoint(dealer) > optimalPoint(player)) return "Dealer";
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

