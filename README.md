# AND LAB 4 - MVVM Architecture

## Questions

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
paysage <-> portrait).

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

### 6.2

> L’accès à la liste des notes issues de la base de données Room se fait avec
> une LiveData. Est-ce que cette solution présente des limites ? Si oui, quelles
> sont-elles ? Voyez-vous une autre approche plus adaptée ?

### 6.3

> Les notes affichées dans la RecyclerView ne sont pas sélectionnables ni
> cliquables. Comment procéderiez-vous si vous souhaitiez proposer une interface
> permettant de sélectionner une note pour l’éditer ?

Pour avoir des éléments cliquable dans le RecyclerView, il faut implémenter un
`Adapter` et un `ViewHolder`. Le `ViewHolder` est un wrapper autour d'un `View`
qui contient la disposition d'un élément individuel dans la liste. Et
l'`Adapter` crée les `ViewHolder` selon le besoin.

Dans la  documentation[^1] Android, nous pouvons voir qu'en définissant une
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
