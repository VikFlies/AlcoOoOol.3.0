package fr.bar.cocktails.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import fr.bar.cocktails.engine.GameEngine;
import fr.bar.cocktails.game.*;

public class GameUI {
    private final Game game;
    private final GameEngine engine;
    private final VBox root;
    private final Label moneyLabel;
    private final Label satisfactionLabel;
    private final Label waveRevenueLabel;
    private final Label waveNumberLabel;
    private final VBox employeesList;
    private final VBox stockList;
    private final VBox orderQueue;
    private final Label waitingCount;
    private final Label preparingCount;
    private final Label completedCount;
    private Button startButton;

    public GameUI(Game game, GameEngine engine) {
        this.game = game;
        this.engine = engine;
        this.root = new VBox();
        this.moneyLabel = new Label("$2000");
        this.satisfactionLabel = new Label("100%");
        this.waveRevenueLabel = new Label("$0");
        this.waveNumberLabel = new Label("Vague #1");
        this.employeesList = new VBox();
        this.stockList = new VBox();
        this.orderQueue = new VBox();
        this.waitingCount = new Label("0");
        this.preparingCount = new Label("0");
        this.completedCount = new Label("0");

        initialize();
        updateUI();
    }

    private void initialize() {
        root.setStyle("-fx-background-color: #0f1419; -fx-text-fill: #e8e8e8;");
        root.setPadding(new Insets(0));
        root.setSpacing(0);

        HBox header = createHeader();
        root.getChildren().add(header);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: #1a1f26;");
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        GridPane mainGrid = createMainGrid();
        scrollPane.setContent(mainGrid);

        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        root.getChildren().add(scrollPane);
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setStyle("-fx-background-color: rgba(20, 25, 35, 0.95); -fx-border-color: #2d5f7d; -fx-border-width: 0 0 2 0; -fx-padding: 20;");
        header.setSpacing(40);
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        header.setPrefHeight(100);

        Label title = new Label("🍹 Bar à Cocktails");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
        title.setStyle("-fx-text-fill: #ff6b5b;");

        VBox moneyBox = createStatBox("💰 Argent", moneyLabel, "#52b788");
        VBox satisfactionBox = createStatBox("⭐ Satisfaction", satisfactionLabel, "#f4a261");
        VBox revenueBox = createStatBox("💵 Revenu Vague", waveRevenueLabel, "#52b788");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(title, spacer, moneyBox, satisfactionBox, revenueBox);
        return header;
    }

    private VBox createStatBox(String label, Label value, String color) {
        VBox box = new VBox();
        box.setStyle("-fx-alignment: center;");
        box.setSpacing(8);

        Label labelText = new Label(label);
        labelText.setStyle("-fx-text-fill: #aaa; -fx-font-size: 13; -fx-font-weight: bold;");
        value.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 22; -fx-font-weight: bold;");

        box.getChildren().addAll(labelText, value);
        return box;
    }

    private GridPane createMainGrid() {
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: #1a1f26; -fx-padding: 20;");
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPrefWidth(1360);

        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(33.33);
        grid.getColumnConstraints().addAll(col, col, col);

        VBox employeePanel = createPanel("👥 EMPLOYÉS", employeesList, createHireButton());
        grid.add(employeePanel, 0, 0);

        VBox stockPanel = createPanel("📦 STOCK", stockList, createStockButton());
        grid.add(stockPanel, 1, 0);

        VBox ordersPanel = createOrdersPanel();
        grid.add(ordersPanel, 2, 0);

        HBox waveControl = createWaveControl();
        GridPane.setColumnSpan(waveControl, 3);
        grid.add(waveControl, 0, 1);

        return grid;
    }

