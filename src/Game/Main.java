package Game;

import Map.GameGui;
import Map.GameMap;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GameController gameInstance = GameController.getInstance();
        stage.setScene(new Scene(gameInstance.getGameGui(), 1280, 720));
        stage.setTitle("Hello World!");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
