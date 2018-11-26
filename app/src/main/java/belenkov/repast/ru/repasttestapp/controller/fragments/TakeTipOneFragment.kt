package belenkov.repast.ru.repasttestapp.controller.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import belenkov.repast.ru.repasttestapp.R
import belenkov.repast.ru.repasttestapp.controller.adapters.TakeTipStaffAdapter

import belenkov.repast.ru.repasttestapp.model.entities.Staff
import kotlinx.android.synthetic.main.fragment_take_tip_one.view.*

class TakeTipOneFragment : Fragment() {

    private lateinit var list: ArrayList<Staff>
    private lateinit var restaurantReview: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = arguments!!.get("staff-list") as ArrayList<Staff>
        restaurantReview = arguments!!.getString("restaurant-review")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_take_tip_one, container, false)
        val takeTipStepOneBackButton = view.takeTipStepOneBackButton
        val reviewRecycler = view.takeTipStepOneStaffRecycler
        val adapter = TakeTipStaffAdapter(restaurantReview, list, context!!)
        reviewRecycler.layoutManager = LinearLayoutManager(activity)
        reviewRecycler.adapter = adapter

        takeTipStepOneBackButton.setOnClickListener {
            activity!!.onBackPressed()
        }

        (activity as AppCompatActivity).supportActionBar!!.hide()
        return view
    }
}
