package fr.bar.cocktails.view;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class StartMenuUI extends BorderPane {

    private Runnable onNewGameCallback;
    private Runnable onLoadGameCallback;

    public StartMenuUI() {
        initializeUI();
    }

    private void initializeUI() {
        setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #1a1a2e 0%, #16213e 50%, #0f3460 100%);");

        VBox centerBox = createCenterBox();
        setCenter(centerBox);

        VBox bottomBox = createBottomBox();
        setBottom(bottomBox);
    }

    private VBox createCenterBox() {
        VBox centerBox = new VBox(30);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(50));

        Label logoLabel = new Label("🍹");
        logoLabel.setFont(Font.font("Arial", 150));

        Label titleLabel = new Label("BAR À COCKTAILS");
        titleLabel.setStyle(
                "-fx-font-size: 48;" +
                        "-fx-text-fill: #00d4ff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, #00d4ff, 20, 0.8, 0, 0);"
        );

        Label subtitleLabel = new Label("Gestionnaire Parfait");
        subtitleLabel.setStyle(
                "-fx-font-size: 24;" +
                        "-fx-text-fill: #ecf0f1;" +
                        "-fx-font-style: italic;"
        );

        Label versionLabel = new Label("v2.0");
        versionLabel.setStyle(
                "-fx-font-size: 12;" +
                        "-fx-text-fill: #95a5a6;"
        );

        centerBox.getChildren().addAll(logoLabel, titleLabel, subtitleLabel, versionLabel, new Separator());
        return centerBox;
    }

    private VBox createBottomBox() {
        VBox bottomBox = new VBox(20);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(50));

        Button newGameButton = new Button("🎮 NOUVELLE PARTIE");
        newGameButton.setStyle(
                "-fx-font-size: 16;" +
                        "-fx-padding: 15 50;" +
                        "-fx-background-color: #00d4ff;" +
                        "-fx-text-fill: #1a1a2e;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-radius: 10;" +
                        "-fx-cursor: hand;"
        );
        newGameButton.setMinWidth(300);
        newGameButton.setOnAction(e -> {
            animateButtonClick(newGameButton);
            if (onNewGameCallback != null) onNewGameCallback.run();
        });

        Button loadGameButton = new Button("📂 CHARGER PARTIE");
        loadGameButton.setStyle(
                "-fx-font-size: 16;" +
                        "-fx-padding: 15 50;" +
                        "-fx-background-color: #e74c3c;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-radius: 10;" +
                        "-fx-cursor: hand;"
        );
        loadGameButton.setMinWidth(300);
        loadGameButton.setOnAction(e -> {
            animateButtonClick(loadGameButton);
            if (onLoadGameCallback != null) onLoadGameCallback.run();
        });

        Button exitButton = new Button("❌ QUITTER");
        exitButton.setStyle(
                "-fx-font-size: 14;" +
                        "-fx-padding: 10 30;" +
                        "-fx-background-color: #34495e;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-radius: 8;" +
                        "-fx-cursor: hand;"
        );
        exitButton.setOnAction(e -> System.exit(0));

        bottomBox.getChildren().addAll(newGameButton, loadGameButton, new Separator(), exitButton);
        return bottomBox;
    }

    private void animateButtonClick(Button button) {
        FadeTransition fade = new FadeTransition(Duration.millis(200), button);
        fade.setFromValue(1.0);
        fade.setToValue(0.7);
        fade.setCycleCount(2);
        fade.setAutoReverse(true);
        fade.play();
    }

    public void setOnNewGameCallback(Runnable callback) {
        this.onNewGameCallback = callback;
    }

    public void setOnLoadGameCallback(Runnable callback) {
        this.onLoadGameCallback = callback;
    }
}
