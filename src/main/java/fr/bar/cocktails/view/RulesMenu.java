package fr.bar.cocktails.view;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class RulesMenu extends BorderPane {

    private Runnable onBackCallback;

    public RulesMenu() {
        initializeUI();
    }

    private void initializeUI() {
        setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #1a1a2e 0%, #16213E 50%, #0f3460 100%);");
        setTop(createHeader());
        setCenter(createMainContent());
        setBottom(createFooter());
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setStyle("-fx-background-color: rgba(20, 25, 35, 0.95); -fx-border-color: #FF4ACF; -fx-border-width: 0 0 3 0; -fx-padding: 25;");
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);
        header.setPrefHeight(90);

        Label titleLabel = new Label("📋 RÈGLES DU JEU");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 42));
        titleLabel.setStyle("-fx-text-fill: #FF4ACF; -fx-effect: dropshadow(gaussian, #FF4ACF, 10, 0.4, 0, 0);");

        Label subtitleLabel = new Label("Découvrez les mécaniques de gestion du bar à cocktails");
        subtitleLabel.setFont(Font.font("Segoe UI", 16));
        subtitleLabel.setStyle("-fx-text-fill: #ecf0f1;");

        VBox titleBox = new VBox(5);
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);
        header.getChildren().add(titleBox);
        return header;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox();
        mainContent.setStyle("-fx-background-color: #1a1f26;");
        mainContent.setPadding(new Insets(40));

        VBox rulesContainer = new VBox(30);
        rulesContainer.setStyle("-fx-background-color: transparent;");

        rulesContainer.getChildren().add(createRuleSection(
                "🎯 OBJECTIF PRINCIPAL",
                "Votre mission est de gérer un bar à cocktails prospère. Servez les clients avec efficacité, gagnez de l'argent pour développer votre établissement, et maintenez la satisfaction de vos clients à un niveau optimal.",
                new String[] {
                        "💰 Générez des revenus en servant des cocktails",
                        "⭐ Maintenez la satisfaction des clients > 50%",
                        "📈 Progressez à travers les vagues de difficulté croissante"
                },
                "#52b788"
        ));

        rulesContainer.getChildren().add(createRuleSection(
                "👥 GESTION DES EMPLOYÉS",
                "Recruter des employés est essentiel pour gérer le flux de commandes. Chaque employé a des capacités différentes et doit être entretenu avec un salaire régulier.",
                new String[] {
                        "🧑‍💼 SERVEUR ($150 d'embauche) - Prend les commandes des clients",
                        "🍸 BARMAN ($200 d'embauche) - Prépare les cocktails",
                        "⬆️ AMÉLIORER - Augmentez vitesse, qualité, et expérience",
                        "💸 SALAIRES - Payés à chaque fin de vague"
                },
                "#f4a261"
        ));

        rulesContainer.getChildren().add(createRuleSection(
                "📦 GESTION DU STOCK",
                "Les ingrédients sont limités et doivent être achetés régulièrement. Sans stock suffisant, vous ne pourrez pas préparer les cocktails demandés.",
                new String[] {
                        "🛒 ACHETEZ des ingrédients en lots de 10 ou 25",
                        "📊 SURVEILLEZ les niveaux de stock (vert ✅, orange ⚠️, rouge 🔴)",
                        "💵 Gérez votre budget pour acheter et payer les salaires",
                        "⚠️ RUPTURE DE STOCK = Commandes non complétées = Perte d'argent"
                },
                "#2d5f7d"
        ));

        rulesContainer.getChildren().add(createRuleSection(
                "⚙️ FLUX DE TRAVAIL (WORKFLOW)",
                "Chaque commande suit un processus automatique en trois étapes. L'efficacité de votre équipe détermine votre succès.",
                new String[] {
                        "1️⃣ ATTENTE (📥) - Commande reçue, en attente d'un serveur",
                        "2️⃣ REMISE AU BARMAN (📝) - Le serveur note la commande",
                        "3️⃣ PRÉPARATION (⚙️) - Le barman prépare le cocktail",
                        "4️⃣ COMPLÉTÉE (✅) - Commande terminée = Gain de $"
                },
                "#ff6b5b"
        ));

        rulesContainer.getChildren().add(createRuleSection(
                "💰 GESTION FINANCIÈRE",
                "L'argent est votre ressource principale. Gérez-le bien pour survivre et prospérer.",
                new String[] {
                        "💵 REVENUS - 80% du prix du cocktail par commande complétée",
                        "💸 DÉPENSES - Salaires des employés + Stock + Embauche",
                        "⚠️ DÉFICIT - Manquer d'argent diminue la satisfaction",
                        "📊 REVENU VAGUE - Consultez vos revenus en temps réel"
                },
                "#FFD700"
        ));

        rulesContainer.getChildren().add(createRuleSection(
                "📈 SYSTÈME DE VAGUES",
                "Chaque vague est une manche de jeu. Plus vous progressez, plus les défis augmentent.",
                new String[] {
                        "🌊 COMMANDES INITIALES - Augmente avec la difficulté",
                        "⬆️ DIFFICULTÉ - Multiplie le nombre de commandes chaque vague",
                        "🎮 DÉMARRER/TERMINER - Contrôlez le timing de chaque vague",
                        "🏁 FIN DE VAGUE - Payez les salaires, améliorez votre équipe"
                },
                "#8b5cf6"
        ));

        rulesContainer.getChildren().add(createRuleSection(
                "💡 CONSEILS STRATÉGIQUES",
                "Voici quelques astuces pour réussir dans le bar à cocktails.",
                new String[] {
                        "🎯 ÉQUILIBRE - Embauchez barmans ET serveurs (ils travaillent ensemble)",
                        "📦 STOCK - Gardez toujours au moins 30 unités de chaque ingrédient",
                        "💪 AMÉLIORATIONS - Investissez dans la vitesse de vos employés",
                        "🔄 BOUCLE - Plus d'employés = Plus de commandes = Plus d'argent"
                },
                "#FF4ACF"
        ));

        ScrollPane scrollPane = new ScrollPane(rulesContainer);
        scrollPane.setStyle("-fx-control-inner-background: #1a1f26; -fx-background-color: #1a1f26; -fx-focus-color: transparent;");
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        mainContent.getChildren().add(scrollPane);
        return mainContent;
    }

    private VBox createRuleSection(String title, String description, String[] points, String color) {
        VBox section = new VBox();
        section.setStyle("-fx-background-color: rgba(30, 40, 55, 0.9); -fx-border-color: " + color + "; -fx-border-width: 0 0 0 4; -fx-padding: 20; -fx-border-radius: 8; -fx-spacing: 15;");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titleLabel.setStyle("-fx-text-fill: " + color + ";");

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Segoe UI", 13));
        descLabel.setStyle("-fx-text-fill: #d0d0d0; -fx-wrap-text: true;");
        descLabel.setWrapText(true);

        Separator sep = new Separator();
        sep.setStyle("-fx-border-color: " + color + "; -fx-opacity: 0.3;");

        VBox pointsBox = new VBox(8);
        for (String point : points) {
            Label pointLabel = new Label(point);
            pointLabel.setFont(Font.font("Segoe UI", 12));
            pointLabel.setStyle("-fx-text-fill: #b0b0b0;");
            pointLabel.setWrapText(true);
            pointsBox.getChildren().add(pointLabel);
        }

        section.getChildren().addAll(titleLabel, descLabel, sep, pointsBox);

        section.setOnMouseEntered(e -> {
            section.setStyle("-fx-background-color: rgba(40, 50, 70, 0.95); -fx-border-color: " + color + "; -fx-border-width: 0 0 0 4; -fx-padding: 20; -fx-border-radius: 8; -fx-spacing: 15;");
            section.setScaleX(1.02);
            section.setScaleY(1.02);
        });
        section.setOnMouseExited(e -> {
            section.setStyle("-fx-background-color: rgba(30, 40, 55, 0.9); -fx-border-color: " + color + "; -fx-border-width: 0 0 0 4; -fx-padding: 20; -fx-border-radius: 8; -fx-spacing: 15;");
            section.setScaleX(1.0);
            section.setScaleY(1.0);
        });

        return section;
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setStyle("-fx-background-color: rgba(20, 25, 35, 0.95); -fx-border-color: #FF4ACF; -fx-border-width: 3 0 0 0; -fx-padding: 25;");
        footer.setAlignment(Pos.CENTER);
        footer.setSpacing(20);
        footer.setPrefHeight(80);

        Region spacerLeft = new Region();
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);

        Button backButton = new Button("🔙 RETOUR AU MENU");
        backButton.setStyle("-fx-font-size: 16; -fx-padding: 15 40; -fx-background-color: #FF4ACF; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;");
        backButton.setMinWidth(250);

        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-font-size: 16; -fx-padding: 15 40; -fx-background-color: #FF6BA5; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-font-size: 16; -fx-padding: 15 40; -fx-background-color: #FF4ACF; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;"));

        backButton.setOnAction(e -> {
            animateExit();
            if (onBackCallback != null) onBackCallback.run();
        });

        Region spacerRight = new Region();
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        footer.getChildren().addAll(spacerLeft, backButton, spacerRight);
        return footer;
    }

    private void animateExit() {
        FadeTransition fade = new FadeTransition(Duration.millis(300), this);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.play();
    }

    public void setOnBackCallback(Runnable callback) {
        this.onBackCallback = callback;
    }
}
