package belenkov.repast.ru.repasttestapp.controller.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import belenkov.repast.ru.repasttestapp.R
import belenkov.repast.ru.repasttestapp.controller.fragments.TakeTipOneFragment
import belenkov.repast.ru.repasttestapp.controller.helpers.Expander
import belenkov.repast.ru.repasttestapp.controller.helpers.Router
import belenkov.repast.ru.repasttestapp.model.entities.Restaurant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_item.view.*

class FavoritesItemsAdapter(
    private val favoritesItems: ArrayList<Restaurant>,
    private val context: Context
) : RecyclerView.Adapter<FavoritesItemsAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): FavoritesItemsAdapter.FavoritesViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.restaurant_item_favorite, viewGroup, false)

        return FavoritesItemsAdapter.FavoritesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favoritesItems.size
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(viewHolder: FavoritesItemsAdapter.FavoritesViewHolder, index: Int) {
        val picasso = Picasso.Builder(context).build()
        picasso.load(favoritesItems[index].photoUrl).into(viewHolder.restaurantImage)

        viewHolder.restaurantTitle.text = favoritesItems[index].title
        viewHolder.restaurantAddress.text = favoritesItems[index].address
        viewHolder.restaurantReview.text = favoritesItems[index].reviewText
        viewHolder.restaurantTip.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("staff-list", favoritesItems[index].staff)
            bundle.putString("restaurant-review", favoritesItems[index].reviewText)
            Router.replaceFragment(context as AppCompatActivity, TakeTipOneFragment(), "tipone-staff", bundle)
        }
        viewHolder.restaurantCallButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:${favoritesItems[index].phone}")
            context.startActivity(callIntent)
        }

        val staffAdapter = FavotiesStaffAdapter(favoritesItems[index].staff, context)
        viewHolder.staffRecyclerView.layoutManager = LinearLayoutManager(context)
        viewHolder.staffRecyclerView.adapter = staffAdapter

        viewHolder.expandRestaurantCardButton.setOnClickListener {
            if (viewHolder.restaurantStaffLayout.visibility == View.VISIBLE)
                viewHolder.expandRestaurantCardButton.setImageResource(R.drawable.ic_expand_more)
            else
                viewHolder.expandRestaurantCardButton.setImageResource(R.drawable.ic_expand_less)

            Expander.expand(viewHolder.restaurantStaffLayout, 400)
        }
    }

    class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val restaurantImage = view.restaurantImage!!
        val restaurantTitle = view.restaurantTitle!!
        val restaurantAddress = view.restaurantAddress!!
        val restaurantReview = view.restaurantReview!!
        val restaurantTip = view.restaurantTip!!
        val restaurantCallButton = view.restaurantCallButton!!
        val staffRecyclerView = view.findViewById<RecyclerView>(R.id.staffRecyclerView)!!
        val expandRestaurantCardButton = view.findViewById<ImageButton>(R.id.expandRestaurantCardButton)!!
        val restaurantStaffLayout = view.findViewById<LinearLayout>(R.id.restaurantInfoLayout)!!
    }
}