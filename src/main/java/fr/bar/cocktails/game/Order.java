package fr.bar.cocktails.game;

public class Order {
    private static int nextId = 1;
    private int id;
    private Cocktail cocktail;
    private String status; // waiting, assigned, preparing, completed
    private Employee assignedServeur;
    private Employee assignedBarman;
    private double satisfactionLevel;
    private long orderStartTime;
    private long serveurStartTime;
    private long barmanStartTime;

    public Order(Cocktail cocktail, double satisfaction) {
        this.id = nextId++;
        this.cocktail = cocktail;
        this.status = "waiting";
        this.assignedServeur = null;
        this.assignedBarman = null;
        this.satisfactionLevel = satisfaction;
        this.orderStartTime = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public Cocktail getCocktail() {
        return cocktail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        if ("assigned".equals(status)) {
            this.serveurStartTime = System.currentTimeMillis();
        } else if ("preparing".equals(status)) {
            this.barmanStartTime = System.currentTimeMillis();
        }
    }

    public Employee getAssignedServeur() {
        return assignedServeur;
    }

    public void assignServeur(Employee serveur) {
        this.assignedServeur = serveur;
        this.serveurStartTime = System.currentTimeMillis();
    }

    public Employee getAssignedBarman() {
        return assignedBarman;
    }

    public void assignBarman(Employee barman) {
        this.assignedBarman = barman;
        this.barmanStartTime = System.currentTimeMillis();
    }

    public String getServeurName() {
        return assignedServeur != null ? assignedServeur.getName() : "Personne";
    }

    public String getBarmanName() {
        return assignedBarman != null ? assignedBarman.getName() : "Personne";
    }

    public double getSatisfactionLevel() {
        return satisfactionLevel;
    }

    /**
     * Définit l'heure de début de la commande
     */
    public void setOrderStartTime(long time) {
        this.orderStartTime = time;
    }

    /**
     * Obtient le temps d'attente total en secondes
     * Le temps d'attente baisse en temps réel une fois pris en charge
     */
    public double getWaitTime() {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - orderStartTime;
        return timeElapsed / 1000.0; // Convertir en secondes
    }

    /**
     * Obtient la progression du serveur en pourcentage
     */
    public double getServeurProgress() {
        if (assignedServeur == null || !"waiting".equals(status)) {
            return 0;
        }
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - serveurStartTime;
        long totalDuration = (long) (1000.0 / assignedServeur.getSpeed());
        return Math.min(100, (timeElapsed * 100) / totalDuration);
    }

    /**
     * Obtient la progression du barman en pourcentage
     */
    public double getBarmanProgress() {
        if (assignedBarman == null || !"preparing".equals(status)) {
            return 0;
        }
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - barmanStartTime;
        long totalDuration = (long) (1000.0 / assignedBarman.getSpeed());
        return Math.min(100, (timeElapsed * 100) / totalDuration);
    }

    /**
     * Obtient le temps restant du serveur en secondes
     */
    public double getServeurTimeRemaining() {
        if (assignedServeur == null || !"waiting".equals(status)) {
            return 0;
        }
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - serveurStartTime;
        long totalDuration = (long) (1000.0 / assignedServeur.getSpeed());
        long timeRemaining = Math.max(0, totalDuration - timeElapsed);
        return timeRemaining / 1000.0;
    }

    /**
     * Obtient le temps restant du barman en secondes
     */
    public double getBarmanTimeRemaining() {
        if (assignedBarman == null || !"preparing".equals(status)) {
            return 0;
        }
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - barmanStartTime;
        long totalDuration = (long) (1000.0 / assignedBarman.getSpeed());
        long timeRemaining = Math.max(0, totalDuration - timeElapsed);
        return timeRemaining / 1000.0;
    }

    /**
     * Complète la commande
     */
    public void complete() {
        this.status = "completed";
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cocktail=" + cocktail.getName() +
                ", status='" + status + '\'' +
                ", serveur=" + getServeurName() +
                ", barman=" + getBarmanName() +
                '}';
    }
}
