package com.alorma.notifix.ui.features.notifications

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.R
import com.alorma.notifix.ui.commons.OnCreate
import com.alorma.notifix.ui.commons.OnStop
import com.alorma.notifix.ui.utils.dsl
import kotlinx.android.synthetic.main.activity_main.*
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

        toolbar.dsl {
            menu = R.menu.notifications_menu
            item {
                id = R.id.action_add_notification
                action = { presenter.onAddNotification() }
            }
        }
    }

    override fun render(state: NotificationsState) {
        return when (state) {
            is ShowNotifications -> onNotificationsList(state.list)
        }
    }

    private fun onNotificationsList(list: List<NotificationViewModel>) {
        Toast.makeText(this, "List: ${list.size}", Toast.LENGTH_SHORT).show()
    }

    override fun navigate(route: NotificationsRoute) {
        when(route) {
            is CreateNotification -> onCreateNotification()
        }
    }

    override fun onStop() {
        presenter.onAction(OnStop())
        super.onStop()
    }

    private fun onCreateNotification() {

    }
}
