package Unit.Enemy;

public class Soulyer extends BaseEnemy {

    public Soulyer() {
        super(100, 50, 100, 50, 1, 0, "Soulyer", 1, "Soulyer/idle.gif", 100, 1000);
        getImageView().setFitWidth(200);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(109);
    }
}
