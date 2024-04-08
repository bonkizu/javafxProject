package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Enemy.BaseEnemy;
import Unit.Type.Attackable;
import Utils.GameUtils;
import Utils.UnitState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Padoru extends BaseHero {

    public Padoru() {
        super(10, 10, 100, 40, 10, 100, 500, "Padoru", 1.5, "padoru.gif");

    }

    @Override
    public BaseHero clone() {
        return new Padoru();
    }


}
