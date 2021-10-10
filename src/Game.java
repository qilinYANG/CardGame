import java.util.Scanner;
import java.util.ArrayList;

public abstract class Game {
    private Scanner scan = new Scanner(System.in);

    protected int playerCount;
    private ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();

    public void registerPlayers() {
        // Input player count
        playerCount = Utils.safeIntInput("How many players? (1-10)", 1, 10);
        System.out.println("\n");

        for (int idx = 0; idx < playerCount; idx++) {
            System.out.println("Register Player " + idx);
            System.out.println("Please input name for Player " + idx + ":");
            PokerPlayer player_tmp = new PokerPlayer(scan.next());
            players.add(player_tmp);
            System.out.println("Success!\n");
        }
    }
}
