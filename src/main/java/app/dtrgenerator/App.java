package app.dtrgenerator;

import app.dtrgenerator.constant.Constant;
import app.dtrgenerator.ui.Window;

public class App {
    public static void main(String[] args) {
        try {
            new Window(Constant.APP_TITLE).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
