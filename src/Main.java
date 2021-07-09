public class Main {

    public static void main(String[] args) {
        Database.createDatabase(args[1]);
        Database.createTable();
        UserInterface user = new UserInterface();
        user.downloadCards();
        user.menu();
    }
}