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
            String tmp =scan.nextLine();
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
            dealer=computer;
            players.add(computer);
            player=players.get(0);
        }else if(size==2){
            Random random=new Random();
            int index=random.nextInt(2);
            players.get(index).setDealer();
            dealer=players.get(index);// 0 or 1
            player=players.get(1-index);// 1 or 0
        }
    }

    public void start_game(){

        while(true){
            distribute_cards();
            printBoard();
            setBet();
            PlayerAction(player);
            dealerHit(dealer);
            printBoard();

            if(player.getScore()==-1){
                updateBalance(player,dealer,"dealer");
            }else {
                updateBalance(player,dealer,compare(player,dealer,false));
            }

            System.out.println("Do you want to continue?(y/n)");
            if(!scan.nextLine().equals("y")){break;}
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
        player.setBet(Double.parseDouble(scan.nextLine()));

        while (true){
            System.out.println("Player "+player.getName()+"'s current bet is: "+player.getBet());
            System.out.println("Do you want to continue adding or withdrawing bet?(y/n)");
            if(scan.nextLine().equals("y")){
                System.out.println("Please input amount of currency you want to add or withdraw to your bet (A Positive or Negative number): ");
                player.setBet(Double.parseDouble(scan.nextLine()));
            }
            break;
        }


    }
}
