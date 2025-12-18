package fr.bar.cocktails.game;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

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

    /**
     * Augmenter la vitesse (setSpeed)
     */
    public void setSpeed(double newSpeed) {
        this.speed = newSpeed;
    }

    /**
     * Augmenter la qualité (setQuality)
     */
    public void setQuality(double newQuality) {
        this.quality = newQuality;
    }

    /**
     * Augmenter le salaire (appelé lors d'amélioration)
     * @param percentage Pourcentage d'augmentation (ex: 0.2 pour +20%)
     */
    public void increaseSalary(double percentage) {
        this.salary = salary * (1 + percentage);
        System.out.println("💰 " + name + " - Salaire augmenté: $" + (int)salary);
    }

    /**
     * Obtenir les améliorations disponibles
     * @return Liste des améliorations possibles
     */
    public List<EmployeeUpgrade> getAvailableUpgrades() {
        List<EmployeeUpgrade> upgrades = new ArrayList<>();

        // Amélioration de vitesse
        upgrades.add(new EmployeeUpgrade(this, "speed", 100, 0.15));  // +15%, coûte 100$

        // Amélioration de qualité
        upgrades.add(new EmployeeUpgrade(this, "quality", 80, 0.15));  // +15%, coûte 80$

        // Amélioration de salaire
        upgrades.add(new EmployeeUpgrade(this, "salary", 150, 0.2));  // +20%, coûte 150$

        return upgrades;
    }

    /**
     * Vérifier si employé peut être amélioré
     */
    public boolean canBeUpgraded(String stat) {
        // Vous pouvez ajouter des vérifications (ex: niveau max)
        return true;
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
