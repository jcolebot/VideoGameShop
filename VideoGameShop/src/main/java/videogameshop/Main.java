package videogameshop;
import java.util.Scanner;

/* Console based app that emulates a marketplace where customers can make offers and purchase items.
Employees are able to manage inventory and customer offers.

Project Requirements
*1. User can log in
*2. Employee can add items to shop
*3. Customer can view available items
*4. Customer can make an offer
5. Items are moved to "owned state" once an offer is accepted
6. Once offer is accepted, all pending offers are rejected
*7. Customer can register for an account
*8. Employee can remove items from shop
*9. Customer can view owned items
10. Customer can view payments left on items
11. Employee can view all payments
12. Payments are calculated for a weekly amount

 */



public class Main {

    static Scanner scanner = new Scanner(System.in);

    static User currentUser = null;

    static UserRepo userRepo = new JDBC_UserRepo();

    static UserLogin userLogin = new UserLoginLogic(userRepo);

    static boolean trying = true;

    static EmployeeDbLogic manageGames = new EmployeeDbLogic();

    static UserOptions cstService = new UserOptions();



    public static void main(String[] args) {
        while(trying != false) {
            System.out.print("Choose (1) for customer login, (2) to create an account, (3) for employee" +
                    " login, or (4) to exit: ");
            int selection = scanner.nextInt();

            // modern switch statement for readability
            switch(selection) {
                case 1 -> startLogin();
                case 2 -> createUser();
                case 3 -> employeeLogin();
                case 4 -> exit();
                default -> {
                    System.out.println("Invalid Selection");
                }
            }
        }
    }
    private static void startLogin() {
        scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        try {
            currentUser = userLogin.login(email, password);
            System.out.println("Login successful!");

            boolean cstLogged = true;

            while (cstLogged != false) {
                System.out.println("Press (1) to view available games, (2) make an offer, (3)" +
                        " view owned games, (4) exit");
                int cstSelection = scanner.nextInt();

                switch (cstSelection) {
                    case 1 -> manageGames.viewInventory();
                    case 2 -> cstService.makeOffer();
                    case 3 -> cstService.viewOwnedGames();
                    case 4 -> cstLogged = false;
                }
            }


        }
        catch (UserNotFoundException | InvalidCredentialException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private static void createUser() {
        scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Choose your password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);

        userLogin.register(user);
    }

    private static void employeeLogin() {
        Scanner scan = new Scanner(System.in);
        String userName = "admin";
        String password = "password";

        System.out.print("Enter your username: ");
        String getUserName = scan.next();
        System.out.print("Enter your password: ");
        String getPassword = scan.next();

        if(getUserName.equals("admin") && getPassword.equals("password")) {
            System.out.println("You have logged in successfully!");
            boolean empLogged = true;

            while (empLogged != false) {
                System.out.println("Choose (1) to view inventory, (2) to add a game, (3) to delete a game, " +
                        "(4) to exit.");
                int empSelection = scan.nextInt();
                switch(empSelection) {
                    case 1 -> manageGames.viewInventory();
                    case 2 -> manageGames.addGame();
                    case 3 -> manageGames.removeGame();
                    case 4 -> empLogged = false;
                }
            }
        }
        else {
            System.out.println("Invalid password or username");
        }
    }

    private static void exit() {
        System.out.println("Goodbye");
        trying = false;
    }
}
