package com.alorma.notifix.ui.features.trigger.time

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alorma.notifix.NotifixApplication
import com.alorma.notifix.R
import com.alorma.notifix.ui.features.trigger.di.CreateTriggerModule
import kotlinx.android.synthetic.main.configure_time_fragment.*
import javax.inject.Inject

class ConfigureTimeTriggerFragment : DialogFragment(), CreateTimeTriggerView {
    @Inject
    lateinit var presenter: CreateTimeTriggerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            NotifixApplication.component add CreateTriggerModule(it) inject this
            presenter init this
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.configure_time_fragment, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectTime.setOnClickListener {
            presenter action CreateTimeTriggerAction.TimeSelectedAction(timePicker.hour, timePicker.minute)
        }
    }

    override fun render(state: CreateTimeTriggerState) {

    }

    override fun navigate(route: CreateTimeTriggerRoute) {

    }
}