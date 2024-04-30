package Unit.Enemy;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Hero.BaseHero;
import Unit.Type.Attackable;
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

public abstract class BaseEnemy extends BaseUnit {
    public Timeline checking;
    public Timeline moving;
    private boolean init = false;
    public BaseEnemy(int attack, int defense, int hp, int speed, String name, double range, String imageUrl, int attackCooldown, int attackAnimationTime, int deadAnimationTime) {
        super(attack, defense, hp, speed,  name, range, imageUrl, attackCooldown, attackAnimationTime, deadAnimationTime);
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
                List<BaseHero> heroesCopy = new ArrayList<>(GameController.getInstance().getHeroes());
                for(BaseHero hero : heroesCopy) {
                    if(GameUtils.inRange(this, hero)) {
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
                List<BaseHero> heroesCopy = new ArrayList<>(GameController.getInstance().getHeroes());
                for(BaseHero hero : heroesCopy) {
                    if(GameUtils.inRange(this, hero)) {
                        Timeline attackA = new Timeline(new KeyFrame(Duration.millis(getAttackAnimationTime()), e -> {
                            if(getHp() > 0) {
                                attack(hero);
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
                    List<BaseHero> heroesCopy = new ArrayList<>(GameController.getInstance().getHeroes());
                    for(BaseHero hero : heroesCopy) {
                        if(GameUtils.inRange(this, hero)) {
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

    private void attack(BaseHero hero) {
        int damage = getAttack() - hero.getDefense();
        if(damage > 0) {
            hero.setHp(hero.getHp() - damage);
            if(hero.getHp() <= 0) {
                hero.destroyed();
            }
        }
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

    public void destroyed() {
        if (!getState().equals(UnitState.DEAD)) {
            if (checking != null) {
                checking.stop();
            }
            if (moving != null) {
                moving.stop();
            }
            GameController.getInstance().getEnemies().remove(this);
            if (!getName().equals("EnemyTower")) {
                getImageView().setImage(new Image(getName() + "/dead.gif"));
                Timeline delete = new Timeline(new KeyFrame(Duration.millis(getDeadAnimationTime()), e -> {
                    GameController.getInstance().getGameMap().getChildren().remove(getImageView());
                }));
                delete.play();
            }
        }
    }
}
