package com.alorma.notifix.ui.features.trigger

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alorma.notifix.R
import com.alorma.notifix.ui.utils.dsl
import kotlinx.android.synthetic.main.bottom_sheet_trigger_selector.*

class SelectTriggerTypeFragment : BottomSheetDialogFragment() {

    private lateinit var callback: (TriggerType) -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.bottom_sheet_trigger_selector, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.dsl {
            menu = R.menu.bottom_sheet_trigger_selector_menu
            item {
                id = R.id.action_close
                action = { dismiss() }
            }
        }

        triggerPhone.setOnClickListener { sendBack(Phone) }
        triggerSms.setOnClickListener { sendBack(Sms) }
        triggerTime.setOnClickListener { sendBack(Time) }
        triggerZone.setOnClickListener { sendBack(Zone) }
    }

    private fun sendBack(type: TriggerType) {
        dismiss()
        callback.invoke(type)
    }

    public fun addListener(onTriggerType: (TriggerType) -> Unit) {
        this.callback = onTriggerType
    }
}