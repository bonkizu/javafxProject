package Utils;

import Unit.BaseUnit;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HpBar extends StackPane {
    private final Rectangle background;
    private final Rectangle foreground;

    public HpBar() {

        // Create background rectangle
        background = new Rectangle(100, 10);
        background.setFill(Color.GRAY);

        // Create foreground rectangle (initially full HP)
        foreground = new Rectangle(100, 10);
        foreground.setFill(Color.GREEN);

        getChildren().addAll(background, foreground);
    }

    public void updateHP(int curHp, int maxHp) {
        double hpRatio = (double) curHp / maxHp;
        foreground.setWidth(hpRatio * background.getWidth()); // Update width based on HP ratio
    }
}
