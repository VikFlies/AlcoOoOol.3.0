package fr.bar.cocktails.game;

import java.util.*;

public class Game {
    private double money = 2000;
    private double staffSatisfaction = 100;
    private int wave = 1;
    private double waveRevenue = 0;
    private int waveOrdersCompleted = 0;

    private List<Employee> employees = new ArrayList<>();
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<Cocktail> cocktails = new ArrayList<>();

    private Random random = new Random();
    private double difficultyMultiplier = 1.0;

    public Game() {
        initializeIngredients();
        initializeCocktails();
        generateInitialOrders();
    }

    private void initializeIngredients() {
        ingredients.add(new Ingredient("Rhum", 2.5, 100));
        ingredients.add(new Ingredient("Vodka", 3.0, 100));
        ingredients.add(new Ingredient("Gin", 3.5, 100));
        ingredients.add(new Ingredient("Jus Citron", 1.0, 150));
        ingredients.add(new Ingredient("Sirop", 0.8, 150));
        ingredients.add(new Ingredient("Soda", 0.5, 200));
        ingredients.add(new Ingredient("Glaçons", 0.2, 300));
    }

    private void initializeCocktails() {
        cocktails.add(new Cocktail("Mojito", 12.0, new String[]{"Rhum", "Jus Citron", "Sirop", "Soda"}));
        cocktails.add(new Cocktail("Margarita", 14.0, new String[]{"Vodka", "Jus Citron", "Sirop"}));
        cocktails.add(new Cocktail("Daiquiri", 13.0, new String[]{"Rhum", "Jus Citron", "Glaçons"}));
        cocktails.add(new Cocktail("Gin Tonic", 11.0, new String[]{"Gin", "Soda", "Glaçons"}));
    }

    public void generateInitialOrders() {
        int orderCount = (int) (3 + difficultyMultiplier * 2);
        for (int i = 0; i < orderCount; i++) {
            orders.add(generateRandomOrder());
        }
    }

    public Order generateRandomOrder() {
        Cocktail cocktail = cocktails.get(random.nextInt(cocktails.size()));
        double satisfaction = 0.5 + random.nextDouble() * 0.5;
        return new Order(cocktail, satisfaction);
    }

    public void hireEmployee(String type) {
        Employee emp = "barman".equals(type)
                ? new Barman("Barman-" + (employees.size() + 1))
                : new Serveur("Serveur-" + (employees.size() + 1));

        if (money >= emp.getHireCost()) {
            money -= emp.getHireCost();
            employees.add(emp);
        }
    }

    public void buyStock(String ingredientName, int quantity) {
        for (Ingredient ing : ingredients) {
            if (ing.getName().equals(ingredientName)) {
                double cost = ing.getBasePrice() * quantity;
                if (money >= cost) {
                    money -= cost;
                    ing.addStock(quantity);
                }
                return;
            }
        }
    }

    public void processWave() {
        waveRevenue = 0;
        waveOrdersCompleted = 0;

        List<Order> completedOrders = new ArrayList<>();
        for (Order order : orders) {
            if (canPrepareOrder(order)) {
                prepareOrder(order);
                waveRevenue += order.getCocktail().getPrice();
                waveOrdersCompleted++;
                completedOrders.add(order);
                money += order.getCocktail().getPrice() * 0.8;
            }
        }

        for (Order order : completedOrders) {
            orders.remove(order);
        }

        if (waveOrdersCompleted == 0) {
            staffSatisfaction -= 10;
            money -= 100;
        }

        staffSatisfaction = Math.min(100, staffSatisfaction + (waveOrdersCompleted * 5));
    }

    private boolean canPrepareOrder(Order order) {
        for (String ingredient : order.getCocktail().getRecipe()) {
            boolean found = false;
            for (Ingredient ing : ingredients) {
                if (ing.getName().equals(ingredient) && ing.getStock() > 0) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return !employees.isEmpty();
    }

    private void prepareOrder(Order order) {
        for (String ingredientName : order.getCocktail().getRecipe()) {
            for (Ingredient ing : ingredients) {
                if (ing.getName().equals(ingredientName)) {
                    ing.removeStock(1);
                    break;
                }
            }
        }
        if (!employees.isEmpty()) {
            employees.get(0).addExperience(10);
        }
    }

    public void endWave() {
        wave++;
        difficultyMultiplier += 0.1;

        double totalSalaries = 0;
        for (Employee emp : employees) {
            totalSalaries += emp.getSalary();
        }
        money -= totalSalaries;

        if (money < 0) {
            money = 0;
            staffSatisfaction -= 20;
        }

        generateInitialOrders();
    }

    public void upgradeEmployee(String empId, String stat) {
        for (Employee emp : employees) {
            if (emp.getId().equals(empId)) {
                double cost = emp.getUpgradeCost(stat);
                if (money >= cost) {
                    money -= cost;
                    emp.upgrade(stat);
                }
                break;
            }
        }
    }

    // GETTERS
    public double getMoney() { return money; }
    public double getStaffSatisfaction() { return staffSatisfaction; }
    public int getWave() { return wave; }
    public double getWaveRevenue() { return waveRevenue; }
    public List<Employee> getEmployees() { return employees; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public List<Order> getOrders() { return orders; }
    public List<Cocktail> getCocktails() { return cocktails; }
}
