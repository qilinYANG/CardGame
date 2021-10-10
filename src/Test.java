import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        PokerPlayer pc = new PokerPlayer("p1","player",0,0,0);
        ArrayList<ArrayList<PokerCard>> handcard = pc.getHandCard();
        handcard.add(new ArrayList<PokerCard>());
        System.out.println(handcard.get(0) instanceof  ArrayList<PokerCard>) ;
    }
}
