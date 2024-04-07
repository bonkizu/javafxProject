package Unit.Hero;

import Unit.BaseUnit;
import Unit.Enemy.BaseEnemy;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Lightning extends BaseHero {
    public Lightning() {
        super(5, 15, 150, 70, 20, 400, 3000, "Lightning", 3, "lightning.gif");
    }

    @Override
    public void attack(BaseUnit target) {
        if(target instanceof BaseEnemy enemy) {
            attacking = new Timeline(new KeyFrame(Duration.millis(10), e -> {
                target.setHp(target.getHp() - getAttack());
                if (target.getHp() <= 0) {
                    hasTarget = false;
                    move();
                    attacking.stop();
                }
            }));
            attacking.setCycleCount(Animation.INDEFINITE);
            attacking.play();
        }
    }

    @Override
    public BaseHero clone() {
        return new Lightning();
    }


}
