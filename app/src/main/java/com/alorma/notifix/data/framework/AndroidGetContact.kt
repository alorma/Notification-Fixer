package com.alorma.notifix.data.framework;

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import com.alorma.notifix.domain.model.Contact
import com.alorma.notifix.ui.utils.subscribeOnIO
import io.reactivex.Single
import javax.inject.Inject

class AndroidGetContact @Inject constructor(private val context: Context) {

    fun loadContact(contactUri: Uri): Single<Contact> {
        return Single.fromCallable {
            getContact(contactUri)
        }.subscribeOnIO()
    }

    private fun getContact(contactUri: Uri): Contact {
        val cursor = contentResolver().query(contactUri, null,
                null, null, null)

        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val phoneIndex = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA1)
            val photoIndex = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)

            val id = cursor.getString(idIndex)
            val name = cursor.getString(nameIndex)
            val phone = cursor.getString(phoneIndex)
            val photo = cursor.getString(photoIndex)

            cursor.close()

            return Contact(id, name, phone, photo)
        } else {
            throw NoSuchElementException()
        }
    }

    private fun contentResolver(): ContentResolver = context.contentResolver
}