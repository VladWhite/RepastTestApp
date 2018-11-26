package belenkov.repast.ru.repasttestapp.controller.helpers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import belenkov.repast.ru.repasttestapp.R

class Router {
    companion object {
        fun initFramgent(activity: AppCompatActivity, fragment: Fragment, bundle: Bundle) {
            val manager = activity.supportFragmentManager
            var transaction = manager.beginTransaction()
            fragment.arguments = bundle

            transaction.add(R.id.frameLayout, fragment)
            transaction.commit()
        }

        fun replaceFragment(activity: AppCompatActivity, fragment: Fragment, replaceName: String, bundle: Bundle?) {
            val manager = activity.supportFragmentManager
            val transaction = manager.beginTransaction()
            fragment.arguments = bundle

            transaction.addToBackStack(null)
            transaction.replace(R.id.frameLayout, fragment, replaceName)
            transaction.commit()
        }
    }
}