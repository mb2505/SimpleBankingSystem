import java.util.ArrayList;

public class BankingSystem {
    private ArrayList<CreditCard> cardArchive = new ArrayList<CreditCard>();

    public void addCard(CreditCard card) {
        this.cardArchive.add(card);
    }

    public CreditCard getCard(int num) {
        return cardArchive.get(num);
    }

    public int checkNumber(String num) {
        for (int i = 0; i < cardArchive.size(); i++) {
            if (this.getCard(i).getCardNumber().equals(num)) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkPin(int card, String pin) {
        if (this.getCard(card).getPin().equals(pin)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean cardCheck(String cardNum) {
        for (CreditCard c : cardArchive) {
            if (c.getCardNumber().equals(cardNum)) {
                return true;
            }
        }
        return false;
    }

    public void removeCard(int num) {
        cardArchive.remove(num);
    }
}
