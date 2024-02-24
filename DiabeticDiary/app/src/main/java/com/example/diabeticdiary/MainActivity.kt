package com.example.diabeticdiary

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.diabeticdiary.R

class MainActivity : AppCompatActivity() {
    private val fbManager = FBManager()
    private lateinit var user: User
    private var productList: List<Product> = emptyList()
    private var mealList: List<Meal> = emptyList()

    private var homeButton: Button? = null
    private var statisticButton: Button? = null
    private var searchButton: Button? = null
    private var gameButton: Button? = null
    private var profileButton: Button? = null

    private var homeTextView: TextView? = null
    private var statisticTextView: TextView? = null
    private var searchTextView: TextView? = null
    private var gameTextView: TextView? = null
    private var profileTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDB()
        setContentView(R.layout.activity_main)

        homeButton = findViewById(R.id.home_icon)
        statisticButton = findViewById(R.id.statistic_icon)
        searchButton = findViewById(R.id.search_icon)
        gameButton = findViewById(R.id.game_icon)
        profileButton = findViewById(R.id.profile_icon)

        homeTextView = findViewById(R.id.home_text)
        statisticTextView = findViewById(R.id.statistic_text)
        searchTextView = findViewById(R.id.search_text)
        gameTextView = findViewById(R.id.game_text)
        profileTextView = findViewById(R.id.profile_text)

        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, LoginFragment()).addToBackStack(null).commit()
//        changeTabColor(homeButton!!, homeTextView!!)

        homeButton!!.setOnClickListener {
            changeTabColor(homeButton!!, homeTextView!!)
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, LoginFragment()).addToBackStack(null).commit()
        }

        statisticButton!!.setOnClickListener {
            changeTabColor(statisticButton!!, statisticTextView!!)
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, CoffeeFragment()).addToBackStack(null).commit()
        }

        searchButton!!.setOnClickListener {
            changeTabColor(searchButton!!, searchTextView!!)
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, SearchFragment()).addToBackStack(null).commit()
        }

        gameButton!!.setOnClickListener {
            changeTabColor(gameButton!!, gameTextView!!)
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FlashlightFragment()).addToBackStack(null).commit()
        }

        profileButton!!.setOnClickListener {
            changeTabColor(profileButton!!, profileTextView!!)
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FlashlightFragment()).addToBackStack(null).commit()
        }
    }

    private fun initDB(){
        fbManager.setUserCallback { user ->
            if (user != null) {
                this@MainActivity.user = user
            }
        }

        fbManager.setProductListCallback { productList ->
            this@MainActivity.productList = productList
        }

//        fbManager.writeProduct("ca tim", "ex3")

        fbManager.setMealListCallback { mealList ->
            this@MainActivity.mealList = mealList
        }

//        fbManager.writeMeal("secondary1", "abczyx")
    }

    private fun changeTabColor(icon: Button, text: TextView) {
        homeButton!!.setBackground(getDrawable(R.drawable.ic_home))
        homeTextView!!.setTextColor(getColor(R.color.light_grey))

        statisticButton!!.setBackground(getDrawable(R.drawable.ic_statistic))
        statisticTextView!!.setTextColor(getColor(R.color.light_grey))

        searchButton!!.setBackground(getDrawable(R.drawable.ic_search))
        searchTextView!!.setTextColor(getColor(R.color.light_grey))

        gameButton!!.setBackground(getDrawable(R.drawable.ic_game))
        gameTextView!!.setTextColor(getColor(R.color.light_grey))

        profileButton!!.setBackground(getDrawable(R.drawable.ic_user))
        profileTextView!!.setTextColor(getColor(R.color.light_grey))

        if (icon == homeButton) {
            icon.setBackground(getDrawable(R.drawable.ic_home_active))
        }
        else if (icon == statisticButton) {
            icon.setBackground(getDrawable(R.drawable.ic_statistic_active))
        }
        else if (icon == searchButton) {
            icon.setBackground(getDrawable(R.drawable.ic_search_active))
        }
        else if (icon == gameButton) {
            icon.setBackground(getDrawable(R.drawable.ic_game_active))
        }
        else if (icon == profileButton) {
            icon.setBackground(getDrawable(R.drawable.ic_user_active))
        }

        text.setTextColor(getColor(R.color.blue))
    }
}