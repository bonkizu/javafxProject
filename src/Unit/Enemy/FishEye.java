package Unit.Enemy;

import Game.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class FishEye extends BaseEnemy {
    public FishEye() {
        super(100, 40, 300, 50, "FishEye", 1, "FishEye/idle.gif", 300, 800, 1600);
        getImageView().setFitWidth(200);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(120);
    }
}
