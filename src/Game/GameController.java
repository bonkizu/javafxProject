package Game;

import Map.GameGui;
import Map.GameMap;
import Unit.Enemy.BaseEnemy;
import Unit.Enemy.EnemyTower;
import Unit.Hero.BaseHero;
import Unit.Hero.HeroTower;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

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
        startEnemySpawn();
        checkGameOver();
    }

    private void startMoneySpawn() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            if (heroTower.getHp() > 0 && enemyTower.getHp() > 0) {
                increaseMoney(getIncome());
                System.out.println("Current money: " + getMoney());
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
        enemySpawn = new Timeline(new KeyFrame(Duration.millis(2000), e -> {

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
        hero.getImageView().setTranslateY(200);
        hero.move();
        heroes.add(hero);
    }

    public void spawn(BaseEnemy enemy) {
        gameMap.getChildren().add(enemy.getImageView());
        enemy.getImageView().setTranslateY(200);
        enemy.getImageView().setTranslateX(1700);
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