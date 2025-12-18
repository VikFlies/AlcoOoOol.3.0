package fr.bar.cocktails.game;

/**
 * Classe représentant un cocktail
 */
public class Cocktail {
    private String name;
    private double price;
    private String[] recipe;

    public Cocktail(String name, double price, String[] recipe) {
        this.name = name;
        this.price = price;
        this.recipe = recipe;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String[] getRecipe() { return recipe; }
}
