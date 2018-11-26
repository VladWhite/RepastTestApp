package belenkov.repast.ru.repasttestapp.controller.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import belenkov.repast.ru.repasttestapp.R
import belenkov.repast.ru.repasttestapp.controller.fragments.TakeTipOneFragment
import belenkov.repast.ru.repasttestapp.controller.helpers.Router
import belenkov.repast.ru.repasttestapp.model.api.RestaurantApi
import belenkov.repast.ru.repasttestapp.model.entities.Restaurant
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.restaurant_item.view.*

class ReviewItemsAdapter(
    private val items: ArrayList<Restaurant>,
    private val context: Context
) : RecyclerView.Adapter<ReviewItemsAdapter.ReviewItemsViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): ReviewItemsViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.restaurant_item, viewGroup, false)

        return ReviewItemsViewHolder(view)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(viewHolder: ReviewItemsViewHolder, index: Int) {
        val picasso = Picasso.Builder(context).build()
        picasso.load(items[index].photoUrl).into(viewHolder.restaurantImage)

        viewHolder.restaurantTitle.text = items[index].title
        viewHolder.restaurantRatingBar.numStars = items[index].rate.toInt()
        viewHolder.restaurantRatingBar.rating = items[index].rate.toFloat()
        viewHolder.restaurantVotesCount.text = items[index].numOfVotes
        viewHolder.restaurantAddress.text = items[index].address
        viewHolder.restaurantReview.text = items[index].reviewText
        viewHolder.restaurantTip.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("staff-list", items[index].staff)
            bundle.putString("restaurant-review", items[index].reviewText)
            Router.replaceFragment(context as AppCompatActivity, TakeTipOneFragment(), "tipone-staff", bundle)
        }

        viewHolder.restaurantCallButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:${items[index].phone}")
            context.startActivity(callIntent)
        }

        RestaurantApi.getFavoriteRestaurants()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                run {
                    if (it.contains(items[index]))
                        viewHolder.restaurantFavoriteButton.setImageResource(R.drawable.ic_star_active)
                    else
                        viewHolder.restaurantFavoriteButton.setImageResource(R.drawable.ic_star_inactive)
                }
            }

        viewHolder.restaurantFavoriteButton.setOnClickListener {
            RestaurantApi.getFavoriteRestaurants()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    run {
                        val favoriteRestaurant = items[index]
                        if (!it.contains(favoriteRestaurant)) {
                            RestaurantApi.addFavoriteRestaurant(favoriteRestaurant)
                            viewHolder.restaurantFavoriteButton.setImageResource(R.drawable.ic_star_active)
                        } else {
                            it.remove(favoriteRestaurant)
                            viewHolder.restaurantFavoriteButton.setImageResource(R.drawable.ic_star_inactive)
                        }
                    }
                }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ReviewItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val restaurantImage = view.restaurantImage!!
        val restaurantTitle = view.restaurantTitle!!
        val restaurantRatingBar = view.restaurantRatingBar!!
        val restaurantRate = view.restaurantRate!!
        val restaurantVotesCount = view.restaurantVotesCount!!
        val restaurantAddress = view.restaurantAddress!!
        val restaurantReview = view.restaurantReview!!
        val restaurantTip = view.restaurantTip!!
        val restaurantCallButton = view.restaurantCallButton!!
        val restaurantFavoriteButton = view.restaurantFavoriteButton!!
    }
}