    private VBox createPanel(String title, VBox content, Button button) {
        VBox panel = new VBox();
        panel.setStyle("-fx-background-color: rgba(30, 40, 55, 0.9); -fx-border-color: #3d5a7a; -fx-border-width: 1; -fx-border-radius: 10; -fx-padding: 20;");
        panel.setSpacing(15);
        panel.setPrefHeight(450);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        titleLabel.setStyle("-fx-text-fill: #ff6b5b; -fx-font-weight: bold;");

        Separator sep = new Separator();
        sep.setStyle("-fx-border-color: #3d5a7a;");

        ScrollPane scroll = new ScrollPane(content);
        scroll.setStyle("-fx-control-inner-background: rgba(30, 40, 55, 0.9); -fx-background-color: rgba(30, 40, 55, 0.9); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(320);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        button.setMaxWidth(Double.MAX_VALUE);
        button.setStyle("-fx-font-size: 13; -fx-padding: 12 16; -fx-font-weight: bold; -fx-border-radius: 6;");

        panel.getChildren().addAll(titleLabel, sep, scroll, button);

        return panel;
    }

    private VBox createOrdersPanel() {
        VBox panel = new VBox();
        panel.setStyle("-fx-background-color: rgba(30, 40, 55, 0.9); -fx-border-color: #3d5a7a; -fx-border-width: 1; -fx-border-radius: 10; -fx-padding: 20;");
        panel.setSpacing(15);
        panel.setPrefHeight(450);

        Label titleLabel = new Label("📋 COMMANDES");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        titleLabel.setStyle("-fx-text-fill: #ff6b5b; -fx-font-weight: bold;");

        Separator sep = new Separator();
        sep.setStyle("-fx-border-color: #3d5a7a;");

        ScrollPane scroll = new ScrollPane(orderQueue);
        scroll.setStyle("-fx-control-inner-background: rgba(30, 40, 55, 0.9); -fx-background-color: rgba(30, 40, 55, 0.9); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(280);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        HBox statsBar = new HBox();
        statsBar.setStyle("-fx-spacing: 10;");
        statsBar.getChildren().addAll(
                createStatItem("📥 Attente", waitingCount, "#ff6b5b"),
                createStatItem("⚙️ Préparation", preparingCount, "#f4a261"),
                createStatItem("✅ Complétées", completedCount, "#52b788")
        );

        panel.getChildren().addAll(titleLabel, sep, scroll, statsBar);
        return panel;
    }

    private HBox createStatItem(String label, Label value, String color) {
        HBox box = new HBox();
        box.setStyle("-fx-background-color: rgba(50, 70, 90, 0.8); -fx-padding: 12; -fx-border-radius: 6; -fx-border-color: #3d5a7a; -fx-border-width: 1;");
        box.setSpacing(8);
        box.setAlignment(javafx.geometry.Pos.CENTER);

        Label labelText = new Label(label);
        labelText.setStyle("-fx-text-fill: #aaa; -fx-font-size: 12; -fx-font-weight: bold;");
        value.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 18; -fx-font-weight: bold;");

        box.getChildren().addAll(labelText, value);
        HBox.setHgrow(box, Priority.ALWAYS);
        return box;
    }

    private HBox createWaveControl() {
        HBox box = new HBox();
        box.setStyle("-fx-background-color: rgba(232, 93, 78, 0.08); -fx-border-color: #ff6b5b; -fx-border-width: 2; -fx-padding: 25; -fx-border-radius: 10;");
        box.setSpacing(25);
        box.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        VBox waveInfo = new VBox();
        waveInfo.setSpacing(8);
        waveNumberLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        waveNumberLabel.setStyle("-fx-text-fill: #ff6b5b;");
        Label waveLabel = new Label("Les commandes se traitent automatiquement selon la rapidité de vos employés");
        waveLabel.setStyle("-fx-text-fill: #999; -fx-font-size: 13;");
        waveInfo.getChildren().addAll(waveNumberLabel, waveLabel);

        startButton = new Button("▶️ DÉMARRER VAGUE");
        startButton.setStyle("-fx-font-size: 13; -fx-padding: 15 25; -fx-background-color: #52b788; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 6; -fx-cursor: hand;");
        startButton.setPrefWidth(200);
        startButton.setOnAction(e -> {
            engine.startWave();
            startButton.setDisable(true);
            startButton.setStyle("-fx-font-size: 13; -fx-padding: 15 25; -fx-background-color: #999; -fx-text-fill: #555; -fx-font-weight: bold; -fx-border-radius: 6; -fx-cursor: default;");
            updateUI();
        });

        Button endButton = new Button("⏹️ TERMINER VAGUE");
        endButton.setStyle("-fx-font-size: 13; -fx-padding: 15 25; -fx-background-color: #f4a261; -fx-text-fill: #1a1f26; -fx-font-weight: bold; -fx-border-radius: 6; -fx-cursor: hand;");
        endButton.setPrefWidth(180);
        endButton.setOnAction(e -> {
            engine.endWave();
            startButton.setDisable(false);
            startButton.setStyle("-fx-font-size: 13; -fx-padding: 15 25; -fx-background-color: #52b788; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 6; -fx-cursor: hand;");
            updateUI();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        box.getChildren().addAll(waveInfo, spacer, startButton, endButton);
        return box;
    }

    private Button createHireButton() {
        Button btn = new Button("+ RECRUTER");
        btn.setStyle("-fx-background-color: #52b788; -fx-text-fill: #1a1f26; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-radius: 6;");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setOnAction(e -> showHireDialog());
        return btn;
    }

    private Button createStockButton() {
        Button btn = new Button("📊 RÉAPPROVISIONNER");
        btn.setStyle("-fx-background-color: #f4a261; -fx-text-fill: #1a1f26; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-radius: 6;");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setOnAction(e -> showStockDialog());
        return btn;
    }

    private void showHireDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Recruter un Employé");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setPrefWidth(380);
        dialog.getDialogPane().setStyle("-fx-background-color: #1a1f26;");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        VBox content = new VBox();
        content.setStyle("-fx-background-color: #1a1f26;");
        content.setSpacing(15);
        content.setPadding(new Insets(20));

        Label title = new Label("👥 RECRUTER UN EMPLOYÉ");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        title.setStyle("-fx-text-fill: #ff6b5b;");
        content.getChildren().add(title);

        Separator sep = new Separator();
        sep.setStyle("-fx-border-color: #3d5a7a;");
        content.getChildren().add(sep);

        Button barmanBtn = new Button("🍸 BARMAN ($200)");
        barmanBtn.setStyle("-fx-padding: 15; -fx-font-size: 13; -fx-background-color: #2d5f7d; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 6; -fx-cursor: hand;");
        barmanBtn.setMaxWidth(Double.MAX_VALUE);
        Label barmanDesc = new Label("Prépare rapidement les cocktails | Vitesse: 1.5x");
        barmanDesc.setStyle("-fx-text-fill: #999; -fx-font-size: 11;");
        barmanBtn.setOnAction(e -> {
            engine.hireEmployee("barman");
            dialog.close();
            updateUI();
        });
        VBox barmanBox = new VBox(barmanBtn, barmanDesc);
        barmanBox.setSpacing(5);

        Button serveurBtn = new Button("🧑‍💼 SERVEUR ($150)");
        serveurBtn.setStyle("-fx-padding: 15; -fx-font-size: 13; -fx-background-color: #52b788; -fx-text-fill: #1a1f26; -fx-font-weight: bold; -fx-border-radius: 6; -fx-cursor: hand;");
        serveurBtn.setMaxWidth(Double.MAX_VALUE);
        Label serveurDesc = new Label("Gère les clients et les commandes | Vitesse: 1.2x");
        serveurDesc.setStyle("-fx-text-fill: #999; -fx-font-size: 11;");
        serveurBtn.setOnAction(e -> {
            engine.hireEmployee("serveur");
            dialog.close();
            updateUI();
        });
        VBox serveurBox = new VBox(serveurBtn, serveurDesc);
        serveurBox.setSpacing(5);

        Button quitBtn = new Button("❌ FERMER");
        quitBtn.setStyle("-fx-padding: 12; -fx-font-size: 12; -fx-background-color: #555; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 6; -fx-cursor: hand;");
        quitBtn.setMaxWidth(Double.MAX_VALUE);
        quitBtn.setOnAction(e -> dialog.close());

        content.getChildren().addAll(barmanBox, serveurBox, quitBtn);
        dialog.getDialogPane().setContent(content);
        dialog.show();
    }

    private void showStockDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Réapprovisionner");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setPrefWidth(480);
        dialog.getDialogPane().setPrefHeight(550);
        dialog.getDialogPane().setStyle("-fx-background-color: #1a1f26;");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        VBox mainContent = new VBox();
        mainContent.setStyle("-fx-background-color: #1a1f26;");
        mainContent.setSpacing(15);
        mainContent.setPadding(new Insets(20));

        Label title = new Label("📦 RÉAPPROVISIONNER");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        title.setStyle("-fx-text-fill: #ff6b5b;");
        mainContent.getChildren().add(title);

        Separator sep = new Separator();
        sep.setStyle("-fx-border-color: #3d5a7a;");
        mainContent.getChildren().add(sep);

        VBox content = new VBox();
        content.setSpacing(10);
        content.setPadding(new Insets(0));

        for (Ingredient ing : game.getIngredients()) {
            HBox itemBox = new HBox();
            itemBox.setStyle("-fx-background-color: rgba(50, 70, 90, 0.8); -fx-padding: 12; -fx-border-radius: 6; -fx-border-color: #3d5a7a; -fx-border-width: 1; -fx-spacing: 12;");
            itemBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            Label nameLabel = new Label(ing.getName());
            nameLabel.setStyle("-fx-text-fill: #e8e8e8; -fx-font-weight: bold; -fx-min-width: 80;");

            Label priceLabel = new Label("$" + (int) ing.getBasePrice());
            priceLabel.setStyle("-fx-text-fill: #52b788; -fx-font-weight: bold; -fx-min-width: 60;");

            Button btn10 = new Button("+10");
            btn10.setStyle("-fx-font-size: 11; -fx-padding: 8 12; -fx-background-color: #2d5f7d; -fx-text-fill: white; -fx-border-radius: 4; -fx-cursor: hand;");
            btn10.setOnAction(e -> {
                engine.buyStock(ing.getName(), 10);
                dialog.close();
                updateUI();
            });

            Button btn25 = new Button("+25");
            btn25.setStyle("-fx-font-size: 11; -fx-padding: 8 12; -fx-background-color: #52b788; -fx-text-fill: #1a1f26; -fx-border-radius: 4; -fx-cursor: hand; -fx-font-weight: bold;");
            btn25.setOnAction(e -> {
                engine.buyStock(ing.getName(), 25);
                dialog.close();
                updateUI();
            });

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            itemBox.getChildren().addAll(nameLabel, priceLabel, spacer, btn10, btn25);
            content.getChildren().add(itemBox);
        }

        ScrollPane scroll = new ScrollPane(content);
        scroll.setStyle("-fx-control-inner-background: #1a1f26; -fx-background-color: #1a1f26; -fx-focus-color: transparent;");
        scroll.setFitToWidth(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        Button quitBtn = new Button("❌ FERMER");
        quitBtn.setStyle("-fx-padding: 12; -fx-font-size: 12; -fx-background-color: #555; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 6; -fx-cursor: hand;");
        quitBtn.setMaxWidth(Double.MAX_VALUE);
        quitBtn.setOnAction(e -> dialog.close());

        mainContent.getChildren().addAll(scroll, quitBtn);
        dialog.getDialogPane().setContent(mainContent);
        dialog.show();
    }

    public void updateUI() {
        moneyLabel.setText("$" + (int) game.getMoney());
        satisfactionLabel.setText((int) game.getStaffSatisfaction() + "%");
        waveRevenueLabel.setText("$" + (int) game.getWaveRevenue());
        waveNumberLabel.setText("Vague #" + game.getWave());

        updateEmployeesList();
        updateStockList();
        updateOrdersList();
    }

    private void updateEmployeesList() {
        employeesList.getChildren().clear();
        employeesList.setSpacing(10);

        if (game.getEmployees().isEmpty()) {
            Label emptyLabel = new Label("👥 Aucun employé.\nRecrutez-en pour commencer!");
            emptyLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12;");
            employeesList.getChildren().add(emptyLabel);
            return;
        }

        for (Employee emp : game.getEmployees()) {
            VBox empBox = new VBox();
            empBox.setStyle("-fx-background-color: rgba(50, 70, 90, 0.8); -fx-padding: 12; -fx-border-radius: 6; -fx-border-color: #52b788; -fx-border-width: 2; -fx-spacing: 8;");

            Label nameLabel = new Label("🧑 " + emp.getName() + " (" + emp.getType().toUpperCase() + ")");
            nameLabel.setStyle("-fx-text-fill: #e8e8e8; -fx-font-weight: bold; -fx-font-size: 12;");

            Label statsLabel = new Label(String.format("⚡ Vitesse: %.2fx | ⭐ Qualité: %d%% | 📊 XP: %.0f",
                    emp.getSpeed(), (int) (emp.getQuality() * 100), emp.getExperience()));
            statsLabel.setStyle("-fx-text-fill: #aaa; -fx-font-size: 11;");

            Label salaryLabel = new Label(String.format("💸 Salaire: $%.0f/vague", emp.getSalary()));
            salaryLabel.setStyle("-fx-text-fill: #f4a261; -fx-font-size: 11; -fx-font-weight: bold;");

            Button upgradeBtn = new Button("⬆️ AMÉLIORER");
            upgradeBtn.setStyle("-fx-font-size: 11; -fx-padding: 8 12; -fx-background-color: #2d5f7d; -fx-text-fill: white; -fx-border-radius: 4; -fx-cursor: hand; -fx-font-weight: bold;");
            upgradeBtn.setMaxWidth(Double.MAX_VALUE);
            upgradeBtn.setOnAction(e -> showUpgradeDialog(emp));

            empBox.getChildren().addAll(nameLabel, statsLabel, salaryLabel, upgradeBtn);
            employeesList.getChildren().add(empBox);
        }
    }

    private void updateStockList() {
        stockList.getChildren().clear();
        stockList.setSpacing(8);

        for (Ingredient ing : game.getIngredients()) {
            HBox box = new HBox();
            box.setStyle("-fx-background-color: rgba(50, 70, 90, 0.8); -fx-padding: 10; -fx-border-radius: 6; -fx-border-color: #3d5a7a; -fx-border-width: 1; -fx-spacing: 12;");
            box.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            Label nameLabel = new Label(ing.getName());
            nameLabel.setStyle("-fx-text-fill: #e8e8e8; -fx-font-weight: bold; -fx-min-width: 70;");

            Label stockLabel = new Label((int) ing.getStock() + "");
            stockLabel.setStyle("-fx-text-fill: #52b788; -fx-font-weight: bold; -fx-min-width: 40; -fx-text-alignment: right; -fx-font-size: 13;");

            String statusStyle;
            String statusEmoji;
            if (ing.getStock() < 20) {
                statusStyle = "-fx-text-fill: #ff6b5b;";
                statusEmoji = "🔴";
            } else if (ing.getStock() < 40) {
                statusStyle = "-fx-text-fill: #f4a261;";
                statusEmoji = "🟠";
            } else {
                statusStyle = "-fx-text-fill: #52b788;";
                statusEmoji = "🟢";
            }

            Label statusLabel = new Label(statusEmoji);
            statusLabel.setStyle(statusStyle + " -fx-font-size: 14;");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            box.getChildren().addAll(nameLabel, spacer, stockLabel, statusLabel);
            stockList.getChildren().add(box);
        }
    }

    private void updateOrdersList() {
        orderQueue.getChildren().clear();
        orderQueue.setSpacing(8);

        int waiting = 0, preparing = 0, completed = 0;

        for (Order order : game.getOrders()) {
            if (order.getStatus().equals("waiting")) waiting++;
            else if (order.getStatus().equals("preparing")) preparing++;
            else if (order.getStatus().equals("completed")) completed++;

            if (orderQueue.getChildren().size() < 10) {
                VBox orderBox = new VBox();

                String borderColor;
                String statusEmoji;
                String statusText;
                if (order.getStatus().equals("completed")) {
                    borderColor = "#52b788";
                    statusEmoji = "✅";
                    statusText = "COMPLÉTÉE";
                } else if (order.getStatus().equals("preparing")) {
                    borderColor = "#f4a261";
                    statusEmoji = "⚙️";
                    statusText = "EN PRÉPARATION";
                } else if (order.getStatus().equals("assigned")) {
                    borderColor = "#2d5f7d";
                    statusEmoji = "📝";
                    statusText = "ASSIGNÉE";
                } else {
                    borderColor = "#ff6b5b";
                    statusEmoji = "📥";
                    statusText = "EN ATTENTE";
                }

                orderBox.setStyle("-fx-background-color: rgba(50, 70, 90, 0.8); -fx-padding: 12; -fx-border-radius: 6; -fx-border-width: 0 0 0 3; -fx-border-color: " + borderColor + "; -fx-spacing: 6;");

                Label cocktailLabel = new Label("🍹 " + order.getCocktail().getName());
                cocktailLabel.setStyle("-fx-text-fill: #e8e8e8; -fx-font-weight: bold; -fx-font-size: 12;");

                Label workflowLabel = new Label(String.format("%s %s | Serveur: %s | Barman: %s",
                        statusEmoji, statusText,
                        order.getServeurName(),
                        order.getBarmanName()));
                workflowLabel.setStyle("-fx-text-fill: #aaa; -fx-font-size: 10;");

                Label timeLabel = new Label(String.format("⏱️ Attente: %.1fs", order.getWaitTime()));
                timeLabel.setStyle("-fx-text-fill: #aaa; -fx-font-size: 11;");

                orderBox.getChildren().addAll(cocktailLabel, workflowLabel, timeLabel);
                orderQueue.getChildren().add(orderBox);
            }
        }

        if (game.getOrders().isEmpty()) {
            Label emptyLabel = new Label("📭 Aucune commande en attente");
            emptyLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12;");
            orderQueue.getChildren().add(emptyLabel);
        }

        waitingCount.setText(waiting + "");
        preparingCount.setText(preparing + "");
        completedCount.setText(completed + "");
    }

    private void showUpgradeDialog(Employee emp) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Améliorer " + emp.getName());
        dialog.setHeaderText(null);
        dialog.getDialogPane().setPrefWidth(420);
        dialog.getDialogPane().setStyle("-fx-background-color: #1a1f26;");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        VBox content = new VBox();
        content.setStyle("-fx-background-color: #1a1f26;");
        content.setSpacing(15);
        content.setPadding(new Insets(20));

        Label empLabel = new Label("⬆️ " + emp.getName().toUpperCase() + " - " + emp.getType().toUpperCase());
        empLabel.setStyle("-fx-text-fill: #ff6b5b; -fx-font-weight: bold; -fx-font-size: 14;");
        content.getChildren().add(empLabel);

        Separator sep = new Separator();
        sep.setStyle("-fx-border-color: #3d5a7a;");
        content.getChildren().add(sep);

        String[] stats = {"speed", "quality", "experience"};
        String[] labels = {"⚡ Vitesse", "⭐ Qualité", "📈 Expérience"};
        String[] descriptions = {"Prépare plus vite", "Meilleure qualité", "Augmente l'expérience"};
        String[] colors = {"#2d5f7d", "#52b788", "#f4a261"};

        for (int i = 0; i < stats.length; i++) {
            HBox itemBox = new HBox();
            itemBox.setStyle("-fx-background-color: rgba(50, 70, 90, 0.8); -fx-padding: 12; -fx-border-radius: 6; -fx-border-color: " + colors[i] + "; -fx-border-width: 1; -fx-spacing: 12;");
            itemBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            VBox infoBox = new VBox();
            infoBox.setSpacing(3);

            Label statLabel = new Label(labels[i]);
            statLabel.setStyle("-fx-text-fill: #e8e8e8; -fx-font-weight: bold; -fx-font-size: 12;");

            Label descLabel = new Label(descriptions[i]);
            descLabel.setStyle("-fx-text-fill: #999; -fx-font-size: 10;");

            infoBox.getChildren().addAll(statLabel, descLabel);

            Label costLabel = new Label("$" + (int) emp.getUpgradeCost(stats[i]));
            costLabel.setStyle("-fx-text-fill: #52b788; -fx-font-weight: bold; -fx-font-size: 13; -fx-min-width: 50;");

            Button upgradeBtn = new Button("UPGRADE");
            upgradeBtn.setStyle("-fx-padding: 8 16; -fx-font-size: 11; -fx-background-color: " + colors[i] + "; -fx-text-fill: white; -fx-border-radius: 4; -fx-cursor: hand; -fx-font-weight: bold;");
            final int index = i;
            upgradeBtn.setOnAction(e -> {
                engine.upgradeEmployee(emp.getId(), stats[index]);
                dialog.close();
                updateUI();
            });

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            itemBox.getChildren().addAll(infoBox, spacer, costLabel, upgradeBtn);
            content.getChildren().add(itemBox);
        }

        dialog.getDialogPane().setContent(content);
        dialog.show();
    }

    public VBox getRoot() {
        return root;
    }
}
