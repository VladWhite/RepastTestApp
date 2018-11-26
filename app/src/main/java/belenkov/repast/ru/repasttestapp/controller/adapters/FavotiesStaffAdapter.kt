package belenkov.repast.ru.repasttestapp.controller.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import belenkov.repast.ru.repasttestapp.R
import belenkov.repast.ru.repasttestapp.model.entities.Staff
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_item_staff.view.*

open class FavotiesStaffAdapter(
    private var staffItems: ArrayList<Staff>,
    private val context: Context
) : RecyclerView.Adapter<FavotiesStaffAdapter.FavotiesStaffViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): FavotiesStaffViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.restaurant_item_staff, viewGroup, false)

        return FavotiesStaffAdapter.FavotiesStaffViewHolder(view)
    }

    override fun getItemCount(): Int {
        return staffItems.size
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(viewHolder: FavotiesStaffViewHolder, index: Int) {
        val picasso = Picasso.Builder(context).build()
        picasso.load(staffItems[index].iconPath).into(viewHolder.staffAvatarImageView)

        viewHolder.staffNameTextView.text = staffItems[index].name
        viewHolder.staffPositionTextView.text = staffItems[index].position

        if (staffItems[index].rate.toInt() == 0) {
            viewHolder.staffLikeButton.setImageResource(R.drawable.ic_like_inactive)
        } else {
            viewHolder.staffLikeButton.setImageResource(R.drawable.ic_like_active)
        }

        viewHolder.staffLikeButton.setOnClickListener {
            if (staffItems[index].rate.toInt() == 0) {
                staffItems[index].rate = "1"
                viewHolder.staffLikeButton.setImageResource(R.drawable.ic_like_active)
            } else {
                staffItems[index].rate = "0"
                viewHolder.staffLikeButton.setImageResource(R.drawable.ic_like_inactive)
            }
        }
    }

    class FavotiesStaffViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val staffAvatarImageView = view.commentAvaterImageView!!
        val staffNameTextView = view.commentNameTextView!!
        val staffPositionTextView = view.commentPositionTextView!!
        val staffLikeButton = view.commentLikeButton!!
        val staffLayout = view.staffLayout!!
    }
}