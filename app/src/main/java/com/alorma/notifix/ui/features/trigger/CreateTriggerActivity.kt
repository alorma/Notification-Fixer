package com.alorma.notifix.ui.features.trigger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.R
import com.alorma.notifix.data.Logger
import com.alorma.notifix.domain.model.Trigger
import com.alorma.notifix.ui.utils.dsl
import kotlinx.android.synthetic.main.activity_add_trigger.*
import javax.inject.Inject

class CreateTriggerActivity : AppCompatActivity(), CreateTriggerView {

    @Inject
    lateinit var presenter: CreateTriggerPresenter

    @Inject
    lateinit var logger: Logger

    private val adapter: TriggersAdapter by lazy { TriggersAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_trigger)

        component inject this
        presenter init this
        presenter attach this

        toolbar.dsl {
            back { action = { finish() } }
        }

        actionSelector.adapter = adapter
        actionSelector.layoutManager = GridLayoutManager(this, 3)
    }

    override fun render(state: CreateTriggerState) {
        when (state) {
            is TriggersSuccess -> displayTriggers(state.triggers)
        }
    }

    private fun displayTriggers(triggers: List<Trigger>) {
        adapter addAll triggers
    }

    override fun navigate(route: CreateTriggerRoute) {

    }
}