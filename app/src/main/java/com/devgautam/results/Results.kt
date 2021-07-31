package com.devgautam.results

/*

        DEVELOPED BY: GAUTAM CHANDRA SAHA
        DATE & TIME: 28/4/21 AT 1:43 AM
        DESCRIPTION: Results Application

*/

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.facebook.shimmer.ShimmerFrameLayout
import org.json.JSONObject

//handles the results to be fetched in this activity

class Results : AppCompatActivity() {


    private lateinit var listView: ListView
    private var id: String? = null
    private var period: String? = null
    private var supplementaryCheck: String? = null
    private var position: Int? = null
    private lateinit var gpaTag: TextView
    private lateinit var infoTag: TextView
    private lateinit var nameTag: TextView
    private var list: ArrayList<Data> = ArrayList()
    private var uri: String? = null
    private var name: NameOfStudent = NameOfStudent("NA")
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    //list to store the links to be fetched from
    private val uriList: List<String> = listOf(
        may_june_2021,
        march_sem_first_year_only_2021,
        jan_supplementary_2021,
        nov_dec_2020,
        july_aug_supplementary_2020,
        may_june_2020,
        jan_supplementary_2020,
        nov_dec_2019,
        june_july_supplementary_2019,
        may_june_2019_OR_april_may_2019,
        jan_supplementary_2019,
        nov_dec_2018,
        may_june_2018_OR_april_may_2018,
        nov_dec_2017
    )

    companion object {
        const val may_june_2021 =
            "https://devgautam2000.github.io/results.github.io/json/21.may_june_2021.json"
        const val march_sem_first_year_only_2021 =
            "https://devgautam2000.github.io/results.github.io/json/21_march_semester(first%20year%20only)_2021.json"
        const val jan_supplementary_2021 =
            "https://devgautam2000.github.io/results.github.io/json/21.jan_supplementary_2021.json"
        const val nov_dec_2020 =
            "https://devgautam2000.github.io/results.github.io/json/20.nov_dec_2020.json"
        const val july_aug_supplementary_2020 =
            "https://devgautam2000.github.io/results.github.io/json/20.july_aug_supplementary_2020.json"
        const val may_june_2020 =
            "https://devgautam2000.github.io/results.github.io/json/20.may_june_2020.json"
        const val jan_supplementary_2020 = //-------
            "https://devgautam2000.github.io/results.github.io/json/20.jan_supplementary_2020.json"
        const val nov_dec_2019 =
            "https://devgautam2000.github.io/results.github.io/json/19.nov_dec_2019.json"
        const val june_july_supplementary_2019 =
            "https://devgautam2000.github.io/results.github.io/json/19.june_july_supplementary_2019.json"
        const val may_june_2019_OR_april_may_2019 =
            "https://devgautam2000.github.io/results.github.io/json/19.may_june_2019.json"
        const val jan_supplementary_2019 =
            "https://devgautam2000.github.io/results.github.io/json/19.jan_supplementary_2019.json"
        const val nov_dec_2018 =
            "https://devgautam2000.github.io/results.github.io/json/18.nov_dec_2018.json"
        const val may_june_2018_OR_april_may_2018 =
            "https://devgautam2000.github.io/results.github.io/json/18.may_june_2018.json"
        const val nov_dec_2017 =
            "https://devgautam2000.github.io/results.github.io/json/17.nov_dec_2017.json"


        //the keys to get the extras from thr intent
        const val ID_KEY = "ID"
        const val POSITION_KEY = "POS"
        const val PERIOD_KEY = "PERIOD"
        const val SERIALIZABLE_LIST_KEY = "LIST"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        adjustFontScale(resources.configuration)
        getReferences() // get the references to the views to be used
        shimmerFrameLayout.startShimmerAnimation()
        getExtraFromIntent() // get the extras from intent


        supplementaryCheck = try {
            period?.length?.minus(5)
                ?.let { period?.substring((period?.indexOf(" ")?.plus((1))!!), it) }
        } catch (e: Exception) {
            "abcd"
        }

        tagGone() //set visibility of tags to GONE

        @Suppress("UNCHECKED_CAST") /*this cast is suppressed as it is confirmed that it
                                            returns only an ArrayList
                                            otherwise need to be handled*/
        list = intent.getSerializableExtra(SERIALIZABLE_LIST_KEY) as ArrayList<Data>

        clearListView() //clear the list view
        queueJSONRequest() //make a JSON request

    }

