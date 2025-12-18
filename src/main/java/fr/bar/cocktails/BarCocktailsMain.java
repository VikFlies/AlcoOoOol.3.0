package fr.bar.cocktails;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import fr.bar.cocktails.game.Game;
import fr.bar.cocktails.engine.GameEngine;
import fr.bar.cocktails.view.StartMenuUI;
import fr.bar.cocktails.view.GameUI;   // ← ancienne interface classique

public class BarCocktailsMain extends Application {

    private Stage primaryStage;
    private Scene menuScene;
    private Scene gameScene;

    private Game game;
    private GameEngine engine;
    private GameUI gameUI;            // ← ton ancienne UI

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("🍹 Bar à Cocktails");
        primaryStage.setWidth(1400);
        primaryStage.setHeight(850);

        showStartMenu();

        primaryStage.show();
    }

    /**
     * Affiche le premier écran : menu de démarrage
     */
    private void showStartMenu() {
        StartMenuUI startMenu = new StartMenuUI();

        // Quand on clique sur "Nouvelle partie"
        startMenu.setOnNewGameCallback(() -> {
            startNewGame();
            transitionToGame();
        });

        // Bouton "Charger" (optionnel, pas encore implémenté)
        startMenu.setOnLoadGameCallback(() -> {
            System.out.println("Chargement de partie : non implémenté pour l'instant.");
        });

        menuScene = new Scene(startMenu, 1400, 850);
        primaryStage.setScene(menuScene);
    }

    /**
     * Crée la partie avec l'ancienne interface GameUI
     */
    private void startNewGame() {
        game = new Game();
        engine = new GameEngine(game);

        // ⚠️ On utilise ici TON ancienne GameUI
        gameUI = new GameUI(game, engine);

        gameScene = new Scene(gameUI.getRoot(), 1400, 850);
    }

    /**
     * Transition visuelle du menu vers le jeu
     */
    private void transitionToGame() {
        FadeTransition fadeOut =
                new FadeTransition(Duration.millis(400), primaryStage.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            primaryStage.setScene(gameScene);

            FadeTransition fadeIn =
                    new FadeTransition(Duration.millis(400), gameScene.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
