package com.alorma.notifix.ui.features.create

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.R
import com.alorma.notifix.ui.commons.OnCreate
import com.alorma.notifix.ui.utils.dsl
import kotlinx.android.synthetic.main.activity_add_notification.*
import javax.inject.Inject

class AddNotificationActivity : AppCompatActivity(), CreateNotificationView {

    @Inject
    lateinit var presenter: CreateNotificationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notification)

        component inject this

        presenter init this
        presenter onAction OnCreate()

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
            presenter onAction getNewNotificationAction()
        }

    }

    private fun previewNotification() {
        presenter onAction getPreviewNotificationAction()
    }

    private fun getNewNotificationAction(): NewNotificationAction {
        return NewNotificationAction(titleField.text.toString(),
                textField.text.toString(),
                enabledField.isChecked)
    }

    private fun getPreviewNotificationAction(): PreviewNotificationAction {
        return PreviewNotificationAction(titleField.text.toString(),
                textField.text.toString(),
                enabledField.isChecked)
    }

    override fun render(state: CreateNotificationState) {

    }

    override fun navigate(route: CreateNotificationRoute) {
        when (route) {
            is SuccessGoBack -> onSaveSuccess()
        }
    }

    private fun onSaveSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

}
