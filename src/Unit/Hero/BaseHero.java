package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Enemy.BaseEnemy;
import Utils.GameUtils;
import javafx.animation.Animation;
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
    protected int cooldown;
    public BaseHero(int attack, int defense, int hp, int speed, int attackSpeed, int cost, int cooldown, String name, double range, String imageUrl) {
        super(attack, defense, hp, speed, attackSpeed, cost,  name, range, imageUrl);
        setCooldown(cooldown);
        setMoving(getSpeed());
        initializeChecking();
    }
    public void initializeChecking() {
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
//    public abstract void attack(BaseUnit target);
    public void attack(BaseUnit target) {
        if(target instanceof BaseEnemy enemy) {
            attacking = new Timeline(new KeyFrame(Duration.millis(getAttackSpeed()), e -> {
                target.setHp(target.getHp() - getAttack());
                if (target.getHp() <= 0) {
                    hasTarget = false;
                    move();
                    attacking.stop();
                    GameController.getInstance().increaseMoney(enemy.getCost());
                }
            }));
            attacking.setCycleCount(Animation.INDEFINITE);
            attacking.play();
        }
    }
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


    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
