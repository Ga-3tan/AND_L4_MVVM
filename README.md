# AND LAB 4 - MVVM Architecture

## Questions

### 6.1

> Quelle est la meilleure approche pour sauver le choix de l’option de tri de la liste des notes ? Vous justifierez votre réponse et l’illustrez en présentant le code mettant en œuvre votre approche.

### 6.2

> L’accès à la liste des notes issues de la base de données Room se fait avec une LiveData. Est- ce que cette solution présente des limites ? Si oui, quelles sont-elles ? Voyez-vous une autre approche plus adaptée ?

### 6.3

> Les notes affichées dans la RecyclerView ne sont pas sélectionnables ni cliquables. Comment procéderiez-vous si vous souhaitiez proposer une interface persmettant de sélectionner une note pour l’éditer ?

C'est de la bricole. Soit faire a la main (implémenter ClickListener depuis le view holder et faire remonter l'élénement. Il faut aussi faire à la main de changement de l'état de l'élément de la liste). Soit utiliser une librairie en plus (a chercher) qui permet de le faire mais qui pue vraiment.
