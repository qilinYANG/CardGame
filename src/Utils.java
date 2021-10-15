import java.util.Scanner;

// A class that provides utility functions.
public class Utils {
    static Scanner scan = new Scanner(System.in);

    // Foolproof integer input
    public static int safeIntInput(String message, int min, int max) {
        int int_tmp;

        while (true) {
            System.out.println(message);
            String str_tmp = scan.next();
            try {
                int_tmp = Integer.parseInt(str_tmp);
                if ((int_tmp >= min) && (int_tmp <= max)) {
                    break;
                }
                System.out.println("Not within range [" + min + ", " + max + "]!");
              } catch(NumberFormatException e) {
                System.out.println("Not an integer!");
            } 
        }
        return int_tmp;
    }

    // Foolproof double input
    public static double safeDoubleInput(String message, double min, double max) {
        double double_tmp;

        while (true) {
            System.out.println(message);
            String str_tmp = scan.next();
            try {
                double_tmp = Double.parseDouble(str_tmp);
                if ((double_tmp >= min) && (double_tmp <= max)) {
                    break;
                }
                System.out.println("Not within range [" + min + ", " + max + "]!");
              } catch(NumberFormatException e) {
                System.out.println("Not a number!");
            } 
        }
        return double_tmp;
    }
}
