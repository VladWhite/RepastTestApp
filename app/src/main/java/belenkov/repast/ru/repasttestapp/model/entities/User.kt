package belenkov.repast.ru.repasttestapp.model.entities

import java.io.Serializable

data class User(
    val name: String,
    val commentText: String,
    val iconPath: String
):Serializable