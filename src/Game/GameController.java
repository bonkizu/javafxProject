package Game;

import Map.GameGui;
import Map.GameMap;
import Unit.Enemy.BaseEnemy;
import Unit.Enemy.Wg;
import Unit.Hero.BaseHero;
import Unit.Hero.Padoru;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameController {
    public static GameController instance;
    private final GameGui gameGui;
    private final GameMap gameMap;
    private final ArrayList<BaseEnemy> enemies = new ArrayList<>();
    private final ArrayList<BaseHero> heroes = new ArrayList<>();
    public static BaseHero selectedHero;
    public static BaseEnemy selectedEnemy;
    private Timeline checkingHeroAndEnemy;

    public GameController() {
        gameGui = new GameGui();
        gameMap = gameGui.getGameMap();
        heroes.add(gameMap.createHeroTower());
        enemies.add(gameMap.createEnemyTower());
        selectedHero = heroes.get(0);
        selectedEnemy = enemies.get(0);
        startEnemySpawn();
        checkHeroAndEnemy();
    }

    private void checkHeroAndEnemy() {
        Duration duration = Duration.millis(100); // Set the duration to 100 milliseconds
        Timeline timelineHero = new Timeline(new KeyFrame(duration, event -> {
            for (BaseHero hero : heroes) {
                if (selectedHero.getImageView().getTranslateX() - hero.getImageView().getTranslateX() < 0) {
                    selectedHero = hero;
                }
            }
        }));
        timelineHero.setCycleCount(Timeline.INDEFINITE);
        timelineHero.play();

        Timeline timelineEnemy = new Timeline(new KeyFrame(duration, event -> {
            for (BaseEnemy enemy : enemies) {
                if (selectedEnemy.getImageView().getTranslateX() - enemy.getImageView().getTranslateX() > 0) {
                    selectedEnemy = enemy;
                }
            }
        }));
        timelineEnemy.setCycleCount(Timeline.INDEFINITE);
        timelineEnemy.play();
    }

    public GameGui getGameGui() {
        return gameGui;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    private void startEnemySpawn() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            spawn(new Wg());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void spawn(BaseHero hero) {
        gameMap.getChildren().add(hero.getImageView());
        hero.getImageView().setTranslateY(400);
        heroes.add(hero);
    }

    public void spawn(BaseEnemy enemy) {
        gameMap.getChildren().add(enemy.getImageView());
        enemy.getImageView().setTranslateY(400);
        enemy.getImageView().setTranslateX(1700);
        enemies.add(enemy);
    }

    public ArrayList<BaseEnemy> getEnemies() {
        return enemies;
    }

    public ArrayList<BaseHero> getHeroes() {
        return heroes;
    }
}
