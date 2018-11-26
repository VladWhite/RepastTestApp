package belenkov.repast.ru.repasttestapp.controller.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import belenkov.repast.ru.repasttestapp.R
import kotlinx.android.synthetic.main.diagnostic_item.view.*

class DiagnosticStaffAdapter(
    var diagnosticList: ArrayList<String>,
    var context: Context
) : RecyclerView.Adapter<DiagnosticStaffAdapter.DiagnosticViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): DiagnosticViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.diagnostic_item, viewGroup, false)

        return DiagnosticStaffAdapter.DiagnosticViewHolder(view)
    }

    override fun getItemCount(): Int {
        return diagnosticList.size
    }

    override fun onBindViewHolder(viewHolder: DiagnosticViewHolder, index: Int) {
        viewHolder.diagnosticStaffRateTextView.text = diagnosticList[index]

        viewHolder.diagnosticStaffRateLayout.setOnClickListener {
            if (viewHolder.diagnosticStaffRateImageView.visibility == View.INVISIBLE)
                viewHolder.diagnosticStaffRateImageView.visibility = View.VISIBLE
            else
                viewHolder.diagnosticStaffRateImageView.visibility = View.INVISIBLE
        }
    }

    class DiagnosticViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val diagnosticStaffRateTextView = view.diagnosticStaffRateTextView!!
        val diagnosticStaffRateImageView = view.diagnosticStaffRateImageView!!
        val diagnosticStaffRateLayout = view.diagnosticStaffRateLayout!!
    }
}