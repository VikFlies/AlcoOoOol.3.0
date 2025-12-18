package fr.bar.cocktails.game;

import java.util.*;

public class Game {
    // ==================== ATTRIBUTS ====================
    private double money;
    private int wave;
    private double staffSatisfaction;
    private double waveRevenue;
    private List<Employee> employees;
    private List<Ingredient> ingredients;
    private List<Cocktail> cocktails;
    private List<Order> orders;
    private boolean isWaveActive;
    private Map<String, Order> barmanCurrentOrder = new HashMap<>();

    // ==================== CONSTANTES ====================
    private static final String[] SERVEUR_NAMES = {"Alice", "Bob", "Charlie", "Diana", "Eva", "Frank"};
    private static final String[] BARMAN_NAMES = {"Mike", "Sarah", "Tom", "Lisa", "Jean", "Marie"};

    // ==================== CONSTRUCTEUR ====================
    public Game() {
        this.money = 2000;
        this.wave = 1;
        this.staffSatisfaction = 100;
        this.waveRevenue = 0;
        this.employees = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.cocktails = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.isWaveActive = false;

        initializeIngredients();
        initializeCocktails();

        System.out.println("✅ Game initialized");
    }

    // ==================== INITIALISATION ====================
    private void initializeIngredients() {
        ingredients.add(new Ingredient("Rhum", 0.5, 50));
        ingredients.add(new Ingredient("Vodka", 0.6, 50));
        ingredients.add(new Ingredient("Tequila", 0.5, 50));
        ingredients.add(new Ingredient("Rhum blanc", 0.5, 50));
        ingredients.add(new Ingredient("Citron frais", 0.2, 80));
        ingredients.add(new Ingredient("Sucre", 0.1, 100));
        ingredients.add(new Ingredient("Glaçon", 0.1, 150));
        ingredients.add(new Ingredient("Menthe", 0.1, 60));
        ingredients.add(new Ingredient("Jus d'orange", 0.3, 100));
        ingredients.add(new Ingredient("Sirop simple", 0.2, 80));
    }

    private void initializeCocktails() {
        // ===== COCKTAILS ALCOOLISÉS =====
        cocktails.add(new AlcoholicCocktail(
                "Mojito",
                new String[]{"Rhum blanc", "Citron frais", "Sucre", "Menthe", "Glaçon"},
                20.0, 7.0, 15.0));  // Prix normal: 8.0$, temps: 5s, alcool: 15°

        cocktails.add(new AlcoholicCocktail(
                "Margarita",
                new String[]{"Tequila", "Citron frais", "Sucre"},
                15.0, 6.0, 14.0));

        cocktails.add(new AlcoholicCocktail(
                "Daiquiri",
                new String[]{"Rhum", "Citron frais", "Sucre", "Glaçon"},
                23.5, 6.5, 16.0));

        cocktails.add(new AlcoholicCocktail(
                "Cuba Libre",
                new String[]{"Rhum", "Citron frais", "Jus d'orange", "Glaçon"},
                25.0, 5.5, 12.0));

        cocktails.add(new AlcoholicCocktail(
                "Piña Colada",
                new String[]{"Rhum blanc", "Jus d'orange", "Sucre", "Glaçon"},
                22.0, 7.5, 17.0));

        // ===== COCKTAILS SANS ALCOOL =====
        cocktails.add(new SoftCocktail(
                "Mojito Vierge",
                new String[]{"Citron frais", "Sucre", "Menthe", "Glaçon", "Eau"},
                18.0, 5.0, false));  // Prix: 5.0 * 0.8 = 4.0$, temps: 3.0 * 0.7 = 2.1s

        cocktails.add(new SoftCocktail(
                "Smoothie Tropical",
                new String[]{"Mangue", "Ananas", "Noix de coco", "Glaçon"},
                10.5, 4.5, false));

        cocktails.add(new SoftCocktail(
                "Jus de Fruits",
                new String[]{"Orange", "Citron", "Fraise", "Glaçon"},
                6.0, 4.0, false));

        cocktails.add(new SoftCocktail(
                "Ice Tea",
                new String[]{"Thé", "Citron", "Sucre", "Glaçon"},
                7.5, 4.0, true));  // true = avec caféine
    }

