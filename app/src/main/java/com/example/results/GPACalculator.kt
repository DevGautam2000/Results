package com.example.results

import kotlin.math.roundToInt


/*

        DEVELOPED BY: GAUTAM CHANDRA SAHA
        DATE & TIME: 29/4/21 AT 4:43 PM
        DESCRIPTION: Results Application

*/

// This class handles the calculation of the grade points average

class GPACalculator(private val list: ArrayList<Data>) {

    private var gpaPoint: Float? = null

    fun getGpaPoint(): Float = gpaPoint!! //return the gpa set

    //function to calculate the gpa
    fun calcGpa() {
        var decPoint: Float
        var decCredit: Float

        var totalPoint = 0f
        var totalCredit = 0f

        //for each Data in list
        list.forEach { data ->
            decPoint = getPointFromGrade(data.grade)

            if (decPoint >= 0f) {
                decCredit = data.credit.toFloat()
                totalPoint += (decPoint * decCredit)
                totalCredit += decCredit
            }
        }
        gpaPoint = totalPoint / totalCredit;
        gpaPoint = ((gpaPoint!! * 100).roundToInt()) / 100f; //round to nearest int
    }

    //function to get the point from grade
    private fun getPointFromGrade(grade: String): Float {
        return when (grade) {
            "S" -> 10f
            "A" -> 9f
            "B" -> 8f
            "C" -> 7f
            "D" -> 6f
            "E" -> 5f
            else -> 0f
        }
    }
}
