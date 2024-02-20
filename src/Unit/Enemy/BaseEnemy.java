package Unit.Enemy;

import Unit.BaseUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class BaseEnemy extends BaseUnit {
    private Timeline moving;
    public BaseEnemy(int attack, int defense, int hp, int speed, int attackSpeed, String name, double range, String imageUrl) {
        super(attack, defense, hp, speed, attackSpeed, name, range, imageUrl);
        setMoving(getSpeed());
        move();
    }

    @Override
    protected void setMoving(int speed) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), getImageView());
        translateTransition.setByX(-speed);
        moving = new Timeline(new KeyFrame(Duration.millis(100), e -> translateTransition.playFromStart()));
        moving.setCycleCount(Timeline.INDEFINITE);
    }

    @Override
    public void move() {
        moving.play();
    }

    @Override
    public void stopMoving() {
        moving.stop();
    }
}
