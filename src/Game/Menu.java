package Game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends VBox {
    private static Stage primaryStage;
    private static Menu instance;

    public Menu(Stage primaryStage) {
        Menu.primaryStage = primaryStage;

        Pane root = new Pane();
        root.setPrefSize(1280, 720);

        ImageView img = new ImageView(new Image("msBgz.png"));
        img.setFitWidth(1280);
        img.setFitHeight(720);
        root.getChildren().add(img);

        StackPane stackPane = new StackPane();
        Rectangle bg = new Rectangle(375, 60);
        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(2);
        bg.setArcWidth(30);
        bg.setArcHeight(30);
        bg.setFill(null);

        Text text = new Text("Line Rangers");
        Stop[] stops = new Stop[] {
                new Stop(0, Color.BLUE),
                new Stop(1, Color.BLACK),
        };
        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, null, stops);
        text.setFill(gradient);

        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 50));
        stackPane.getChildren().addAll(bg, text);
        stackPane.setTranslateX(50);
        stackPane.setTranslateY(200);

        MenuBox vbox = new MenuBox(new MenuItem("New Game", () -> startNewGame()), new MenuItem("Exit", () -> System.exit(0)));
        vbox.setTranslateX(130);
        vbox.setTranslateY(300);
        root.getChildren().addAll(stackPane, vbox);

        getChildren().add(root);
        instance = this;
    }

    public void startNewGame() {
        GameController gameInstance = GameController.getInstance();
        primaryStage.setScene(new Scene(gameInstance.getGameGui(), 1280, 720));
    }

    public static Menu getInstance() {
        return instance;
    }
}

