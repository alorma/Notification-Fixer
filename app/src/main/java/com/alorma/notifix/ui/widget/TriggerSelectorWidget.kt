package com.alorma.notifix.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.alorma.notifix.R
import com.alorma.notifix.ui.features.trigger.*
import kotlinx.android.synthetic.main.trigger_selector_layout.view.*

class TriggerSelectorWidget @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var callback: (TriggerType) -> Unit

    init {
        inflate(context, R.layout.trigger_selector_layout, this)
        isInEditMode
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        selectTriggerPhone.setOnClickListener {
            callback.invoke(Phone)
        }

        selectTriggerSms.setOnClickListener {
            callback.invoke(Sms)
        }

        selectTriggerTime.setOnClickListener {
            callback.invoke(Time)
        }

        selectTriggerZone.setOnClickListener {
            callback.invoke(Zone)
        }
    }

    fun setCallback(function: (TriggerType) -> Unit) {
        this.callback = function
    }
}