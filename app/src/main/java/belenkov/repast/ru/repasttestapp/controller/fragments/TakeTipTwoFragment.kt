package belenkov.repast.ru.repasttestapp.controller.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import belenkov.repast.ru.repasttestapp.R
import belenkov.repast.ru.repasttestapp.controller.helpers.Router
import belenkov.repast.ru.repasttestapp.model.entities.Staff
import kotlinx.android.synthetic.main.fragment_take_tip_two.view.*

class TakeTipTwoFragment : Fragment() {
    lateinit var staff: Staff
    lateinit var restaurantReview: String
    private var staffTipCount = 0
    private var staffTipCountSelector = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        staff = arguments!!.get("staff") as Staff
        restaurantReview = arguments!!.getString("restaurant-review")
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_take_tip_two, container, false)
        val takeTipStepOneBackButton = view.findViewById<ImageButton>(R.id.takeTipStepOneBackButton)
        val takeTipStepTwoTipMinusButton = view.findViewById<ImageButton>(R.id.takeTipStepTwoTipMinusButton)
        val takeTipStepTwoTipPlusButton = view.findViewById<ImageButton>(R.id.takeTipStepTwoTipPlusButton)
        val takeTipStepTwoTakeTipButton = view.takeTipStepTwoTakeTipButton

        val takeTipStepTwoTipCountTip = view.findViewById<TextView>(R.id.takeTipStepTwoTipCountTip)
        val takeTipStepTwoComissionTextView = view.findViewById<TextView>(R.id.takeTipStepTwoComissionTextView)

        val takeTipStepTwoTipSelector100 = view.findViewById<Button>(R.id.takeTipStepTwoTipSelector100)
        val takeTipStepTwoTipSelector200 = view.findViewById<Button>(R.id.takeTipStepTwoTipSelector200)
        val takeTipStepTwoTipSelector400 = view.findViewById<Button>(R.id.takeTipStepTwoTipSelector400)

        val selectorsClickListener = View.OnClickListener {
            val button = it as Button
            when (button.text) {
                "100" -> {
                    staffTipCountSelector = 100
                }
                "200" -> {
                    staffTipCountSelector = 200
                }
                "400" -> {
                    staffTipCountSelector = 400
                }
            }
        }
        val plusMinusClickListener = View.OnClickListener {
            val imageButton = it as ImageButton
            when (imageButton.id) {
                takeTipStepTwoTipMinusButton.id -> {
                    if ((staffTipCount - staffTipCountSelector) >= 0) {
                        staffTipCount -= staffTipCountSelector
                    }
                }
                takeTipStepTwoTipPlusButton.id -> {
                    staffTipCount += staffTipCountSelector
                }
            }
            takeTipStepTwoTipCountTip.text = "$staffTipCount \u20bd"
            val percent = staffTipCount.toFloat() / 100 * 1
            takeTipStepTwoComissionTextView.text = "К оплате ${staffTipCount - percent}"
        }

        takeTipStepOneBackButton.setOnClickListener {
            activity!!.onBackPressed()
        }

        takeTipStepTwoTipMinusButton.setOnClickListener(plusMinusClickListener)
        takeTipStepTwoTipPlusButton.setOnClickListener(plusMinusClickListener)

        takeTipStepTwoTipSelector100.setOnClickListener(selectorsClickListener)
        takeTipStepTwoTipSelector200.setOnClickListener(selectorsClickListener)
        takeTipStepTwoTipSelector400.setOnClickListener(selectorsClickListener)

        takeTipStepTwoTakeTipButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("staff", staff)
            bundle.putString("restaurant-review", restaurantReview)
            Router.replaceFragment(view.context as AppCompatActivity, TakeTipRateFragment(), "taketip-rate", bundle)
        }
        return view
    }
}
