package Unit;

import Game.GameController;
import Unit.Enemy.BaseEnemy;
import Unit.Type.SpecialEffect;
import Utils.GameUtils;
import Utils.UnitState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseUnit {
    private int attack;
    private int defense;
    private int hp;
    private int speed;
    private int cost;
    private int attackSpeed;
    private String name;
    private double range;
    private ImageView imageView;
    private UnitState state;
    private int attackCooldown;
    private int attackAnimationTime;

    public BaseUnit(int attack, int defense, int hp, int speed, int attackSpeed, int cost, String name, double range, String imageUrl, int attackCooldown, int attackAnimationTime) {
        setAttack(attack);
        setDefense(defense);
        setHp(hp);
        setSpeed(speed);
        setAttackSpeed(attackSpeed);
        setName(name);
        setCost(cost);
        setRange(range);
        setImageView(imageUrl);
        state = UnitState.RUNNING;
        getImageView().setFitWidth(200);
        getImageView().setPreserveRatio(true);
        setAttackCooldown(attackCooldown);
        setAttackAnimationTime(attackAnimationTime);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    protected abstract void setMoving(int speed);
    public abstract void move();
    public abstract void stopMoving();

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHp() {
        return Math.max(0, hp);
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public ImageView getImageView() {
        return imageView;
    }
    public String getImageUrl() {
        return imageView.getImage().getUrl();
    }

    public void setImageView(String imageUrl) {
        if(imageView == null) {
            imageView = new ImageView(new Image(imageUrl));
        } else {
            imageView.setImage(new Image(imageUrl));
        }
    }

    public UnitState getState() {
        return state;
    }

    public void setState(UnitState state) {
        this.state = state;
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public int getAttackAnimationTime() {
        return attackAnimationTime;
    }

    public void setAttackAnimationTime(int attackAnimationTime) {
        this.attackAnimationTime = attackAnimationTime;
    }
}
