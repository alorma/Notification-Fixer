package com.alorma.notifix.data.framework.triggers

import android.content.Context
import com.alorma.notifix.R
import com.alorma.notifix.domain.model.Trigger
import io.reactivex.Single
import javax.inject.Inject

class DefaultTriggersDataSource @Inject constructor(private val context: Context)
    : TriggersDataSource {
    override fun getTriggers(): Single<List<Trigger>> = Single.fromCallable {
        mutableListOf<Trigger>().apply {
            add(Trigger(context.getString(R.string.trigger_SMS), R.drawable.ic_sms))
            add(Trigger(context.getString(R.string.trigger_PHONE), R.drawable.ic_phone_in_talk))
            add(Trigger(context.getString(R.string.trigger_TIME), R.drawable.ic_av_timer))
            add(Trigger(context.getString(R.string.trigger_ZONE), R.drawable.ic_location))
        }.toList()
    }
}