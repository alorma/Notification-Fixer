package com.alorma.notifix.ui.features.trigger

import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.R
import com.alorma.notifix.ui.features.trigger.di.CreateTriggerModule
import com.alorma.notifix.ui.utils.GlideApp
import com.alorma.notifix.ui.utils.dsl
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.configure_number_activity.*
import javax.inject.Inject

class ConfigureNumberTriggerActivity : AppCompatActivity(), CreateTriggerView {

    companion object {
        private const val REQ_CONTACT_DIRECTORY = 110
    }

    @Inject
    lateinit var presenter: CreateTriggerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configure_number_activity)

        component add CreateTriggerModule(this) inject this

        presenter init this

        toolbar.dsl {
            back { action = { finish() } }
        }

        val colorDrawable = ColorDrawable(ContextCompat.getColor(this@ConfigureNumberTriggerActivity, R.color.fake_grey1))
        GlideApp.with(fakeUserAvatar)
                .load(colorDrawable)
                .fallback(colorDrawable)
                .transform(CircleCrop())
                .into(fakeUserAvatar)

        fakeUserSelectButton.setOnClickListener {
            presenter action RequestContactAction()
        }
        contactCard.setOnClickListener {
            presenter action RequestContactAction()
        }
    }

    override fun render(state: CreateTriggerState) {
        when (state) {
            is DeniedPermissionMessage -> Toast.makeText(this, "Will try later", Toast.LENGTH_SHORT).show()
            is DeniedAlwaysPermissionMessage -> Toast.makeText(this, " :( ", Toast.LENGTH_SHORT).show()
            is ContactLoaded -> onContactLoaded(state)
        }
    }

    override fun navigate(route: CreateTriggerRoute) {
        when (route) {
            is SelectContact -> openContactPicker()
        }
    }

    private fun openContactPicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = ContactsContract.Contacts.CONTENT_TYPE
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
            val colorDrawable = ColorDrawable(ContextCompat.getColor(this@ConfigureNumberTriggerActivity, R.color.fake_grey1))
            GlideApp.with(userAvatar)
                    .load(getContactPhoto(id))
                    .fallback(colorDrawable)
                    .transform(CircleCrop())
                    .into(userAvatar)

            userName.text = name
            userPhone.text = phone ?: getString(R.string.no_phone)
            userEmail.text = email ?: getString(R.string.no_email)
        }
    }

    private fun getContactPhoto(androidId: String): Uri {
        val contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, androidId.toLong())
        return Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
    }
}