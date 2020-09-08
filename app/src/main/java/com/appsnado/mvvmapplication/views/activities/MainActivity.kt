package com.appsnado.mvvmapplication.views.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.appsnado.mvvmapplication.R
import com.appsnado.mvvmapplication.databinding.ActivityMainBinding
import com.appsnado.mvvmapplication.viewmodels.LoginViewModel


class MainActivity : AppCompatActivity() {


    @BindingAdapter("toastMessage")
    fun runMe(view: View, toastMessage: String?) {
        if (toastMessage != null) Toast.makeText(view.context, toastMessage, Toast.LENGTH_SHORT)
            .show()
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.model = LoginViewModel
        binding.executePendingBindings()
        /*binding.tvHeading.text = "Headingggggggggggg"
        binding.tvSubHeading.text = "SubHeadinggggggggggggggg"*/


    }

}