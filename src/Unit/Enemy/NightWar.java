package Unit.Enemy;

public class NightWar extends BaseEnemy {
    public NightWar() {
        super(0, 10000, 10000, 50, 1, 0, "NightWar", 10, "NightWar/idle.gif", 100, 2200);
        getImageView().setFitWidth(350);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(107);
    }
}
