package fr.bar.cocktails.game;

/**
 * Classe Serveur - Spécialisé dans la gestion des clients et commandes
 */
public class Serveur extends Employee {

    public Serveur(String name) {
        super(name, 120); // Salaire de 120$ par vague
        this.speed = 1.2;
        this.quality = 0.8;
        this.hireCost = 150;
    }

    @Override
    public void upgrade(String stat) {
        if ("speed".equals(stat)) {
            this.speed *= 1.15;
        } else if ("quality".equals(stat)) {
            this.quality = Math.min(1.0, this.quality + 0.1);
        } else if ("experience".equals(stat)) {
            this.experience += 80;
        }
    }

    @Override
    public double getUpgradeCost(String stat) {
        return switch (stat) {
            case "speed" -> 250;
            case "quality" -> 300;
            case "experience" -> 150;
            default -> 0;
        };
    }
}
