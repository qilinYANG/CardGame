import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

// The class of Playing Trianta game, which extends Class PointGame. Class Trianta also contains unique game process and game rules.  For example, the method rotate_dealer() is unique and just for Trianta rules.
public class Trianta extends PointGame{
    private ArrayList<PokerPlayer> give_up_player=new ArrayList<>();

    //Comparator used to Sort players by current balance
    class Sortbybalance implements Comparator<PokerPlayer>{
        public int compare(PokerPlayer a, PokerPlayer b){
            Double a_balance=a.getBalance();
            Double b_balance=b.getBalance();
            return b_balance.compareTo(a_balance);
        }
    }
    //Constructor. Preparations before starting
    public Trianta(){
        // Configure the superclass
        super(31);

        // Greetings
        System.out.println("Welcome to Trianta!");
        Utils.beautifulWait(1);

        // Register players
        int num = Utils.safeIntInput("Please input amount of players(2~6): ",2,6);
        registerPlayers(num);
        Utils.beautifulWait(1);

        // note: 1) determine the dealer; 2) the non-dealers determine the balance; 3) the dealer adjusts his/her balance accordingly

        // Determine the dealer
        Random random=new Random();
        int random_index = random.nextInt(players.size());
        players.get(random_index).setDealer();
        dealer=players.get(random_index);
        System.out.println(dealer.getName()+" will be the Dealer! ");
        Utils.beautifulWait(1);

        // Configure the non-dealers
        int initial_balance=Utils.safeIntInput("Please input PLayers' Initial balance(1 ~ 10000): ",1,10000);
        for(PokerPlayer ppl: players){
            if(ppl.isDealer()){// "the dealer adjusts his/her balance accordingly"
                ppl.setBalance(3*initial_balance);
            }else {
                ppl.setBalance(initial_balance);
            }

        }
    }

    // Start the game
    public void startGame(){
        while (true){
            // Init and shuffle the deck
            Utils.beautifulWait(1);
            System.out.println("Shuffling the deck.");
            Utils.beautifulWait(1);
            deck.reset();
            deck.shuffle();

            //First time to distribute Cards
            System.out.println("Dealing cards.");
            distribute_cards("First distribute");
            Utils.beautifulWait(1);

            printBoard();
            Utils.beautifulWait(1);

            player_setBet();

            //Second time to distribute Cards
            System.out.println("Dealing cards.");
            distribute_cards("Second distribute");
            Utils.beautifulWait(1);

            printBoard();
            Utils.beautifulWait(1);

            // note: if all non-dealers bust, then this round immediately ends.
            boolean allbust=true;
            for(PokerPlayer ppl:players){//Perform Actions for every none-dealer player
                if(!ppl.isDealer()){
                    ppl.getHand().get(0).setVisible();//none-dealer player can see their own hand in their round
                    printBoard();
                    PlayerAction(ppl,false,true);
                    if(!isBust(ppl)){
                        allbust=false;
                    }
                    ppl.getHand().get(0).setInvisible();
                }
            }

            // If everyone busts, then the dealer trumps everyone
            if(allbust){
                for(PokerPlayer ppl:players){
                    if(!ppl.isDealer()){
                        updateBalance(ppl,dealer,"Dealer");
                    }
                }
            }else{
                // ----- //
                // Dealer's actions
                // ----- //
                // Demonstrate dealer's hand
                dealerHit(dealer,true);

                // Demonstrate the board
                printBoard();

                // ----- //
                // Conclude the game
                // ----- //
                for(PokerPlayer ppl:players){
                    if(!ppl.isDealer()){
                        updateBalance(ppl,dealer,compare(ppl,dealer,true));
                        for(PokerCard card: ppl.getHand()){
                            card.setVisible();
                        }
                    }
                }
            }


            printBoard();
            Utils.beautifulWait(1);

            //recover players
            for(PokerPlayer ppl:give_up_player){
                players.add(ppl);
            }
            give_up_player.clear();

            // Everyone clears their hand
            playersClearHands();

            //change dealer base on all players' balance
            rotate_dealer();

            //if player don't have enough balance, he or she won't be able to continue playing.
            remove_loser();



            //if there is only one player, game has to end
            if(players.size()<2){
                System.out.println("Player "+players.get(0).getName()+" is the last one standing! Game Over!");
                break;
            }

            if (Utils.safeIntInput("Do you want to continue?\n0: Yes!\n1: No.", 0, 1) == 1) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }

    public void distribute_cards(String stage){
        if(stage.equals("First distribute")){
            for(PokerPlayer ppl: players){
                if (ppl.isDealer()) {
                    // If the player is a dealer
                    // Add a visible card
                    ppl.addCard(deck.pop());
                } else {
                    // Add an invisible card
                    PokerCard card= deck.pop();
                    card.setInvisible();
                    ppl.addCard(card);
                }
            }
        }else{
            for(PokerPlayer ppl: players){
                //second time add two visible card
                ppl.addCard(deck.pop());
                ppl.addCard(deck.pop());
            }
        }
    }

    //ask none-dealer players to set bet
    public void player_setBet(){
        for(PokerPlayer ppl: players){
            if(!ppl.isDealer()&&!isBust(ppl)){
                System.out.println("------------------------------------");
                System.out.println("Player "+ppl.getName()+"   balance: "+ppl.getBalance()+"  bet: "+ppl.getBet());
                System.out.println("Card: "+ppl.getHand());
                System.out.println("Do you want to set Bet? If not you will give up in this round. (y/n)");
                if(scan.next().equals("n")){
                    give_up_player.add(ppl);
                    System.out.println(ppl.getName()+" give up in this round!");
                }else{
                    Double bet_tmp= Utils.safeDoubleInput("Please input the amount of bet you want to add: ",10,ppl.getBalance());
                    ppl.addBet(bet_tmp);
                    System.out.println("Success!");
                }
                Utils.beautifulWait(1);
            }
        }
        for(PokerPlayer ppl: give_up_player){
            players.remove(ppl);
        }
    }

    //change dealer base on players' balance
    public void rotate_dealer(){
        players.sort(new Sortbybalance());
        for(PokerPlayer ppl: players){
            if(dealer.equals(ppl)){
                break;
            }else {
                System.out.println("Player "+ppl.getName()+" higher balance. Do you want to be the Dealer?(y/n)");
                if(scan.next().equals("y")){
                    dealer.setNonDealer();
                    ppl.setDealer();
                    dealer=ppl;
                    System.out.println("Success! Now "+dealer.getName()+" is Dealer!");
                    break;
                }
            }
        }
    }

    //remove players who don't have enough balance
    public void remove_loser(){
        ArrayList<PokerPlayer>players_tmp=new ArrayList<>(players);
        for(PokerPlayer ppl: players_tmp){
            if(ppl.getBalance()<10){
                System.out.println("Player "+ppl.getName()+"'s balance is less than 10$!!! So he cannot continue playing!");
                players.remove(ppl);
                Utils.beautifulWait(1);
            }
        }
    }
}

// "At the end of each round, the player with the largest total cash amount exceeding that of the Banker, is given the option to become the Banker. If they choose to accept, the Player becomes the Banker and current Banker becomes a Player. If they decline the player with the next greatest amount is given the same option, etc."
