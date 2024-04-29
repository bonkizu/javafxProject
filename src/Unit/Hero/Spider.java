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
        super(1000, 40, 100, 60, 10, 150, 500, "Spider", 1.4, "Spider/idle.gif", 1000, 1600);
    }
    @Override
    public BaseHero clone() {
        return new Spider();
    }
}
