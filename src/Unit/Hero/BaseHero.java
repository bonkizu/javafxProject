package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Enemy.BaseEnemy;
import Unit.Type.Attackable;
import Unit.Type.SpecialEffect;
import Utils.GameUtils;
import Utils.UnitState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseHero extends BaseUnit {
    public Timeline checking;
    public Timeline moving;
    protected int cooldown;
    private boolean init = false;
    public BaseHero(int attack, int defense, int hp, int speed, int attackSpeed, int cost, int cooldown, String name, double range, String imageUrl, int attackCooldown, int attackAnimationTime) {
        super(attack, defense, hp, speed, attackSpeed, cost,  name, range, imageUrl, attackCooldown, attackAnimationTime);
        setCooldown(cooldown);
        setMoving(getSpeed());
    }

    public void initialize() {
        setMoving(getSpeed());
        checking = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if(getState() == UnitState.RUNNING) {
                if(!init) {
                    setImageView(getName() + "/run.gif");
                    move();
                    init = true;
                }
                List<BaseEnemy> enemiesCopy = new ArrayList<>(GameController.getInstance().getEnemies());
                for(BaseEnemy enemy : enemiesCopy) {
                    if(GameUtils.inRange(this, enemy)) {
                        stopMoving();
                        init = false;
                        setState(UnitState.ATTACKING);
                        break;
                    }
                }
            }
            if(getState() == UnitState.ATTACKING && !init) {
                stopMoving();
                setImageView(getName() + "/attack.gif");
                if(this.getClass().getInterfaces().length > 0 && this.getClass().getInterfaces()[0] == SpecialEffect.class) {
                    ((SpecialEffect) this).showEffect(this);
                }
                Timeline delay = new Timeline(new KeyFrame(Duration.millis(getAttackAnimationTime()), e-> {
                    init = false;
                    setState(UnitState.IDLE);
                }));
                delay.play();
                List<BaseEnemy> enemiesCopy = new ArrayList<>(GameController.getInstance().getEnemies());
                for(BaseEnemy enemy : enemiesCopy) {
                    if(GameUtils.inRange(this, enemy)) {
                        Timeline attackA = new Timeline(new KeyFrame(Duration.millis(getAttackAnimationTime()), e -> {
                            if(getHp() > 0) {
                                attack(enemy);
                            }
                        }));
                        attackA.play();
                    }
                }
                init = true;
            }
            if(getState() == UnitState.IDLE && !init) {
                setImageView(getName() + "/idle.gif");
                Timeline delay = new Timeline(new KeyFrame(Duration.millis(getAttackCooldown()), e -> {
                    boolean found = false;
                    List<BaseEnemy> enemiesCopy = new ArrayList<>(GameController.getInstance().getEnemies());
                    for(BaseEnemy enemy : enemiesCopy) {
                        if(GameUtils.inRange(this, enemy)) {
                            init = false;
                            setState(UnitState.ATTACKING);
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        init = false;
                        setState(UnitState.RUNNING);
                    }
                }));
                delay.play();
                init = true;
            }
        }));
        checking.setCycleCount(Timeline.INDEFINITE);
        checking.play();
    }

    private void attack(BaseEnemy enemy) {
        int damage = getAttack() - enemy.getDefense();
        if(damage > 0) {
            enemy.setHp(enemy.getHp() - damage);
            System.out.println(enemy.getHp());
            if(enemy.getHp() <= 0) {
                enemy.destroyed();
            }
        }
    }

    public abstract BaseHero clone();

    @Override
    protected void setMoving(int speed) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), getImageView());
        translateTransition.setByX(speed);
        moving = new Timeline(new KeyFrame(Duration.millis(100), e -> translateTransition.playFromStart()));
        moving.setCycleCount(Timeline.INDEFINITE);
    }

    public void destroyed() {
        if(checking != null) {
            checking.stop();
        }
        if(moving != null) {
            moving.stop();
        }
        setState(UnitState.DEAD);
        GameController.getInstance().getHeroes().remove(this);
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
