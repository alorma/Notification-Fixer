package com.alorma.notifix.ui.features.trigger

import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
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
import kotlinx.android.synthetic.main.configure_number_activity.*
import javax.inject.Inject

class ConfigureNumberTriggerFragment : DialogFragment(), CreateTriggerView {

    companion object {
        private const val REQ_CONTACT_DIRECTORY = 110
    }

    @Inject
    lateinit var presenter: CreateTriggerPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.configure_number_activity, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            component add CreateTriggerModule(it) inject this

            presenter init this

            loadDefaultAvatar()

            fakeUserSelectButton.setOnClickListener {
                presenter action RequestContactAction()
            }
            contactCard.setOnClickListener {
                presenter action RequestContactAction()
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

    override fun render(state: CreateTriggerState) {
        when (state) {
            is DeniedPermissionMessage -> context.toast("Will try later")
            is DeniedAlwaysPermissionMessage -> context.toast(" :( ")
            is ContactLoaded -> onContactLoaded(state)
        }
    }

    override fun navigate(route: CreateTriggerRoute) {
        when (route) {
            is SelectContact -> openContactPicker()
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
                            presenter action ContactImportAction(it)
                        }
                    }
                }
            }
        }
    }

    private fun onContactLoaded(state: ContactLoaded) {
        contactLayout.visibility = View.VISIBLE
        fakeContactLayout.visibility = View.GONE

        with(state.contact) {
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

    private fun getContactPhoto(androidId: String): Uri {
        val contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, androidId.toLong())
        return Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
    }
}