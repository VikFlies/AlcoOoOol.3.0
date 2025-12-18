package fr.bar.cocktails.game;

public class Order {
    private static int nextId = 1;
    private int id;
    private Cocktail cocktail;
    private String status; // waiting, assigned, preparing, completed
    private Employee assignedServeur;
    private Employee assignedBarman;
    private double satisfactionLevel;
    private long orderStartTime; // Quand la commande a commencé (en millisecondes)

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
    }

    public Employee getAssignedServeur() {
        return assignedServeur;
    }

    public void assignServeur(Employee serveur) {
        this.assignedServeur = serveur;
    }

    public Employee getAssignedBarman() {
        return assignedBarman;
    }

    public void assignBarman(Employee barman) {
        this.assignedBarman = barman;
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
     * Obtient le temps d'attente en secondes
     * Le temps d'attente baisse en temps réel une fois pris en charge
     */
    public double getWaitTime() {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - orderStartTime;
        return timeElapsed / 1000.0; // Convertir en secondes
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
