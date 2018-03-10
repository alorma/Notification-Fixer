package com.alorma.notifix.ui.widget

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.R
import kotlinx.android.synthetic.main.trigger_preview.*

class TriggerPreviewWidget : Fragment() {

    companion object {
        private const val EXTRA_ID = "extra_id"

        fun newInstance(triggerId: Long) = TriggerPreviewWidget().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_ID, triggerId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component inject this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.trigger_preview, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getLong(EXTRA_ID)?.let {
            loadTrigger(it)
        }
    }

    private fun loadTrigger(it: Long) {
        triggerType.text = it.toString()
    }
}