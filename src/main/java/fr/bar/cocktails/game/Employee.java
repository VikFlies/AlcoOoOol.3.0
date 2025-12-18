package fr.bar.cocktails.game;

import java.util.UUID;

/**
 * Classe abstraite représentant un employé du bar
 */
public abstract class Employee {
    protected String id;
    protected String name;
    protected double speed;
    protected double quality;
    protected double experience;
    protected double salary;
    protected double hireCost;

    /**
     * Constructeur principal
     */
    public Employee(String id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.experience = 0;
    }

    /**
     * Constructeur avec UUID automatique
     */
    public Employee(String name, double salary) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.salary = salary;
        this.experience = 0;
    }

    /**
     * Méthode abstraite pour upgrader un employé
     */
    public abstract void upgrade(String stat);

    /**
     * Méthode abstraite pour obtenir le coût d'une amélioration
     */
    public abstract double getUpgradeCost(String stat);

    /**
     * Ajoute de l'expérience à l'employé
     */
    public void addExperience(double amount) {
        experience += amount;
        quality = Math.min(1.0, 0.5 + experience / 500);
    }

    // ==================== GETTERS ====================
    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return this.getClass().getSimpleName(); }
    public double getSpeed() { return speed; }
    public double getQuality() { return quality; }
    public double getExperience() { return experience; }
    public double getSalary() { return salary; }
    public double getHireCost() { return hireCost; }
}
