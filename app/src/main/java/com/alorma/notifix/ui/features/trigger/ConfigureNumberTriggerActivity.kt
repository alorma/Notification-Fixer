package com.alorma.notifix.ui.features.trigger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alorma.notifix.R
import com.alorma.notifix.ui.utils.dsl
import kotlinx.android.synthetic.main.configure_number_activity.*

abstract class ConfigureNumberTriggerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configure_number_activity)

        toolbar.dsl {
            title = getScreenTitle()
            back { action = { finish() } }
        }
    }

    abstract fun triggerType(): TriggerType

    abstract fun getScreenTitle(): Int
}