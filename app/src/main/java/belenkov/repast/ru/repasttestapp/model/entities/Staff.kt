package belenkov.repast.ru.repasttestapp.model.entities

import java.io.Serializable

data class Staff(
    val name: String,
    val position: String,
    var rate: String,
    val iconPath:String,
    val tipCount:Int
):Serializable