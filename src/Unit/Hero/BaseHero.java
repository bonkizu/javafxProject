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
    public Timeline checking;
    public Timeline moving;
    protected int cooldown;
    protected int cost;
    private boolean init = false;
    public BaseHero(int attack, int defense, int hp, int speed, int cost, int cooldown, String name, double range, String imageUrl, int attackCooldown, int attackAnimationTime, int deadAnimationTime) {
        super(attack, defense, hp, speed,  name, range, imageUrl, attackCooldown, attackAnimationTime, deadAnimationTime);
        setCost(cost);
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
                                System.out.println(enemy.getName() + " " + enemy.getHp());
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
        if(!getState().equals(UnitState.DEAD)){
            setState(UnitState.DEAD);
            if(checking != null) {
                checking.stop();
            }
            if(moving != null) {
                moving.stop();
            }
            GameController.getInstance().getHeroes().remove(this);
            if(!getName().equals("HeroTower")) {
                getImageView().setImage(new Image(getName() + "/dead.gif"));
                Timeline delete = new Timeline(new KeyFrame(Duration.millis(getDeadAnimationTime()), e -> {
                    GameController.getInstance().getGameMap().getChildren().remove(getImageView());
                }));
                delete.play();
            }
        }
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


}
