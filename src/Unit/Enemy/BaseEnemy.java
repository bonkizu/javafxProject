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
    private Timeline enemyLogic;
    private Timeline enemyMove;
    private boolean isCooldown = false;
    private boolean isMoving = false;
    public BaseEnemy(int attack, int defense, int hp, int speed, String name, double range, String imageUrl, int attackCooldown, int attackAnimationTime, int deadAnimationTime) {
        super(attack, defense, hp, speed,  name, range, imageUrl, attackCooldown, attackAnimationTime, deadAnimationTime);
    }

    public void initializeEnemyLogic() {
        enemyLogic = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            List<BaseHero> heroList = new ArrayList<>(GameController.getInstance().getHeroes());
            switch (getState()) {
                case RUNNING:
                    if(!isMoving) {
                        move();
                        setImageView(getName() + "/run.gif");
                        isMoving = true;
                    }
                    for(BaseHero hero : heroList) {
                        if (GameUtils.inRange(this, hero)) {
                            setState(UnitState.ATTACKING);
                            break;
                        }
                    }
                    break;
                case ATTACKING:
                    stopMoving();
                    isMoving = false;
                    setImageView(getName() + "/attack.gif");
                    for(BaseHero hero : heroList) {
                        if (GameUtils.inRange(this, hero)) {
                            attack(hero);
                            break;
                        }
                    }
                    isCooldown = true;
                    toIdle();
                    setState(UnitState.IDLE);
                    break;
                case IDLE:
                    if(!isCooldown) {
                        setState(UnitState.RUNNING);
                    }
                    break;
                case DEAD:
                    enemyDestroyed();
                    stopMoving();
                    stopEnemyLogic();
                    break;
            }
        }));
        enemyLogic.setCycleCount(Timeline.INDEFINITE);
    }

    public void playEnemyLogic() {
        enemyLogic.play();
    }

    public void stopEnemyLogic() {
        enemyLogic.stop();
    }

    public void initializeEnemyMove() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), getImageView());
        translateTransition.setByX(-getSpeed());
        enemyMove = new Timeline(new KeyFrame(Duration.millis(100), e -> translateTransition.playFromStart()));
        enemyMove.setCycleCount(Timeline.INDEFINITE);
    }

    private void move() {
        enemyMove.play();
    }

    private void stopMoving() {
        enemyMove.stop();
    }

    private void attack(BaseHero hero) {
        if(this.getClass().getInterfaces().length > 0 && this.getClass().getInterfaces()[0] == SpecialEffect.class) {
            ((SpecialEffect) this).showEffect(this);
        }
        int damage = getAttack() - hero.getDefense();
        Timeline attackAnimationPlay = new Timeline(new KeyFrame(Duration.millis(getAttackAnimationTime()), e -> {
            if(getState() != UnitState.DEAD) {
                hero.setHp(hero.getHp() - damage);
                System.out.println("hero hp " + hero.getHp());
                if (hero.getHp() <= 0) {
                    hero.setState(UnitState.DEAD);
                }
            }
        }));
        attackAnimationPlay.play();
    }

    private void enemyDestroyed() {
        GameController.getInstance().getEnemies().remove(this);
        if (!getName().equals("EnemyTower")) {
            getImageView().setImage(new Image(getName() + "/dead.gif"));
            Timeline delete = new Timeline(new KeyFrame(Duration.millis(getDeadAnimationTime()), e -> {
                GameController.getInstance().getGameMap().getChildren().remove(getImageView());
            }));
            delete.play();
        }
    }

    private void setCooldown() {
        Timeline cooldown = new Timeline(new KeyFrame(Duration.millis(getAttackCooldown()), e -> {
            isCooldown = false;
        }));
        cooldown.play();
    }

    private void toIdle() {
        Timeline toIdle = new Timeline(new KeyFrame(Duration.millis(getAttackAnimationTime()), e -> {
            if(getState() != UnitState.DEAD) {
                setImageView(getName() + "/idle.gif");
                setCooldown();
            }
        }));
        toIdle.play();
    }
}
