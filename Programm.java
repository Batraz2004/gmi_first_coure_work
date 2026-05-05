
import app.App;
import app.Menu;
import java.io.FileWriter;
import java.util.List;

import com.google.gson.Gson;

import app.data.Models.User;

public class Programm {
    public static void main(String[] args) {
        App app = new App();
        app.init();
        Menu menu = new Menu(app);
        menu.run();

    }
}