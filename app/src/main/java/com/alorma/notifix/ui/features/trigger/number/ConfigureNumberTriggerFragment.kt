package com.alorma.notifix.ui.features.trigger.number

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.DialogFragment
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.ui.commons.Route
import com.alorma.notifix.ui.features.trigger.TriggerRoute
import com.alorma.notifix.ui.features.trigger.di.CreateTriggerModule
import com.alorma.notifix.ui.utils.toast
import javax.inject.Inject

class ConfigureNumberTriggerFragment : DialogFragment(), CreateNumberTriggerView {

    companion object {
        private const val REQ_CONTACT_DIRECTORY = 110
        val PHONE = Type.PHONE()
        val SMS = Type.SMS()
    }

    @Inject
    lateinit var presenter: CreateNumberTriggerPresenter

    lateinit var type: Type

    var callback: ((Long) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            component add CreateTriggerModule(it) inject this
            presenter init this
            presenter action CreateNumberTriggerAction.RequestContactAction(type)
        }
    }

    override fun render(state: CreateNumberTriggerState) {
        when (state) {
            is CreateNumberTriggerState.DeniedPermissionMessage -> context.toast("Will try later")
            is CreateNumberTriggerState.DeniedAlwaysPermissionMessage -> context.toast(" :( ")
        }
    }

    override fun navigate(route: Route) {
        when (route) {
            is CreateNumberTriggerRoute.SelectContactRoute -> openContactPicker()
            is TriggerRoute.Success -> {
                callback?.invoke(route.triggerId)
                dismiss()
            }
        }
    }

    private fun openContactPicker() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
        startActivityForResult(intent, REQ_CONTACT_DIRECTORY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CONTACT_DIRECTORY -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        data?.data?.let {
                            presenter action CreateNumberTriggerAction.ContactImportAction(it)
                        }
                    }
                }
            }
        }
    }
}