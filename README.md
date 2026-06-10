
## 🎮 Fonctionnalités Implémentées

L'intégralité du sujet des **Phases 1 et 2** a été implémentée.

### 1. Structure de Jeu et Moteur (`Engine`)
* **Déroulement de la partie** : Le jeu se joue en **3 manches** (le joueur gagne s'il remporte au moins 2 des 3 manches).
* **Moteur de jeu (`GameEngine`)** : Gère l'initialisation de la partie, les tours de jeu alternés, et la phase d'attaque à la fin de chaque tour.
* **Affichage console dynamique** : Représentation ASCII du plateau de jeu (3 lignes de 4 emplacements), du score (balance), de la main et de la pioche.

### 2. Cartes et Plateau de Jeu (`modele.board`)
* **Grille de jeu (`Board`)** : Divisée en 3 lignes : la file d'attente de l'adversaire (indiquant ses intentions de jeu), la ligne active de l'adversaire, et la ligne du joueur.
* **Obstacles** : Présence d'obstacles destructibles (`Rocher` et `Sapin`) placés de manière semi-aléatoire en début de manche.
* **Cartes animaux** : Implémentation de toutes les créatures avec affichage de leurs caractéristiques et de leurs pouvoirs (Écureuil, Chat, Moineau, Corbeau, Hermine, Louveteau, Loup, Grizzly, Coyote, Punaise, Élan, Vipère, Porc-épic).

### 3. Gestion des Joueurs et Ressources (`modele.player`)
* **Ressources d'invocation** : 
  * Coût en **Sang** (gouttes de sang) : nécessite le sacrifice de créatures du joueur déjà présentes sur le plateau.
  * Coût en **Os** : accumulés à chaque fois qu'une créature du joueur meurt (cimetière `Graves`).
* **Pioche et Main** : Pioche de cartes avec mélange initial et gestion de la main (`Hand`).
* **Intelligence Artificielle (`OpponentAI`)** : L'adversaire joue de manière prévisible selon une file d'attente de cartes définie, assurant une stratégie déterministe propice aux tests.

### 4. Pouvoirs Spéciaux (`modele.Power`)
Tous les pouvoirs de la Phase 2 ont été implémentés à l'aide d'un design orienté objet polymorphe :
* **Nombreuses Vies (`NombreuseVie`)** : Le Chat reste en vie sur le plateau lorsqu'il est sacrifiée.
* **Croissance (`Croissance`)** : Le Louveteau se transforme automatiquement en Loup au début du tour suivant son placement, **en conservant tous ses autres pouvoirs** (transférés via la Pierre de Sacrifice).
* **Puant (`Puant`)** : Réduit de 1 point l'attaque de la créature adverse qui lui fait face.
* **Coureur (`Coureur`)** : L'Élan se déplace d'un emplacement vers la droite après avoir attaqué (ou vers la gauche si la droite est bloquée).
* **Contact Mortel (`ContactMortel`)** : La Vipère élimine instantanément toute créature à laquelle elle inflige des dégâts (ne fonctionne pas sur les obstacles).
* **Piques Pointues (`PiquesPointues`)** : Le Porc-épic inflige 1 point de dégât en retour à toute créature qui l'attaque.

### 5. Pierre de Sacrifice (Événement Inter-manches)
À la fin de la deuxième manche (et après avoir choisi une nouvelle carte à ajouter au Deck parmi 3 propositions), le joueur accède à la Pierre de Sacrifice :
* Permet de choisir une carte de son deck à sacrifier pour récupérer son pouvoir.
* Permet ensuite de transférer ce pouvoir sur une autre carte animal du deck.
* La carte sacrifiée est définitivement retirée du deck.

---

## 🧪 Tests Unitaires et d'Intégration (`tests`)

Pour valider le bon fonctionnement de l'application, nous avons écrit une suite complète de tests unitaires et d'intégration avec JUnit 4/5.

### Liste des Suites de Tests et Couverture :
1. **Attaque et Combat** :
   * [GameEngineTest.java](file:///home/nexxo/project-inscryption/tests/Engine/GameEngineTest.java) : Teste les combats simples, les dégâts directs, le surplus de dégâts infligés au score lors de la mort d'une créature bloqueuse, et le vol (Moineau/Corbeau) passant par-dessus les bloqueurs.
   * [SimulationTour.java](file:///home/nexxo/project-inscryption/tests/Engine/SimulationTour.java) : Test d'intégration simulant un tour complet de jeu (saisies utilisateur simulées, pioche, placement, résolution de combat, attaques de l'IA et mise à jour du score).
2. **Pouvoirs Individuels** :
   * [NombreuseVieTest.java](file:///home/nexxo/project-inscryption/tests/modele/Power/NombreuseVieTest.java) : Vérifie que le sacrifice d'une carte avec ce pouvoir ne la retire pas du plateau.
   * [CroissanceTest.java](file:///home/nexxo/project-inscryption/tests/modele/Power/CroissanceTest.java) : Vérifie la transformation du Louveteau en Loup au début du tour et le transfert de ses autres pouvoirs.
   * [PuantTest.java](file:///home/nexxo/project-inscryption/tests/modele/Power/PuantTest.java) : Vérifie la réduction d'attaque subie par la créature en face.
   * [CoureurTest.java](file:///home/nexxo/project-inscryption/tests/modele/Power/CoureurTest.java) : Vérifie le déplacement latéral après l'attaque et le correctif anti-multi-attaques.
   * [ContactMortelTest.java](file:///home/nexxo/project-inscryption/tests/modele/Power/ContactMortelTest.java) : Vérifie que la Vipère élimine une créature adverse en un coup mais n'applique pas cet effet aux obstacles.
   * [PiquesPointuesTest.java](file:///home/nexxo/project-inscryption/tests/modele/Power/PiquesPointuesTest.java) : Vérifie les dégâts de retour infligés à l'attaquant.
3. **Composants** :
   * [BoardTest.java](file:///home/nexxo/project-inscryption/tests/modele/board/BoardTest.java) : Teste la grille, le placement et le déplacement de cartes.
   * [CardFactoryTest.java](file:///home/nexxo/project-inscryption/tests/modele/board/CardFactoryTest.java) : Teste la création correcte de chaque type de carte et d'obstacle.
   * [ScoreTest.java](file:///home/nexxo/project-inscryption/tests/modele/board/ScoreTest.java) : Teste le calcul du score et la détection de victoire/défaite (balance à 5/-5).
   * [SlotTest.java](file:///home/nexxo/project-inscryption/tests/modele/board/SlotTest.java) : Teste l'état vide/occupé des slots et la gestion du cimetière.
   * [DeckTest.java](file:///home/nexxo/project-inscryption/tests/modele/player/DeckTest.java) : Gère la pioche, la distribution et le mélange.
   * [HandTest.java](file:///home/nexxo/project-inscryption/tests/modele/player/HandTest.java) : Gestion de la main et de ses index.
   * [GravesTest.java](file:///home/nexxo/project-inscryption/tests/modele/player/GravesTest.java) : Gestion des os lors de la mort d'une carte.
   * [OpponentAITest.java](file:///home/nexxo/project-inscryption/tests/modele/player/OpponentAITest.java) : Teste le comportement déterministe de placement de l'IA.
4. **Validation et Saisie** :
   * [InputHandelerTest.java](file:///home/nexxo/project-inscryption/tests/vue/InputHandelerTest.java) : Vérifie la gestion des commandes utilisateurs, le filtrage des mauvaises entrées et la validation des coordonnées du plateau.

---



## 🗺️ Conception et Architecture (UML)

Le diagramme de classes de la semaine 5 est disponible sous [semaine5.puml](file:///home/nexxo/project-inscryption/uml/semaine5.puml). Il documente :
* Le découpage propre en packages (`modele`, `modele.board`, `modele.player`, `modele.Power`, `vue`, `Engine`).
* L'absence de couplage direct via des chaînes de getters grâce à l'application de la **loi de Déméter**.
* La structure du système de pouvoirs basée sur le polymorphisme.