    // ==================== GESTION DES COMMANDES ====================
    public void generateOrders(int count) {
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            Cocktail cocktail = cocktails.get(rand.nextInt(cocktails.size()));
            double satisfaction = 0.85 + rand.nextDouble() * 0.15;
            Order order = new Order(cocktail, satisfaction);
            orders.add(order);
            System.out.println("📝 Nouvelle commande: " + cocktail.getName());
        }
        System.out.println("📥 Total: " + count + " commandes générées");
    }

    /**
     * ⚠️ CORRIGÉ - TRAITE LES COMMANDES AUTOMATIQUEMENT
     * Cette méthode est appelée par GameEngine toutes les 100ms
     */
    public void processOrdersAutomatically() {
        if (!isWaveActive || orders.isEmpty()) {
            return;
        }

        // ===== PHASE 1 : ASSIGNER LES COMMANDES EN ATTENTE =====
        List<Order> waitingOrders = orders.stream()
                .filter(o -> "waiting".equals(o.getStatus()))
                .toList();

        for (Order order : waitingOrders) {
            if (!employees.isEmpty()) {
                // Trouver un serveur
                List<Employee> serveurs = employees.stream()
                        .filter(e -> e instanceof Serveur)
                        .toList();

                // Trouver un barman
                List<Employee> barmans = employees.stream()
                        .filter(e -> e instanceof Barman)
                        .toList();

                if (!serveurs.isEmpty() && !barmans.isEmpty()) {
                    Employee serveur = serveurs.get(new Random().nextInt(serveurs.size()));
                    Employee barman = barmans.get(new Random().nextInt(barmans.size()));

                    order.assignServeur(serveur);
                    order.assignBarman(barman);
                    order.setStatus("assigned");
                    order.setOrderStartTime(System.currentTimeMillis());

                    System.out.println("📝 Commande assignée: " + order.getCocktail().getName() +
                            " → Serveur: " + serveur.getName() + ", Barman: " + barman.getName());
                }
            }
        }

        // ===== PHASE 2 : COMMANDES ASSIGNÉES → EN PRÉPARATION =====
        List<Order> assignedOrders = orders.stream()
                .filter(o -> "assigned".equals(o.getStatus()))
                .toList();

        // APRÈS (une seule commande par barman)
        for (Order order : assignedOrders) {
            Employee barman = order.getAssignedBarman();
            if (barman != null) {
                // Vérifier si ce barman est libre (pas de commande en cours)
                Order currentOrder = barmanCurrentOrder.get(barman.getId());

                // APRÈS (corrigé - les commandes vont à la bonne vitesse)
                if (currentOrder == null) {
                    double preparationTime = order.getCocktail().getPreparationTime() / barman.getSpeed();

                    if (order.getWaitTime() >= preparationTime) {
                        order.setStatus("preparing");
                        order.resetOrderTime();  // ← RÉINITIALISER LE TIMER !
                        barmanCurrentOrder.put(barman.getId(), order);
                        System.out.println("⏱️ Barman " + barman.getName() + " commence: " +
                                order.getCocktail().getName());
                    }
                }
            }
        }


        // ===== PHASE 3 : COMMANDES EN PRÉPARATION → COMPLÉTÉES =====
        List<Order> preparingOrders = orders.stream()
                .filter(o -> "preparing".equals(o.getStatus()))
                .toList();

        // APRÈS
        for (Order order : preparingOrders) {
            Employee barman = order.getAssignedBarman();
            if (barman != null) {
                double preparationTime = order.getCocktail().getPreparationTime() / barman.getSpeed();
                double totalTime = preparationTime + 2.0;

                if (order.getWaitTime() >= totalTime) {
                    completeOrder(order);
                    barmanCurrentOrder.remove(barman.getId());  // ← Libérer le barman
                    System.out.println("✅ Barman " + barman.getName() + " est maintenant libre");
                }
            }
        }
    }

    private void completeOrder(Order order) {
        order.complete();

        double qualityModifier = order.getCocktail().getQualityModifier();
        double revenue = order.getCocktail().getPrice() * order.getSatisfactionLevel() * qualityModifier;

        money += revenue;
        waveRevenue += revenue;

        // ← EXPÉRIENCE DES EMPLOYÉS
        if (order.getAssignedServeur() != null) {
            order.getAssignedServeur().addExperience(5 * order.getSatisfactionLevel());
        }
        if (order.getAssignedBarman() != null) {
            order.getAssignedBarman().addExperience(8 * order.getSatisfactionLevel());
        }

        // ← SATISFACTION DES CLIENTS
        staffSatisfaction = Math.min(100, staffSatisfaction + 2);

        System.out.println("✅ Commande complétée: " + order.getCocktail().getName() +
                " | Gain: $" + (int)revenue);
    }

    // ==================== GESTION DES VAGUES ====================
    public void startWave() {
        if (isWaveActive) {
            System.out.println("⚠️ Une vague est déjà active !");
            return;
        }

        isWaveActive = true;
        waveRevenue = 0;
        orders.clear(); // ← IMPORTANT : Nettoyer les anciennes commandes

        int orderCount = 7 + (3 * wave)%2;
        generateOrders(orderCount);

        System.out.println("\n🌊 VAGUE #" + wave + " COMMENCÉE!");
        System.out.println("📊 État: isWaveActive=" + isWaveActive +
                ", orders.size()=" + orders.size());
    }

    public void endWave() {
        if (!isWaveActive) {
            System.out.println("⚠️ Aucune vague en cours !");
            return;
        }

        isWaveActive = false;
        barmanCurrentOrder.clear();
        // Payer les salaires
        double totalSalaries = 0;
        for (Employee emp : employees) {
            totalSalaries += emp.getSalary();
            money -= emp.getSalary();
        }

        staffSatisfaction = Math.max(0, staffSatisfaction - 5);

        wave++;

        System.out.println("\n✅ VAGUE TERMINÉE!");
        System.out.println("💰 Revenu: +$" + (int)waveRevenue);
        System.out.println("💸 Salaires: -$" + (int)totalSalaries);
        System.out.println("📊 Argent restant: $" + (int)money);
        System.out.println("🌊 Prochaine vague: #" + wave);
    }

    // ==================== GESTION DES EMPLOYÉS ====================
    public void hireEmployee(String type) {
        double hireCost;
        Employee employee;

        if ("barman".equalsIgnoreCase(type)) {
            employee = new Barman(BARMAN_NAMES[new Random().nextInt(BARMAN_NAMES.length)]);
            hireCost = employee.getHireCost();
        } else if ("serveur".equalsIgnoreCase(type)) {
            employee = new Serveur(SERVEUR_NAMES[new Random().nextInt(SERVEUR_NAMES.length)]);
            hireCost = employee.getHireCost();
        } else {
            return;
        }

        if (money >= hireCost) {
            money -= hireCost;
            employees.add(employee);
            staffSatisfaction = Math.min(100, staffSatisfaction + 3);
            System.out.println("✅ " + employee.getName() + " (" + type + ") recruté ! -$" + (int)hireCost);
        } else {
            System.out.println("❌ Fonds insuffisants pour recruter");
        }
    }

    public void upgradeEmployee(String empId, String stat) {
        for (Employee emp : employees) {
            if (emp.getId().equals(empId)) {
                double upgradeCost = emp.getUpgradeCost(stat);

                if (money >= upgradeCost) {
                    money -= upgradeCost;
                    emp.upgrade(stat);
                    System.out.println("⬆️ " + emp.getName() + " amélioré en " + stat);
                } else {
                    System.out.println("❌ Fonds insuffisants");
                }
                return;
            }
        }
    }

    // ==================== GESTION DU STOCK ====================
    public void buyStock(String ingredientName, int quantity) {
        for (Ingredient ing : ingredients) {
            if (ing.getName().equalsIgnoreCase(ingredientName)) {
                double totalCost = ing.getBasePrice() * quantity;

                if (money >= totalCost) {
                    money -= totalCost;
                    ing.addStock(quantity);
                    System.out.println("📦 +" + quantity + " " + ingredientName + " ! -$" + (int)totalCost);
                } else {
                    System.out.println("❌ Fonds insuffisants");
                }
                return;
            }
        }
    }

    // ==================== GETTERS ====================
    public double getMoney() { return money; }
    public int getWave() { return wave; }
    public double getStaffSatisfaction() { return staffSatisfaction; }
    public double getWaveRevenue() { return waveRevenue; }
    public List<Employee> getEmployees() { return new ArrayList<>(employees); }
    public List<Ingredient> getIngredients() { return new ArrayList<>(ingredients); }
    public List<Cocktail> getCocktails() { return new ArrayList<>(cocktails); }
    public List<Order> getOrders() { return new ArrayList<>(orders); }
    public boolean isWaveActive() { return isWaveActive; }
}