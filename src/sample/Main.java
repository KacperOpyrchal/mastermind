package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    static Presenter presenter = new Presenter();

    public static void initAgain() {
        presenter.initAgain();
    }

    @Override
    public void start(Stage primaryStage) {
        presenter.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
