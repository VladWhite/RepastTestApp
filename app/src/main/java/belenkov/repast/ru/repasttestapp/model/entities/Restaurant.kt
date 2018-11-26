package belenkov.repast.ru.repasttestapp.model.entities

import java.io.Serializable

data class Restaurant(
    val id: String,
    val title: String,
    var rate: String,
    val numOfVotes: String,
    val address: String,
    val reviewText: String,
    val phone: String,
    val qrCode: String,
    val photoUrl: String,
    val comments: ArrayList<Comment>,
    val staff: ArrayList<Staff>
) : Serializable