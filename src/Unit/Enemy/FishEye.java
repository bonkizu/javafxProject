package Unit.Enemy;

public class FishEye extends BaseEnemy {
    public FishEye() {
        super(100, 100, 300, 50, 1, 0, "FishEye", 1, "FishEye/idle.gif", 100, 800);
        getImageView().setFitWidth(200);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(120);
    }
}
