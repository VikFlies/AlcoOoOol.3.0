# 🍹 Bar Cocktails - Idle Tycoon Game

Un jeu de gestion de bar à cocktails en Java utilisant la **Programmation Orientée Objet** et **JavaFX**. Gérez votre bar, recruter des employés, préparez des cocktails et maximisez vos profits !

## 📋 Table des Matières

- [Caractéristiques](#-caractéristiques)
- [Architecture du Projet](#-architecture-du-projet)
- [Installation](#-installation)
- [Utilisation](#-utilisation)
- [Gameplay](#-gameplay)
- [Structure POO](#-structure-poo)
- [Technologies Utilisées](#-technologies-utilisées)
- [Auteur](#-auteur)

## ✨ Caractéristiques

### Gameplay
- 🌊 **Système de Vagues** : Gérez plusieurs vagues de commandes avec difficulté progressive
- 👥 **Gestion du Personnel** : Recruter Barmans et Serveurs, les améliorer et gérer leur satisfaction
- 🍸 **Catalogue de Cocktails** : Mojito, Margarita, Daiquiri, Gin Tonic, etc.
- 📊 **Système Économique** : Gagnez de l'argent, gérez les salaires, achetez du stock
- ⏱️ **Progression en Temps Réel** : Traitement automatique des commandes en 100ms intervals

### Interface
- 🎨 **Menu de Démarrage** : Interface élégante avec animations emoji
- 📈 **Dashboard en Temps Réel** : Affiche argent, satisfaction du staff, vagues en cours
- 🎮 **Gestion Intuitive** : Boutons pour recruter, améliorer, acheter du stock

### Systèmes Avancés
- **Système de Timing** : Chaque commande suit un cycle : Attente → Serveur (1s) → Barman (1s) → Complétée
- **Gestion des Stocks** : Consommation automatique d'ingrédients, achat de réapprovisionnement
- **Amélioration d'Employés** : Augmenter vitesse, qualité, coût croissant
- **Satisfaction du Staff** : Impact sur la performance, baisse si salaires impayés

## 🏗️ Architecture du Projet

### Structure de Répertoires

```
fr/bar/cocktails/
├── BarCocktailsMain.java          # Point d'entrée principal
├── engine/
│   └── GameEngine.java             # Moteur du jeu + boucle d'animation
├── game/
│   ├── Game.java                   # Logique métier principale
│   ├── Employee.java               # Classe abstraite des employés
│   ├── Barman.java                 # Spécialisation Employee
│   ├── Serveur.java                # Spécialisation Employee
│   ├── Cocktail.java               # Modèle de cocktail
│   ├── Ingredient.java             # Modèle d'ingrédient
│   ├── Order.java                  # Modèle de commande
│   └── Timer.java                  # Utilitaire de timing
└── view/
    ├── StartMenuUI.java            # Interface du menu principal
    └── GameUI.java                 # Interface du jeu
```

### Flux de Données

```
BarCocktailsMain
    └── GameEngine
        ├── Game (Logique métier)
        └── GameUI (Affichage)
            └── Mise à jour en temps réel (Platform.runLater)
```

## 💻 Installation

### Prérequis

- **Java 11+** (JDK 11 ou supérieur)
- **JavaFX SDK 21+**
- **Maven** ou IDE (IntelliJ IDEA, Eclipse)

### Étapes

1. **Cloner le projet**
   ```bash
   git clone <repository-url>
   cd Bar-Cocktails
   ```

2. **Compiler le projet**
   ```bash
   javac -cp ".:lib/*" src/fr/bar/cocktails/*.java src/fr/bar/cocktails/**/*.java
   ```

3. **Ajouter JavaFX au classpath** (si nécessaire)
    - Télécharger JavaFX SDK depuis [openjfx.io](https://openjfx.io)
    - Configurer dans votre IDE (VM options) :
      ```
      --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls
      ```

4. **Lancer l'application**
   ```bash
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls fr.bar.cocktails.BarCocktailsMain
   ```

## 🎮 Utilisation

### Démarrage

1. Lancez l'application
2. Cliquez sur **"NOUVELLE PARTIE"** depuis le menu principal
3. Le jeu commence avec 2000€ de départ

### Actions Disponibles

| Action | Effet |
|--------|-------|
| **Recruter Barman** | Ajoute un barman (coût: 300€) - Prépare les cocktails |
| **Recruter Serveur** | Ajoute un serveur (coût: 200€) - Prend les commandes |
| **Acheter Stock** | Ajoute des ingrédients pour préparer les cocktails |
| **Améliorer Employé** | Augmente vitesse/qualité (coût croissant) |
| **Démarrer Vague** | Lance une nouvelle vague de commandes |
| **Fin Vague** | Termine la vague, calcule les gains et salaires |

### Indicateurs de Santé

- 💰 **Argent** : Votre budget actuel
- 👥 **Satisfaction du Staff** : Affecte performance (< 50% = danger)
- 📊 **Vague Actuelle** : Numéro et difficulté
- ✅ **Commandes Complétées** : Dans la vague actuelle

## 🎯 Gameplay

### Cycle d'une Commande

```
1. ATTENTE (0s)
   └─ Commande en attente dans la file

2. SERVEUR (≈1000ms / vitesse serveur)
   └─ Serveur disponible prend la commande
   └─ Gagne 5 XP

3. BARMAN (≈1000ms / vitesse barman)
   └─ Barman disponible prépare le cocktail
   └─ Vérification des ingrédients
   └─ Gagne 15 XP

4. COMPLÉTÉE
   └─ Argent gagné (80% du prix du cocktail)
   └─ Commande supprimée
```

### Exemple de Progression

- **Vague 1** : Difficulté x1.0 → 3 commandes
- **Vague 2** : Difficulté x1.1 → Plus de commandes, plus rapides
- **Vague 3+** : Difficulté augmente (+10% par vague)

### Gestion Économique

```
Revenus vague = Σ(Prix cocktails × 0.8)
Coûts vague = Σ(Salaires employés)
Profit net = Revenus - Coûts
```

**⚠️ Attention** : Si vous n'avez pas assez d'argent pour les salaires :
- Satisfaction baisse (-20%)
- Argent devient 0

## 🏛️ Structure POO

### Hiérarchie des Classes

```
Employee (abstraite)
├── Barman
│   ├── speed: double (vitesse de préparation)
│   ├── quality: double (qualité des cocktails)
│   └── getUpgradeCost(): double
└── Serveur
    ├── speed: double (vitesse de service)
    ├── quality: double (charisme)
    └── getUpgradeCost(): double
```

### Interfaces et Abstraction

- **Employee** : Classe abstraite définissant l'interface commune
- **Game** : Façade gérant toute la logique métier
- **GameEngine** : Contrôleur reliant Game et GameUI
- **Order** : État machine (WAITING → ASSIGNED → PREPARING → COMPLETED)

### Principes SOLID Appliqués

✅ **Single Responsibility** : Chaque classe a un rôle unique
- `Game` : Logique métier
- `GameUI` : Présentation
- `GameEngine` : Orchestration

✅ **Open/Closed** : Extensible pour nouveaux employés/cocktails
- Héritage Employee pour Barman/Serveur
- ArrayList générique pour cocktails/ingrédients

✅ **Liskov Substitution** : Barman et Serveur interchangeables
- Les deux héritent d'Employee
- Même interface de gestion

✅ **Dependency Inversion** : GameEngine utilise abstractions
- GameEngine → GameUI (interface)
- Game → Employee (abstraite)

## 🛠️ Technologies Utilisées

| Technologie | Version | Rôle |
|-------------|---------|------|
| **Java** | 11+ | Langage principal |
| **JavaFX** | 21+ | Interface graphique |
| **POO** | - | Paradigme de programmation |
| **MVC** | Pattern | Architecture (Model-View-Controller) |

### Dépendances

```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>21.0.2</version>
</dependency>
```

## 📊 Statistiques du Code

| Métrique | Valeur |
|----------|--------|
| **Classes** | 12+ |
| **Lignes de Code** | ~2500+ |
| **Packages** | 3 (`engine`, `game`, `view`) |
| **Emplois disponibles** | 2 (Barman, Serveur) |
| **Cocktails** | 4+ (Mojito, Margarita, Daiquiri, Gin Tonic) |
| **Ingrédients** | 7 (Rhum, Vodka, Gin, Jus Citron, etc.) |

## 🐛 Débogage

### Logs Console

Le jeu affiche les événements importants :

```
VAGUE 1 COMMENCE!
Serveur-0 prend Mojito. Durée: 1000.0ms
Barman-0 prépare Mojito. Durée: 1000.0ms
Mojito TERMINE! Gains: 9.60€ +15 XP (Barman-0)
VAGUE 1 TERMINE
Commandes complétées: 5
Revenus: 48.00€
```

### Points de Vérification

- Vérifier `Game.processOrdersAutomatically()` pour le timing
- Vérifier `GameEngine.updateUI()` pour les synchronisations JavaFX
- Logs d'Employee pour tracking du XP et améliorations

## 📈 Améliorations Futures

- [ ] Système de sauvegarde/chargement persistant
- [ ] Achievements et statistiques globales
- [ ] Nouveaux types d'employés (gestionnaire, marketing)
- [ ] Upgrades de bar (équipement, décoration)
- [ ] Système d'événements aléatoires (inspections, clients VIP)
- [ ] Multijoueur / Leaderboards
- [ ] Animations améliorées
- [ ] Effets sonores et musique

## 📝 Licence

Ce projet est fourni à titre éducatif. Libre d'utilisation et de modification.

## 👨‍💻 Auteur

Développé en **Java POO + JavaFX** comme projet pédagogique de gestion de ressources et d'interface graphique.

---

**Bon jeu ! 🍹** Gérez votre bar avec intelligence et devenez le roi des cocktails !