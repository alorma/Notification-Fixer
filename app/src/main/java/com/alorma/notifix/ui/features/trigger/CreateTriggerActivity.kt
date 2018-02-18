package com.alorma.notifix.ui.features.trigger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alorma.notifix.R
import com.alorma.notifix.ui.utils.dsl
import kotlinx.android.synthetic.main.activity_add_trigger.*

class CreateTriggerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_trigger)

        toolbar.dsl {
            back { action = { finish() } }
        }
    }

}