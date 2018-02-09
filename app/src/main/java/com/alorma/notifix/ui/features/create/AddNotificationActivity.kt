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
        setSupportActionBar(toolbar)

        component inject this

        presenter init this

        toolbar.dsl {
            back { action = { finish() } }
        }
        presenter onAction OnCreate()

        save.setOnClickListener {
            val action = NewNotificationAction(titleField.editText?.text.toString(),
                    textField.editText?.text.toString(),
                    enabledField.isChecked)
            presenter onAction action
        }
    }

    override fun render(state: CreateNotificationState) {

    }

    override fun navigate(route: CreateNotificationRoute) {
        when(route) {
            is SuccessGoBack -> onSaveSuccess()
        }
    }

    private fun onSaveSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

}
