package belenkov.repast.ru.repasttestapp.controller.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import belenkov.repast.ru.repasttestapp.R
import belenkov.repast.ru.repasttestapp.controller.adapters.ReviewItemsAdapter
import belenkov.repast.ru.repasttestapp.model.api.RestaurantApi
import belenkov.repast.ru.repasttestapp.model.entities.Restaurant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_review.view.*


class ReviewFragment() : Fragment() {

    private lateinit var list: ArrayList<Restaurant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_review, container, false)
        RestaurantApi.getRestourantList(context!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { restaurantsList ->
                run {
                    list = restaurantsList
                    val reviewRecycler = view.reviewRecycler
                    val adapter = ReviewItemsAdapter(list, context!!)
                    reviewRecycler.layoutManager = LinearLayoutManager(activity)
                    reviewRecycler.adapter = adapter
                    (activity as AppCompatActivity).supportActionBar!!.hide()
                }
            }
        return view
    }
}
