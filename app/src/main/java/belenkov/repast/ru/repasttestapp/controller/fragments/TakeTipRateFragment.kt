package belenkov.repast.ru.repasttestapp.controller.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import belenkov.repast.ru.repasttestapp.R
import belenkov.repast.ru.repasttestapp.controller.adapters.DiagnosticStaffAdapter
import belenkov.repast.ru.repasttestapp.controller.helpers.Router
import belenkov.repast.ru.repasttestapp.model.entities.Staff
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_take_tip_rate.view.*

class TakeTipRateFragment : Fragment() {

    private lateinit var restaurantReview: String
    private lateinit var staff: Staff
    private lateinit var diagnosticList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        staff = arguments!!.get("staff") as Staff
        restaurantReview = arguments!!.getString("restaurant-review")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_take_tip_rate, container, false)
        val reviewRecycler = view.takeTipRateDiagnosticRecycler
        val diagnosticBar = view.takeTipRateWorkRateDiagnosticBar
        val commentAvaterImageView = view.commentAvaterImageView
        val commentNameTextView = view.commentNameTextView
        val commentPositionTextView = view.commentPositionTextView
        val takeTipRateCommentNextButton = view.takeTipRateCommentNextButton
        val restaurantReviewTextView = view.takeTipRateRestaurantReview
        val takeTipRateWorkRateTextView = view.takeTipRateWorkRateTextView
        val takeTipRateWorkRateQuestionTextView = view.takeTipRateWorkRateQuestionTextView

        diagnosticList = ArrayList()
        diagnosticBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            run {
                diagnosticList.clear()
                when (rating.toInt()) {
                    1, 2, 3 -> {
                        diagnosticList.add("Оффициант был неучтив")
                        diagnosticList.add("Некачественные блюда")
                        diagnosticList.add("Неубранный стол")
                        diagnosticList.add("Нарушение порядка подачи блюд")
                        takeTipRateWorkRateTextView.text = "Плохо"
                        takeTipRateWorkRateQuestionTextView.text = "Что Вас огорчило?"
                    }
                    4, 5 -> {
                        diagnosticList.add("10 из 10")
                        diagnosticList.add("Оффициант хороший и блюда вкусные")
                        diagnosticList.add("Отличная подача блюд")
                        diagnosticList.add("Высокий уровень обслуживания")
                        takeTipRateWorkRateTextView.text = "Хорошо"
                        takeTipRateWorkRateQuestionTextView.text = "Что Вам понравилось?"
                    }
                }
                val adapter = DiagnosticStaffAdapter(diagnosticList, context!!)
                reviewRecycler.layoutManager = LinearLayoutManager(activity)
                reviewRecycler.adapter = adapter
            }
        }

        restaurantReviewTextView.text = restaurantReview

        val picasso = Picasso.Builder(context).build()
        picasso.load(staff.iconPath).into(commentAvaterImageView)

        commentNameTextView.text = staff.name
        commentPositionTextView.text = staff.position
        takeTipRateCommentNextButton.setOnClickListener {
            Router.replaceFragment(context as AppCompatActivity, ReviewFragment(), "review-fragment", Bundle())
        }
        return view
    }

}
