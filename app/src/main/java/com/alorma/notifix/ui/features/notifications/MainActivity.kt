package com.alorma.notifix.ui.features.notifications

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alorma.notifix.R
import com.alorma.notifix.ui.commons.OnCreate

class MainActivity : AppCompatActivity(), NotificationsView {

    private val presenter: NotificationsPresenter by lazy { NotificationsPresenter(NotificationsMapper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
