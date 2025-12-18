package fr.bar.cocktails;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import fr.bar.cocktails.game.Game;
import fr.bar.cocktails.engine.GameEngine;

public class BarCocktailsMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        Game game = new Game();
        GameEngine engine = new GameEngine(game);
        Scene scene = new Scene(engine.getGameUI().getRoot(), 1400, 850);

        primaryStage.setTitle("🍹 Bar à Cocktails - Gestionnaire Parfait");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
