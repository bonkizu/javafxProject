package Unit.Enemy;

import Game.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Soulyer extends BaseEnemy {

    public Soulyer() {
        super(100, 50, 100, 50, "Soulyer", 1, "Soulyer/idle.gif", 100, 1000, 1200);
        getImageView().setFitWidth(200);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(109);
    }
}
