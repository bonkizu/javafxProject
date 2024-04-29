package Game;

import Map.GameGui;
import Map.GameMap;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Menu menu = new Menu(stage);
        stage.setScene(new Scene(menu, 1280, 720));
        stage.getIcons().add(new Image("padoru.gif"));
        stage.setTitle("Rangers Line");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
