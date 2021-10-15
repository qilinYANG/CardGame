import java.util.ArrayList;

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
    public void PlayerAction(PokerPlayer ppl, boolean allowSplit, boolean aceFreedomRule) {
        out:while(true){
            int index;
            if (allowSplit) {
                System.out.println("Please select your action: 1. hit  2. stand 3. double up  4. split");
                index = Utils.safeIntInput("Please input your selection (1 to 4): ", 1, 4);
            }
            else {
                System.out.println("Please select your action: 1. hit  2. stand 3. double up");
                index = Utils.safeIntInput("Please input your selection (1 to 3): ", 1, 3);
            }

            switch (index){
                case 1:  // hit
                    ppl.addCard(deck.pop());
                    optimalPoint(ppl, aceFreedomRule);
                    if (isBust(ppl)) {
                        break out;
                    }
                    printBoard();
                    break;

                case 2:  // stand
                    break out;

                case 3:  // double up
                    ppl.addBet(ppl.getBet());
                    ppl.addCard(deck.pop());
                    optimalPoint(ppl, aceFreedomRule);
                    break out;  // end, whether bust or not

                case 4: 
                    throw new UnsupportedOperationException("Not implemented yet!");  // debug: Please implement this part.
            }
        }
    }




    /**
     * Calculate optimal total hand point
     * @param ppl, aceFreedomRule (aceFreedomRule: True: Ace has freedom; False: Ace has restrictions)
     * @return -1 (bust), sum, or 21.5 or 31.5 (Bonus case)
     * updated by Junyi at Oct 12.
     */

    public void optimalPoint(PokerPlayer ppl, boolean aceFreedomRule) {
        int sum = 0; 
        int aceNum = 0;

        // Get hand
        ArrayList<PokerCard> hand = ppl.getHand();
        
        // Get basic sum
        for (PokerCard pc : hand) {
            String value = pc.getValue();
            if (value.equals("1")) {
                // Update Ace number
                aceNum += 1;

                // If Ace has freedom
                if (aceFreedomRule) {
                    sum += 1;
                    continue;
                }

                // If Ace has restrictions (Then at most one Ace can be 1)
                if (aceNum == 1) {
                    sum += 1;
                }
                else {
                    sum += 11;
                }    
            }

            if (value.equals("jack") ||
                value.equals("queen") ||
                value.equals("king")) {
                // If it is a face card
                sum += 10;
            }
            else {
                // If it is neither an Ace nor a face card
                sum += Double.parseDouble(value);
            }
        }

        // If bust
        if (sum > points) {
            ppl.setScore(-1);
            return;
        }

        // Get optimal sum
        for (int idx = 0; idx < aceNum; idx++) {
            // If Ace has restrictions (Then at most one Ace is free to change)
            if ((!aceFreedomRule) && (idx == 1)) {
                break;
            }
            // If is optimal
            if (sum + 10 > points) {
                break;
            }
            sum += 10;
        }

        // Check bonus case
        // note: "A natural Trianta Ena is defined as a hand having a value of 31 (i.e. an Ace and two face cards)."
        boolean condition_1 = sum == points;
        boolean condition_2 = aceNum == 1;  // Natural Blackjack or "i.e. an Ace and two face cards"
        boolean condition_3 = hand.size() == (int)((sum - 1) / 10);  // The other cards are all face cards. note: "i.e. an Ace and two face cards"

        if (condition_1 && condition_2 && condition_3) {
            sum += 0.5;
        }

        ppl.setScore(sum);
    }

    /**
     * Print list of cards and points at hand for each player
     */

    public void printBoard() {
        System.out.println("Dealer: "+dealer.getName()+"   Balance: "+dealer.getBalance());
        System.out.println(dealer.print_hand()+"\n");

        for(PokerPlayer ppl: players){
            if(!ppl.isDealer()){
                System.out.println("Player "+ppl.getName()+"   Balance: "+ppl.getBalance()+"   Bet: "+ppl.getBet());
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
            dealer.setBalance(dealer.getBalance() - player.getBet());
            System.out.println("Winner is Player!");
        }

        if (winner.equals("Dealer")) {
            dealer.setBalance(dealer.getBalance() + player.getBet());
            System.out.println("Winner is Dealer!");
        }

        if(winner.equals("Draw")){
            player.setBalance(player.getBalance()+player.getBet());
            System.out.println("Draw!");
        }

    }

}

