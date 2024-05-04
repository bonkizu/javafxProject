package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Enemy.BaseEnemy;
import Unit.Type.SpecialEffect;
import Utils.GameUtils;
import Utils.UnitState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseHero extends BaseUnit {

    private Timeline heroLogic;
    private Timeline heroMove;
    private boolean isCooldown = false;
    private boolean isMoving = false;
    protected int cooldown;
    protected int cost;

    public BaseHero(int attack, int defense, int hp, int speed, int cost, int cooldown, String name, double range, String imageUrl, int attackCooldown, int attackAnimationTime, int deadAnimationTime) {
        super(attack, defense, hp, speed, name, range, imageUrl, attackCooldown, attackAnimationTime, deadAnimationTime);
        setCost(cost);
        setCooldown(cooldown);
    }

    public abstract BaseHero clone();

    public void initializeHeroLogic() {
        heroLogic = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            List<BaseEnemy> enemyList = new ArrayList<>(GameController.getInstance().getEnemies());
            switch (getState()) {
                case RUNNING:
                    if (!isMoving) {
                        move();
                        setImageView(getName() + "/run.gif");
                        isMoving = true;
                    }
                    for (BaseEnemy enemy : enemyList) {
                        if (GameUtils.inRange(this, enemy)) {
                            setState(UnitState.ATTACKING);
                            break;
                        }
                    }
                    break;
                case ATTACKING:
                    stopMoving();
                    isMoving = false;
                    setImageView(getName() + "/attack.gif");
                    attack();
                    isCooldown = true;
                    toIdle();
                    setState(UnitState.IDLE);
                    break;
                case IDLE:
                    if (!isCooldown) {
                        setState(UnitState.RUNNING);
                    }
                    break;
                case DEAD:
                    heroDestroyed();
                    stopMoving();
                    stopHeroLogic();
                    break;
            }
        }));
        heroLogic.setCycleCount(Timeline.INDEFINITE);
    }

    public void playHeroLogic() {
        if (heroLogic != null) {
            heroLogic.play();
        }
    }

    public void stopHeroLogic() {
        if (heroLogic != null) {
            heroLogic.stop();
        }
    }

    public void initializeHeroMove() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), getImageView());
        translateTransition.setByX(getSpeed());
        heroMove = new Timeline(new KeyFrame(Duration.millis(100), e -> translateTransition.playFromStart()));
        heroMove.setCycleCount(Timeline.INDEFINITE);
    }

    public void move() {
        if (heroMove != null) {
            heroMove.play();
        }
    }

    public void stopMoving() {
        if (heroMove != null) {
            heroMove.stop();
        }
    }

    private void attack() {
        if (this.getClass().getInterfaces().length > 0 && this.getClass().getInterfaces()[0] == SpecialEffect.class) {
            ((SpecialEffect) this).showEffect(this);
        }
        Timeline attackAnimationPlay = new Timeline(new KeyFrame(Duration.millis(getAttackAnimationTime()), e -> {
            if (getState() != UnitState.DEAD) {
                for (BaseEnemy enemy : GameController.getInstance().getEnemies()) {
                    if (GameUtils.inRange(this, enemy)) {
                        int damage = getAttack() - enemy.getDefense();
                        enemy.setHp(enemy.getHp() - damage);
                        if (enemy.getHp() <= 0) {
                            enemy.setState(UnitState.DEAD);
                        }
                    }
                }
            }
        }));
        attackAnimationPlay.play();
    }

    private void setCooldown() {
        Timeline cooldown = new Timeline(new KeyFrame(Duration.millis(getAttackCooldown()), e -> {
            isCooldown = false;
        }));
        cooldown.play();
    }

    private void toIdle() {
        Timeline toIdle = new Timeline(new KeyFrame(Duration.millis(getAttackAnimationTime()), e -> {
            if (getState() != UnitState.DEAD) {
                setImageView(getName() + "/idle.gif");
                setCooldown();
            }
        }));
        toIdle.play();
    }

    private void heroDestroyed() {
        GameController.getInstance().getHeroes().remove(this);
        if (!getName().equals("HeroTower")) {
            getImageView().setImage(GameUtils.setImageByPath(getName() + "/dead.gif"));
            Timeline delete = new Timeline(new KeyFrame(Duration.millis(getDeadAnimationTime()), e -> {
                GameController.getInstance().getGameMap().getChildren().remove(getImageView());
            }));
            delete.play();
        }
    }


    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
