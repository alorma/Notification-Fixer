package com.alorma.notifix.background.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.data.Logger
import com.alorma.notifix.domain.usecase.ShowNotificationsUseCase
import com.alorma.notifix.ui.utils.observeOnUI
import com.alorma.notifix.ui.utils.plusAssign
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NotificationsBootBroadcast : BroadcastReceiver() {
    @Inject
    lateinit var useCase: ShowNotificationsUseCase

    @Inject
    lateinit var logger: Logger

    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            component inject this
            disposable += useCase.execute()
                    .observeOnUI()
                    .subscribe({}, {})
        }
    }
}