package com.google.android.nancychen.vacationresponse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

/**
 * Created by nancychen on 1/30/15.
 */
public class SmsReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        // Get a list of incoming messages
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        // retrieve the default vacation response
        SharedPreferences pref = context.getSharedPreferences(
                VacationResponseActivity.VACATION_RESPONSE_PREFERENCE,
                Context.MODE_PRIVATE);
        String response = pref.getString(VacationResponseActivity.SAVED_VACATION_RESPONSE, null);
        if (response != null) {
            for (SmsMessage message : messages) {
                String sender = message.getOriginatingAddress();
                SmsManager.getDefault().sendTextMessage(sender, null, response, null, null);
            }
        }
    }
}
