package com.example.pr22_individual_gorbatyukdenis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var TextView: MaterialTextView
    lateinit var BookISBN: TextInputEditText
    lateinit var GetInfoBTN: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TextView = findViewById(R.id.bookInfo)
        BookISBN = findViewById(R.id.bookISBN)
        GetInfoBTN = findViewById(R.id.getBookInfoButton)
    }
}