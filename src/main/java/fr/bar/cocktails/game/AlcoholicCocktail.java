package fr.bar.cocktails.game;

/**
 * Classe pour les cocktails alcoolisés
 * Hérite de Cocktail
 *
 * Caractéristiques :
 * - Plus cher (prix +20%)
 * - Temps de préparation normal
 * - Bonus de satisfaction (+10%)
 */
public class AlcoholicCocktail extends Cocktail {
    private double alcoholDegree;  // Degré d'alcool (optionnel)

    /**
     * Constructeur pour cocktail alcoolisé
     */
    public AlcoholicCocktail(String name, String[] recipe, double basePrice,
                             double preparationTime, double alcoholDegree) {
        // Augmenter le prix de 20% pour les cocktails alcoolisés
        super(name, recipe, basePrice * 1.2, preparationTime);
        this.alcoholDegree = alcoholDegree;
    }

    /**
     * Constructeur simplifié (sans degré d'alcool)
     */
    public AlcoholicCocktail(String name, String[] recipe, double basePrice,
                             double preparationTime) {
        this(name, recipe, basePrice, preparationTime, 15.0); // 15° par défaut
    }

    @Override
    public String getType() {
        return "🍷 Alcoolisé";
    }

    @Override
    public String getDescription() {
        return "Cocktail alcoolisé (" + alcoholDegree + "°) - Prix premium";
    }

    /**
     * Les cocktails alcoolisés donnent un bonus de satisfaction (+10%)
     */
    @Override
    public double getQualityModifier() {
        return 1.1;  // +10% de satisfaction
    }

    // ==================== GETTERS ====================
    public double getAlcoholDegree() {
        return alcoholDegree;
    }

    @Override
    public String toString() {
        return name + " 🍷 ($" + Math.round(price * 100.0) / 100.0 + ")";
    }
}