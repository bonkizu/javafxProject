package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Enemy.BaseEnemy;
import Utils.GameUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class BaseHero extends BaseUnit {
    protected Timeline checking;

    protected Timeline attacking;
    protected boolean hasTarget = false;

    protected Timeline moving;
    public BaseHero(int attack, int defense, int hp, int speed, int attackSpeed, String name, double range, String imageUrl) {
        super(attack, defense, hp, speed, attackSpeed, name, range, imageUrl);
        setMoving(getSpeed());
        initializeChecking();
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
                for(BaseEnemy enemy : GameController.getInstance().getEnemies()) {
                    if (GameUtils.inRange(this, enemy)) {
                        stopMoving();
                        hasTarget = true;
                        attack(enemy);
                        break;
                    }
                }
            }
        }));
        checking.setCycleCount(Timeline.INDEFINITE);
        checking.play();
    }
    public abstract void attack(BaseUnit target);
    public abstract BaseHero clone();
    private void destroyed() {
        GameController.getInstance().getHeroes().remove(this);
        GameController.getInstance().getGameMap().getChildren().remove(getImageView());
    }
    @Override
    protected void setMoving(int speed) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), getImageView());
        translateTransition.setByX(speed);
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
