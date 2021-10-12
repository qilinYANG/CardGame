public class Play {
    public static void main(String[] args) {

        Deck deck = new Deck(4);
        PointGame g1 = new PointGame(21);
        g1.registerPlayers();
        g1.initCard(g1.players,deck,2);
        for (PokerPlayer ppl : g1.players){
            for (int i=0; i < ppl.getHand().size();i++){
                System.out.println(i+1 + "th card: "+ppl.getHand().get(i));
            }
        }
        System.out.println();
    }
}
