package fr.bar.cocktails.game;

/**
 * Classe pour les cocktails sans alcool (soft)
 * Hérite de Cocktail
 *
 * Caractéristiques :
 * - Moins cher (prix -20%)
 * - Temps de préparation réduit (-30%)
 * - Bonus de satisfaction modéré (+5%)
 * - Plus facile à préparer
 */
public class SoftCocktail extends Cocktail {
    private boolean containsCaffeine;  // Contient de la caféine?

    /**
     * Constructeur pour cocktail sans alcool
     */
    public SoftCocktail(String name, String[] recipe, double basePrice,
                        double preparationTime, boolean containsCaffeine) {
        // Réduire le prix de 20% pour les cocktails soft
        super(name, recipe, basePrice * 0.8, preparationTime * 0.7);
        this.containsCaffeine = containsCaffeine;
    }

    /**
     * Constructeur simplifié (sans caféine)
     */
    public SoftCocktail(String name, String[] recipe, double basePrice,
                        double preparationTime) {
        this(name, recipe, basePrice, preparationTime, false);
    }

    @Override
    public String getType() {
        return "🧃 Sans alcool";
    }

    @Override
    public String getDescription() {
        String desc = "Cocktail sans alcool - Prix réduit";
        if (containsCaffeine) {
            desc += " (avec caféine)";
        }
        return desc;
    }

    /**
     * Les cocktails soft donnent un petit bonus (+5%)
     * Mais c'est plus facile à préparer
     */
    @Override
    public double getQualityModifier() {
        return 1.05;  // +5% de satisfaction
    }

    // ==================== GETTERS ====================
    public boolean isContainsCaffeine() {
        return containsCaffeine;
    }

    @Override
    public String toString() {
        return name + " 🧃 ($" + Math.round(price * 100.0) / 100.0 + ")";
    }
}