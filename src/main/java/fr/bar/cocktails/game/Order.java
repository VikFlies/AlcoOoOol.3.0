package fr.bar.cocktails.game;

/**
 * Classe représentant une commande de cocktail
 * Workflow : waiting → assigned → preparing → completed
 * ✅ Serveur gagne +5 XP en prenant la commande
 * ✅ Barman gagne +15 XP en complétant la commande
 */
public class Order {
    private static int idCounter = 0;
    private int id;
    private Cocktail cocktail;
    private String status; // "waiting", "assigned", "preparing", "completed"
    private double waitTime;
    private double customerSatisfaction;
    private long createdTime;
    private Employee assignedBarman; // Le barman qui prépare cette commande
    private Employee assignedServeur; // Le serveur qui a pris la commande

    public Order(Cocktail cocktail, double satisfaction) {
        this.id = idCounter++;
        this.cocktail = cocktail;
        this.status = "waiting";
        this.customerSatisfaction = satisfaction;
        this.createdTime = System.currentTimeMillis();
        this.waitTime = 0;
        this.assignedBarman = null;
        this.assignedServeur = null;
    }

    /**
     * Met à jour le temps d'attente
     */
    public void updateWaitTime() {
        waitTime = (System.currentTimeMillis() - createdTime) / 1000.0;
    }

    /**
     * Assigne un serveur à cette commande
     * ✅ Le serveur gagne +5 XP
     */
    public void assignServeur(Employee serveur) {
        this.assignedServeur = serveur;
        this.status = "assigned";
        if (this.assignedServeur != null) {
            this.assignedServeur.addExperience(5); // ← SERVEUR GAGNE +5 XP
        }
    }

    /**
     * Assigne un barman à cette commande pour la préparer
     */
    public void assignBarman(Employee barman) {
        this.assignedBarman = barman;
        this.status = "preparing";
    }

    /**
     * Marque la commande comme complétée
     * ✅ Le barman gagne +15 XP
     */
    public void complete() {
        this.status = "completed";
        if (this.assignedBarman != null) {
            this.assignedBarman.addExperience(15); // ← BARMAN GAGNE +15 XP
        }
    }

    // ==================== GETTERS ====================
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Cocktail getCocktail() { return cocktail; }
    public double getWaitTime() { updateWaitTime(); return waitTime; }
    public double getCustomerSatisfaction() { return customerSatisfaction; }
    public int getId() { return id; }
    public Employee getAssignedBarman() { return assignedBarman; }
    public Employee getAssignedServeur() { return assignedServeur; }

    public String getBarmanName() {
        return assignedBarman != null ? assignedBarman.getName() : "En attente...";
    }

    public String getServeurName() {
        return assignedServeur != null ? assignedServeur.getName() : "Non assigné";
    }
}
