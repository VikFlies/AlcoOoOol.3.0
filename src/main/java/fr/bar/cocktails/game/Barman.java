package fr.bar.cocktails.game;

/**
 * Classe Barman - Spécialisé dans la préparation rapide des cocktails
 */
public class Barman extends Employee {

    public Barman(String name) {
        super(name, 30); // Salaire de 150$ par vague
        this.speed = 1.5;
        this.quality = 0.7;
        this.hireCost = 100;
    }

    @Override
    public void upgrade(String stat) {
        if ("speed".equals(stat)) {
            this.speed *= 1.2;
        } else if ("quality".equals(stat)) {
            this.quality = Math.min(1.0, this.quality + 0.15);
        } else if ("experience".equals(stat)) {
            this.experience += 100;
        }
    }

    @Override
    public double getUpgradeCost(String stat) {
        return switch (stat) {
            case "speed" -> 300;
            case "quality" -> 400;
            case "experience" -> 200;
            default -> 0;
        };
    }
}
