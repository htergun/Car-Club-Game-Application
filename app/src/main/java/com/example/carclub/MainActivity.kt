package com.example.carclub

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private enum class ListMode {
        COUNTRY, CAR
    }

    private var currentMode: ListMode? = null

    private lateinit var etName: EditText
    private lateinit var tvSelectedCountry: TextView
    private lateinit var btnChangeCountry: Button
    private lateinit var tvSelectedCar: TextView
    private lateinit var btnChangeCar: Button
    private lateinit var btnShare: Button
    private lateinit var listView: ListView

    private lateinit var countryList: List<ItemModel>
    private lateinit var carList: List<ItemModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        etName = findViewById(R.id.etName)
        tvSelectedCountry = findViewById(R.id.tvSelectedCountry)
        btnChangeCountry = findViewById(R.id.btnChangeCountry)
        tvSelectedCar = findViewById(R.id.tvSelectedCar)
        btnChangeCar = findViewById(R.id.btnChangeCar)
        btnShare = findViewById(R.id.btnShare)
        listView = findViewById(R.id.listView)

        // Populate Country List (at least 10 countries with flags) using strings.xml
        countryList = listOf(
            ItemModel(getString(R.string.country_turkey), R.drawable.flag_turkey),
            ItemModel(getString(R.string.country_usa), R.drawable.flag_usa),
            ItemModel(getString(R.string.country_germany), R.drawable.flag_germany),
            ItemModel(getString(R.string.country_japan), R.drawable.flag_japan),
            ItemModel(getString(R.string.country_france), R.drawable.flag_france),
            ItemModel(getString(R.string.country_italy), R.drawable.flag_italy),
            ItemModel(getString(R.string.country_spain), R.drawable.flag_spain),
            ItemModel(getString(R.string.country_england), R.drawable.flag_england),
            ItemModel(getString(R.string.country_canada), R.drawable.flag_canada),
            ItemModel(getString(R.string.country_brazil), R.drawable.flag_brazil),
        )

        // Populate Car List (each car with a different car image from zip files) using strings.xml
        carList = listOf(
            ItemModel(getString(R.string.car_mercedes), R.drawable.car_10),
            ItemModel(getString(R.string.car_mustang), R.drawable.car_1),
            ItemModel(getString(R.string.car_chevrolet), R.drawable.car_2),
            ItemModel(getString(R.string.car_aston), R.drawable.car_3),
            ItemModel(getString(R.string.car_jaguar), R.drawable.car_4),
            ItemModel(getString(R.string.car_dodge), R.drawable.car_5),
            ItemModel(getString(R.string.car_porsche), R.drawable.car_6),
            ItemModel(getString(R.string.car_chevelle), R.drawable.car_7),
            ItemModel(getString(R.string.car_shelby), R.drawable.car_8),
            ItemModel(getString(R.string.car_300sl), R.drawable.car_9),
        )

        // Country Change Button OnClick: Display country list below, set visibility to true
        btnChangeCountry.setOnClickListener {
            currentMode = ListMode.COUNTRY
            listView.adapter = CustomListAdapter(this, countryList)
            listView.visibility = View.VISIBLE
        }

        // Car Change Button OnClick: Display car list below, set visibility to true
        btnChangeCar.setOnClickListener {
            currentMode = ListMode.CAR
            listView.adapter = CustomListAdapter(this, carList)
            listView.visibility = View.VISIBLE
        }

        // ListView onItemClick: Update selected country/car, set listview visibility to false
        listView.setOnItemClickListener { _, _, position, _ ->
            when (currentMode) {
                ListMode.COUNTRY -> {
                    val selectedCountry = countryList[position].name
                    tvSelectedCountry.text = selectedCountry
                }
                ListMode.CAR -> {
                    val selectedCar = carList[position].name
                    tvSelectedCar.text = selectedCar
                }
                null -> {}
            }
            listView.visibility = View.GONE
        }

        // Share Button OnClick: Open share dialog using Intent(ACTION_SEND)
        btnShare.setOnClickListener {
            val nameInput = etName.text.toString().trim()
            val name = nameInput.ifEmpty { getString(R.string.default_name) }
            val selectedCountry = tvSelectedCountry.text.toString()
            val selectedCar = tvSelectedCar.text.toString()

            val shareText = "hello I am $name, I am from $selectedCountry, I like $selectedCar"

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_chooser_title)))
        }
    }
}
