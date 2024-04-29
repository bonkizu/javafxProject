package Unit.Enemy;

public class Rat extends BaseEnemy {
    public Rat() {
        super(10000, 100, 10000, 50, 1, 0, "Rat", 1, "Rat/idle.gif", 100, 1600);
        getImageView().setFitWidth(250);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(145);
    }
}
