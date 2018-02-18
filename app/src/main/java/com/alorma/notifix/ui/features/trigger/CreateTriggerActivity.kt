package com.alorma.notifix.ui.features.trigger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.R
import com.alorma.notifix.data.Logger
import com.alorma.notifix.ui.utils.dsl
import kotlinx.android.synthetic.main.activity_add_trigger.*
import javax.inject.Inject

class CreateTriggerActivity : AppCompatActivity() {

    @Inject
    lateinit var logger: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_trigger)

        component inject this

        toolbar.dsl {
            back { action = { finish() } }
        }

        actionSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> logger.i("SMS")
                    1 -> logger.i("PHONE")
                    2 -> logger.i("TIME")
                    3 -> logger.i("ZONE")
                }
            }
        }
    }

}