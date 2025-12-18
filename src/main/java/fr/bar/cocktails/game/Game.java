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

    private Map<Integer, Long> orderStartTimes = new HashMap<>();
    private Map<Integer, Boolean> orderAssignedToServeur = new HashMap<>();
    private Map<Integer, Boolean> orderAssignedToBarman = new HashMap<>();
    private Map<String, Integer> employeeCurrentOrder = new HashMap<>();

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
            employeeCurrentOrder.put(emp.getId(), -1);
            OrderLog.getInstance().addLog("✅ " + emp.getName() + " embauché!", "serveur");
        }
    }

    public void buyStock(String ingredientName, int quantity) {
        for (Ingredient ing : ingredients) {
            if (ing.getName().equals(ingredientName)) {
                double cost = ing.getBasePrice() * quantity;
                if (money >= cost) {
                    money -= cost;
                    ing.addStock(quantity);
                    OrderLog.getInstance().addLog("📦 " + quantity + "x " + ingredientName + " achetés!", "order");
                }
                return;
            }
        }
    }

    /**
     * ════════════════════════════════════════════════════════════════════
     * TRAITEMENT AUTOMATIQUE EN TEMPS RÉEL
     *
     * ÉTAPE 1: waiting → assigned (Serveur prend la commande)
     * ÉTAPE 2: assigned → preparing (Barman reçoit la commande)
     * ÉTAPE 3: preparing → completed (Barman termine)
     * ════════════════════════════════════════════════════════════════════
     */
    public void processOrdersAutomatically() {
        List<Order> completedOrders = new ArrayList<>();
        long currentTime = System.currentTimeMillis();

        // ═══════════════════════════════════════════════════════════════
        // ÉTAPE 1 : Serveurs prennent les commandes en attente
        // waiting → assigned
        // ═══════════════════════════════════════════════════════════════
        List<Order> waitingOrders = new ArrayList<>(orders);
        waitingOrders.removeIf(o -> !o.getStatus().equals("waiting"));

        for (Order order : waitingOrders) {
            if (!orderStartTimes.containsKey(order.getId())) {
                orderStartTimes.put(order.getId(), currentTime);
                orderAssignedToServeur.put(order.getId(), false);
                order.setOrderStartTime(currentTime);
            }

            if (!orderAssignedToServeur.getOrDefault(order.getId(), false)) {
                Employee serveur = findAvailableServeur();
                if (serveur != null) {
                    order.assignServeur(serveur);
                    orderAssignedToServeur.put(order.getId(), true);
                    employeeCurrentOrder.put(serveur.getId(), order.getId());
                    OrderLog.getInstance().addLog("📝 " + serveur.getName() + " prend: " +
                            order.getCocktail().getName(), "order");
                }
            }

            if (order.getAssignedServeur() != null && orderAssignedToServeur.getOrDefault(order.getId(), false)) {
                long timeElapsed = currentTime - orderStartTimes.get(order.getId());
                double serveurSpeed = order.getAssignedServeur().getSpeed();
                long serveurDuration = (long) (1000.0 / serveurSpeed);

                if (timeElapsed >= serveurDuration) {
                    order.setStatus("assigned");
                    orderStartTimes.put(order.getId(), currentTime);
                    employeeCurrentOrder.put(order.getAssignedServeur().getId(), -1);
                    order.getAssignedServeur().addExperience(5);
                    OrderLog.getInstance().addLog("✅ " + order.getAssignedServeur().getName() +
                            " remet: " + order.getCocktail().getName() + " (+5 XP)", "serveur");
                }
            }
        }

        // ═══════════════════════════════════════════════════════════════
        // ÉTAPE 2 : Barmans reçoivent et préparent les commandes
        // assigned → preparing
        // ═══════════════════════════════════════════════════════════════
        List<Order> assignedOrders = new ArrayList<>(orders);
        assignedOrders.removeIf(o -> !o.getStatus().equals("assigned"));

        for (Order order : assignedOrders) {
            if (!orderAssignedToBarman.containsKey(order.getId())) {
                orderStartTimes.put(order.getId(), currentTime);
                orderAssignedToBarman.put(order.getId(), false);
            }

            if (!orderAssignedToBarman.getOrDefault(order.getId(), false)) {
                Employee barman = findAvailableBarman();
                if (barman != null) {
                    order.assignBarman(barman);
                    orderAssignedToBarman.put(order.getId(), true);
                    employeeCurrentOrder.put(barman.getId(), order.getId());
                    order.setStatus("preparing");
                    orderStartTimes.put(order.getId(), currentTime);
                    OrderLog.getInstance().addLog("🍸 " + barman.getName() + " prépare: " +
                            order.getCocktail().getName(), "barman");
                }
            }

            if (order.getAssignedBarman() != null && orderAssignedToBarman.getOrDefault(order.getId(), false)) {
                long timeElapsed = currentTime - orderStartTimes.get(order.getId());
                double barmanSpeed = order.getAssignedBarman().getSpeed();
                long barmanDuration = (long) (1000.0 / barmanSpeed);

                if (timeElapsed >= barmanDuration) {
                    if (canPrepareOrder(order)) {
                        prepareOrder(order);
                        order.complete();

                        double earnings = order.getCocktail().getPrice() * 0.8;
                        money += earnings;
                        waveRevenue += order.getCocktail().getPrice();
                        waveOrdersCompleted++;

                        order.getAssignedBarman().addExperience(15);
                        staffSatisfaction = Math.min(100, staffSatisfaction + 5);

                        employeeCurrentOrder.put(order.getAssignedBarman().getId(), -1);
                        completedOrders.add(order);

                        OrderLog.getInstance().addLog("🎉 " + order.getCocktail().getName() +
                                " TERMINÉE! (+$" + String.format("%.0f", earnings) + " | +15 XP)", "completed");
                    } else {
                        order.setStatus("waiting");
                        orderStartTimes.remove(order.getId());
                        orderAssignedToServeur.remove(order.getId());
                        orderAssignedToBarman.remove(order.getId());
                        employeeCurrentOrder.put(order.getAssignedBarman().getId(), -1);
                        order.assignBarman(null);

                        OrderLog.getInstance().addLog("❌ " + order.getCocktail().getName() +
                                " : Ingrédients insuffisants!", "error");
                    }
                }
            }
        }

        // ═══════════════════════════════════════════════════════════════
        // ÉTAPE 3 : Supprimer les commandes complétées de la liste
        // ═══════════════════════════════════════════════════════════════
        for (Order order : completedOrders) {
            orders.remove(order);
            orderStartTimes.remove(order.getId());
            orderAssignedToServeur.remove(order.getId());
            orderAssignedToBarman.remove(order.getId());
        }
    }

    private Employee findAvailableServeur() {
        for (Employee emp : employees) {
            if ("Serveur".equals(emp.getType())) {
                if (employeeCurrentOrder.getOrDefault(emp.getId(), -1) == -1) {
                    return emp;
                }
            }
        }
        return null;
    }

    private Employee findAvailableBarman() {
        for (Employee emp : employees) {
            if ("Barman".equals(emp.getType())) {
                if (employeeCurrentOrder.getOrDefault(emp.getId(), -1) == -1) {
                    return emp;
                }
            }
        }
        return null;
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
        return true;
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
    }

    public void endWave() {
        OrderLog.getInstance().addLog("🏁 VAGUE #" + wave + " TERMINÉE! " + waveOrdersCompleted +
                " commandes | $" + (int) waveRevenue, "completed");

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

        waveRevenue = 0;
        waveOrdersCompleted = 0;
        orderStartTimes.clear();
        orderAssignedToServeur.clear();
        orderAssignedToBarman.clear();

        for (String empId : employeeCurrentOrder.keySet()) {
            employeeCurrentOrder.put(empId, -1);
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
                    OrderLog.getInstance().addLog("⬆️ " + emp.getName() + " amélioré!", "serveur");
                }
                break;
            }
        }
    }

    public double getMoney() { return money; }
    public double getStaffSatisfaction() { return staffSatisfaction; }
    public int getWave() { return wave; }
    public double getWaveRevenue() { return waveRevenue; }
    public int getWaveOrdersCompleted() { return waveOrdersCompleted; }
    public List<Employee> getEmployees() { return employees; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public List<Order> getOrders() { return orders; }
    public List<Cocktail> getCocktails() { return cocktails; }
}
