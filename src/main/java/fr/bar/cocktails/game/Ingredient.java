package fr.bar.cocktails.game;

/**
 * Classe représentant un ingrédient du stock
 */
public class Ingredient {
    private String name;
    private double basePrice;
    private int quantity;  // Stock actuel

    public Ingredient(String name, double basePrice, int initialQuantity) {
        this.name = name;
        this.basePrice = basePrice;
        this.quantity = initialQuantity;
    }

    // ==================== GESTION DU STOCK ====================

    /**
     * Utiliser une unité d'ingrédient
     * @return true si l'ingrédient était disponible, false sinon
     */
    public boolean useIngredient() {
        if (quantity > 0) {
            quantity--;
            return true;
        }
        return false;  // Stock insuffisant
    }

    /**
     * Utiliser X unités d'ingrédient
     * @param amount Quantité à utiliser
     * @return true si suffisant stock, false sinon
     */
    public boolean useIngredients(int amount) {
        if (quantity >= amount) {
            quantity -= amount;
            return true;
        }
        return false;  // Stock insuffisant
    }

    /**
     * Ajouter du stock
     */
    public void addStock(int amount) {
        this.quantity += amount;
    }

    /**
     * Vérifier si suffisant stock
     */
    public boolean hasEnoughStock(int required) {
        return quantity >= required;
    }

    /**
     * Obtenir le stock courant
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Vérifier si stock faible (< 5 unités)
     */
    public boolean isLowStock() {
        return quantity < 5;
    }

    // ==================== GETTERS ====================
    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public String toString() {
        String status = isLowStock() ? " ⚠️" : "";
        return name + ": " + quantity + " unités" + status;
    }
}