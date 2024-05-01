package Unit.Enemy;

import Game.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Soulyer extends BaseEnemy {

    public Soulyer() {
        super(200, 80, 1000, 70, "Soulyer", 1, "Soulyer/idle.gif", 500, 1000, 1200);
        getImageView().setFitWidth(200);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(109);
    }
}
