// Manages the main loop and the choosing of games.
public class Menu {
    public void run() {
        while (true) {
            Utils.beautifulWait(0.5);
            System.out.println("Welcome!");
            Utils.beautifulWait(0.5);
            int user_choice_tmp = Utils.safeIntInput("Which game do you want to play?\n0: BlackJack\n1: Trianta\n2: Exit Program", 0, 2);
            Utils.beautifulWait(1);
            System.out.println("\n");
            if (user_choice_tmp == 2) {
                System.out.println("Thanks for playing!");
                break;
            }
            if (user_choice_tmp == 0) {
                new BlackJack().startGame();
            }
            if (user_choice_tmp == 1) {
                new Trianta().startGame();
            }
            
        } 
        
    }
}
