package fr.bar.cocktails;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import fr.bar.cocktails.view.StartMenuUI;
import fr.bar.cocktails.view.RulesMenu;
import fr.bar.cocktails.view.GameUI;
import fr.bar.cocktails.game.Game;
import fr.bar.cocktails.engine.GameEngine;

public class BarCocktailsMain extends Application {

    private Stage primaryStage;
    private StartMenuUI startMenuUI;
    private RulesMenu rulesMenu;
    private GameUI gameUI;
    private Game game;
    private GameEngine engine;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("🍹 Bar à Cocktails");
        primaryStage.setWidth(1400);
        primaryStage.setHeight(900);
        primaryStage.centerOnScreen();

        showStartMenu();
        primaryStage.show();
    }

    private void showStartMenu() {
        startMenuUI = new StartMenuUI();

        startMenuUI.setOnNewGameCallback(() -> {
            showGameScreen();
        });

        startMenuUI.setOnLoadGameCallback(() -> {
            // TODO: Implémenter le chargement de partie
        });

        startMenuUI.setOnRulesCallback(() -> {
            showRulesMenu();
        });

        Scene scene = new Scene(startMenuUI, 1400, 900);
        primaryStage.setScene(scene);
    }

    private void showRulesMenu() {
        rulesMenu = new RulesMenu();

        rulesMenu.setOnBackCallback(() -> {
            showStartMenu();
        });

        Scene scene = new Scene(rulesMenu, 1400, 900);
        primaryStage.setScene(scene);
    }

    private void showGameScreen() {
        game = new Game();
        engine = new GameEngine(game);
        gameUI = new GameUI(game, engine);

        startGameUpdateLoop();

        Scene scene = new Scene(gameUI.getRoot(), 1400, 900);
        primaryStage.setScene(scene);
    }

    private void startGameUpdateLoop() {
        Thread gameThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(16);
                    javafx.application.Platform.runLater(() -> {
                        engine.updateUI();
                        gameUI.updateUI();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.setDaemon(true);
        gameThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
