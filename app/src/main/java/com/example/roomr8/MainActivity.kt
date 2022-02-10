package com.example.roomr8

import android.app.Activity
import android.os.Bundle
import android.util.Log

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("room_r8", "class: ${this::class.java}")
        val x = TestInitializer(applicationContext)
        x.initialize()
    }
}