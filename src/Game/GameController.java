package Game;

import Map.GameGui;
import Map.GameMap;
import Unit.Enemy.*;
import Unit.Hero.BaseHero;
import Unit.Hero.HeroTower;
import Utils.UnitState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
//        startEnemySpawn();
        spawn(new NightWar());
        checkGameOver();
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
        enemySpawn = new Timeline(new KeyFrame(Duration.millis(3000), e -> {
            ArrayList<BaseEnemy> allEnemies = new ArrayList<>();
            allEnemies.add(new NightWar());
            allEnemies.add(new FishEye());
            allEnemies.add(new Kobold());
            allEnemies.add(new Rat());
            allEnemies.add(new Soulyer());

            double[] probabilities = {0.1, 0.20, 0.25, 0.25, 0.2};

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
        Text heroTowerHP = new Text("10000/10000");
        Text enemyTowerHP = new Text("10000/10000");
        heroTowerHP.setTranslateX(80);
        heroTowerHP.setTranslateY(-120);
        heroTowerHP.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        enemyTowerHP.setTranslateX(1780);
        enemyTowerHP.setTranslateY(-120);
        enemyTowerHP.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        getGameMap().getChildren().add(heroTowerHP);
        getGameMap().getChildren().add(enemyTowerHP);
        gameOver = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            heroTowerHP.setText(heroTower.getHp() + "/10000");
            enemyTowerHP.setText(enemyTower.getHp() + "/10000");
            if (heroTower.getHp() <= 0 || enemyTower.getHp() <= 0) {
                System.out.println("Game Over");
                if(enemySpawn != null) {
                    enemySpawn.stop();
                }
                if(gameOver != null) {
                    gameOver.stop();
                }
                for (BaseEnemy enemy : enemies) {
                    if(enemy.checking != null)
                        enemy.checking.stop();
                    if(enemy.moving != null)
                        enemy.moving.stop();
                    if(!enemy.getName().equals("EnemyTower")) {
                        enemy.getImageView().setImage(new Image(enemy.getName() + "/idle.gif"));
                    }
                }
                for (BaseHero hero : heroes) {
                    if(hero.checking != null)
                        hero.checking.stop();
                    if(hero.moving != null)
                        hero.moving.stop();
                    if(!hero.getName().equals("HeroTower")) {
                        hero.getImageView().setImage(new Image(hero.getName() + "/idle.gif"));
                    }
                }
                Text gameOverText = new Text("Game Over");
                gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 36));
                gameOverText.setFill(Color.RED);
                gameOverText.setStrokeWidth(1.5);

                Text gameOverText2 = new Text(heroTower.getHp() <= 0 ? "You Lose" : "You Win");
                gameOverText2.setFont(Font.font("Arial", FontWeight.BOLD, 36));
                gameOverText2.setFill(heroTower.getHp() <= 0 ? Color.RED : Color.GREEN);
                gameOverText2.setStrokeWidth(1.5);

                Button newGameButton = new Button("New Game");
                newGameButton.setOnAction(event -> Menu.getInstance().startNewGame());

                gameOverText.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
                gameOverText2.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
                newGameButton.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

                VBox gameOverBox = new VBox(20);
                gameOverBox.setAlignment(Pos.CENTER);
                gameOverBox.getChildren().addAll(gameOverText, gameOverText2, newGameButton);

                gameGui.getChildren().add(gameOverBox);
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
        hero.initialize();
        hero.move();
        heroes.add(hero);
    }

    public void spawn(BaseEnemy enemy) {
        gameMap.getChildren().add(enemy.getImageView());
        enemy.getImageView().setScaleX(-1);
        enemy.getImageView().setTranslateX(1800);
        enemy.initialize();
        enemy.move();
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