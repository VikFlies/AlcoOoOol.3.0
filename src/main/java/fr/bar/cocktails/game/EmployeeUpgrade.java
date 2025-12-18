package fr.bar.cocktails.game;

/**
 * Classe représentant une amélioration d'employé
 * Définit les coûts et gains d'amélioration
 */
public class EmployeeUpgrade implements Payable {
    private String name;
    private String stat;  // "speed", "quality", "salary"
    private double cost;
    private double improvement;  // Pourcentage ou valeur fixe
    private Employee employee;

    public EmployeeUpgrade(Employee employee, String stat, double cost, double improvement) {
        this.employee = employee;
        this.stat = stat;
        this.cost = cost;
        this.improvement = improvement;
        this.name = "Amélioration " + stat + " - " + employee.getName();
    }

    /**
     * Appliquer l'amélioration à l'employé
     */
    public void apply() {
        switch (stat.toLowerCase()) {
            case "speed":
                // Augmenter la vitesse de 15%
                employee.setSpeed(employee.getSpeed() * (1 + improvement));
                break;
            case "quality":
                // Augmenter la qualité de 15%
                employee.setQuality(employee.getQuality() * (1 + improvement));
                break;
            case "salary":
                // Augmenter le salaire de 20%
                employee.increaseSalary(improvement);
                break;
        }
    }

    // ==================== IMPLÉMENTATION PAYABLE ====================

    @Override
    public double getPrice() {
        return cost;
    }

    @Override
    public String getPriceDescription() {
        return name + " - $" + cost;
    }

    @Override
    public double calculateFinalPrice() {
        return cost;  // Pas de réduction
    }

    // ==================== GETTERS ====================
    public String getStat() { return stat; }
    public double getImprovement() { return improvement; }
    public Employee getEmployee() { return employee; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name + " ($" + cost + ")";
    }
}