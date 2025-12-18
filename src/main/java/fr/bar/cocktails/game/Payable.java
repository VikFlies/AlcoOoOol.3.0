package fr.bar.cocktails.game;

public interface Payable {

    double getPrice();

    String getPriceDescription();

    double calculateFinalPrice();
}