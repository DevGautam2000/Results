package com.devgautam.results

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


/*

        DEVELOPED BY: GAUTAM CHANDRA SAHA
        DATE & TIME: 28/4/21 AT 3:46 AM
        DESCRIPTION: Results Application

*/

class CustomAdapter(private val arrayList: ArrayList<Data>) : BaseAdapter() {

    override fun getCount(): Int = arrayList.size

    override fun getItem(position: Int): Any = arrayList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    //inflate the view from the layout resource to be set in list view in this function
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        //context to parent
        val context = parent?.context
        var inflatedView: View? = null

        //get the layout inflater service from Context
        val inflater: LayoutInflater =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        //NOTE: this is an important line
        //optimizes the application as the views that gets folded is reused as a new view

        //if the inflated view is null only then inflate the view
        // else the same vies are set to new values
        if (inflatedView == null)
            inflatedView = inflater.inflate(R.layout.layout_view_of_results, parent, false)


        val itemInList = arrayList[position] //get the item from the list
        setValuesInViews(itemInList, inflatedView, context) //set the values to the inflated layout

        return inflatedView!!
    }

    //function to set the values to the inflated layout
    private fun setValuesInViews(itemInList: Data, inflatedView: View?, context: Context?) {
        //get the reference from the inflated view to the values to be set
        val subject = inflatedView?.findViewById<TextView>(R.id.subject)
        val subjectCode = inflatedView?.findViewById<TextView>(R.id.subjectCode)
        val subjectInt = inflatedView?.findViewById<TextView>(R.id.subjectInt)
        val subjectExt = inflatedView?.findViewById<TextView>(R.id.subjectExt)
        val subjectTot = inflatedView?.findViewById<TextView>(R.id.subjectTot)
        val subjectGra = inflatedView?.findViewById<TextView>(R.id.subjectGra)
        val subjectCredit = inflatedView?.findViewById<TextView>(R.id.subjectCredit)


        //set the values
        subject?.text = itemInList.subject

        subjectCredit?.text =
                context?.resources?.let {
                    String.format(it.getString(R.string.credit),
                            itemInList.credit)
                }

        subjectInt?.text =
                context?.resources?.let {
                    String.format(it.getString(R.string.internal),
                            itemInList.internal)
                }

        subjectExt?.text =
                context?.resources?.let {
                    String.format(it.getString(R.string.external),
                            itemInList.external)
                }

        subjectTot?.text =
                context?.resources?.let {
                    String.format(it.getString(R.string.total),
                            itemInList.total)
                }

        subjectGra?.text = itemInList.grade

        subjectCode?.text = itemInList.subjectCode

    }
}
