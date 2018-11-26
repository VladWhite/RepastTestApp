package belenkov.repast.ru.repasttestapp.controller

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import belenkov.repast.ru.repasttestapp.R
import belenkov.repast.ru.repasttestapp.controller.fragments.FavoritesFragment
import belenkov.repast.ru.repasttestapp.controller.fragments.ProfileFragment
import belenkov.repast.ru.repasttestapp.controller.fragments.ReviewFragment
import belenkov.repast.ru.repasttestapp.controller.helpers.Router
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bundle = Bundle()
        Router.initFramgent(this, ReviewFragment(), bundle)

        val menu: BottomNavigationView = bottomMenu
        menu.setOnNavigationItemSelectedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
            when (it.itemId) {
                R.id.bottomMenuReview -> {
                    if (currentFragment !is ReviewFragment) {
                        Router.replaceFragment(this, ReviewFragment(), "review screen", null)
                        true
                    } else {
                        false
                    }
                }
                R.id.bottomMenuFavorite -> {
                    if (currentFragment !is FavoritesFragment) {
                        Router.replaceFragment(this, FavoritesFragment(), "favorites screen", null)
                        true
                    } else {
                        false
                    }
                }
                R.id.bottomMenuProfile -> {
                    if (currentFragment !is ProfileFragment) {
                        Router.replaceFragment(this, ProfileFragment(), "profile screen", null)
                        true
                    } else {
                        false
                    }
                }
                else -> false
            }
        }
    }


}
