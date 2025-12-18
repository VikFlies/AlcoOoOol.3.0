package fr.bar.cocktails.engine;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import fr.bar.cocktails.game.Game;
import fr.bar.cocktails.view.GameUI;

public class GameEngine {
    private final Game game;
    private final GameUI gameUI;
    private AnimationTimer gameLoop;
    private boolean isWaveActive = false;

    public GameEngine(Game game) {
        this.game = game;
        this.gameUI = new GameUI(game, this);
        initializeGameLoop();
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    /**
     * Initialise la boucle de jeu qui traite les commandes en temps réel
     */
    private void initializeGameLoop() {
        gameLoop = new AnimationTimer() {
            private long lastProcessTime = 0;
            private static final long PROCESS_INTERVAL = 100; // Vérifier tous les 100ms

            @Override
            public void handle(long now) {
                if (isWaveActive) {
                    // Traiter les commandes chaque 100ms
                    if (now - lastProcessTime >= PROCESS_INTERVAL * 1_000_000) {
                        game.processOrdersAutomatically();
                        updateUI();
                        lastProcessTime = now;
                    }
                }
            }
        };
        gameLoop.start();
    }

    /**
     * Démarre une vague (les commandes se traitent automatiquement)
     */
    public void startWave() {
        if (!isWaveActive) {
            isWaveActive = true;
            System.out.println("\n🌊 VAGUE #" + game.getWave() + " COMMENCÉE!");
            updateUI();
        }
    }

    /**
     * Termine la vague actuelle
     */
    public void endWave() {
        if (isWaveActive) {
            isWaveActive = false;
            game.endWave();
            System.out.println("\n✅ Vague terminée!");
            updateUI();
        }
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

    public boolean isWaveActive() {
        return isWaveActive;
    }
}
