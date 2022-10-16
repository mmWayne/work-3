/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fianlProject;

import static java.lang.System.out;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class final_project_Game extends Application {

    int count = 0, bossCount = 0;
    Pane paneBegin, paneInGame;
    Circle player, bullet, b2, enemy, enemyBoss;
    Line l1, l2;
    PathTransition pt, ptEB;
    Stage primaryStage;
    private Timeline PlayerAnimation, EnemyAnimation, EnemyShoot, EnemyCreate;
    double enemyX = 15 + Math.random() * 185;
    double enemyY = 15 + Math.random() * 100;
    double enemyR = 15;
    private double dx = 1, dy = 1;
    ArrayList<Circle> array;
    ImageView imageView1, imageView2, imageView3, win, lose;

    /*java.io.File f1 = new java.io.File("bgm.mp3");
    private final String MEDIA_URL1
            = f1.toURI().toString();
    Media media1 = new Media(MEDIA_URL1);
    MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
    MediaView mediaView1 = new MediaView(mediaPlayer1);

    java.io.File f2 = new java.io.File("win.mp3");
    private final String MEDIA_URL2
            = f2.toURI().toString();
    Media media2 = new Media(MEDIA_URL2);
    MediaPlayer mediaPlayer2 = new MediaPlayer(media2);
    MediaView mediaView2 = new MediaView(mediaPlayer2);

    java.io.File f3 = new java.io.File("lose.mp3");
    private final String MEDIA_URL3
            = f3.toURI().toString();
    Media media3 = new Media(MEDIA_URL3);
    MediaPlayer mediaPlayer3 = new MediaPlayer(media3);
    MediaView mediaView3 = new MediaView(mediaPlayer3);*/

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create a pane and set its properties
        paneBegin = new Pane();
        paneInGame = new Pane();
        begining();
        Scene scene = new Scene(paneBegin, 200, 200);
        primaryStage.setTitle("Final_Project"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.setResizable(false);
        primaryStage.show(); // Display the stage

    }

    void begining() {//開始畫面、操作說明、介面設定
        Button btStart = new Button("START");
        btStart.setLayoutX(78);
        btStart.setLayoutY(80);
        Button Hint = new Button(" HINT ");
        Hint.setLayoutX(78);
        Hint.setLayoutY(120);
        paneBegin.getChildren().addAll(btStart, Hint);
        Hint.setOnAction(e -> {
            Alert info = new Alert(AlertType.INFORMATION);
            info.setTitle("遊戲玩法");
            info.setContentText("按→←鍵移動，按↑鍵射擊，摧毀敵方戰機即獲勝!!");
            info.show();
        });
        btStart.setOnAction(e -> {
           // MediaView mediaView = new MediaView(mediaPlayer1);
           // mediaPlayer1.play();
           // mediaPlayer1.setVolume(10);
            imageView3 = new ImageView("image/sky.png");
            paneInGame.getChildren().add(imageView3);
            createPlayer();
            enemy();
            Scene scene = new Scene(paneInGame, 300, 600);
            Stage gaming = new Stage();
            gaming.setTitle("Final_Project"); // Set the stage title
            gaming.setScene(scene); // Place the scene in the stage
            gaming.setResizable(false);
            gaming.show();
            player.requestFocus();
        });
    }

    void createPlayer() {//產生我方
        imageView1 = new ImageView("image/me.png");
        paneInGame.getChildren().add(imageView1);
        player = new Circle(150, 590, 10);
        player.setFill(Color.TRANSPARENT);
        paneInGame.getChildren().add(player);
        imageView1.setX(player.getCenterX() - 25);
        imageView1.setY(player.getCenterY() - 30);
        move();
    }

    void enemy() {//產生敵人並使敵人移動
        enemy = new Circle(enemyX, enemyY, 15);
        enemy.setFill(Color.TRANSPARENT);
        imageView2 = new ImageView("image/boss.png");
        imageView2.setX(enemy.getCenterX() - 25);
        imageView2.setY(enemy.getCenterY() - 30);
        paneInGame.getChildren().addAll(enemy, imageView2);
        EnemyAnimation = new Timeline(
                new KeyFrame(Duration.millis(10), e -> {
                    moveEnemy();
                }));
        EnemyAnimation.setCycleCount(Timeline.INDEFINITE);
        EnemyAnimation.play();
        EnemyShoot = new Timeline(
                new KeyFrame(Duration.millis(500), e -> shootEnemy()));
        EnemyShoot.setCycleCount(Timeline.INDEFINITE);
        EnemyShoot.play();
    }

    void shoot() {//我方子彈的路徑，向上發射
        bullet = new Circle(player.getCenterX(), player.getCenterY() - 25, 2);
        bullet.setFill(Color.YELLOW);
        paneInGame.getChildren().add(bullet);
        pt = new PathTransition();
        l1 = new Line(player.getCenterX(), player.getCenterY() - 25, player.getCenterX(), -10);
        pt.setPath(l1);
        pt.setDuration(Duration.millis(1000));
        pt.setNode(bullet);
        pt.setAutoReverse(false);
        pt.play();
    }

    void shootEnemy() {//敵方發射子彈的路徑，會依據我方的位置而改變路徑
        Circle bulletE = new Circle(enemy.getCenterX(), enemy.getCenterY() + 25, 2);
        bulletE.setFill(Color.RED);
        ptEB = new PathTransition();
        l2 = new Line(enemy.getCenterX(), enemy.getCenterY() + 25, player.getCenterX(), player.getCenterY() + 450);
        ptEB.setPath(l2);
        ptEB.setDuration(Duration.millis(1000));
        ptEB.setNode(bulletE);
        ptEB.setAutoReverse(false);
        paneInGame.getChildren().add(bulletE);
        ptEB.play();

    }

    void move() {//我方的移動路線，按方向鍵右則向右移，按方向鍵左則向左移，按方向鍵上則發射子彈
        paneInGame.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    if (player.getCenterX() - 20 > 0) {
                        player.setCenterX(player.getCenterX() - 10);
                        imageView1.setX(player.getCenterX() - 25);
                    }
                    break;
                case RIGHT:
                    if (player.getCenterX() + 20 <= 300) {
                        player.setCenterX(player.getCenterX() + 10);
                        imageView1.setX(player.getCenterX() - 25);
                    }
                    break;
                case UP:
                    shoot();
                    if (player.getCenterX() >= enemy.getCenterX() - 5 && player.getCenterX() <= enemy.getCenterX() + 5) {
                        scores();
                    }
            }
        });

    }

    void moveEnemy() {//敵方的移動路線，若碰壁則會彈走且敵方加一分
        // Check boundaries
        if (enemyX < enemyR || enemyX > paneInGame.getWidth() - enemyR) {
            dx *= -1; // Change ball move direction'
            scoreForLose();
        }
        if (enemyY < enemyR || enemyY > paneInGame.getHeight() - 300) {
            dy *= -1;
            if (enemyY == 0) {
                scoreForLose();
            }
        }
        // Adjust ball position
        enemyX += dx;
        enemyY += dy;
        enemy.setCenterX(enemyX);
        enemy.setCenterY(enemyY);
        imageView2.setX(enemy.getCenterX() - 25);
        imageView2.setY(enemy.getCenterY() - 25);

    }

    void scores() {//計算我方的得分次數
        count++;
        if (count == 10) {
            paneInGame.getChildren().clear();
          //  mediaPlayer1.pause();
          //  mediaPlayer2.play();
            EnemyAnimation.pause();
            EnemyShoot.pause();
            win = new ImageView("image/win.png");
            paneInGame.getChildren().add(win);
        }

    }

    void scoreForLose() {//計算敵方的得分次數
        bossCount++;
        System.out.print(bossCount);
        if (bossCount == 10) {
            paneInGame.getChildren().clear();
           // mediaPlayer1.pause();
           // mediaPlayer3.play();
            EnemyShoot.pause();
            lose = new ImageView("image/lose.png");
            paneInGame.getChildren().add(lose);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
