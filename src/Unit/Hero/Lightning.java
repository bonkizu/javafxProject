package Unit.Hero;

import Unit.BaseUnit;
import Unit.Enemy.BaseEnemy;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Lightning extends BaseHero {
    public Lightning() {
        super(5, 15, 150, 70, 20, 200, 2000, "Lightning", 3, "lightning.gif");
    }



    @Override
    public BaseHero clone() {
        return new Lightning();
    }


}
