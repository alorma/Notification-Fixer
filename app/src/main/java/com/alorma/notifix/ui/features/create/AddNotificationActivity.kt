package com.alorma.notifix.ui.features.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View.VISIBLE
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.R
import com.alorma.notifix.ui.features.trigger.TriggerRoute
import com.alorma.notifix.ui.features.trigger.number.ConfigureNumberTriggerFragment
import com.alorma.notifix.ui.features.trigger.preview.TriggerPreviewWidget
import com.alorma.notifix.ui.features.trigger.time.ConfigureTimeTriggerFragment
import com.alorma.notifix.ui.features.trigger.zone.ConfigureZoneTriggerActivity
import com.alorma.notifix.ui.utils.dsl
import kotlinx.android.synthetic.main.activity_add_notification.*
import kotlinx.android.synthetic.main.add_item_button.*
import kotlinx.android.synthetic.main.add_item_colors.*
import kotlinx.android.synthetic.main.add_item_enable.*
import kotlinx.android.synthetic.main.add_item_text.*
import kotlinx.android.synthetic.main.add_item_title.*
import kotlinx.android.synthetic.main.add_item_triggers.*
import javax.inject.Inject

class AddNotificationActivity : AppCompatActivity(), CreateNotificationView {

    @Inject
    lateinit var presenter: CreateNotificationPresenter

    companion object {
        private const val REQUEST_TRIGGER_PHONE = "PHONE"
        private const val REQUEST_TRIGGER_SMS = "SMS"
        private const val REQUEST_TRIGGER_TIME = "TIME"
        private const val REQUEST_TRIGGER_ZONE = 112
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notification)

        component inject this

        presenter init this

        toolbar.dsl {
            menu = R.menu.add_notification_menu
            item {
                id = R.id.action_preview
                action = {
                    previewNotification()
                }
            }
            back { action = { finish() } }
        }

        save.setOnClickListener {
            presenter action getNewNotificationAction()
        }

        triggerSelector.setCallback {
            presenter action OnTriggerSelected(it)
        }
    }

    private fun previewNotification() {
        presenter action getPreviewNotificationAction()
    }

    private fun getNewNotificationAction(): NewNotificationAction {
        return NewNotificationAction(titleField.text.toString(),
                textField.text.toString(),
                enabledField.isChecked,
                colorSelector.selectedColor.color)
    }

    private fun getPreviewNotificationAction(): PreviewNotificationAction {
        return PreviewNotificationAction(titleField.text.toString(),
                textField.text.toString(),
                enabledField.isChecked,
                colorSelector.selectedColor.color)
    }

    override fun render(state: CreateNotificationState) {
        when (state) {
            is CreateNotificationState.Trigger.Any -> {
                triggerData.visibility = VISIBLE
                val previewWidget = TriggerPreviewWidget.newInstance(state.triggerId)
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.triggerData, previewWidget)
                }.commit()
            }
        }
    }

    override fun navigate(route: CreateNotificationRoute) {
        when (route) {
            is SuccessGoBack -> onSaveSuccess()
            is ConfigureTrigger -> openConfTrigger(route)
        }
    }

    private fun openConfTrigger(route: ConfigureTrigger) {
        when (route) {
            is ConfigureTrigger.PhoneTrigger -> openPhoneTrigger()
            is ConfigureTrigger.SmsTrigger -> openSmsTrigger()
            is ConfigureTrigger.TimeTrigger -> openTimeTrigger()
            is ConfigureTrigger.ZoneTrigger -> openZoneTrigger()
        }
    }

    private fun openPhoneTrigger() {
        val fragment = ConfigureNumberTriggerFragment().apply {
            type = ConfigureNumberTriggerFragment.PHONE
            callback = { id ->
                this@AddNotificationActivity.presenter action TriggerCreatedAction(id)
            }
        }

        addNoUiFragment({ fragment }, { ConfigureNumberTriggerFragment.PHONE })
    }

    private fun openSmsTrigger() {
        val fragment = ConfigureNumberTriggerFragment().apply {
            type = ConfigureNumberTriggerFragment.SMS
            callback = { id ->
                this@AddNotificationActivity.presenter action TriggerCreatedAction(id)
            }
        }

        addNoUiFragment({ fragment }, { ConfigureNumberTriggerFragment.SMS })
    }

    private fun addNoUiFragment(fragment: () -> Fragment, tag: () -> Any) {
        supportFragmentManager.beginTransaction().apply {
            add(fragment(), tag().toString())
        }.commit()
    }

    private fun openTimeTrigger() {
        ConfigureTimeTriggerFragment().apply {
            callback = { id ->
                this@AddNotificationActivity.presenter action TriggerCreatedAction(id)
            }
        }.show(supportFragmentManager, REQUEST_TRIGGER_TIME)
    }

    private fun openZoneTrigger() {
        val intent = Intent(this, ConfigureZoneTriggerActivity::class.java)
        startActivityForResult(intent, REQUEST_TRIGGER_ZONE)
    }

    private fun onSaveSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_TRIGGER_ZONE) {
                val triggerId = data?.extras?.getLong(TriggerRoute.TRIGGER_ID) ?: 0
                presenter action TriggerCreatedAction(triggerId)
            }
        }
    }

}
