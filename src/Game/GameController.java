package Game;

import Map.GameGui;
import Map.GameMap;
import Unit.Enemy.*;
import Unit.Hero.BaseHero;
import Unit.Hero.HeroTower;
import Utils.UnitState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    public static GameController instance;
    private GameGui gameGui;
    private final GameMap gameMap;
    private final ArrayList<BaseEnemy> enemies = new ArrayList<>();
    private final ArrayList<BaseHero> heroes = new ArrayList<>();
    public static HeroTower heroTower;
    public static EnemyTower enemyTower;
    private Timeline enemySpawn;
    private Timeline gameOver;

    private int money;
    private int income;
    private AudioClip bgm;

    public GameController() {
        gameGui = new GameGui();
        gameMap = gameGui.getGameMap();
        heroTower = gameMap.createHeroTower();
        enemyTower = gameMap.createEnemyTower();
        heroes.add(heroTower);
        enemies.add(enemyTower);
        setMoney(0);
        setIncome(50);
        startMoneySpawn();
        startEnemySpawn();
        checkGameOver();
        setUpBgm();
    }

    private void setUpBgm() {
        bgm = new AudioClip(ClassLoader.getSystemResource("bgm.mp3").toString());
        bgm.setVolume(0.5);
        bgm.setCycleCount(AudioClip.INDEFINITE);
        bgm.play();
    }

    private void stopBgm() {
        bgm.stop();
    }

    private void startMoneySpawn() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            if (heroTower.getHp() > 0 && enemyTower.getHp() > 0) {
                increaseMoney(getIncome());
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public GameGui getGameGui() {
        return gameGui;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    private void startEnemySpawn() {
        enemySpawn = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            ArrayList<BaseEnemy> allEnemies = new ArrayList<>();
            allEnemies.add(new NightWar());
            allEnemies.add(new FishEye());
            allEnemies.add(new Kobold());
            allEnemies.add(new Rat());
            allEnemies.add(new Soulyer());

            double[] probabilities = {0.1, 0.25, 0.25, 0.25, 0.15};

            // Generate a random number between 0 and 1
            double rand = Math.random();
            double cumulativeProbability = 0.0;

            // Find the enemy corresponding to the generated random number
            BaseEnemy selectedEnemy = null;
            for (int i = 0; i < probabilities.length; i++) {
                cumulativeProbability += probabilities[i];
                if (rand <= cumulativeProbability) {
                    selectedEnemy = allEnemies.get(i);
                    break;
                }
            }

            // Spawn the selected enemy
            spawn(selectedEnemy);
        }));
        enemySpawn.setCycleCount(Timeline.INDEFINITE);
        enemySpawn.play();
    }


    private void checkGameOver() {
        Text heroTowerHP = new Text(heroTower.getHp() + "/" + heroTower.getMaxHp());
        Text enemyTowerHP = new Text(enemyTower.getHp() + "/" + enemyTower.getMaxHp());
        heroTowerHP.setTranslateX(80);
        heroTowerHP.setTranslateY(-120);
        heroTowerHP.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        heroTowerHP.setFill(Color.FLORALWHITE);
        enemyTowerHP.setTranslateX(1780);
        enemyTowerHP.setTranslateY(-120);
        enemyTowerHP.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        enemyTowerHP.setFill(Color.FLORALWHITE);
        getGameMap().getChildren().add(heroTowerHP);
        getGameMap().getChildren().add(enemyTowerHP);
        gameOver = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            heroTowerHP.setText(heroTower.getHp() + "/" + heroTower.getMaxHp());
            enemyTowerHP.setText(enemyTower.getHp() + "/" + enemyTower.getMaxHp());
            if (heroTower.getHp() <= 0 || enemyTower.getHp() <= 0) {
                if (enemySpawn != null) enemySpawn.stop();
                for (BaseEnemy enemy : enemies) {
                    if (enemy.equals(enemyTower)) continue;
                    if (enemyTower.getHp() <= 0) {
                        enemy.setState(UnitState.DEAD);
                        continue;
                    }
                    enemy.stopEnemyLogic();
                    enemy.stopMoving();
                    enemy.getImageView().setImage(new Image(enemy.getName() + "/idle.gif"));
                }
                for (BaseHero hero : heroes) {
                    if (hero.equals(heroTower)) continue;
                    if (heroTower.getHp() <= 0) {
                        hero.setState(UnitState.DEAD);
                        continue;
                    }
                    hero.stopHeroLogic();
                    hero.stopMoving();
                    hero.getImageView().setImage(new Image(hero.getName() + "/idle.gif"));
                }
                if (heroTower.getHp() <= 0)
                    heroTower.getImageView().setImage(new Image(heroTower.getName() + "/dead.gif"));
                else {
                    enemyTower.getImageView().setImage(new Image(enemyTower.getName() + "/dead.gif"));
                }
                Text gameOverText = new Text("Game Over");
                gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 36));
                gameOverText.setFill(Color.DARKCYAN);
                gameOverText.setStrokeWidth(1.5);

                Text gameOverText2 = new Text(heroTower.getHp() <= 0 ? "You Lose" : "You Win");
                gameOverText2.setFont(Font.font("Arial", FontWeight.BOLD, 36));
                gameOverText2.setFill(heroTower.getHp() <= 0 ? Color.RED : Color.GREEN);
                gameOverText2.setStrokeWidth(1.5);

                MenuItem newGameButton = new MenuItem("New Game",() -> {
                    getInstance().reset();
                    Menu.getInstance().startNewGame();
                });
                gameOverText.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
                gameOverText2.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

                Rectangle background = new Rectangle(400, 200);
                background.setFill(Color.LIGHTGRAY);
                background.setStroke(Color.DARKGRAY);
                background.setStrokeWidth(2);
                background.setArcWidth(20);
                background.setArcHeight(20);

                VBox gameOverBox = new VBox(20);
                gameOverBox.setAlignment(Pos.CENTER);
                gameOverBox.getChildren().addAll(gameOverText, gameOverText2, newGameButton);
                DropShadow dropShadow = new DropShadow();
                dropShadow.setRadius(5); // Adjust the radius to make the shadow softer
                dropShadow.setColor(Color.rgb(0, 0, 0, 0.5));
                gameOverBox.setEffect(dropShadow);
                StackPane gameOverPane = new StackPane();
                gameOverPane.setMaxWidth(400);
                gameOverPane.setMaxHeight(200);
                gameOverPane.getChildren().addAll(background, gameOverBox);

                gameGui.getChildren().add(gameOverPane);
                stopBgm();
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
        hero.getImageView().setTranslateX(100);
        hero.initializeHeroLogic();
        hero.initializeHeroMove();
        hero.playHeroLogic();
        heroes.add(hero);
    }

    public void spawn(BaseEnemy enemy) {
        gameMap.getChildren().add(enemy.getImageView());
        enemy.getImageView().setScaleX(-1);
        enemy.getImageView().setTranslateX(1700);
        enemy.initializeEnemyLogic();
        enemy.initializeEnemyMove();
        enemy.playEnemyLogic();
        enemies.add(enemy);
    }

    public ArrayList<BaseEnemy> getEnemies() {
        return enemies;
    }

    public ArrayList<BaseHero> getHeroes() {
        return heroes;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        gameGui.setPlayerMoney(money);
    }

    public void decreaseMoney(int money) {
        this.setMoney(Math.max(0, getMoney() - money));
    }

    public void increaseMoney(int money) {
        setMoney(getMoney() + money);
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public void reset() {
        instance = new GameController();
    }

}