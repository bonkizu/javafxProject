package Unit;

import Utils.UnitState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class BaseUnit {
    private int attack;
    private int defense;
    private int hp;
    private int speed;
    private int attackSpeed;
    private String name;
    private double range;
    private ImageView imageView;
    private UnitState state;

    public BaseUnit(int attack, int defense, int hp, int speed, int attackSpeed, String name, double range, String imageUrl) {
        setAttack(attack);
        setDefense(defense);
        setHp(hp);
        setSpeed(speed);
        setAttackSpeed(attackSpeed);
        setName(name);
        setRange(range);
        setImageView(imageUrl);
        state = UnitState.MOVING;
        getImageView().setFitWidth(200);
        getImageView().setPreserveRatio(true);
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
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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
        this.imageView = new ImageView(new Image(imageUrl));
    }

    public UnitState getState() {
        return state;
    }

    public void setState(UnitState state) {
        this.state = state;
    }
}
