package Unit.Hero;

import Unit.BaseUnit;
import Unit.Enemy.BaseEnemy;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Saber extends BaseHero{

    public Saber() {
        super(40, 40, 200, 30, 5, 400, 3000, "Saber", 1, "saber.gif");
        getImageView().setScaleX(-1);
        getImageView().setFitWidth(300);
        getImageView().setPreserveRatio(true);
    }

    @Override
    public BaseHero clone() {
        return new Saber();
    }


}
