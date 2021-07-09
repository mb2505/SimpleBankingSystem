import java.util.Scanner;

public class UserInterface {
    Scanner scanner = new Scanner(System.in);
    Operation op = new Operation();

    public void menu() {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
        int next = scanner.nextInt();
        if (next == 1) {
            op.createAccount();
            menu();
        } else if (next == 2) {
            op.login();
        } else if (next == 0) {

            System.out.println("Bye!");
            System.exit(0);
        }
    }

    public void cardMenu(int slot) {
        System.out.println("\n1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit");
        int next = scanner.nextInt();
        if (next == 1) {
            System.out.printf("\nBalance: %d%n", op.getBalance(slot));
            cardMenu(slot);
        } else if (next == 2) {
            op.addIncome(slot);
            cardMenu(slot);
        } else if (next == 3) {
            op.transfer(slot);
            cardMenu(slot);
        } else if (next == 4) {
            op.closeAccount(slot);
            menu();
        } else if (next == 5) {
            System.out.println("\nYou have successfully logged out!\n");
            menu();
        }else if (next == 0) {

            System.out.println("\nBye!");
            System.exit(0);
        }
    }

    public void downloadCards() {
        op.downloadCards();
    }
}
