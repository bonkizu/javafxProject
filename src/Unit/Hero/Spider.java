package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Type.SpecialEffect;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Spider extends BaseHero {

    public Spider() {
        super(600, 30, 300, 60, 0, 500, "Spider", 0.9, "Spider/idle.gif", 200, 1600, 1800);
        getImageView().setFitWidth(150);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(135);
    }

    @Override
    public BaseHero clone() {
        return new Spider();
    }
}
