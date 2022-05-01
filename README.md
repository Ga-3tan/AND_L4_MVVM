# AND LAB 4 - MVVM Architecture

> Auteurs : MAZIERO Marco, ZWICK Gaétan, LAMRANI Soulaymane

## Questions

---

### 6.1

> Quelle est la meilleure approche pour sauver le choix de l’option de tri de la
> liste des notes ? Vous justifierez votre réponse et l’illustrez en présentant
> le code mettant en œuvre votre approche.

Comme le choix de l'option de tri n'est « utile » que pendant que l'utilisateur
est sur l'application, on enregistre le choix de l'option de tri dans le
NotesViewModel, car comme vu en cours, dès qu'il s'agit de faire une sauvegarde
d'état (donc pas sauvegarde persistant), il faut sauver l'information dans le
ViewModel histoire que la variable ne soit pas réinitialisée lorsque que
l'activité est détruite puis reconstruite (pendant un changement d'orientation
paysage ⟷ portrait).

Et voici comment nous avons mis en œuvre la sauvegarde du tri de la liste de
notes.

```kotlin
private val _allNotesOption: MutableLiveData<Int> by lazy{MutableLiveData(-1)}

// Switches the live data source when a sort option is chosen
val allNotes = _allNotesOption.switchMap { sortOption ->
    when(sortOption){
        0 -> repository.allNotesCreationDate
        1 -> repository.allNotesETA
        else -> repository.allNotes
    }
}
```

On remarque que pour déclencher le notify au changement de la live data de notes, on utilise une live data intermédiaire contenant de simples entiers. Au changement de la live data intermédiaire, on map ce changement sur la live data de notes (on change la source de la live data). Cela notifie les observeurs et met à jour la liste de notes.

---

### 6.2

> L’accès à la liste des notes issues de la base de données Room se fait avec
> une LiveData. Est-ce que cette solution présente des limites ? Si oui, quelles
> sont-elles ? Voyez-vous une autre approche plus adaptée ?

Il existe certains problèmes de performances lorsque beaucoup de données sont présentes dans la base de données. En effet, si l'UI observe une live data qui pointe vers des données de la base de données, il y aura une requête que sera faite à chaque fois qu'une ligne de la base de données est modifiée (même si cette ligne n'est pas dans le résultat).

Des requêtes de type `SELECT * FROM table` vont charger toutes les données de la table `table` en mémoire. Cela n'est pas adapté si il y a beaucoup de données (car on récupère toutes les notes d'un coup).

On pourrait résoudre ce problème en faisant de la pagination (avec Paging de Jetpack) pour récupérer des bouts ou bien utiliser un Flow afin de recevoir des données "en live" tel un stream depuis base de donnée (au lieu de tout attendre d'un coup)

---

### 6.3

> Les notes affichées dans la RecyclerView ne sont pas sélectionnables ni
> cliquables. Comment procéderiez-vous si vous souhaitiez proposer une interface
> permettant de sélectionner une note pour l’éditer ?

Pour avoir des éléments cliquable dans le RecyclerView, il faut implémenter l'interface `onClickListener` dans le `ViewHolder` de l'adapter de la recycler view. Autrement dit, il faut implémenter la gestion du click manuellement, il n'y a pas de façon simple de le faire.

Dans la documentation[^1] Android, nous pouvons voir qu'en définissant une
classe `ViewHolder` à l'intérieur d'un `Adapter` custom, on peut définir un
*click listener* sur la vue.

[^1]: https://developer.android.com/guide/topics/ui/layout/recyclerview

Donc une manière de mettre ça en œuvre, serait de de faire un `ViewHolder`
custom qui aurait une fonction `bind` et aurait parmi ses arguments un
`OnItemClickListener`. Et ensuite on ferait un `Adapter` custom qui override
`onBindViewHolder`. Et enfin on ajoute l'interface `OnItemClickListener` dans
l'activité et on override la méthode `onItemClicked` afin de faire un intent et
de lancer une nouvelle activité.

Voici un exemple d'implémentation[^2] de RecyclerView cliquable.

[^2]: https://medium.com/android-gate/recyclerview-item-click-listener-the-right-way-daecc838fbb9

Une autre option est d'utiliser une librairie offran la gestion des intéractions avec les éléments d'une recycler view. Par exemple `RecyclerViewEnhanced`, une librairie offrant ce genre de listes. Le problème est que les librairies sont souvent très complexes à mettre en place.

[Android Library to provide swipe, click and other functionality to RecyclerView | AndroidRepo](https://androidrepo.com/repo/nikhilpanju-RecyclerViewEnhanced-android-recyclerview)
