package com.alorma.notifix.ui.features.notifications

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.R
import com.alorma.notifix.background.notifications.NotificationsBootBroadcast
import com.alorma.notifix.ui.features.create.AddNotificationActivity
import com.alorma.notifix.ui.features.create.OnCreateSucces
import com.alorma.notifix.ui.utils.dsl
import com.mapbox.mapboxsdk.Mapbox
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NotificationsView {

    companion object {
        private const val REQUEST_CODE_CREATE: Int = 121
    }

    @Inject
    lateinit var presenter: NotificationsPresenter

    private lateinit var adapter: NotificationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component inject this
        Mapbox.getInstance(this, getString(R.string.MAPS_KEY))
        presenter.init(this)

        initNotificationsService()
        initToolbar()
        initAdapter()
    }

    private fun initNotificationsService() {
        val intent = Intent(this, NotificationsBootBroadcast::class.java)
        startService(intent)
    }

    private fun initToolbar() {
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

    private fun initAdapter() {
        adapter = NotificationsAdapter {
            presenter.updateNotification(this, it)
        }
        recycler.adapter = adapter
        adapter.hasStableIds()
        recycler.setHasFixedSize(true)
    }

    override fun render(state: NotificationsState) = when (state) {
        is ShowNotifications -> onNotificationsList(state.list)
        is Invalid -> showInvalid(state.it)
    }

    private fun onNotificationsList(list: List<NotificationViewModel>) {
        adapter.addAll(list)
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

    private fun showInvalid(it: Throwable) {
        Snackbar.make(recycler, "Error: $it", Snackbar.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CREATE && resultCode == Activity.RESULT_OK) {
            presenter action OnCreateSucces()
        }
    }
}
