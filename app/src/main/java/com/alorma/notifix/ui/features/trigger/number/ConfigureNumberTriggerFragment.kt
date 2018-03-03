package com.alorma.notifix.ui.features.trigger.number

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.R
import com.alorma.notifix.domain.model.Contact
import com.alorma.notifix.ui.features.trigger.di.CreateTriggerModule
import com.alorma.notifix.ui.utils.GlideApp
import com.alorma.notifix.ui.utils.toast
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.configure_number_fragment.*
import javax.inject.Inject

class ConfigureNumberTriggerFragment : DialogFragment(), CreateNumberTriggerView {

    companion object {
        private const val REQ_CONTACT_DIRECTORY = 110
    }

    @Inject
    lateinit var presenter: CreateNumberTriggerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            component add CreateTriggerModule(it) inject this
            presenter init this
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.configure_number_fragment, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            loadDefaultAvatar()

            fakeUserSelectButton.setOnClickListener {
                presenter action CreateNumberTriggerAction.RequestContactActionNumber()
            }
            contactCard.setOnClickListener {
                presenter action CreateNumberTriggerAction.RequestContactActionNumber()
            }
            userSelectButton.setOnClickListener {

            }
        }
    }

    private fun loadDefaultAvatar() {
        context?.let {
            val colorDrawable = ColorDrawable(ContextCompat.getColor(it, R.color.fake_grey1))
            GlideApp.with(fakeUserAvatar)
                    .load(colorDrawable)
                    .fallback(colorDrawable)
                    .transform(CircleCrop())
                    .into(fakeUserAvatar)
        }
    }

    override fun render(state: CreateNumberTriggerState) {
        when (state) {
            is CreateNumberTriggerState.DeniedPermissionMessage -> context.toast("Will try later")
            is CreateNumberTriggerState.DeniedAlwaysPermissionMessage -> context.toast(" :( ")
            is CreateNumberTriggerState.ContactLoaded -> onContactLoaded(state)
        }
    }

    override fun navigate(route: CreateNumberTriggerRoute) {
        when (route) {
            is CreateNumberTriggerRoute.SelectContact -> openContactPicker()
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
                            presenter action CreateNumberTriggerAction.ContactImportActionNumber(it)
                        }
                    }
                }
            }
        }
    }

    private fun onContactLoaded(stateNumber: CreateNumberTriggerState.ContactLoaded) {
        contactLayout.visibility = View.VISIBLE
        fakeContactLayout.visibility = View.GONE

        with(stateNumber.contact) {
            loadAvatar(this)
            userName.text = name
            userPhone.text = phone ?: getString(R.string.no_phone)
        }
    }

    private fun loadAvatar(contact: Contact) {
        context?.let {
            val colorDrawable = ColorDrawable(ContextCompat.getColor(it, R.color.fake_grey1))
            GlideApp.with(userAvatar)
                    .load(contact.photo)
                    .fallback(colorDrawable)
                    .transform(CircleCrop())
                    .into(userAvatar)
        }
    }

}