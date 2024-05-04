package Game;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MenuBox extends VBox {
    public MenuBox(MenuItem... items) {
        for (MenuItem item : items) {
            Line sep = new Line();
            setMargin(item, new Insets(0, 0, 10, 0));
            getChildren().addAll(item, sep);
        }
    }
}
