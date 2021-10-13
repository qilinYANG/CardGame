public class Play {
    public static void main(String[] args) {


        PointGame g1 = new PointGame(21);
        g1.registerPlayers(6);
        for (PokerPlayer ppl : g1.players){
            for (int i=0; i < ppl.getHand().size();i++){
                System.out.println(i+1 + "th card: "+ppl.getHand().get(i));
            }
        }
        System.out.println();
    }
}
