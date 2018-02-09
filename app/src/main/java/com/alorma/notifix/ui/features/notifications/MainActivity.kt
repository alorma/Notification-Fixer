package com.alorma.notifix.ui.features.notifications

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.alorma.notifix.R
import com.alorma.notifix.ui.DiComponent.Companion.component
import com.alorma.notifix.ui.commons.OnCreate
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NotificationsView {

    @Inject
    lateinit var presenter: NotificationsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component inject this

        presenter.init(this)
        presenter.onAction(OnCreate())
    }

    override fun render(state: NotificationsState) {
        return when(state) {
            is ShowNotifications -> onNotificationsList(state.list)
        }
    }

    private fun onNotificationsList(list: List<NotificationViewModel>) {
        Toast.makeText(this, "List: ${list.size}", Toast.LENGTH_SHORT).show()
    }

    override fun navigate(route: NotificationsRoute) {

    }
}
