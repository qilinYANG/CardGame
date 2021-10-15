import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

// Working on here
public class Trianta extends PointGame{

    ArrayList<PokerPlayer> activeplayers=new ArrayList<>(players);

    class Sortbybalance implements Comparator<PokerPlayer>{
        public int compare(PokerPlayer a, PokerPlayer b){
            Double a_balance=a.getBalance();
            Double b_balance=b.getBalance();
            return b_balance.compareTo(a_balance);
        }
    }

    public Trianta(){
        super(31);
        int num = Utils.safeIntInput("Please input amount of players: ",2,6);
        registerPlayers(num);
        Random random=new Random();
        int random_index = random.nextInt(players.size());
        players.get(random_index).setDealer();
        dealer=players.get(random_index);
        int initial_balance=Utils.safeIntInput("Please input PLayers' Initial balance(1 ~ 10000): ",1,10000);
        for(PokerPlayer ppl: players){
            if(ppl.isDealer()){
                ppl.setBalance(3*initial_balance);
            }else {
                ppl.setBalance(initial_balance);
            }

        }
    }

    public void startGame(){
        while (true){

            deck.reset();
            deck.shuffle();
            distribute_cards("First distribute");
            printBoard();
            player_setBet();
            distribute_cards("Second distribute");

            printBoard();
            player_setBet();
            boolean allbust=true;
            for(PokerPlayer ppl:players){
                if(!ppl.isDealer()){
                    ppl.getHand().get(0).setVisible();
                    printBoard();
                    PlayerAction(ppl,false,true);
                    if(!isBust(ppl)){
                        allbust=false;
                    }
                    ppl.getHand().get(0).setInvisible();
                }
            }
            if(allbust){
                for(PokerPlayer ppl:players){
                    if(!ppl.isDealer()){
                        updateBalance(ppl,dealer,"Dealer");
                    }
                }
            }else{
                dealerHit(dealer,true);

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

            playersClearHands();
            rotate_dealer();
            remove_loser();
            if(players.size()<2){
                System.out.println("Player "+players.get(0).getName()+" is the last one standing! Game Over!");
                break;
            }
            System.out.println("Do you want to continue playing?(y/n)");
            if(!scan.next().equals("y")) break;
        }
    }

    public void distribute_cards(String stage){
        if(stage.equals("First distribute")){
            for(PokerPlayer ppl: players){
                if (ppl.isDealer()) {
                    ppl.addCard(deck.pop());
                } else {
                    PokerCard card= deck.pop();
                    card.setInvisible();
                    ppl.addCard(card);
                }
            }
        }else{
            for(PokerPlayer ppl: players){
                ppl.addCard(deck.pop());
                ppl.addCard(deck.pop());
            }
        }
    }

    public void player_setBet(){
        for(PokerPlayer ppl: players){
            if(!ppl.isDealer()&&!isBust(ppl)){
                System.out.println("------------------------------------");
                System.out.println("Player "+ppl.getName()+"   balance: "+ppl.getBalance()+"  bet: "+ppl.getBet());
                System.out.println("Card: "+ppl.getHand());
                System.out.println("Please input the amount of bet you want to add: ");
                String bet_tmp=scan.next();
                ppl.addBet(Double.parseDouble(bet_tmp));
                System.out.println("Success!");
            }
        }
    }

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

    public void remove_loser(){
        for(PokerPlayer ppl: players){
            if(ppl.getBalance()<10){
                System.out.println("Player "+ppl.getName()+"'s balance is less than 10$!!! So he cannot continue playing!");
                players.remove(ppl);
            }
        }
    }
}

// "At the end of each round, the player with the largest total cash amount exceeding that of the Banker, is given the option to become the Banker. If they choose to accept, the Player becomes the Banker and current Banker becomes a Player. If they decline the player with the next greatest amount is given the same option, etc."