> Destiné à but pédagogique j'ai réalisé ce projet en lien avec [RenardBOT](https://github.com/RenardBOT) à mes débuts en informatique à l'université. Il est donc loin de respecter toutes les règles de l'art en développement et en programmation. N'hésitez pas à corriger, compléter, modifier, ou d'ajouter vos remarques et bonnes pratiques soit par l'intermédiaire de Pull requests ou d'issues, c'est aussi pour ça que je le partage avec la communauté Github 💪 😀
# • Table des matières

- Intro
- Les consignes
  - Références

# • Présentation

## Intro

Ce répertoire contient le programme CoreWar en lien avec le [module d'info4B ( Licence 2 )](https://github.com/EricLeclercq/Teaching-Operating-Systems-Info4B).

Responsable du module : [Eric Leclercq](https://github.com/EricLeclercq)

## Les consignes

On se propose d'appliquer les concepts des systèmes d'exploitation afin de réaliser un simulateur Mars dont la description se trouve dans l'article de A. K. Dewdney cité dans les références et dont la traduction en français sera déposée sur la plateforme Plubel. Votre simulateur doit être capable d'exécuter simultanément plusieurs programmes écrits en Red Code. La taille mémoire de la machine est fixée à l'initialisation du jeu. Le programme doit déclarer un joueur gagnant et classer le ou les autres joueurs. 

Les programmes Mars sont des fichiers texte que l'on charge en début de partie, qui sont interprétés lors de l'exécution (voir la description de la syntaxe dans les références).

En plus des éléments décrits dans les références, les fonctionnalités principales que vous avez à ajouter sont de deux types : 

* par rapport à l'article original de Dewdney vous devez permettre la soumission de programmes par le réseau, c'est-à-dire développer une partie serveur et une partie client. Un même serveur peut héberger plusieurs parties se déroulant en même temps (figure 1). Une partie démarre lorsque chaque joueur participant a envoyé son programme ; 
* par rapport à la gestion des joueurs et des programmes : à mesure des parties gagnées un joueur progresse dans le classement (stocké sur la machine qui héberge le programme serveur). On gardera aussi le hit parade des noms des programmes gagnants. Avant de lancer une partie, il est possible de consulter les classements. Si vous souhaitez réaliser d'autres extensions du langage Redcode, votre interpréteur devra être capable de supporter deux syntaxes : celle des programmes en RedCode Base correspondant à descrip-tion du langage dans l'article de A. Dewdney et la syntaxe du Red Code Étendu correspondant à vos ajouts. On doit pouvoir sélectionner l'une des syntaxes au lancement des programmes. 
<p align="center">
  <img src="https://raw.githubusercontent.com/KieceDonc/CoreWars/main/figure1.PNG"/>
</p>
<h3 align="center">
  Figure 1
</h3>

### Références : 

Article original en anglais :

https://www.corewars.org/sciam/ 

http://www.koth.org/index.html 

https://fr.wikipedia.org/wiki/Core_War 


