package Utils;

import Unit.BaseUnit;

public class GameUtils {
    public static boolean inRange(BaseUnit unit1, BaseUnit unit2) {
        double x1 = unit1.getImageView().getTranslateX() + unit1.getImageView().getFitWidth() / 2;
        double x2 = unit2.getImageView().getTranslateX() + unit2.getImageView().getFitWidth() / 2;
        return Math.abs(x1 - x2) <= unit1.getRange() * 100;
    }
}
