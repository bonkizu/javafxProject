package Unit.Enemy;

public class Rat extends BaseEnemy {
    public Rat() {
        super(200, 200, 200, 50, 1, 0, "Rat", 1, "Rat/idle.gif", 500, 1600);
        getImageView().setFitWidth(250);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(145);
    }
}
