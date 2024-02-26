package Unit.Enemy;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Hero.BaseHero;
import Unit.Type.Attackable;
import Utils.GameUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Wg extends BaseEnemy implements Attackable {

    private Timeline checking;
    public Wg() {
        super(10, 10, 100, 50, 10, "Ui", 1.5, "wg.gif");

    }
    @Override
    public void check() {

    }

    @Override
    public void attack(BaseUnit target) {
        if(target instanceof BaseHero hero) {

        }
    }
}
