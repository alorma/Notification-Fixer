package com.alorma.notifix.ui.features.create

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alorma.notifix.R
import com.alorma.notifix.ui.utils.dsl
import kotlinx.android.synthetic.main.activity_add_notification.*

class AddNotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notification)
        setSupportActionBar(toolbar)

        toolbar.dsl {
            back { action = { finish() } }
        }
    }

}
