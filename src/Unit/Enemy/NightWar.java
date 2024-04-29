package Unit.Enemy;

public class NightWar extends BaseEnemy {
    public NightWar() {
        super(500, 100, 1000, 50, 1, 0, "NightWar", 1.5, "NightWar/idle.gif", 1000, 2200);
        getImageView().setFitWidth(350);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(107);
    }
}
