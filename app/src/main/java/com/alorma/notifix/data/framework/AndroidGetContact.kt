package com.alorma.notifix.data.framework;

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import com.alorma.notifix.domain.model.Contact
import io.reactivex.Single
import javax.inject.Inject

class AndroidGetContact @Inject constructor(private val context: Context) {

    fun loadContact(contactUri: Uri): Single<Contact> {
        return Single.fromCallable {
            getContact(contactUri)
        }
    }

    private fun getContact(contactUri: Uri): Contact {
        val cursor = contentResolver().query(contactUri, null,
                null, null, null)

        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

            val id = cursor.getString(idIndex)
            val name = cursor.getString(nameIndex)

            cursor.close()
            
            val email: String? = loadEmail(id)
            val phone: String? = loadPhone(id)

            return Contact(androidId = id,
                    name = name,
                    userEmail = email,
                    userPhone = phone)
        } else {
            throw NoSuchElementException()
        }
    }

    private fun loadEmail(id: String): String? {
        val cur1 = contentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                arrayOf(id), null, null)
        var email: String? = null
        if (cur1.moveToFirst()) {
            email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
        }

        cur1.close()
        return email
    }

    private fun loadPhone(id: String): String? {
        val cur1 = contentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                arrayOf(id), null, null)
        var phone: String? = null
        if (cur1.moveToFirst()) {
            phone = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA))
        }

        cur1.close()
        return phone
    }

    private fun contentResolver(): ContentResolver = context.contentResolver
}