package fr.bar.cocktails.engine;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import fr.bar.cocktails.game.Game;
import fr.bar.cocktails.view.GameUI;

/**
 * Moteur de jeu - Gère la boucle de jeu et le traitement des commandes
 * ⚠️ PROBLÈME IDENTIFIÉ : processOrdersAutomatically() n'est pas appelée
 */
public class GameEngine {

    private final Game game;
    private final GameUI gameUI;
    private AnimationTimer gameLoop;
    private boolean isWaveActive = false;

    // ← NOUVEAU : Variables de timing
    private long lastUpdateTime = 0;
    private static final long UPDATE_INTERVAL_NANOS = 100_000_000; // 100ms en nanosecondes

    public GameEngine(Game game) {
        this.game = game;
        this.gameUI = new GameUI(game, this);
        initializeGameLoop();
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    /**
     * ⚠️ CORRIGÉ : Initialise la boucle de jeu qui traite les commandes
     * Cette boucle s'exécute 60 fois par seconde (60 FPS)
     * Toutes les 100ms, elle traite les commandes si une vague est active
     */
    private void initializeGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Vérifier si une vague est en cours
                if (isWaveActive) {
                    // Traiter toutes les 100ms (pas à chaque frame)
                    if (now - lastUpdateTime >= UPDATE_INTERVAL_NANOS) {

                        // ← CRITIQUE : Traiter les commandes
                        game.processOrdersAutomatically();

                        // ← Mettre à jour l'interface
                        updateUI();

                        lastUpdateTime = now;

                        // DEBUG
                        System.out.println("⏱️ TICK: " + game.getOrders().size() +
                                " commandes | En attente: " +
                                game.getOrders().stream()
                                        .filter(o -> "waiting".equals(o.getStatus()))
                                        .count() +
                                " | Argent: $" + (int)game.getMoney());
                    }
                }
            }
        };
        gameLoop.start();
        System.out.println("✅ GameLoop démarrée");
    }

    /**
     * Démarre une vague
     */
    public void startWave() {
        if (isWaveActive) {
            System.out.println("⚠️ Une vague est déjà en cours !");
            return;
        }

        isWaveActive = true;
        lastUpdateTime = System.nanoTime(); // Reset le timer

        // ← IMPORTANT : Générer les commandes
        game.startWave();

        System.out.println("\n🌊 VAGUE #" + game.getWave() + " COMMENCÉE!");
        System.out.println("📊 Commandes à traiter: " + game.getOrders().size());

        updateUI();
    }

    /**
     * Termine la vague actuelle
     */
    public void endWave() {
        if (!isWaveActive) {
            System.out.println("⚠️ Aucune vague en cours !");
            return;
        }

        isWaveActive = false;
        game.endWave();

        System.out.println("\n✅ Vague terminée!");
        System.out.println("💰 Revenu: $" + (int)game.getWaveRevenue());
        System.out.println("💸 Salaires payés");

        updateUI();
    }

    /**
     * Recrute un employé
     */
    public void hireEmployee(String type) {
        game.hireEmployee(type);
        updateUI();
    }

    /**
     * Achète du stock
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
     * ← CRITIQUE : Met à jour l'interface
     * Doit être appelée depuis le thread JavaFX
     */
    public void updateUI() {
        Platform.runLater(() -> {
            try {
                gameUI.updateUI();
            } catch (Exception e) {
                System.err.println("❌ Erreur dans updateUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public Game getGame() {
        return game;
    }

    public boolean isWaveActive() {
        return isWaveActive;
    }
}