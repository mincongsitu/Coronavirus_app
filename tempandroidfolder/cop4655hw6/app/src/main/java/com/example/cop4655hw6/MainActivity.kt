package com.example.cop4655hw6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

val textView = findViewById<TextView>(R.id.text)
// ...

// Instantiate the RequestQueue.
val queue = Volley.newRequestQueue(this)
val url = "http://www.google.com"

// Request a string response from the provided URL.
val stringRequest = StringRequest(Request.Method.GET, url,
    Response.Listener<String> { response ->
        // Display the first 500 characters of the response string.
        textView.text = "Response is: ${response.substring(0, 500)}"
    },
    Response.ErrorListener { textView.text = "That didn't work!" })

// Add the request to the RequestQueue.
queue.add(stringRequest)

val TAG = "MyTag"
val stringRequest: StringRequest // Assume this exists.
val requestQueue: RequestQueue? // Assume this exists.

// Set the tag on the request.
stringRequest.tag = TAG

// Add the request to the RequestQueue.
requestQueue?.add(stringRequest)

protected fun onStop() {
    super.onStop()
    requestQueue?.cancelAll(TAG)
}