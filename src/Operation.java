import java.util.ArrayList;
import java.util.Scanner;

public class Operation {
    final static BankingSystem bs = new BankingSystem();
    Scanner scanner = new Scanner(System.in);

    public void createAccount() {
        CreditCard card = new CreditCard();
        bs.addCard(card);
        System.out.printf("\nYour card has been created\n" +
                "Your card number:\n" +
                "%s\n" +
                "Your card PIN:\n" +
                "%s\n\n", card.getCardNumber(), card.getPin());
    }

    public void login() {
        UserInterface user = new UserInterface();
        System.out.println("\nEnter your card number:");
        String cardNumber = scanner.next();
        System.out.println("Enter your PIN:");
        String pin = scanner.next();
        int slot = bs.checkNumber(cardNumber);
        if (slot != -1) {
            if (bs.checkPin(slot, pin)) {
                System.out.println("\nYou have successfully logged in!");
                user.cardMenu(slot);
            } else {
                System.out.println("Wrong card number or PIN!\n");
                user.menu();
            }
        } else {
            System.out.println("\nWrong card number or PIN!\n");
            user.menu();
        }
    }

    public int getBalance(int slot) {
        return bs.getCard(slot).getBalance();
    }

    public void addIncome(int slot) {
        System.out.println("\nEnter income:");
        int income = scanner.nextInt();
        bs.getCard(slot).addBalance(income);
        Database.setIncome(bs.getCard(slot));
        System.out.println("Income was added!");
    }

    public void transfer(int slot) {
        System.out.println("\nTransfer\n" +
                "Enter card number:");
        String cardNum = scanner.next();
        if (!checkLuhn(cardNum)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        } else {
            if (!bs.cardCheck(cardNum)) {
                System.out.println("Such a card does not exist.");
            } else if(bs.getCard(slot).getCardNumber().equals(cardNum)) {
                System.out.println("You can't transfer money to the same account!");
            } else if (bs.cardCheck(cardNum)) {
                System.out.println("Enter how much money you want to transfer:");
                int money = scanner.nextInt();
                if (bs.getCard(slot).checkBalance(money)) {
                    int slotSecondCard = bs.checkNumber(cardNum);
                    bs.getCard(slot).removeBalance(money);
                    bs.getCard(slotSecondCard).addBalance(money);
                    Database.setIncome(bs.getCard(slot));
                    Database.setIncome(bs.getCard(slotSecondCard));
                    System.out.println("Success!");
                } else {
                    System.out.println("Not enough money!");
                }
            }
        }
    }

    public void closeAccount(int slot) {
        Database.deleteCard(bs.getCard(slot));
        bs.removeCard(slot);
        System.out.println("\nThe account has been closed!\n");
    }

    public void downloadCards() {
        ArrayList<String> list = Database.downloadCards();
        for (int i = 0; i < list.size() - 2; i+=3) {
            CreditCard card = new CreditCard(list.get(i), list.get(i + 1),
                    Integer.parseInt(list.get(i + 2)));
            bs.addCard(card);
        }
    }

    public boolean checkLuhn(String number) {
        ArrayList<Integer> num = new ArrayList<>();

        for (int i = 0; i < number.length(); i++) {
            int x = number.charAt(i) - '0';
            num.add(x);
        }

        int lastDigit = num.get(15);
        num.remove(15);

        for (int i = 0; i < num.size(); i++) {
            if ((i + 1) % 2 != 0) {
                num.set(i, num.get(i) * 2);
            }
        }

        for (int i = 0; i < num.size(); i++) {
            if (num.get(i) > 9) {
                num.set(i, num.get(i) - 9);
            }
        }

        num.add(lastDigit);
        int sum = 0;
        for (Integer integer : num) {
            sum += integer;
        }

        return sum % 10 == 0;
    }
}
