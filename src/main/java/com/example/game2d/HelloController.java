package com.example.game2d;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label pause;
    @FXML
    private Label lose, count;

    private int counts;
    private TranslateTransition anemyTransition, anemyTwoTransition;

    @FXML
    private ImageView player, bg2, bg1, anemy, anemyTwo;
    @FXML
    private Button buttonStart;
    private ParallelTransition parallelTransition;

    public static boolean right = false;
    public static boolean left = false;
    public static boolean jump = false;
    public static boolean isPause = false;
    public static boolean isLose = false;
    public static boolean start = false;
    public static boolean check = true;

    private int playerSpeed = 3, jumpDownSpeed = 4;


    private final AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long l) {

            if (jump && player.getLayoutY() > 130f) {
                player.setLayoutY(player.getLayoutY() - jumpDownSpeed);
            } else if (player.getLayoutY() <= 230f) {
                jump = false;
                player.setLayoutY(player.getLayoutY() + jumpDownSpeed);

            }

            if (right) {
                player.setLayoutX(player.getLayoutX() + playerSpeed);
            }
            if (left) {
                player.setLayoutX(player.getLayoutX() - playerSpeed);
            }
            if (isPause && !pause.isVisible()) {

                playerSpeed = 0;
                jumpDownSpeed = 0;
                parallelTransition.pause();
                anemyTransition.pause();
                anemyTwoTransition.pause();
                pause.setVisible(true);

            } else if (!isPause && pause.isVisible()) {
                pause.setVisible(false);
                playerSpeed = 3;
                jumpDownSpeed = 5;
                parallelTransition.play();
                anemyTransition.play();
                anemyTwoTransition.play();
            }


            if (!start && player.getBoundsInParent().intersects(anemy.getBoundsInParent())
                    || player.getBoundsInParent().intersects(anemyTwo.getBoundsInParent())) {

                if (player.getX() < anemy.getX()) {
                    counts++;
                    count.setText("Count: " + count);
                }
                isLose = true;
                lose.setVisible(true);
                playerSpeed = 0;
                jumpDownSpeed = 0;
                counts = 0;
                parallelTransition.pause();
                anemyTransition.pause();
                anemyTwoTransition.pause();
                buttonStart.setVisible(true);
                check = false;
            }
            if (start) {
                lose.setVisible(false);
                isLose = false;
                playerSpeed = 3;
                jumpDownSpeed = 5;
                parallelTransition.play();
                anemyTwoTransition.playFromStart();
                anemyTransition.playFromStart();
                start = false;
            }
        }
    };


    @FXML
    void initialize() {


        TranslateTransition bgOneTransition = new TranslateTransition(Duration.millis(5000), bg1);

        bgOneTransition.setFromX(0);
        int BG_WIDTH = 711;
        bgOneTransition.setToX(BG_WIDTH * -1);
        bgOneTransition.setInterpolator(Interpolator.LINEAR);


        TranslateTransition bgTwoTransition = new TranslateTransition(Duration.millis(5000), bg2);
        bgTwoTransition.setFromX(0);
        bgTwoTransition.setToX(BG_WIDTH * -1);
        bgTwoTransition.setInterpolator(Interpolator.LINEAR);

        anemyTransition = new TranslateTransition(Duration.millis(3500), anemy);
        anemyTransition.setFromX(0);
        anemyTransition.setToX(BG_WIDTH * -1 - 100);
        anemyTransition.setInterpolator(Interpolator.LINEAR);
        anemyTransition.setCycleCount(Animation.INDEFINITE);
        anemyTransition.play();


        anemyTwoTransition = new TranslateTransition(Duration.millis(3600), anemyTwo);
        anemyTwoTransition.setFromX(0);
        anemyTwoTransition.setToX(BG_WIDTH * -1 - 100);
        anemyTwoTransition.setInterpolator(Interpolator.LINEAR);
        anemyTwoTransition.setCycleCount(Animation.INDEFINITE);
        anemyTwoTransition.play();

        parallelTransition = new ParallelTransition(bgOneTransition, bgTwoTransition);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();

        timer.start();

        buttonStart.setOnAction(event -> {
                    start = true;
                    buttonStart.setVisible(false);
                }
        );
    }


}
