package com.alorma.notifix.ui.features.trigger

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.alorma.notifix.R
import com.alorma.notifix.ui.grantContactsPermission
import com.alorma.notifix.ui.utils.dsl
import kotlinx.android.synthetic.main.configure_number_activity.*

abstract class ConfigureNumberTriggerActivity : AppCompatActivity() {


    companion object {
        private const val REQ_CONTACT_DIRECTORY = 110
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configure_number_activity)

        toolbar.dsl {
            title = getScreenTitle()
            back { action = { finish() } }
        }


        selectContact.setOnClickListener {
            grantContactsPermission(
                    onPermissionsGranted = {
                        openContactPicker()
                    },
                    onPermissionDenied = { isPermanent ->
                        if (isPermanent) {
                            Toast.makeText(this@ConfigureNumberTriggerActivity, " :( ", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@ConfigureNumberTriggerActivity, " Will try again ", Toast.LENGTH_SHORT).show()
                        }
                    }
            )
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
                        val uri = data?.data
                      
                    }
                    else -> {

                    }
                }
            }
        }
    }


    abstract fun triggerType(): TriggerType

    abstract fun getScreenTitle(): Int
}