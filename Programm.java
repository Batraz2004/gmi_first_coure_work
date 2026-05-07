
import java.util.Scanner;

import app.App;
import app.Auth;
import app.Controller;

public class Programm {
    public static void main(String[] args) {
        App app = new App();
        app.init();

        Scanner scannerIn = new Scanner(System.in);

        outerLoop: while (true) {
            System.out.println("Войти:(yes/no)");
            String choice = scannerIn.nextLine();

            switch (choice) {
                case "yes":
                    Auth authUser = new Auth();

                    if (authUser.login()) {
                        System.out.print("Вы авторизованы!");
                        System.out.print(authUser);
                        Controller controller = new Controller(app, authUser);
                        controller.run();
                    }

                    break;
                case "no":
                    break outerLoop;
                default:
                    break;
            }
        }

        scannerIn.close();
    }
}