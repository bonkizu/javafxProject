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
        return new Saber();
    }


}
