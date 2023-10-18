package com.example.pr22_individual_gorbatyukdenis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pr22_individual_gorbatyukdenis.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import org.json.JSONObject
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var bindingClass: ActivityMainBinding
    lateinit var BookInf: MaterialTextView
    lateinit var BookISBN: TextInputEditText
    lateinit var GetInfoBTN: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        BookInf = bindingClass.bookInfo
        BookISBN = bindingClass.bookISBN
        GetInfoBTN = bindingClass.getBookInfoButton
        GetInfoBTN.setOnClickListener() { GetBook(BookISBN.text.toString()) }
    }

    fun GetBook(ISBN: String) {
        //var url = "https://openlibrary.org/api/books?bibkeys=ISBN:" + ISBN + "&jscmd=data&format=json"
        var url = "https://openlibrary.org/isbn/" + ISBN + ".json"
        if (!ISBN.isNullOrEmpty()) {
            var validRes = ValidateISBN(ISBN)
            if (validRes != "0") { var snackBar = Snackbar.make(findViewById(android.R.id.content), validRes, Snackbar.LENGTH_LONG).show() }
            else {
                val queue = Volley.newRequestQueue(this)
                val stringRequest = StringRequest(
                    Request.Method.GET,
                    url,
                    {
                            response->
                        val jsonObject = JSONObject(response)
                        val title = jsonObject.getString("title").toString()
                        var publisher = jsonObject.getString("publishers")
                        publisher = publisher.replace("\"", "")
                        publisher = publisher.replace("[", "")
                        publisher = publisher.replace("]", "")
                        val publish_date = jsonObject.getString("publish_date")
                        var author = jsonObject.getString("authors")
                        author = author.subSequence(10,(author.length - 3)).toString()
                        author = author.replace("\\", "")
                        var url_author = "https://openlibrary.org" + author + ".json"
                        val queue1 = Volley.newRequestQueue(this)
                        val stringRequest1 = StringRequest(
                            Request.Method.GET,
                            url_author,
                            {
                                    response->
                                val jsonObject1 = JSONObject(response)
                                val name = jsonObject1.getString("personal_name")
                                BookInf.text = "Title: $title\nAuthor: $name\nPublish date: $publish_date\nPublisher: $publisher"

                            }, {
                                Log.d("MyLog","Volley error: $it")
                            }
                        )
                        queue1.add(stringRequest1)
                    }, {
                        Log.d("MyLog","Volley error: $it")
                    }
                 )
            queue.add(stringRequest)
            }
        }
    }

    fun ValidateISBN(ISBN: String): String {
        var err: Int = 0
        if (ISBN.length == 10 || ISBN.length == 13){
            for (char in ISBN ) if (!char.isDigit()) err++
            if (err > 0) return "ISBN should contain only digits"
            else return "0"
        }
        else return "Invalid ISBN length"
    }
}