package fr.bar.cocktails.view;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Random;

public class StartMenuUI extends BorderPane {

    private Runnable onNewGameCallback;
    private Runnable onLoadGameCallback;
    private Pane backgroundPane;
    private Random random = new Random();
    private Timeline emojiLauncherTimeline;

    public StartMenuUI() {
        initializeUI();
    }

    private void initializeUI() {
        setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #1a1a2e 0%, #16213E 50%, #0f3460 100%);");

        // Créer le pane d'arrière-plan pour les animations
        backgroundPane = new Pane();
        backgroundPane.setStyle("-fx-background-color: transparent;");
        backgroundPane.setPrefSize(1000, 700);

        // Créer le centre avec le titre et les boutons superposés
        VBox centerBox = createCenterBox();
        StackPane stackPane = new StackPane(backgroundPane, centerBox);
        stackPane.setStyle("-fx-background-color: transparent;");

        setCenter(stackPane);
        setBottom(createBottomBox());

        // Démarrer l'animation des emojis
        startEmojiAnimation();
    }

    private void startEmojiAnimation() {
        // Lancer régulièrement des emojis toutes les 800ms
        emojiLauncherTimeline = new Timeline(
                new KeyFrame(Duration.millis(400), e -> launchEmoji())
        );
        emojiLauncherTimeline.setCycleCount(Timeline.INDEFINITE);
        emojiLauncherTimeline.play();
    }

    private void launchEmoji() {
        String[] emojis = {"🍹", "🍸"};
        String emoji = emojis[random.nextInt(emojis.length)];

        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(Font.font("Arial", 40));
        emojiLabel.setStyle("-fx-text-fill: #FF4ACF; -fx-opacity: 0.8;");

        // Position initiale aléatoire en bas
        double startX = random.nextDouble() * 1000 - 50;
        double startY = 900; // Hauteur du pane

        emojiLabel.setLayoutX(startX);
        emojiLabel.setLayoutY(startY);

        backgroundPane.getChildren().add(emojiLabel);

        // Paramètres de la trajectoire
        double velocityX = (random.nextDouble() - 0.5) * 400; // Vitesse horizontale aléatoire
        double velocityY = -(random.nextDouble() * 300 + 350); // Vitesse verticale vers le haut
        double gravity = 300; // Accélération de la gravité
        double duration = 3.0; // Durée totale en secondes

        animateEmojiWithPhysics(emojiLabel, startX, startY, velocityX, velocityY, gravity, duration);
    }

    private void animateEmojiWithPhysics(Label emoji, double startX, double startY,
                                         double velocityX, double velocityY,
                                         double gravity, double duration) {
        Timeline timeline = new Timeline();

        // Nombre d'images par seconde
        int fps = 60;
        int frameCount = (int)(duration * fps);

        for (int frame = 0; frame <= frameCount; frame++) {
            double time = (double) frame / fps;

            // Équations de mouvement avec gravité
            double x = startX + velocityX * time;
            double y = startY + velocityY * time + 0.5 * gravity * time * time;

            // Opacité diminue avec le temps
            double opacity = 1.0 - (time / duration);

            // Rotation basée sur la vélocité
            double rotation = (velocityX / 2) * time;

            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(frame * (1000.0 / fps)),
                    event -> {
                        emoji.setLayoutX(x);
                        emoji.setLayoutY(y);
                        emoji.setOpacity(Math.max(0, opacity));
                        emoji.setRotate(rotation);
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
        }

        // Supprimer l'emoji après animation
        timeline.setOnFinished(e -> {
            if (backgroundPane.getChildren().contains(emoji)) {
                backgroundPane.getChildren().remove(emoji);
            }
        });
        timeline.play();
    }

    private VBox createCenterBox() {
        VBox centerBox = new VBox(30);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(50));
        centerBox.setStyle("-fx-background-color: transparent;");

        Label logoLabel = new Label("🍹");
        logoLabel.setFont(Font.font("Arial", 150));
        logoLabel.setStyle("-fx-text-fill: #FF4ACF;" +
                "-fx-opacity: 0.8;" +
                "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, #FF4ACF, 10, 0.4, 0, 0);"
        );

        Label titleLabel = new Label("BAR À COCKTAILS");
        titleLabel.setStyle(
                "-fx-font-size: 48;" +
                        "-fx-text-fill: #FF4ACF;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, #FF4ACF, 10, 0.4, 0, 0);"
        );

        Label subtitleLabel = new Label("");
        subtitleLabel.setStyle(
                "-fx-font-size: 24;" +
                        "-fx-text-fill: #ecf0f1;" +
                        "-fx-font-style: italic;"
        );

        Label versionLabel = new Label("");
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
        bottomBox.setStyle("-fx-background-color: transparent;");

        Button newGameButton = new Button("🎮 NOUVELLE PARTIE");
        newGameButton.setStyle(
                "-fx-font-size: 16;" +
                        "-fx-padding: 15 50;" +
                        "-fx-background-color: #00B025;" +
                        "-fx-text-fill: #00380B;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-radius: 50;" +
                        "-fx-background-radius: 50;" +
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
                        "-fx-background-color: #002DB5;" +
                        "-fx-text-fill: #000C3D;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-radius: 50;" +
                        "-fx-background-radius: 50;" +
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
                        "-fx-background-color: #A00000;" +
                        "-fx-text-fill: #400000;" +
                        "-fx-border-radius: 50;" +
                        "-fx-background-radius: 50;" +
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

    // Méthode pour arrêter les animations (utile si tu quittes le menu)
    public void stopAnimations() {
        if (emojiLauncherTimeline != null) {
            emojiLauncherTimeline.stop();
        }
    }
}
