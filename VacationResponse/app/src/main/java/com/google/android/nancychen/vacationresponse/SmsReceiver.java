package com.google.android.nancychen.vacationresponse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by nancychen on 1/30/15.
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // retrieve the default vacation response
        SharedPreferences pref = context.getSharedPreferences(
                VacationResponseActivity.VACATION_RESPONSE_PREFERENCE,
                Context.MODE_PRIVATE);

        Boolean enabled = pref.getBoolean(
                VacationResponseActivity.VACATION_RESPONSE_ENABLED, false);

        Log.i("VacationResponseActivity", "Checked: " + String.valueOf(enabled));
        if (!enabled) {
            return;
        }
        String response = pref.getString(VacationResponseActivity.SAVED_VACATION_RESPONSE, null);
        if (response == null) {
            return;
        }

        // First get a list of the SMS messages
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            // get sms objects
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus.length == 0) {
                return;
            }

            // large message might be broken into many but will have the same originating address.
            String sender = SmsMessage.createFromPdu((byte[]) pdus[0]).getOriginatingAddress();
            SmsManager.getDefault().sendTextMessage(sender, null, response, null, null);
        }
    }
}
