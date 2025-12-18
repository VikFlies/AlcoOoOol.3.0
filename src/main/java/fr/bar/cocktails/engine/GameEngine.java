package fr.bar.cocktails.engine;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.application.Platform;
import fr.bar.cocktails.game.Game;
import fr.bar.cocktails.view.GameUI;

public class GameEngine {
    private final Game game;
    private final GameUI gameUI;
    private Timeline waveTimer;
    private boolean isProcessing = false;

    public GameEngine(Game game) {
        this.game = game;
        this.gameUI = new GameUI(game, this);
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    /**
     * Traite la vague avec animation de 10 secondes
     */
    public void processWaveWithTimer() {
        if (isProcessing) return;
        isProcessing = true;

        waveTimer = new Timeline(new KeyFrame(
                Duration.seconds(10),
                event -> {
                    game.processWave();
                    updateUI();
                    isProcessing = false;
                }
        ));
        waveTimer.setCycleCount(1);
        waveTimer.play();
    }

    /**
     * Termine la vague actuelle
     */
    public void endWave() {
        if (waveTimer != null) {
            waveTimer.stop();
        }
        game.endWave();
        updateUI();
        isProcessing = false;
    }

    /**
     * Recrute un employé (barman ou serveur)
     */
    public void hireEmployee(String type) {
        game.hireEmployee(type);
        updateUI();
    }

    /**
     * Achète du stock d'ingrédient
     */
    public void buyStock(String ingredient, int quantity) {
        game.buyStock(ingredient, quantity);
        updateUI();
    }

    /**
     * Améliore un employé
     */
    public void upgradeEmployee(String empId, String stat) {
        game.upgradeEmployee(empId, stat);
        updateUI();
    }

    /**
     * Met à jour l'interface utilisateur
     */
    public void updateUI() {
        Platform.runLater(() -> gameUI.updateUI());
    }

    public Game getGame() {
        return game;
    }
}
