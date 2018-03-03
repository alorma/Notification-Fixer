package com.alorma.notifix.ui.features.create

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.R
import com.alorma.notifix.ui.features.trigger.number.ConfigureNumberTriggerFragment
import com.alorma.notifix.ui.features.trigger.SelectTriggerTypeFragment
import com.alorma.notifix.ui.features.trigger.time.ConfigureTimeTriggerFragment
import com.alorma.notifix.ui.utils.dsl
import com.alorma.notifix.ui.utils.toast
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

        addTrigger.setOnClickListener {
            presenter action AddTriggerAction()
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

    }

    override fun navigate(route: CreateNotificationRoute) {
        when (route) {
            is SuccessGoBack -> onSaveSuccess()
            is SelectTrigger -> openNewTrigger()
            is ConfigureTrigger -> openConfTrigger(route)
        }
    }

    private fun openNewTrigger() {
        SelectTriggerTypeFragment().apply {
            addListener {
                presenter action OnTriggerSelected(it)
            }

            show(supportFragmentManager, "trigger")
        }
    }

    private fun openConfTrigger(route: ConfigureTrigger) {
        when (route) {
            is ConfigureTrigger.SmsTrigger -> openSmsTrigger()
            is ConfigureTrigger.PhoneTrigger -> openPhoneTrigger()
            is ConfigureTrigger.TimeTrigger -> openTimeTrigger()
            is ConfigureTrigger.ZoneTrigger -> toast("Not yet...")
        }
    }

    private fun openPhoneTrigger() {
        ConfigureNumberTriggerFragment().show(supportFragmentManager, REQUEST_TRIGGER_PHONE)
    }

    private fun openSmsTrigger() {
        ConfigureNumberTriggerFragment().show(supportFragmentManager, REQUEST_TRIGGER_SMS)
    }

    private fun openTimeTrigger() {
        ConfigureTimeTriggerFragment().show(supportFragmentManager, REQUEST_TRIGGER_TIME)
    }

    private fun onSaveSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

}
