import java.util.Random;

// The class of Playing BlackJack game, which extends Class PointGame. Class BlackJack contains special game process and game rules just for BlackJack. For example, the method distribute_card() is unique and just for BlackJack rules.
public class BlackJack extends PointGame{
    // Preparations before starting
    public BlackJack(){
        // Configure the superclass
        super(21);

        // Greetings
        System.out.println("Welcome to BlackJack!\n\nPlease choose:\n1. Player VS Computer\n2. Player VS Player\n");
        Utils.beautifulWait(1);

        // Register players
        registerPlayers(Utils.safeIntInput("Please select 1 or 2: ", 1, 2));
        Utils.beautifulWait(1);

        // note: 1) determine the dealer; 2) the non-dealers determine the balance; 3) the dealer adjusts his/her balance accordingly

        // Determine the dealer
        if (players.size() == 1) {
            // If one player
            dealer = new PokerPlayer("Computer");
            System.out.println("The Computer will be the dealer!");
            Utils.beautifulWait(1);
            players.add(dealer);
        }
        else {
            // If two players
            dealer = players.get(new Random().nextInt(2));
            System.out.println(dealer.name + " will be the dealer!\n");
            Utils.beautifulWait(1);

        }
        dealer.setDealer();

        // Configure the non-dealers
        double bet_tmp = Utils.safeDoubleInput("Please input the balance for the players (the non-dealers) ($10 ~ $100000): ", 10, 100000);
        nonDealerInitBalance(bet_tmp);
        Utils.beautifulWait(1);
        System.out.println("Success.\n");

        // "the dealer adjusts his/her balance accordingly"
        dealer.setBalance(bet_tmp * 100);
    }

    // Start the game
    public void startGame() {
        while (true) {
            // Init and shuffle the deck
            Utils.beautifulWait(1);
            System.out.println("Shuffling the deck.");
            Utils.beautifulWait(1);
            deck.reset();
            deck.shuffle();
            
            // ----- //
            // Non-dealers' actions
            // ----- //
            // Blackjack special: Determine the principal player
            PokerPlayer principalPlayer;
            if (players.get(0).isDealer()) {
                principalPlayer = players.get(1);
            }
            else {
                principalPlayer = players.get(0);
            }
            // The non-dealer places his/her bet
            playerSetBet(principalPlayer);
            Utils.beautifulWait(1);

            // Deal cards
            dealCards();
            System.out.println("Dealing cards.");
            Utils.beautifulWait(1);

            // note: if all non-dealers bust, then this round immediately ends.
            boolean allBust = true;
            int counter = 0;
            while (counter < players.size()) {
                PokerPlayer playerTmp = players.get(counter);

                // Skip if is dealer
                if (playerTmp.isDealer()) {
                    counter += 1;
                    continue;
                }

                // Blackjack special:
                // If he/she is not the principal player
                if (counter >= 2) {
                    // The principal player lends his/her money to the non-principal player
                    playerTmp.setBalance(principalPlayer.getBalance());
                }
                
                // Demonstrate the board
                printBoard();
                Utils.beautifulWait(1);
                // Perform the actions
                PlayerAction(playerTmp, true, false);  // note: moved "optimalPoint(player, false)" into PlayerAction - case "stand"
                
                // Blackjack special:
                // If he/she is not the principal player
                if (counter >= 2) {
                    // The non-principal player returns his/her money to the principal player                
                    principalPlayer.setBalance(playerTmp.getBalance());
                    playerTmp.setBalance(0);
                }

                // If anyone doesn't bust, then allBust is false
                if (!isBust(playerTmp)) {
                    allBust = false;
                }
                counter += 1;
            }
            
            // If everyone busts, then the dealer trumps everyone
            if (allBust) {
                for (int idx = 0; idx < players.size(); idx++) {
                    PokerPlayer playerTmp = players.get(idx);
                    if (playerTmp.isDealer()) {
                        continue;
                    }
                    updateBalance(playerTmp, dealer, "Dealer");
                }
            }
            else {
                // ----- //
                // Dealer's actions
                // ----- //
                // Demonstrate dealer's hand
                for (PokerCard card : dealer.getHand()) {
                    card.setVisible();
                }
                dealerHit(dealer, false);
                // Demonstrate the board
                printBoard();
                
                // ----- //
                // Conclude the game
                // ----- //
                for (int idx = 0; idx < players.size(); idx++) {
                    PokerPlayer playerTmp = players.get(idx);
                    if (playerTmp.isDealer()) {
                        continue;
                    }                   
                    updateBalance(playerTmp, dealer, compare(playerTmp, dealer, false));
                }
            }

            // Blackjack special:
            // Merge the principal player with the non-principal players
            while (players.size() != 2) {
                // Pop the first non-principal player
                PokerPlayer playerTmp = players.remove(2);
                principalPlayer.setBalance(principalPlayer.getBalance() + playerTmp.getBalance());
            }

            printBoard();
            playersClearHands();  // Everyone clears their hand

            // If "non-sufficient funds (NSF)" for every player, then the game has to end.
            boolean allNSF = true;
            for (int idx = 0; idx < players.size(); idx++) {
                PokerPlayer playerTmp = players.get(idx);
                if (playerTmp.isDealer()) {
                    continue;
                }
                if (playerTmp.getBalance() > 10) {
                    allNSF = false;
                    break;
                }
            }

            if (allNSF) {
                Utils.safeIntInput("Sorry, but you have to leave :( (non-sufficient funds; $10)\n0: Acknowledged!\n1: Bye!", 0, 1);
                System.out.println("Goodbye!");
                break;
            }

            if (Utils.safeIntInput("Do you want to continue?\n0: Yes!\n1: No.", 0, 1) == 1) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }

    public void dealCards() {
        for (PokerPlayer ppl : players) {
            if (ppl.isDealer()) {
                // If the player is a dealer
                // Add a visible card
                ppl.addCard(deck.pop());
                // Add an invisible card
                PokerCard tmp = deck.pop();
                tmp.setInvisible();
                ppl.addCard(tmp);
            }
            else {
                // If the player is not a dealer
                // Add two cards
                ppl.addCard(deck.pop());
                ppl.addCard(deck.pop());
            }
        }
    }
}
