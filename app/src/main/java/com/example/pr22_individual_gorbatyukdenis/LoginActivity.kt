package com.example.pr22_individual_gorbatyukdenis

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pr22_individual_gorbatyukdenis.databinding.ActivityLoginBinding
import com.example.pr22_individual_gorbatyukdenis.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    lateinit var bindingClass: ActivityLoginBinding
    lateinit var shPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        shPref = getPreferences(MODE_PRIVATE)
        bindingClass.login.setText(shPref.getString("login", ""))
        bindingClass.psswd.setText(shPref.getString("password", ""))
        bindingClass.auth.setOnClickListener {
            if( bindingClass.login.text.toString().isNotEmpty() && bindingClass.psswd.text.toString().isNotEmpty())
            {
                    if(bindingClass.login.text.toString() == shPref.getString("login", "") && bindingClass.psswd.text.toString() == shPref.getString("password", ""))
                    {
                        Saving()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this,"логин или пароль неверный",Toast.LENGTH_SHORT).show()
                    }
            }
            else
            {
                Toast.makeText(this,"Введите логин и пароль",Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun Saving()
    {
        shPref = getPreferences(MODE_PRIVATE)

        val ed = shPref.edit()
        ed.putString("login", bindingClass.login.text.toString())
        ed.putString("password", bindingClass.psswd.text.toString())
        ed.apply()
    }
}