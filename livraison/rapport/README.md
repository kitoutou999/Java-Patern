# Règles du jeu

## Déplacement du joueur

+ Le joueur se déplace d'une case.

+ Le joueur peut se déplacer en haut, en bas, à droite et à gauche.

+ Le joueur ne peut pas se déplacer sur une case qui contient un mur ou une bombe.

## Déployer une mine

+ La mine peut être déployé sur une des 8 cases autour du joueur.

+ La mine ne peut pas être posé sur une case qui contient un mur ou un joueur.

+ La mine peut exploser si on marche dessus ou que l'on pose une autre mine/bombe dessus.

+ La mine est visible pour le joueur qui l'a déployé uniquement.

## Déployer une bombe

+ La bombe peut être déployé sur une des 8 cases autour du joueur.

+ La bombe ne peut pas être posé sur une case qui contient un mur, un joueur ou une autre bombe.

+ La bombe peut exploser quand son timer est à 0.

+ La bombe est visible par tout les joueurs (possibilité de changer cette règle).

## Détonation d'un explosif

+ Quand un explosif détonne il fait des dégats autour de lui (rayon 1 par défaut).

+ Quand un explosif détonne il n'y a pas de réaction en chaîne pour éviter de faire exploser tous les explosifs de la grille.

+ Quand un explosif détonne il détruit les autres explosifs autour de lui et il détruit les pillules d'énergie.

## Tirer sur un joueur

+ Un joueur peut tirer à l'horizontal ou à la vertical.

+ Un tir n'a pas de porté minimal ou maximal.

+ Un tir réussi fait 5 (par défaut) de dégats au joueur ennemi.

## Déclencher un bouclier

+ Pour le moment pas de bouclier.

## Energie

+ Pour le moment pas de système d'énergie.

+ Le joueur à des PV et peut faire une seule action par tour.