    @SuppressLint("ServiceCast")
    private fun adjustFontScale(configuration: Configuration?) {
        configuration?.let {
            it.fontScale = 0.9f
            val metrics: DisplayMetrics = resources.displayMetrics
            val wm: WindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            wm.defaultDisplay.getMetrics(metrics)
            metrics.scaledDensity = configuration.fontScale * metrics.density

            baseContext.applicationContext.createConfigurationContext(it)
            baseContext.resources.displayMetrics.setTo(metrics)

        }
    }

    //function to get the references to the vies=ws to be used
    private fun getReferences() {
        listView = findViewById(R.id.listView)
        gpaTag = findViewById(R.id.gpaTag)
        infoTag = findViewById(R.id.infoTag)
        nameTag = findViewById(R.id.nameTag)
        shimmerFrameLayout = findViewById(R.id.shimmerLayout)
    }

    //function to get all the extras from the intent
    private fun getExtraFromIntent() {
        id = intent.getStringExtra(ID_KEY)
        period = intent.getStringExtra(PERIOD_KEY)
        position = intent.getIntExtra(POSITION_KEY, 0)
    }


    //function to set visibility of view to GONE
    private fun tagGone() {
        gpaTag.visibility = View.GONE
        infoTag.visibility = View.GONE
        nameTag.visibility = View.GONE
    }

    //function to clear list view
    private fun clearListView() {
        listView.adapter = null
    }

    //function to queue a JSON request from the API
    private fun queueJSONRequest() {
        uri = uriList[position!! - 1]
        val queueReq = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, uri, null,
            { response ->
                try {

                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE

                    setData(response) //set the fetched response to the list

                    if (!supplementaryCheck.equals("supplementary", true)) {
                        val gpaObj = GPACalculator(list) //instantiate an object of GPACalculator
                        gpaObj.calcGpa() //invoke calcGpa() to calculate the GPA
                        tagVisible() // set visibility of the tags to VISIBLE

                        if (!name.nameOfStudent.equals("NA", true)) {
                            nameTagVisible()
                            nameTag.text = name.nameOfStudent
                        }

                        setTextToTag(gpaObj) //set the data to the tags
                    }

                    makeToast(
                        getStringFromRes(R.string.fetched),
                        Toast.LENGTH_SHORT
                    )


                } catch (e: Exception) {
                    clearListView() //clear list view
                    makeToast(
                        getStringFromRes(R.string.regNotFound),
                        Toast.LENGTH_LONG
                    )

                    intentToParent()
                }
            },
            { /*error ->*/ // handle the error
                clearListView()
                makeToast(
                    getStringFromRes(R.string.checkNetwork),
                    Toast.LENGTH_SHORT
                )
                intentToParent()
            })
        queueReq.add(jsonObjectRequest) /* NOTE: this line is crucial , no request is queued
                                          without this powerful  line of code*/

    }

    private fun intentToParent() {
        parentActivityIntent //intent back to the parent activity
        finish() //remove current activity from stack
    }

    //function to set data to list to be set to the inflated view in CustomAdapter
    private fun setData(response: JSONObject) {
        list.clear() //clear the list

        val reg = response.getJSONObject(id!!) //get the response by id

        val keys = reg.keys() //set the keys , here key is the SUB CODE

        keys.forEach { ele ->
            if (ele.equals("name", true)) {
                name = NameOfStudent(reg.getString("name"))
            } else if (!ele.equals("name", true)) {

                val keyObject = reg.getJSONObject(ele)

                list.add(
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
        }

        //set the custom adapter
        setCustomAdapter()
    }

    //function to set custom adapter to list view
    private fun setCustomAdapter() {
        listView.adapter = CustomAdapter(list)
    }

    //function to set visibility of view to VISIBLE
//after some delay
    private fun tagVisible() {
        val mHandler = Handler(Looper.getMainLooper())
        val monitor = Runnable {
            gpaTag.visibility = View.VISIBLE
            infoTag.visibility = View.VISIBLE
        }
        mHandler.postDelayed(monitor, 200)
    }

    private fun nameTagVisible() {
        val mHandler = Handler(Looper.getMainLooper())
        val monitor = Runnable {
            nameTag.visibility = View.VISIBLE

        }
        mHandler.postDelayed(monitor, 200)
    }

    //function to set the data to the tags
    private fun setTextToTag(gpaObj: GPACalculator) {
        gpaTag.text = String.format(
            resources.getString(R.string.gpa),
            gpaObj.getGpaPoint().toString()
        )
        infoTag.text = String.format(
            resources.getString(R.string.info),
            id, period
        )
    }

    //function to make toast
    private fun makeToast(text: String, length: Int) {
        Toast.makeText(applicationContext, text, length).show()
    }

    //function to get string from resources
    private fun getStringFromRes(strId: Int): String {
        return resources.getString(strId)
    }
}