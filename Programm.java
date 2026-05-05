
import app.App;
import app.Controller;

public class Programm {
    public static void main(String[] args) {
        App app = new App();
        app.init();
        Controller controller = new Controller(app);
        controller.run();
    }
}