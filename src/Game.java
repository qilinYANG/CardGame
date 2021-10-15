import java.util.Scanner;
import java.util.ArrayList;

// An abstraction for a general game.
public abstract class Game {
    protected Scanner scan = new Scanner(System.in);

    protected ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();

    final void registerPlayers(int num) {
        // Input player count
        for (int idx = 0; idx < num; idx++) {
            System.out.println("Register Player " + idx);
            System.out.println("Please input name for Player " + idx + ":");
            PokerPlayer player_tmp = new PokerPlayer(scan.next());
            players.add(player_tmp);
            System.out.println("Success!\n");
        }
    }

    public void setPlayers(ArrayList<PokerPlayer> players) {
        this.players = players;
    }

    public ArrayList<PokerPlayer> getPlayers() {
        return players;
    }
}
