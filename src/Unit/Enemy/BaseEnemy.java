package Unit.Enemy;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Hero.BaseHero;
import Unit.Type.Attackable;
import Utils.GameUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public abstract class BaseEnemy extends BaseUnit implements Attackable {
    protected Timeline moving;
    protected Timeline attacking;
    protected Timeline checking;
    protected boolean hasTarget = false;
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

    protected void initializeChecking() {
        checking = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            if(getHp() <= 0) {
                if(attacking != null) {
                    attacking.stop();
                }
                stopMoving();
                hasTarget = false;
                checking.stop();
                destroyed();
            }
            if(!hasTarget) {
                for(BaseHero hero : GameController.getInstance().getHeroes()) {
                    if (GameUtils.inRange(this, hero)) {
                        stopMoving();
                        hasTarget = true;
                        attack(hero);
                        break;
                    }
                }
            }
        }));
        checking.setCycleCount(Timeline.INDEFINITE);
        checking.play();
    }

    @Override
    public abstract void attack(BaseUnit target);

    private void destroyed() {
        GameController.getInstance().getEnemies().remove(this);
        GameController.getInstance().getGameMap().getChildren().remove(getImageView());
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
