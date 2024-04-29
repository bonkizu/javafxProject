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
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEnemy extends BaseUnit {
    protected Timeline checking;
    protected Timeline moving;
    private boolean init = false;
    public BaseEnemy(int attack, int defense, int hp, int speed, int attackSpeed, int cost, String name, double range, String imageUrl, int attackCooldown, int attackAnimationTime) {
        super(attack, defense, hp, speed, attackSpeed, cost,  name, range, imageUrl, attackCooldown, attackAnimationTime);
        setMoving(getSpeed());
    }

    public void initialize() {
        setMoving(getSpeed());
        checking = new Timeline(new KeyFrame(Duration.millis(100), event -> {
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
                        attack(hero);
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
        System.out.println("Enemy " + this.getName() + " attack to " + hero.getName());
        if(damage > 0) {
            hero.setHp(hero.getHp() - damage);
            //check damage from enemy
            System.out.println("Hero " + hero.getName() + " get attacked " + hero.getHp());
            if(hero.getHp() <= 0) {
                hero.destroyed();
                //check hero dead
                System.out.println("Hero " + hero.getName() + " is dead");
            }
        }
        else {
            System.out.println("No Damage");
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
        checking.stop();
        moving.stop();
        setState(UnitState.DEAD);
        GameController.getInstance().getEnemies().remove(this);
    }
}
