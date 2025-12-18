package fr.bar.cocktails.game;

/**
 * Classe représentant un ingrédient du bar
 */
public class Ingredient {
    private String name;
    private double basePrice;
    private double stock;

    public Ingredient(String name, double basePrice, double initialStock) {
        this.name = name;
        this.basePrice = basePrice;
        this.stock = initialStock;
    }

    public void addStock(int quantity) {
        stock += quantity;
    }

    public void removeStock(int quantity) {
        stock = Math.max(0, stock - quantity);
    }

    public String getName() { return name; }
    public double getBasePrice() { return basePrice; }
    public double getStock() { return stock; }
}
