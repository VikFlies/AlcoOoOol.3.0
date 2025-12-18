package fr.bar.cocktails.game;

/**
 * Classe abstraite représentant un cocktail
 * Parent de AlcoholicCocktail et SoftCocktail
 */
public abstract class Cocktail implements Payable{
    protected String name;
    protected double price;
    protected String[] recipe;
    protected double preparationTime;

    /**
     * Constructeur parent
     */
    protected Cocktail(String name, String[] recipe, double price, double preparationTime) {
        this.name = name;
        this.price = price;
        this.recipe = recipe;
        this.preparationTime = preparationTime;
    }

    /**
     * Méthode abstraite pour obtenir le type de cocktail
     */
    public abstract String getType();

    /**
     * Méthode abstraite pour obtenir la description
     */
    public abstract String getDescription();

    /**
     * Méthode abstraite pour obtenir un bonus/malus selon le type
     */
    public abstract double getQualityModifier();

    // ==================== GETTERS ====================
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public String[] getRecipe() {
        return recipe;
    }

    public double getPreparationTime() {
        return preparationTime;
    }

    @Override
    public String toString() {
        return name + " (" + getType() + ") - $" + price;
    }
    @Override
    public String getPriceDescription() {
        return getName() + " " + getType() + " - $" + String.format("%.2f", price);
    }
    @Override
    public double calculateFinalPrice() {
        return price * getQualityModifier();
    }
}