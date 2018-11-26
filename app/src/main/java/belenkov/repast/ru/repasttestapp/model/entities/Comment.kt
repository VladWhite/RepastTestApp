package belenkov.repast.ru.repasttestapp.model.entities

import java.io.Serializable

data class Comment(
    var username: String,
    var commentText: String,
    var iconPath: String
) : Serializable