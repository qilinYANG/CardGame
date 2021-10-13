import java.util.Random;

// Working on here
public class BlackJack extends PointGame{
    private Player player;
    private Player dealer;
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
            players.add(computer);
        }else if(size==2){
            Random random=new Random();
            int index=random.nextInt(2);
            players.get(index).setDealer();
        }
    }

    public void start_game(){
        distribute_cards();


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
}
