import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreditCard {
    final private String cardNumber;
    final private String pin;
    private int balance;

    public CreditCard() {
        this.cardNumber = generateCardNumber();
        this.pin = generatePin();
        this.balance = 0;
        Database.addCardToDB(this);
    }

    public CreditCard(String cardNumber, String pin, int balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String generateCardNumber() {
        ArrayList<Integer> num = new ArrayList<>(List.of(4, 0, 0, 0, 0, 0));
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            num.add(random.nextInt(10));
        }
        int checkSum = luhnAlgorithm(num);
        num.add(checkSum);
        return num.toString().replaceAll("\\D","");
    }

    public String generatePin() {
        int[] code = new int[4];
        Random random = new Random();
        for (int i = 0; i < code.length; i++) {
            code[i] = random.nextInt(10);
        }
        String pin = Arrays.toString(code).replaceAll("\\D","");
        return pin;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public int luhnAlgorithm(ArrayList<Integer> arr) {
        ArrayList<Integer> num = new ArrayList<>(arr);
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

        int sum = 0;
        for (int i = 0; i < num.size(); i++) {
            sum += num.get(i);
        }

        int checkSum = 10 - (sum % 10);
        if (checkSum == 10) {
            checkSum = 0;
        }

        return checkSum;
    }

    public void addBalance(int balance) {
        this.balance += balance;
    }

    public void removeBalance(int balance) {
        this.balance -= balance;
    }

    public boolean checkBalance(int balance) {
        if (this.balance >= balance) {
            return true;
        } else {
            return false;
        }
    }
}
