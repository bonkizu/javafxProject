package Unit.Enemy;

import Game.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class NightWar extends BaseEnemy {
    public NightWar() {
        super(800, 100, 3000, 50, "NightWar", 1.5, "NightWar/idle.gif", 2000, 2200, 3800);
        getImageView().setFitWidth(350);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(107);
    }
}
