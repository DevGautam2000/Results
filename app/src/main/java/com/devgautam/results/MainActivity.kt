package com.devgautam.results
/*

        DEVELOPED BY: GAUTAM CHANDRA SAHA
        DATE & TIME: 28/4/21 AT 1:30 AM
        DESCRIPTION: Results Application

*/
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    private var list: ArrayList<Data>? = null
    private var id: String? = null
    private lateinit var getId: EditText
    private lateinit var submitButton: Button
    private lateinit var dropDownButton: ImageView
    private lateinit var periodList: Spinner
    private var position: Int? = null
    private var period: String? = null

    companion object { // to set the keys that is to be put with the intent to new activity
        const val ID_KEY = "ID"
        const val POSITION_KEY = "POS"
        const val PERIOD_KEY = "PERIOD"
        const val SERIALIZABLE_LIST_KEY = "LIST"
    }

    //onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getReferences() //get the references to the views

        setAdapterToSpinner() // set the adapter to the spinner

        dropDownButton.setOnClickListener {
            periodList.performClick()
        }

        //function of submit button after click
        submitButton.setOnClickListener { view ->

            hideKeyboard(view)

            if (checkConditionals()) {
                return@setOnClickListener
            }

            //fetching...
            makeToast(getStringFromRes(R.string.fetch))
            intentToResults() //intent to Results activity

        }

    }

    //function to get the references to the view used in this context
    private fun getReferences() {
        getId = findViewById(R.id.getId)
        submitButton = findViewById(R.id.submitButton)
        periodList = findViewById(R.id.periodList)
        dropDownButton = findViewById(R.id.dropDownButton)
    }


    //function to st the adapter to the spinner
    private fun setAdapterToSpinner() {
        val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.period, android.R.layout.simple_spinner_item
        )
//
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
//            resources.getStringArray(R.array.period))

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        periodList.adapter = adapter
    }

    // function to check the corner cases that might create a crash in the application
    private fun checkConditionals(): Boolean {


        when { //check for the id if null or not
            getId.text.toString() != "" -> {
                id = getId.text.toString()
                list = ArrayList()
            }
            else -> {
                makeToast(getStringFromRes(R.string.checkEnter))
                return true
            }
        }
        if (getId.text.toString().length < 6 || getId.text.toString().length > 9) { //check for the id length
            makeToast(getStringFromRes(R.string.invalid_id))
            return true
        }

        position = periodList.selectedItemPosition //set the position
        period = periodList.selectedItem.toString()

        if (position == 0) {
            makeToast(getStringFromRes(R.string.checkPeriod))
            return true
        }
        return false
    }

    //function to hide the keyboard
    private fun hideKeyboard(view: View?) {
        try {
            val inputMethodManager: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(view!!.windowToken, 0)
        } catch (ignored: java.lang.Exception) { //do nothing
        }

    }

    //function to make toast
    private fun makeToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    //function to get string from the resources
    private fun getStringFromRes(strId: Int): String {
        return resources.getString(strId)
    }

    //function intents to start the intended activity
    private fun intentToResults() {
        //create a new Handler to with Looper as Handler only is deprecated
        //create a Runnable to delay this new intent
        val mHandler = Handler(Looper.getMainLooper())
        val monitor = Runnable {
            startActivity(
                    Intent(applicationContext, Results::class.java)
                            .putExtra(ID_KEY, id!!)
                            .putExtra(POSITION_KEY, position)
                            .putExtra(PERIOD_KEY, period)
                            .putExtra(SERIALIZABLE_LIST_KEY, list)
            ) //put the extras to new activity
        }
        mHandler.postDelayed(monitor, 500) //delays the intent to new activity

    }


}