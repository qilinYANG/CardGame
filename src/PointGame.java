import java.util.ArrayList;

public class PointGame extends Game {

    // 21 or 31 depending on which game
    protected int points;
    protected PokerPlayer dealer;
    Deck deck = new Deck(1);


    public PointGame(int points) {
        super();
        this.points = points;
    }

    public boolean isBust(PokerPlayer ppl) {
        if (ppl.getScore() == -1) return true;
        return false;
    }

    public void PlayerAction(PokerPlayer ppl, boolean allowSplit, boolean aceRestricted) {
        out:while(true){
            int index;
            if (allowSplit) {
                System.out.println("Player " + ppl.getName() + ": Please select your action:\n1. hit\n2. stand\n3. double up\n4. split");
                index = Utils.safeIntInput("Please input your selection (1 to 4): ", 1, 4);
            }
            else {
                System.out.println("Player " + ppl.getName() + ": Please select your action:\n1. hit\n2. stand\n3. double up");
                index = Utils.safeIntInput("Please input your selection (1 to 3): ", 1, 3);
            }

            Utils.beautifulWait(1);

            switch (index){
                case 1:  // hit
                    ppl.addCard(deck.pop());
                    optimalPoint(ppl, aceRestricted);
                    if (isBust(ppl)) {
                        break out;
                    }
                    printBoard();
                    Utils.beautifulWait(1);
                    break;

                case 2:  // stand
                    optimalPoint(ppl, aceRestricted);
                    break out;

                case 3:  // double up
                    // If has enough balance, else: notify and break (not break out)
                    if(ppl.getBalance() < ppl.getBet()){
                        System.out.println("Your Balance is not Enough");
                        break;
                    }
                    ppl.addBet(ppl.getBet());
                    ppl.addCard(deck.pop());
                    optimalPoint(ppl, aceRestricted);
                    break out;  // end, whether bust or not
                
                case 4: 
                    // If has enough balance
                    boolean condition_1 = ppl.getBalance() >= ppl.getBet();
                    // If has exactly two cards
                    boolean condition_2 = ppl.getHand().size() == 2;
                    // If the cards are of the same rank
                    String value1 = ppl.getHand().get(0).getValue();
                    String value2 = ppl.getHand().get(1).getValue();
                    if ((value1 == "jack") || (value1 == "queen") || (value1 == "king")) {
                        value1 = "10";
                    }
                    if ((value2 == "jack") || (value2 == "queen") || (value2 == "king")) {
                        value2 = "10";
                    }                    
                    boolean condition_3 = value1 == value2;
                    // otherwise: notify and break (not break out)
                    if (!(condition_1 && condition_2 && condition_3)) {
                        System.out.println("You are not qualified to split!");
                        Utils.beautifulWait(1);
                        break;
                    }
                    // Start Split
                    // Create a new player
                    PokerPlayer pplSplit = new PokerPlayer(ppl.getName());
                    // Add the new player to players
                    players.add(pplSplit);

                    // ----- //
                    // Money matters
                    // ----- //
                    // Current player loses money
                    ppl.setBalance(ppl.getBalance() - ppl.getBet());
                    // The money goes to the new player
                    pplSplit.setBalance(ppl.getBet());
                    // The new player places bet
                    pplSplit.addBet(ppl.getBet());

                    // ----- //
                    // Deal cards
                    // ----- //
                    // Current player gives one of his/her card to the new player
                    pplSplit.addCard(ppl.getHand().remove(0));
                    // Deal a card to the current player
                    ppl.addCard(deck.pop());
                    // Deal a card to the new player
                    pplSplit.addCard(deck.pop());

                    // break, but not "break out"
                    printBoard();
                    Utils.beautifulWait(1);
                    break;
            }
        }
    }

    // Calculate optimal total hand point
    public void optimalPoint(PokerPlayer ppl, boolean aceRestricted) {
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

                // If Ace is not restricted
                if (!aceRestricted) {
                    sum += 1;
                    continue;
                }

                // If Ace is restricted (Then at most one Ace can be 1)
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
            System.out.println("\n" + ppl.getName() + ": Bust! :(\n");
            return;
        }

