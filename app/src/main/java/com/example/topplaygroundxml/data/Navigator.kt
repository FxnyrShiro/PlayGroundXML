package com.example.topplaygroundxml.data

import android.content.Context
import android.content.Intent
import com.example.topplaygroundxml.ui.second.SecondActivity

class Navigator {

    fun getSecondActivityIntent(context: Context): Intent {
        return Intent(context, SecondActivity::class.java)
    }
}