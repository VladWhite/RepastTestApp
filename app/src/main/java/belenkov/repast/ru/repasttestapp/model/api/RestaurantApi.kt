package belenkov.repast.ru.repasttestapp.model.api

import android.content.Context
import belenkov.repast.ru.repasttestapp.model.entities.Comment
import belenkov.repast.ru.repasttestapp.model.entities.Restaurant
import belenkov.repast.ru.repasttestapp.model.entities.Staff
import io.reactivex.Single
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class RestaurantApi {
    companion object {
        private const val RESTAURANTS_JSON_ARRAY = "restaurants"
        private const val ID_FIELD = "id"
        private const val TITLE_FIELD = "title"
        private const val RATE_FIELD = "rate"
        private const val ADDRESS_FIELD = "address"
        private const val PHONE_FIELD = "phone"
        private const val QRCODE_FIELD = "qrCode"
        private const val PHOTOURL_FIELD = "photoUrl"
        private const val VOICES_FIELD = "numOfVotes"
        private const val COMMENTS_FIELD = "comments"
        private const val COMMENT_FIELD = "comment"
        private const val REVIEW_FIELD = "reviewText"
        private const val STAFF_FIELD = "staff"
        private const val USERNAME_FIELD = "username"
        private const val POSITION_FIELD = "position"
        private const val ICON_FIELD = "icon"

        private const val RESPONSE_FILEPATH = "response.json"
        private const val JSON_CHARSET = "UTF-8"

        private val favoriteRestaurantsList = ArrayList<Restaurant>()

        fun addFavoriteRestaurant(restaurant: Restaurant){
            favoriteRestaurantsList.add(restaurant)
        }

        fun getFavoriteRestaurants():Single<ArrayList<Restaurant>>{
            return Single.just(favoriteRestaurantsList)
        }

        fun getRestourantList(context: Context): Single<ArrayList<Restaurant>> {
            return Single.create {
                try {
                    val restaurantList = arrayListOf<Restaurant>()
                    val inputStream = context.assets.open(RESPONSE_FILEPATH)
                    val buffer = ByteArray(inputStream.available())
                    inputStream.read(buffer)
                    inputStream.close()

                    val json = String(buffer, Charset.forName(JSON_CHARSET))

                    val jsonObj = JSONObject(json)
                    val restaurantsJsonArray = jsonObj.getJSONArray(RESTAURANTS_JSON_ARRAY)

                    for (i in 0 until restaurantsJsonArray.length()) {
                        val restaurantJson = restaurantsJsonArray.getJSONObject(i)
                        val id = restaurantJson.get(ID_FIELD) as String
                        val title = restaurantJson.get(TITLE_FIELD) as String
                        val rate = restaurantJson.get(RATE_FIELD) as String
                        val numOfVotes = restaurantJson.get(VOICES_FIELD) as String
                        val reviewText = restaurantJson.get(REVIEW_FIELD) as String
                        val address = restaurantJson.get(ADDRESS_FIELD) as String
                        val phone = restaurantJson.get(PHONE_FIELD) as String
                        val qrCode = restaurantJson.get(QRCODE_FIELD) as String
                        val photoUrl = restaurantJson.get(PHOTOURL_FIELD) as String

                        val commentsList = getComments(restaurantJson)
                        val staffList = getStaff(restaurantJson)

                        restaurantList.add(
                            Restaurant(
                                id,
                                title,
                                rate,
                                numOfVotes,
                                address,
                                reviewText,
                                phone,
                                qrCode,
                                photoUrl,
                                commentsList,
                                staffList
                            )
                        )
                    }
                    it.onSuccess(restaurantList)
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }

        private fun getComments(jsonObj: JSONObject): ArrayList<Comment> {
            val comments: JSONArray = jsonObj.getJSONArray(COMMENTS_FIELD)
            val commentsList = arrayListOf<Comment>()

            for (j in 0 until comments.length()) {
                val commentsJson = comments.getJSONObject(j)
                val userName = commentsJson.get(USERNAME_FIELD) as String
                val commentText = commentsJson.get(COMMENT_FIELD) as String
                val iconPath = commentsJson.get(ICON_FIELD) as String

                commentsList.add(Comment(userName, commentText, iconPath))
            }
            return commentsList
        }

        private fun getStaff(jsonObj: JSONObject): ArrayList<Staff> {
            val staff: JSONArray = jsonObj.getJSONArray(STAFF_FIELD)
            val staffList = arrayListOf<Staff>()

            for (j in 0 until staff.length()) {
                val staffJsonObject = staff.getJSONObject(j)
                val staffName = staffJsonObject.get(USERNAME_FIELD) as String
                val position = staffJsonObject.get(POSITION_FIELD) as String
                val rate = staffJsonObject.get(RATE_FIELD) as String
                val iconPath = staffJsonObject.get(ICON_FIELD) as String

                staffList.add(Staff(staffName, position, rate, iconPath, 0))
            }
            return staffList
        }
    }
}