package com.example.results


/*

        DEVELOPED BY: GAUTAM CHANDRA SAHA
        DATE & TIME: 28/4/21 AT 1:45 AM
        DESCRIPTION: Results Application

*/

//class to handle data to be set to list view as custom adapter
data class Data(
        //declare set of variables to be used
        val subject: String,
        val subjectCode: String,
        val internal: String,
        val external: String,
        val grade: String,
        val total: String,
        val credit: String,
)