        // Get optimal sum
        for (int idx = 0; idx < aceNum; idx++) {
            // If Ace is restricted (Then at most one Ace is free to change)
            if ((aceRestricted) && (idx == 1)) {
                break;
            }
            // If the sum is optimal
            if (sum + 10 > points) {
                break;
            }
            sum += 10;
        }

        // Check bonus case
        // note: "A natural Trianta Ena is defined as a hand having a value of 31 (i.e. an Ace and two face cards)."
        // note: more like "i.e. an Ace and two "ten cards""
        boolean condition_1 = sum == points;
        boolean condition_2 = aceNum == 1;  // Natural Blackjack or "i.e. an Ace and two face cards"
        boolean condition_3 = hand.size() == (int)((sum - 1) / 10);  // The other cards are all face cards. note: "i.e. an Ace and two face cards"

        if (condition_1 && condition_2 && condition_3) {
            sum += 0.5;
            System.out.println("\n" + ppl.getName() + ": Natural! :)\n");
        }

        ppl.setScore(sum);
    }

    // Print list of cards and points at hand for each player
    public void printBoard() {
        System.out.println("Dealer: " + dealer.getName() + "   Balance: $" + dealer.getBalance());
        System.out.println(dealer.print_hand() + "\n");

        for(PokerPlayer ppl: players){
            if (!ppl.isDealer()) {
                System.out.println("Player: " + ppl.getName() + "   Balance: $"+ppl.getBalance()+"   Bet: $"+ppl.getBet());
                System.out.println(ppl.print_hand() + "\n");
            }
        }
    }

    // Dealer actions
    public void dealerHit(PokerPlayer dealer, boolean aceRestricted) {
        optimalPoint(dealer, aceRestricted);
        int threshold = points - 4;  // 17 for 21; 27 for 31
        double curPoint = dealer.getScore();
        while ((curPoint < threshold) && (curPoint != -1)) {
            dealer.addCard(deck.pop());
            optimalPoint(dealer, aceRestricted);
            curPoint = dealer.getScore();
        }
    }

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

    // update Balance account
    public void updateBalance(PokerPlayer player, PokerPlayer dealer, String winner) {
        Utils.beautifulWait(0.3);
        // If player wins
        if (winner.equals("Player")) {
            // Player wins money
            player.setBalance(player.getBalance() + 2 * player.getBet());
            // Dealer loses money
            dealer.setBalance(dealer.getBalance() - player.getBet());
            // Player clears bet
            player.clearBet();
            System.out.println(player.getName() + " trumps the Dealer!");
        }

        // If dealer wins
        if (winner.equals("Dealer")) {
            // Dealer wins money
            dealer.setBalance(dealer.getBalance() + player.getBet());
            // Player clears bet
            player.clearBet();
            System.out.println("The Dealer trumps " + player.getName() + "!");
        }

        // If no one wins
        if(winner.equals("Draw")){
            // Player gets back the money
            player.setBalance(player.getBalance() + player.getBet());
            // Player clears bet
            player.clearBet();
            System.out.println(player.getName() + " VS the Dealer:  Draw!");
        }
        Utils.beautifulWait(0.3);
    }

    public void nonDealerInitBalance(double value) {
        for (int idx = 0; idx < players.size(); idx++) {
            PokerPlayer player_tmp = players.get(idx);
            if (!player_tmp.isDealer()) {
                player_tmp.setBalance(value);
            }
        }
    }

    public void playersClearHands() {
        for (int idx = 0; idx < players.size(); idx++) {
            players.get(idx).clearHand();
        }
    }

    public void playerSetBet(PokerPlayer player) {
        System.out.println("Player: " + player.getName() + " Please place your bet.");
        // note: minimum bet: $10; maximum bet: the player's balance
        player.addBet(Utils.safeDoubleInput("Please input the amount (minimum bet is $10): ", 10, player.getBalance()));
        System.out.println("Success.\n");
    }
}

