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

class CreateTriggerActivity : AppCompatActivity(), CreateTriggerView {

    @Inject
    lateinit var presenter: CreateTriggerPresenter

    @Inject
    lateinit var logger: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_trigger)

        component inject this
        presenter init this
        presenter attach this

        toolbar.dsl {
            back { action = { finish() } }
        }

        actionSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter action when (position) {
                    0 -> SMS()
                    1 -> PHONE()
                    2 -> TIME()
                    3 -> ZONE()
                    else -> UNKNOW()
                }
            }
        }
    }

    override fun render(state: CreateTriggerState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigate(route: CreateTriggerRoute) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}