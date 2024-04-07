package Game;

import Map.GameGui;
import Map.GameMap;
import Unit.Enemy.BaseEnemy;
import Unit.Enemy.EnemyTower;
import Unit.Enemy.Wg;
import Unit.Hero.BaseHero;
import Unit.Hero.HeroTower;
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
    public static HeroTower heroTower;
    public static EnemyTower enemyTower;
    private Timeline enemySpawn;
    private Timeline gameOver;

    public GameController() {
        gameGui = new GameGui();
        gameMap = gameGui.getGameMap();
        heroTower = gameMap.createHeroTower();
        enemyTower = gameMap.createEnemyTower();
        heroes.add(heroTower);
        enemies.add(enemyTower);
        startEnemySpawn();
        checkGameOver();
    }

    public GameGui getGameGui() {
        return gameGui;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    private void startEnemySpawn() {
        enemySpawn = new Timeline(new KeyFrame(Duration.millis(2000), e -> {
            spawn(new Wg());
        }));
        enemySpawn.setCycleCount(Timeline.INDEFINITE);
        enemySpawn.play();
    }


    private void checkGameOver() {
        gameOver = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            if (heroTower.getHp() <= 0 || enemyTower.getHp() <= 0) {
                System.out.println("Game Over");
                enemySpawn.stop();
                gameOver.stop();
            }
        }));
        gameOver.setCycleCount(Timeline.INDEFINITE);
        gameOver.play();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void spawn(BaseHero hero) {
        gameMap.getChildren().add(hero.getImageView());
        hero.getImageView().setTranslateY(200);
        hero.initializeChecking();
        hero.move();
        heroes.add(hero);
    }

    public void spawn(BaseEnemy enemy) {
        gameMap.getChildren().add(enemy.getImageView());
        enemy.getImageView().setTranslateY(200);
        enemy.getImageView().setTranslateX(1700);
        enemy.initializeChecking();
        enemy.move();
        enemies.add(enemy);
    }

    public ArrayList<BaseEnemy> getEnemies() {
        return enemies;
    }

    public ArrayList<BaseHero> getHeroes() {
        return heroes;
    }
}
