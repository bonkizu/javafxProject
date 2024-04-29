package Unit.Enemy;

public class Soulyer extends BaseEnemy {

    public Soulyer() {
        super(0, 100000, 10000, 50, 1, 0, "Soulyer", 1, "Soulyer/idle.gif", 1000, 1000);
        getImageView().setFitWidth(200);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(109);
    }
}
