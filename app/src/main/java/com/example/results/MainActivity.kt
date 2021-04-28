package com.example.results

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    private var list: ArrayList<Data>? = null
    private var id: String? = null
    private lateinit var getId: EditText
    private lateinit var listView: ListView
    private lateinit var submitButton: Button
    private lateinit var periodList: Spinner
    private val uriList: List<String> = listOf(
            march_sem_first_year_only_2021,
            jan_supplementary_2021,
            nov_dec_2020,
            may_june_2020,
            nov_dec_2019,
            may_june_2019,
            nov_dec_2018, may_june_2018,
            nov_dec_2017
    )

    private var uri: String? = null

    companion object {
        const val march_sem_first_year_only_2021 =
                "https://devgautam2000.github.io/results.github.io/json/21_march_semester(first%20year%20only)_2021.json"
        const val jan_supplementary_2021 =
                "https://devgautam2000.github.io/results.github.io/json/21.jan_supplementary_2021.json"
        const val nov_dec_2020 =
                "https://devgautam2000.github.io/results.github.io/json/20.nov_dec_2020.json"
        const val may_june_2020 =
                "https://devgautam2000.github.io/results.github.io/json/20.may_june_2020.json"
        const val nov_dec_2019 =
                "https://devgautam2000.github.io/results.github.io/json/19.nov_dec_2019.json"
        const val may_june_2019 =
                "https://devgautam2000.github.io/results.github.io/json/19.may_june_2019.json"
        const val nov_dec_2018 =
                "https://devgautam2000.github.io/results.github.io/json/18.nov_dec_2018.json"
        const val may_june_2018 =
                "https://devgautam2000.github.io/results.github.io/json/18.may_june_2018.json"
        const val nov_dec_2017 =
                "https://devgautam2000.github.io/results.github.io/json/17.nov_dec_2017.json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getId = findViewById(R.id.getId)
        listView = findViewById(R.id.listView)
        submitButton = findViewById(R.id.submitButton)
        periodList = findViewById(R.id.periodList)

        setAdaptorToSpinner()
        submitButton.setOnClickListener { view ->
            hideKeyboard(view)

            if (checkConditionals()) {
                clearListView()
                submitButton.setBackgroundResource(R.drawable.btn_danger)
                return@setOnClickListener
            }

            //fetching...
            Toast.makeText(this, resources.getString(R.string.fetch), Toast.LENGTH_LONG).show()
            clearListView()
            queueJSONRequest()
        }

    }

    private fun clearListView() {
        listView.adapter = null
    }

    private fun queueJSONRequest() {
        val queueReq = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, uri, null,
                { response ->
                    try {
                        setData(response)
                        Toast.makeText(this, resources.getString(R.string.fetched), Toast.LENGTH_SHORT).show()
                        submitButton.setBackgroundResource(R.drawable.btn_go)

                    } catch (e: Exception) {
                        clearListView()
                        Toast.makeText(
                                this,
                                resources.getString(R.string.regNotFound), Toast.LENGTH_LONG
                        ).show()
                        submitButton.setBackgroundResource(R.drawable.btn_danger)
                    }
                },
                { error ->
                    clearListView()
                    Toast.makeText(
                            this,
                            resources.getString(R.string.checkNetwork), Toast.LENGTH_SHORT
                    ).show()
                    submitButton.setBackgroundResource(R.drawable.btn_danger)

                })
        queueReq.add(jsonObjectRequest)
    }

    private fun checkConditionals(): Boolean {
        if (getId.text.toString() != "") {
            id = getId.text.toString()
            list = ArrayList()
        } else if (getId.text.toString() == "") {
            Toast.makeText(this, resources.getString(R.string.checkEnter), Toast.LENGTH_SHORT)
                    .show()
            return true
        }
        val position = periodList.selectedItemPosition
        if (position == 0) {
            Toast.makeText(this, resources.getString(R.string.checkPeriod), Toast.LENGTH_SHORT)
                    .show()
            return true
        }
        uri = uriList[position - 1]
        return false
    }

    private fun setAdaptorToSpinner() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.period, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        periodList.adapter = adapter
    }

    private fun hideKeyboard(view: View?) {
        try {
            val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view!!.windowToken, 0)
        } catch (ignored: java.lang.Exception) {
        }

    }

    private fun setCustomAdaptor() {
        listView.adapter = CustomAdaptor(list!!)
    }

    private fun setData(response: JSONObject) {
        list?.clear()

        val reg = response.getJSONObject(id!!)

        val keys = reg.keys()
        keys.forEach { ele ->
            val keyObject = reg.getJSONObject(ele)
            list?.add(
                    Data(
                            subject = keyObject.getString("sub"),
                            subjectCode = ele,
                            internal = keyObject.getString("int"),
                            external = keyObject.getString("ext"),
                            grade = keyObject.getString("grade"),
                            total = keyObject.getString("tot"),
                            credit = keyObject.getString("credit")
                    )
            )
        }

        setCustomAdaptor()
    }

}