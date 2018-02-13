package com.alorma.notifix.ui.features.notifications

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.R
import com.alorma.notifix.ui.features.create.AddNotificationActivity
import com.alorma.notifix.ui.features.create.OnCreateSucces
import com.alorma.notifix.ui.utils.dsl
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NotificationsView {

    companion object {
        private const val REQUEST_CODE_CREATE: Int = 121
    }

    @Inject
    lateinit var presenter: NotificationsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component inject this

        presenter.init(this)
        presenter attach this

        toolbar.dsl {
            menu = R.menu.notifications_menu
            item {
                id = R.id.action_add_notification
                action = {
                    presenter.onAddNotification()
                }
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
        when (route) {
            is CreateNotification -> onCreateNotification()
        }
    }

    private fun onCreateNotification() {
        val intent = Intent(this, AddNotificationActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_CREATE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CREATE && resultCode == Activity.RESULT_OK) {
            presenter onAction OnCreateSucces()
        }
    }
}
