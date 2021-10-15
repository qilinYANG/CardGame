import java.util.Random;

// Working on here
public class BlackJack extends PointGame{
    private PokerPlayer player;
    public BlackJack(){
        super(21);
        int num=0;
        System.out.println("Welcome to BlackJack! Please choose: 1.Player VS Computer  2.Player VS Player ");
        while(true){
            System.out.println("Please select 1 or 2: ");
            String tmp =scan.next();
            if (tmp.equals("1") || tmp.equals("2")){
                num=Integer.parseInt(tmp);
                break;
            }
            System.out.println("Invalid Number!");
        }
        registerPlayers(num);
        set_dealer();
    }
    public void set_dealer(){
        int size=players.size();
        if(size==1){
            PokerPlayer computer=new PokerPlayer("Computer");
            computer.setDealer();
            computer.setBalance(99999);
            dealer=computer;
            players.add(computer);
            player=players.get(0);
            player.setBalance(1000);
        }else if(size==2){
            Random random=new Random();
            int index=random.nextInt(2);
            players.get(index).setDealer();
            dealer=players.get(index);// 0 or 1
            player=players.get(1-index);// 1 or 0
            dealer.setBalance(99999);
            player.setBalance(1000);
        }
    }

    public void start_game(){

        while(true){
            deck.shuffle();
            distribute_cards();
            optimalPoint(player,false);
            printBoard();
            setBet();
            PlayerAction(player, false, false);
            for(PokerCard card:dealer.getHand()){
                card.setVisible();
            }
            if(player.getScore()==-1){
                updateBalance(player,dealer,"Dealer");
            }else {
                dealerHit(dealer, false);
                updateBalance(player, dealer, compare(player, dealer, false));
            }
            player.clearBet();
            printBoard();

            System.out.println("Do you want to continue?(y/n)");
            if(!scan.nextLine().equals("y")){break;}
            player.clearHand();
            dealer.clearHand();
            deck.reset();

        }

    }
    public void distribute_cards(){
        for(PokerPlayer man:players){
            if(man.isDealer()){
                man.addCard(deck.pop());
                PokerCard tmp =deck.pop();
                tmp.setInvisible();
                man.addCard(tmp);
            }else {
                man.addCard(deck.pop());
                man.addCard(deck.pop());
            }
        }
    }
    public void setBet(){

        System.out.println("Please input amount of currency you want to add to your bet (A number): ");
        String bet=scan.next();
        player.addBet(Double.parseDouble(bet));

        while (true){
            System.out.println("Player "+player.getName()+"'s current bet is: "+player.getBet());
            System.out.println("Do you want to continue adding or withdrawing bet?(y/n)");
            if(scan.nextLine().equals("y")){
                System.out.println("Please input amount of currency you want to add or withdraw to your bet (A Positive or Negative number): ");
                player.addBet(Double.parseDouble(scan.nextLine()));
            }
            break;
        }


    }
}
