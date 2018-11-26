package belenkov.repast.ru.repasttestapp.controller.adapters

import android.content.Context
import android.os.Bundle
import belenkov.repast.ru.repasttestapp.controller.MainActivity
import belenkov.repast.ru.repasttestapp.controller.fragments.TakeTipTwoFragment
import belenkov.repast.ru.repasttestapp.controller.helpers.Router
import belenkov.repast.ru.repasttestapp.model.entities.Staff

class TakeTipStaffAdapter(
    private var restaurantReview:String,
    private var staffItems: ArrayList<Staff>,
    private val context: Context
) : FavotiesStaffAdapter(staffItems, context) {

    override fun onBindViewHolder(viewHolder: FavotiesStaffViewHolder, index: Int) {
        super.onBindViewHolder(viewHolder, index)
        viewHolder.staffLayout.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("staff", staffItems[index])
            bundle.putSerializable("restaurant-review", restaurantReview)
            Router.replaceFragment(context as MainActivity, TakeTipTwoFragment(), "taketip-two", bundle)
        }
    }
}