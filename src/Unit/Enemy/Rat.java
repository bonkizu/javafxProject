package Unit.Enemy;

import Game.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Rat extends BaseEnemy {
    public Rat() {
        super(200, 20, 100, 50, "Rat", 1, "Rat/idle.gif", 500, 1600, 1000);
        getImageView().setFitWidth(250);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(145);
    }
}
